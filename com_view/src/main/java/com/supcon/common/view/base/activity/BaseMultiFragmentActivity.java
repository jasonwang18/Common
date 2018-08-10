package com.supcon.common.view.base.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.supcon.common.view.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 16/12/12.
 */
public abstract class BaseMultiFragmentActivity/*<P extends BasePresenter>*/ extends BaseFragmentActivity {

    public int selectIndex = -1;
    public List<Fragment> fragments = new ArrayList<Fragment>();


    @Override
    protected void onInit() {
        super.onInit();
//        initPresenter();
    }

//    public P mPresenter;
//
//    private void initPresenter() {
//        if (this instanceof IBaseView &&
//                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
//                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
//            Type[] types = ((ParameterizedType) (this.getClass()
//                    .getGenericSuperclass())).getActualTypeArguments();
//
//            Class<P> mPresenterClass = (Class<P>) types[types.length-1];
//            mPresenter = InstanceUtil.getInstance(mPresenterClass);
//            mPresenter.attachView(this);
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mPresenter != null) mPresenter.detachView();
    }

    @IdRes
    public abstract int getFragmentContainerId();

    public abstract void createFragments();

    @Override
    protected void initView() {
        super.initView();
        createFragments();
    }

    public void showFragment(int selectIndex) {
        FragmentTransaction addTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments() == null || (fragments.size() > selectIndex && !fragmentManager.getFragments().contains(fragments.get(selectIndex)))) {
            addTransaction.add(getFragmentContainerId(), fragments.get(selectIndex), String.format("fragment_%s", selectIndex));
            addTransaction.commitAllowingStateLoss();
        }
        showFragmentIndex(selectIndex);
    }

    private void showFragmentIndex(int selectIndex) {
        if (fragments != null && fragments.size() > selectIndex) {
            if (this.selectIndex != selectIndex) {
                this.selectIndex = selectIndex;
                FragmentTransaction showTranscation = fragmentManager.beginTransaction();
                for (Fragment fragment : fragments) {
                    showTranscation.hide(fragment);
                }
                showTranscation.show(fragments.get(selectIndex));
                showTranscation.commitAllowingStateLoss();
            }
        }
    }

    protected void pushFragmentToContainer(int resId, BaseFragment fragment) {
        if (resId > 0 && fragment != null) {
            FragmentTransaction addTransaction = fragmentManager.beginTransaction();
            addTransaction.add(resId, fragment);
            addTransaction.commitAllowingStateLoss();
        }
    }


}
