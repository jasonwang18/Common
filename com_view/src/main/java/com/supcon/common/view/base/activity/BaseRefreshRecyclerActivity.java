package com.supcon.common.view.base.activity;

import com.supcon.common.view.R;
import com.supcon.common.view.base.controller.IRefreshListController;
import com.supcon.common.view.base.controller.RefreshRecyclerController;
import com.supcon.common.view.base.presenter.BasePresenter;

/**
 * 下拉与加载更多Recycler Activity
 * Created by wangshizhan on 16/3/31.
 */
public abstract class BaseRefreshRecyclerActivity<TModel> extends BaseRefreshListActivity<TModel> {

    @Override
    protected IRefreshListController createRefreshListController() {

        refreshListController = new RefreshRecyclerController<>(this, findViewById(R.id.refreshFrameLayout), createAdapter());

        return refreshListController;
    }

}
