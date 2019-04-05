package com.supcon.common.view.base.controller;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.BaseListViewAdapter;
import com.supcon.common.view.base.adapter.IListAdapter;

/**
 * Created by wangshizhan on 17/6/30.
 */

public class RefreshAbsListController<TModel> extends RefreshListController<AbsListView,TModel> {

    protected AbsListView.OnScrollListener onScrollListener;
    protected IListAdapter<TModel> emptyAdapter;

    public RefreshAbsListController(Context context, View view, IListAdapter<TModel> listAdapter) {
        super(context, view, listAdapter);
    }


    @Override
    public void initData() {
        this.setAbsListViewAdapter(listAdapter);
        super.initData();
    }


    private void setAbsListViewAdapter(IListAdapter adapter) {
        if( getContentView() instanceof ExpandableListView) {
            ((ExpandableListView) getContentView()).setAdapter((ExpandableListAdapter)adapter);
        }
        else
            getContentView().setAdapter((ListAdapter) adapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        getContentView().setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null)
                    onScrollListener.onScrollStateChanged(view, scrollState);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                        if (isLoadMoreEnable)
                            refreshMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null)
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }


    /**
     * 监听AbsListView 滚动及位置判断是否自动加载更多
     * @param enableLoadMore
     */
    @Override
    public void setLoadMoreEnable(boolean enableLoadMore) {
        super.setLoadMoreEnable(enableLoadMore);
        if( enableLoadMore ) {
            if( getContentView() instanceof ListView) {
                ((ListView)getContentView()).addFooterView(mDefaultMoreViewController.getMoreView());
            }
        }
        else {
            if( getContentView() instanceof ListView) {
                ((ListView)getContentView()).removeFooterView(mDefaultMoreViewController.getMoreView());
            }
        }
    }

    @Override
    public void setEmpterAdapter(IListAdapter emptyAdapter) {
        this.emptyAdapter = emptyAdapter;
    }


    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void switchAdapter() {
        if (emptyAdapter != null && (listAdapter.getList() == null || listAdapter.getList().size() == 0)) {
            getContentView().setAdapter((ListAdapter) emptyAdapter);
        }
        else if( getContentView().getAdapter() != listAdapter) {
            getContentView().setAdapter((ListAdapter)listAdapter);
        }

    }

}
