package com.supcon.common.view.base.activity;

import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.base.controller.IRefreshController;
import com.supcon.common.view.base.controller.IRefreshListController;
import com.supcon.common.view.base.presenter.BasePresenter;
import com.supcon.common.view.listener.OnRefreshListener;
import com.supcon.common.view.listener.OnRefreshPageListener;

/**
 * 下拉与加载更多列表Activity
 * Created by wangshizhan on 16/3/31.
 */
public abstract class BaseRefreshListActivity<TModel> extends BaseControllerActivity {

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

    /**
     * 不需分页刷新
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        refreshListController.setOnRefreshListener(onRefreshListener);
    }

    /**
     * 创建适配器
     * @return
     */
    protected abstract IListAdapter<TModel> createAdapter();
}
