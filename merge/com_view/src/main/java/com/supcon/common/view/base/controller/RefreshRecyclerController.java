package com.supcon.common.view.base.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.IListAdapter;


/**
 * Created by wangshizhan on 16/12/1.
 */
public class RefreshRecyclerController<TModel> extends RefreshListController<RecyclerView, TModel> {

    protected RecyclerView.OnScrollListener onScrollListener;

    protected IListAdapter<TModel> emptyAdapter;

    public RefreshRecyclerController(Context context, View view) {
        this(context, view, null);
    }

    public RefreshRecyclerController(Context context, View view, IListAdapter<TModel> listAdapter) {
        super(context, view, listAdapter);
    }

    @Override
    public BaseListDataRecyclerViewAdapter<TModel> getListAdapter() {
        return (BaseListDataRecyclerViewAdapter<TModel>) listAdapter;
    }


    @Override
    public void onInit() {
        super.onInit();
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        getContentView().setAdapter(getListAdapter());
        super.initData();
    }


    /**
     * 设置加载更多模式
     *
     * @param enableLoadMore
     */
    @Override
    public void setLoadMoreEnable(boolean enableLoadMore) {
        super.setLoadMoreEnable(enableLoadMore);
        if (enableLoadMore) {
            getListAdapter().setLoadMoreView(mDefaultMoreViewController.getMoreView());
        } else {
            getListAdapter().setLoadMoreView(null);
        }
    }

    @Override
    public void initListener() {
        super.initListener();

//        final PauseOnScrollListener pauseOnScrollListener =  new PauseOnScrollListener(ImageLoader.getInstance(), true, true);
        getContentView().setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (onScrollListener != null)
                    onScrollListener.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        ImageLoader.getInstance().resume();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        ImageLoader.getInstance().pause();
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//                       ImageLoader.getInstance().pause();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollListener != null)
                    onScrollListener.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void switchAdapter() {
        if (emptyAdapter != null && (listAdapter.getList() == null || listAdapter.getList().size() == 0)) {
            getContentView().setAdapter((BaseListDataRecyclerViewAdapter<TModel>) emptyAdapter);
        }
        else if( getContentView().getAdapter() != listAdapter) {
            getContentView().setAdapter(getListAdapter());
        }
    }

    @Override
    public void setEmpterAdapter(IListAdapter emptyAdapter) {
        this.emptyAdapter = emptyAdapter;
    }
}
