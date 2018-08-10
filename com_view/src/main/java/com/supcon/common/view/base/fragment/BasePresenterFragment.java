package com.supcon.common.view.base.fragment;

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
 * Created by wangshizhan on 17/1/5.
 */

public abstract class BasePresenterFragment extends BaseFragment{

    @Override
    protected void onInit() {
        super.onInit();
        initPresenter();
    }

    protected PresenterRouter presenterRouter ;
    private List<BasePresenter> mPresenters = new ArrayList<>();

    protected void initPresenter() {
        presenterRouter = new PresenterRouter();

        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation:annotations){

            if(annotation instanceof Presenter){
                Class[] presenters = ((Presenter) annotation).value();

                for(Class presenter : presenters){

                    if(this instanceof IBaseView){

//                        if(mPresenterRouter.hasPresenter(presenter)){
//                            BasePresenter basePresenter = (BasePresenter) mPresenterRouter.getPresenter(presenter);
//                            addPresenter(basePresenter);
//                            LogUtil.d("presenter " + presenter.getName() + " got!");
//                        }
//                        else {
                            BasePresenter basePresenter = (BasePresenter) InstanceUtil.getInstance(presenter);
                            presenterRouter.register(basePresenter);
                            addPresenter(basePresenter);
                            LogUtil.d("presenter " + presenter.getName() + " added!");
//                        }
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
