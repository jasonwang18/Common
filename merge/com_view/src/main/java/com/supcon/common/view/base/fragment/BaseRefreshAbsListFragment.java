package com.supcon.common.view.base.fragment;

import com.supcon.common.view.R;
import com.supcon.common.view.base.controller.IRefreshListController;
import com.supcon.common.view.base.controller.RefreshAbsListController;
import com.supcon.common.view.base.presenter.BasePresenter;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseRefreshAbsListFragment<TModel> extends BaseRefreshListFragment<TModel> {


    @Override
    protected IRefreshListController createRefreshListController() {

        refreshListController = new RefreshAbsListController<>(getActivity(), rootView.findViewById(R.id.refreshFrameLayout), createAdapter());

        return refreshListController;
    }

}
