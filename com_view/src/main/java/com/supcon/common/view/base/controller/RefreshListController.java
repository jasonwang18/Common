package com.supcon.common.view.base.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.supcon.common.view.R;
import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.base.view.BaseRelativeLayout;
import com.supcon.common.view.listener.OnRefreshPageListener;

import java.util.List;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class RefreshListController<TView extends View, TModel> extends RefreshController<TView> implements IRefreshListController<TModel>{
    protected int pageIndex = 0;
    protected boolean isRefresh = false;
    protected boolean isMoreData = true;
    protected boolean isLoadMoreEnable = false;
    protected IListAdapter<TModel> listAdapter;
    protected OnRefreshPageListener onRefreshPageListener;
    protected DefaultMoreViewController mDefaultMoreViewController;
    protected final static int MoreStatus_None = 1000;
    protected final static int MoreStatus_Load = MoreStatus_None + 1;
    protected final static int MoreStatus_Error = MoreStatus_Load + 1;
    protected final static int MoreStatus_Complete = MoreStatus_Error + 1;
    protected final static int MoreStatus_NoMoreData = MoreStatus_Complete + 1;
    protected final static int MoreStatus_NoMoreData2 = MoreStatus_NoMoreData + 1;


    public RefreshListController(Context context, View layout, IListAdapter<TModel> listAdapter) {
        this(context, layout);
        this.listAdapter = listAdapter;
    }


    @Override
    public IListAdapter<TModel> getListAdapter() {
        return listAdapter;
    }

    /**
     * 设置分页加载监听
     *
     * @param onRefreshPageListener
     */
    @Override
    public void setOnRefreshPageListener(OnRefreshPageListener onRefreshPageListener) {
        this.onRefreshPageListener = onRefreshPageListener;
        setLoadMoreEnable(true);
    }

    /**
     * 设置是否启用下来刷新 目前自己根据设置的监听识别
     *
     * @param loadMoreEnable
     */
    @Override
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        isLoadMoreEnable = loadMoreEnable;
        if (isLoadMoreEnable && mDefaultMoreViewController == null) {
            mDefaultMoreViewController = new DefaultMoreViewController(mContext);
            mDefaultMoreViewController.getMoreView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isRefresh && mDefaultMoreViewController.status != MoreStatus_NoMoreData) {
                        refreshMore();
                    }
                }
            });
        }
    }

    @Override
    public void setDefaultMoreViewController(DefaultMoreViewController defaultMoreViewController) {
        this.mDefaultMoreViewController = defaultMoreViewController;
        defaultMoreViewController.getMoreView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRefresh && mDefaultMoreViewController.status != MoreStatus_NoMoreData) {
                    refreshMore();
                }
            }
        });
    }

    @Override
    public void setListAdapter(IListAdapter listAdapter) {
        this.listAdapter = listAdapter;
    }

    public RefreshListController(Context context, View layout) {
        super(context, layout);
    }


    /**
     * 下接刷新
     */
    @Override
    public void onRefreshBegin() {
        if (!isRefresh) {
            pageIndex = 0;
            if (isLoadMoreEnable) {
                isMoreData = true;
                setMoreStatus(MoreStatus_None);
                onRefresh(pageIndex + 1);
            } else {
                if(onRefreshPageListener != null)
                    onRefreshPageListener.onRefresh(pageIndex+1);
                else
                    super.onRefreshBegin();
            }
        }
    }

    /***
     * 是否还有更多数据加载
     *
     * @return
     */
    protected boolean isMoreData() {
        return isMoreData;
    }

    /**
     * 加载更多
     */
    protected void refreshMore() {
        if (!isRefresh && isLoadMoreEnable && isMoreData()) {
            setPullDownRefreshEnabled(false);
            setMoreStatus(MoreStatus_Load);
            onRefresh(pageIndex + 1);
        }
    }

    /**
     * 开始加载按页加载数据
     *
     * @param pageIndex
     */
    private void onRefresh(int pageIndex) {
        if (onRefreshPageListener != null) {
            onRefreshPageListener.onRefresh(pageIndex);
            isRefresh = true;
        }
    }

    /**
     * 设置加载更多view状态
     *
     * @param status
     */
    protected void setMoreStatus(int status) {
        setPullDownRefreshEnabled(true);
        if (MoreStatus_NoMoreData == status) {
            isMoreData = false;
        }
        isRefresh = false;
        try {
            if (mDefaultMoreViewController != null)
                mDefaultMoreViewController.setStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
            mDefaultMoreViewController.setStatus(0);
        }

    }

    /**
     * 下接刷新完成
     */
    @Override
    public void refreshComplete() {
        super.refreshComplete();
        isRefresh = false;
//        setPullDownRefreshEnabled(true);
    }

    private boolean isEmptyList(List<?> list) {
        return list == null || list.size() == 0;
    }

    /**
     * 数据加载完成时调用
     *
     * @param list
     */
    @Override
    public void refreshComplete(List list) {
        ++pageIndex;//页码计数加1
        if (listAdapter != null) {
            if (pageIndex == 1 || !isLoadMoreEnable) {
                listAdapter.setList(list);
                switchAdapter();
                refreshComplete();
                listAdapter.notifyDataSetChanged();
            } else if (isEmptyList(list)) {
                setMoreStatus(MoreStatus_NoMoreData);
            } else {
                setMoreStatus(MoreStatus_Complete);
                listAdapter.addList(list);
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 数据加载失败时调用
     *
     * @param ex
     */
    public void refreshError(Throwable ex) {

        if (pageIndex == 0) {
            refreshComplete();
        } else { //加载更多时出错
            setMoreStatus(MoreStatus_Error);
        }
    }

    /**
     * 加载更多View控制器
     */
    public static class DefaultMoreViewController extends BaseRelativeLayout{
        private ProgressBar progressBar;
        private TextView textView;
        private int status;

        public DefaultMoreViewController(Context context) {
            super(context);
        }

        public DefaultMoreViewController(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        @Override
        protected void initView() {
            super.initView();
            rootView.setVisibility(View.GONE);
            progressBar =  rootView.findViewById(R.id.progressBar);
            textView =  rootView.findViewById(R.id.textView);
        }

        public View getMoreView() {
            return this;
        }

        public void setStatus(int status) {
            rootView.setVisibility(View.VISIBLE);
            this.status = status;
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            switch (status) {
                case MoreStatus_Load:
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setText("加载中...");
                    break;
                case MoreStatus_Error:
                    textView.setText("加载更多");
                    break;
                case MoreStatus_NoMoreData:
                    textView.setText("没有更多了");
                    break;
                case MoreStatus_NoMoreData2:
                    textView.setVisibility(View.GONE);
                    break;
                case MoreStatus_Complete:
                    textView.setVisibility(View.GONE);
                    break;
                default:
                    textView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected int layoutId() {
            return R.layout.item_loadmore;
        }
    }

    public boolean isLoadMoreEnable() {
        return isLoadMoreEnable;
    }


    /**
     * 刷新适配器 检测是否空数据来不同的适配器
     */
    protected void switchAdapter(){

    }


}
