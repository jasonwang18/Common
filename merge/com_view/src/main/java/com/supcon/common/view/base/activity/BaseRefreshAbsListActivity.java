package com.supcon.common.view.base.activity;

import com.supcon.common.view.R;
import com.supcon.common.view.base.controller.IRefreshListController;
import com.supcon.common.view.base.controller.RefreshAbsListController;

/**
 * Created by wangshizhan on 16/3/31.
 */
public abstract class BaseRefreshAbsListActivity<TModel> extends BaseRefreshListActivity<TModel> {

    @Override
    protected IRefreshListController createRefreshListController() {

        refreshListController = new RefreshAbsListController<>(this, findViewById(R.id.refreshFrameLayout), createAdapter());

        return refreshListController;
    }

}
