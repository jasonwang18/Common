package com.supcon.common.view.base.activity;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.app.annotation.Controller;
import com.app.annotation.Presenter;
import com.supcon.common.com_router.router.PresenterRouter;
import com.supcon.common.view.Lifecycle;
import com.supcon.common.view.LifecycleManage;
import com.supcon.common.view.base.controller.BaseController;
import com.supcon.common.view.base.fragment.BaseFragment;
import com.supcon.common.view.base.presenter.BasePresenter;
import com.supcon.common.view.contract.IBaseView;
import com.supcon.common.view.util.InstanceUtil;
import com.supcon.common.view.util.LogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshizhan on 16/12/12.
 */
public abstract class BaseVPFragmentActivity extends BaseFragmentActivity {

    public int selectIndex = -1;
    public List<BaseFragment> fragments = new ArrayList<>();
    ViewPager mViewPager;

    protected PresenterRouter presenterRouter;
    private List<BasePresenter> mPresenters = new ArrayList<>();

    protected void initPresenter() {
        presenterRouter = new PresenterRouter();
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation:annotations){

            if(annotation instanceof Presenter){
                Class[] presenters = ((Presenter) annotation).value();

                for(Class presenter : presenters){

                    if(this instanceof IBaseView){
                        BasePresenter basePresenter = (BasePresenter) InstanceUtil.getInstance(presenter);
                        presenterRouter.register(basePresenter);
                        addPresenter(basePresenter);
                        LogUtil.d("presenter " + presenter.getName() + " added!");
                    }

                }

            }
        }
    }

    private void addPresenter(BasePresenter basePresenter) {
        basePresenter.attachView(this);
        mPresenters.add(basePresenter);
    }

    protected LifecycleManage controllers = new LifecycleManage();
    protected List<BaseController> dataControllers = new ArrayList<>();
    protected void initControllers() {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation:annotations){

            if(annotation instanceof Controller){
                Class[] controllerClasses = ((Controller) annotation).value();

                for(Class controller : controllerClasses){

                    try {
                        Constructor constructor = null;
                        BaseController baseController = null;
                        if(controller.getSuperclass().getSimpleName().equals("BasePresenterController")){
                            constructor = controller.getConstructor();
                            baseController = (BaseController) constructor.newInstance();
                        }
                        else if(controller.getSuperclass().getSimpleName().equals("BaseDataController")) {
                            constructor = controller.getConstructor(new Class[]{Context.class});
                            baseController = (BaseController) constructor.newInstance(context);
                        }
                        else if(controller.getSuperclass().getSimpleName().equals("BaseViewController")){
                            constructor = controller.getConstructor(new Class[]{View.class});
                            baseController = (BaseController) constructor.newInstance(rootView);
                        }
                        else{
                            constructor = controller.getConstructor(new Class[]{View.class});
                            baseController = (BaseController) constructor.newInstance(rootView);
                        }

                        if(baseController!=null) {
                            LogUtil.d("controller " + controller.getName() + " added!");
                            registerController(controller.getSimpleName(), baseController);
                            addDataController(baseController);
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

    protected void addDataController(BaseController baseController){
            dataControllers.add(baseController);

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
        initPresenter();
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


    public void onRetry(){
        controllers.onRetry();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controllers.onDestroy();
        for(BasePresenter presenter: mPresenters){

            if(presenter!=null){
                presenter.detachView();
            }

        }
    }

    @Override
    public void refresh() {
        super.refresh();
        for(BaseController baseController : dataControllers){
            baseController.refresh();
        }
    }

    @Override
    public boolean checkBeforeSubmit(Map<String, Object> map) {
        for(BaseController baseController : dataControllers){
            if(!baseController.checkBeforeSubmit(map)){
                return false;
            }
        }
        return super.checkBeforeSubmit(map);
    }

    @Override
    public boolean doSave(Map<String, Object> map) {
        for(BaseController baseController : dataControllers){
            if(!baseController.doSave(map)){
                return false;
            }
        }
        return super.doSave(map);
    }

    @Override
    public boolean isModified() {
        boolean isModified;
        for(BaseController baseController : dataControllers){
            isModified = baseController.isModified();
            if(isModified){
                return true;
            }
        }
        return super.isModified();
    }

    @IdRes
    public abstract int getViewPagerId();

    public abstract void createFragments();

    @Override
    protected void initView() {
        super.initView();
        controllers.initView();
        createFragments();
        mViewPager = rootView.findViewById(getViewPagerId());
        initVP();
    }

    protected void initVP() {

        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        mViewPager.setOffscreenPageLimit(fragments.size());
    }

    public void showFragment(int selectIndex) {
        this.selectIndex = selectIndex;
        mViewPager.setCurrentItem(selectIndex);
    }


    public int currentItem(){
        return selectIndex;
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {

        MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }

}
