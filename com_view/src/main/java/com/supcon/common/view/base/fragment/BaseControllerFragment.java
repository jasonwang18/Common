package com.supcon.common.view.base.fragment;

import android.text.TextUtils;

import com.supcon.common.view.Lifecycle;
import com.supcon.common.view.LifecycleManage;
import com.supcon.common.view.base.presenter.BasePresenter;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseControllerFragment extends BasePresenterFragment {
    protected LifecycleManage controllers = new LifecycleManage();

    /**
     * 注册控制器
     *
     * @param key        控制器key
     * @param controller
     */
    protected void registerController(String key, Lifecycle controller) {
        if (!TextUtils.isEmpty(key) && controller != null) {
            controllers.register(key, controller);
        }
    }

    protected  void registerController(Lifecycle controller) {
        if( controller != null )
        {
            registerController(controller.getClass().getSimpleName(),controller);
        }
    }

    /**
     * 获取注册的控制器
     *
     * @param key
     * @return
     */
    public Lifecycle getController(String key) {
        return  controllers.get(key);
    }

    @Override
    protected void onInit() {
        super.onInit();
        onRegisterController();
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
    public void onPause() {
        super.onPause();
        controllers.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();
        controllers.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        controllers.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
        controllers.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controllers.onDestroy();
    }

    public void onRetry(){

        controllers.onRetry();

    }
}
