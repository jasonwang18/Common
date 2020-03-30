package com.supcon.common.controller;

import android.view.View;

import com.supcon.common.model.bean.TestEntity;
import com.supcon.common.model.contract.SystemCodeContract;
import com.supcon.common.view.base.controller.BaseViewController;
import com.supcon.common.view.util.LogUtil;

/**
 * Created by wangshizhan on 2018/10/19
 * Email:wangshizhan@supcom.com
 */
public class TestViewController extends BaseViewController implements SystemCodeContract.View{
    public TestViewController(View rootView) {
        super(rootView);
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
