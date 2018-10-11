package com.supcon.common.view.base.activity;

import android.text.TextUtils;

import com.supcon.common.view.Lifecycle;
import com.supcon.common.view.LifecycleManage;
import com.supcon.common.view.base.presenter.BasePresenter;


/**
 * Created by wangshizhan on 16/12/12.
 */
public abstract class BaseControllerActivity extends BasePresenterActivity {

    protected LifecycleManage controllers = new LifecycleManage();

    /**
     * 注册控制器
     *
     * @param key        控制器key
     * @param controller 控制器
     */
    protected void registerController(String key, Lifecycle controller) {
        if (!TextUtils.isEmpty(key) && controller != null) {
            controllers.register(key, controller);

        }
    }

    /**
     * 获取注册的控制器
     *
     * @param key key
     * @return 注册器
     */
    public Lifecycle  getController(String key) {
        return controllers.get(key);
    }

    @Override
    protected void onInit() {
        super.onInit();
        onRegisterController();
        controllers.onInit();
    }


    /**
     * 开始注册控制器
     */
    protected void onRegisterController() {
    }


    @Override
    protected void initView() {
        super.initView();
        controllers.initView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        controllers.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
        controllers.initData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        controllers.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();
        controllers.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        controllers.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        controllers.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controllers.onDestroy();
    }

    public void onRetry(){
        controllers.onRetry();

    }
}
