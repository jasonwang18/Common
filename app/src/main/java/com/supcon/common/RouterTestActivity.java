package com.supcon.common;

import android.view.View;

import com.app.annotation.BindByTag;
import com.app.annotation.Controller;
import com.app.annotation.apt.Router;
import com.app.annotation.custom.OnChild;
import com.app.annotation.custom.OnDateChange;
import com.app.annotation.custom.OnItemSelect;
import com.app.annotation.custom.OnTextChange;
import com.app.annotation.javassist.Bus;
import com.supcon.common.controller.TestController;
import com.supcon.common.controller.TestViewController;
import com.supcon.common.model.bean.TestEntity;
import com.supcon.common.ui.BaseTestActivity;
import com.supcon.common.ui.view.CustomView;
import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.base.activity.BaseControllerActivity;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.LogUtils;

import static com.app.annotation.custom.OnTextChange.DEFAULT;
import static com.app.annotation.custom.OnTextChange.LONG;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */
@Router("routerTest")
@Controller(value = {TestController.class, TestViewController.class})
public class RouterTestActivity extends BaseTestActivity{


//    private String[] values = null;
//    private TestEntity mTestEntity = null;
//    @BindByTag("customView")
//    CustomView customView;
//
//    @BindByTag("customView2")
//    @OnTextChange(param = "mTestEntity.textChangeResult")
////    @OnItemSelect(values = "values", param = "mTestEntity.itemResult", cancelOutsideEnable = true, cycleEnable = true, dividerVisble = true)
//    @OnDateChange(param = "mTestEntity.date2")
//    CustomView customView2;



    @Override
    protected int getLayoutID() {
        return R.layout.activity_router_test;
    }

    @Override
    protected void onInit() {
        super.onInit();
        mTestEntity = new TestEntity();
    }

    @Override
    protected void initView() {
        super.initView();
        values = new String[]{"1","2","3","4", "5","6","7","8","9"};

    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Bus(0x0000)
    public void onLogin(){

    }


}
