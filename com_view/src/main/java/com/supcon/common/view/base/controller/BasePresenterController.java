package com.supcon.common.view.base.controller;

import com.app.annotation.Presenter;
import com.supcon.common.com_router.router.PresenterRouter;
import com.supcon.common.view.base.presenter.BasePresenter;
import com.supcon.common.view.contract.IBaseView;
import com.supcon.common.view.util.InstanceUtil;
import com.supcon.common.view.util.LogUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2018/7/23
 * Email:wangshizhan@supcom.com
 */
public class BasePresenterController extends BaseController {
    protected PresenterRouter presenterRouter;
    private List<BasePresenter> mPresenters = new ArrayList<>();

    public BasePresenterController(){
        initPresenter();
    }

    public void initPresenter(){

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
                        LogUtil.d("Controller presenter " + presenter.getName() + " added!");
                    }

                }

            }
        }

    }

    private void addPresenter(BasePresenter basePresenter) {
        basePresenter.attachView(this);
        mPresenters.add(basePresenter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        for(BasePresenter presenter: mPresenters){

            if(presenter!=null){
                presenter.detachView();
            }

        }

    }

}
