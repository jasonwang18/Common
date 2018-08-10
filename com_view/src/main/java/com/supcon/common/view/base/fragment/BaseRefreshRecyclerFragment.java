package com.supcon.common.view.base.fragment;

import com.supcon.common.view.R;
import com.supcon.common.view.base.controller.IRefreshListController;
import com.supcon.common.view.base.controller.RefreshRecyclerController;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseRefreshRecyclerFragment<TModel> extends BaseRefreshListFragment<TModel> {

    @Override
    protected IRefreshListController createRefreshListController() {

        refreshListController = new RefreshRecyclerController<>(getActivity(), rootView.findViewById(R.id.refreshFrameLayout), createAdapter());

        return refreshListController;
    }

}
