package com.supcon.common.view.base.fragment;

import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.base.controller.IRefreshListController;
import com.supcon.common.view.listener.OnRefreshListener;
import com.supcon.common.view.listener.OnRefreshPageListener;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract  class BaseRefreshListFragment<TModel> extends BaseControllerFragment {

    protected IRefreshListController<TModel> refreshListController;

    private static final String _RefreshListController = "RefreshListController";


    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        registerController(_RefreshListController, createRefreshListController());
    }

    abstract protected IRefreshListController createRefreshListController();
    /**
     * 设置分页刷新监听 自动启用分页模式
     * @param onRefreshPageListener
     */
    public void setOnRefreshPageListener(OnRefreshPageListener onRefreshPageListener) {
        refreshListController.setOnRefreshPageListener(onRefreshPageListener);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        refreshListController.setOnRefreshListener(onRefreshListener);
    }

    protected abstract IListAdapter<TModel> createAdapter();
}
