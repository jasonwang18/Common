package com.supcon.common.view.base.fragment;

import com.supcon.common.view.R;
import com.supcon.common.view.base.controller.IRefreshController;
import com.supcon.common.view.base.controller.RefreshController;
import com.supcon.common.view.listener.OnRefreshListener;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract  class BaseRefreshFragment extends BaseControllerFragment {

    private static final String _RefreshController = "RefreshController";

    protected IRefreshController refreshController;

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        registerController(_RefreshController, createRefreshController());
    }

    protected IRefreshController createRefreshController() {

        refreshController = new RefreshController(getActivity(), rootView.findViewById(R.id.refreshFrameLayout));

        return refreshController;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        refreshController.setOnRefreshListener(onRefreshListener);
    }

}
