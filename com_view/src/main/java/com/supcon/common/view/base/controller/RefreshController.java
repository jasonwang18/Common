package com.supcon.common.view.base.controller;

import android.content.Context;
import android.view.View;

import com.supcon.common.view.R;
import com.supcon.common.view.listener.OnRefreshListener;
import com.supcon.common.view.ptr.PtrClassicDefaultHeader;
import com.supcon.common.view.ptr.PtrDefaultHandler;
import com.supcon.common.view.ptr.PtrFrameLayout;
import com.supcon.common.view.ptr.PtrHandler;
import com.supcon.common.view.ptr.header.MaterialHeader;
import com.supcon.common.view.util.DisplayUtil;

/**
 * Created by wangshizhan on 16/12/1.
 */
public class RefreshController<TContentView extends View>  implements PtrHandler, IRefreshController {

    protected PtrFrameLayout refreshFrameLayout;
    protected TContentView contentView;
    private OnRefreshListener onRefreshListener;
    private boolean isAutoPullDownRefresh = true;
    protected Context mContext;


    /**
     * 设置下拉刷新监听
     *
     * @param onRefreshListener
     */
    @Override
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public RefreshController(Context context, View view) {
        this.mContext = context;
        refreshFrameLayout = (PtrFrameLayout) view;
        contentView = (TContentView) refreshFrameLayout.findViewById(R.id.contentView);

    }


    /**
     * 设置是否启用下拉刷新
     *
     * @param enabled
     */
    @Override
    public void setPullDownRefreshEnabled(boolean enabled) {
        refreshFrameLayout.setEnabled(enabled);
    }

    /**
     * 设置是否生命周期initData自动下拉刷新
     *
     * @param autoPullDownRefresh
     */
    @Override
    public void setAutoPullDownRefresh(boolean autoPullDownRefresh) {
        isAutoPullDownRefresh = autoPullDownRefresh;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void initView() {

        if (refreshFrameLayout != null) {
            refreshFrameLayout.setHeaderView(getHeaderView());
            refreshFrameLayout.setPtrHandler(this);
            refreshFrameLayout.setResistance(1.7f);
            refreshFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
            refreshFrameLayout.setDurationToClose(300);
            refreshFrameLayout.setDurationToCloseHeader(300);
            refreshFrameLayout.setPullToRefresh(false);
            refreshFrameLayout.setKeepHeaderWhenRefresh(true);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        if (isAutoPullDownRefresh) {
            refreshBegin();
        }
    }

    /**
     * check刷新位置
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, contentView, header);
    }

    /**
     * 开发刷新
     */
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        onRefreshBegin();
    }

    /**
     * 开发刷新
     */
    protected void onRefreshBegin() {
        if (onRefreshListener != null)
            onRefreshListener.onRefresh();
    }

    /**
     * 手动调用下拉刷新
     */
    @Override
    public void refreshBegin() {
        if (refreshFrameLayout != null) {
            refreshFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshFrameLayout.autoRefresh(true, 500);
                }
            }, 200);
        }

    }

    /**
     * 刷新完成
     */
    @Override
    public void refreshComplete() {
        if (refreshFrameLayout != null) {
            refreshFrameLayout.refreshComplete();
        }
    }


    /**
     * 刷新错误
     *
     * @param ex
     */
    public void refreshError(Throwable ex) {
        refreshComplete();
    }

    private PtrClassicDefaultHeader mPtrClassicHeader;

    /**
     * 下接刷新样式view
     *
     * @return
     */
    protected View getHeaderView() {
        MaterialHeader header = new MaterialHeader(mContext);
        int colors[] = mContext.getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DisplayUtil.dip2px(5, mContext), 0, DisplayUtil.dip2px(5, mContext));
        header.setPtrFrameLayout(refreshFrameLayout);
        refreshFrameLayout.addPtrUIHandler(header);
        refreshFrameLayout.setPinContent(true);

/*        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(mContext);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, DisplayUtil.dip2px(50, mContext)));
        refreshFrameLayout.addPtrUIHandler(header);*/
        return header;
    }

    /**
     * 设置下拉视图header
     * @param header
     */
    public void setHeader(View header){
        refreshFrameLayout.setHeaderView(header);
    }


    /**
     * 设置下拉视图是否覆盖内容
     * @param isPinContent
     */
    public void setPinContent(boolean isPinContent){
        refreshFrameLayout.setPinContent(isPinContent);
    }

    /**
     * 下接刷新显示的ContentView
     *
     * @return
     */
    public TContentView getContentView() {
        return contentView;
    }


    /**
     * activity 或 fragment onStart
     */
    public void onStart() {

    }

    /**
     * activity 或 fragment onStop
     */
    public void onStop() {

    }

    /**
     * activity 或 fragment onResume
     */
    public void onResume() {

    }

    /**
     * activity 或 fragment onPause
     */
    public void onPause() {
    }

    /**
     * activity 或 fragment onDestroy
     */
    public void onDestroy() {

    }

    /**
     * activity 或 fragment onRetry
     */
    public void onRetry() {

    }
}
