package com.supcon.common.view.base.activity;


import com.supcon.common.view.R;
import com.supcon.common.view.base.controller.IRefreshController;
import com.supcon.common.view.base.controller.RefreshController;
import com.supcon.common.view.base.presenter.BasePresenter;
import com.supcon.common.view.listener.OnRefreshListener;
import com.supcon.common.view.view.loader.base.LoaderController;

/**
 * 下新刷新任何view Activity
 * Created by wangshizhan on 16/3/31.
 */
public abstract  class BaseRefreshActivity extends BaseControllerActivity {

    private static final String _RefreshController = "RefreshController";

    protected IRefreshController refreshController;

    @Override
    protected void onRegisterController() {
        super.onRegisterController();
        registerController(_RefreshController, createRefreshController());
    }

    protected IRefreshController createRefreshController() {

        refreshController = new RefreshController(this, findViewById(R.id.refreshFrameLayout));

        return refreshController;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        refreshController.setOnRefreshListener(onRefreshListener);
    }

}
