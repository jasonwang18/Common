package com.supcon.common.view.base.controller;

import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.listener.OnRefreshPageListener;

import java.util.List;

/**
 * Created by wangshizhan on 17/6/29.
 */

public  interface IRefreshListController<T> extends IRefreshController {

    void setOnRefreshPageListener(OnRefreshPageListener onRefreshPageListener);

    void setLoadMoreEnable(boolean loadMoreEnable);

    IListAdapter<T> getListAdapter();

    void setListAdapter(IListAdapter<T> listAdapter);

    void refreshComplete(List<T> list);

    void setDefaultMoreViewController(RefreshListController.DefaultMoreViewController defaultMoreViewController);

    void setEmpterAdapter(IListAdapter emptyAdapter);
}
