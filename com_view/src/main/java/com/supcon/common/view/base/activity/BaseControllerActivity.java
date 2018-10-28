package com.supcon.common.view.base.activity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.app.annotation.Controller;
import com.supcon.common.view.Lifecycle;
import com.supcon.common.view.LifecycleManage;
import com.supcon.common.view.util.LogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


/**
 * Created by wangshizhan on 16/12/12.
 */
public abstract class BaseControllerActivity extends BasePresenterActivity {

    protected LifecycleManage controllers = new LifecycleManage();
    
    protected void initControllers() {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation:annotations){

            if(annotation instanceof Controller){
                Class[] controllerClasses = ((Controller) annotation).value();

                for(Class controller : controllerClasses){

                    try {
                        Constructor constructor = null;
                        Lifecycle baseController = null;
                        if(controller.getSuperclass().getSimpleName().equals("BasePresenterController")){
                            constructor = controller.getConstructor();
                            baseController = (Lifecycle) constructor.newInstance();
                        }
                        else if(controller.getSuperclass().getSimpleName().equals("BaseDataController")) {
                            constructor = controller.getConstructor(new Class[]{Context.class});
                            baseController = (Lifecycle) constructor.newInstance(context);
                        }
                        else if(controller.getSuperclass().getSimpleName().equals("BaseViewController")){
                            constructor = controller.getConstructor(new Class[]{View.class});
                            baseController = (Lifecycle) constructor.newInstance(rootView);
                        }

                        if(baseController!=null) {
                            LogUtil.d("controller " + controller.getName() + " added!");
                            controllers.register(controller.getSimpleName(), baseController);
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }

            }
        }
    }

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
     * @param  clazz
     * @return 注册器
     */
    public <T extends Lifecycle> T  getController(Class<T> clazz) {
        return (T) controllers.get(clazz.getSimpleName());
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
        initControllers();
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
