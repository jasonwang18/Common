package com.supcon.common.controller;

import android.content.Context;

import com.app.annotation.Presenter;
import com.supcon.common.model.bean.TestEntity;
import com.supcon.common.model.contract.SystemCodeContract;
import com.supcon.common.presenter.SystemCodePresenter;
import com.supcon.common.view.base.controller.BaseDataController;
import com.supcon.common.view.util.LogUtil;

/**
 * Created by wangshizhan on 2018/10/19
 * Email:wangshizhan@supcom.com
 */
@Presenter(SystemCodePresenter.class)
public class TestController extends BaseDataController implements SystemCodeContract.View{

    public TestController(Context context) {
        super(context);
    }

    @Override
    public void onInit() {
        super.onInit();
        LogUtil.w("onInit");
        LogUtil.w("context "+context);
    }

    @Override
    public void getSystemCodeListSuccess(String entity) {

    }

    @Override
    public void getSystemCodeListFailed(String errorMsg) {

    }

    @Override
    public void getNullEntitySuccess() {

    }

    @Override
    public void getNullEntityFailed(String errorMsg) {

    }

    @Override
    public void getTestEntitySuccess(TestEntity entity) {

    }

    @Override
    public void getTestEntityFailed(String errorMsg) {

    }
}
