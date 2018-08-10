package com.supcon.common;

import com.app.annotation.apt.Router;
import com.supcon.common.view.base.activity.BaseActivity;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */
@Router("routerTest")
public class RouterTestActivity extends BaseActivity{


    @Override
    protected int getLayoutID() {
        return R.layout.activity_router_test;
    }

    @Override
    protected void onInit() {
        super.onInit();

    }
}
