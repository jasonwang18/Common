package com.supcon.common.view.base.presenter;

import com.supcon.common.view.contract.IBasePresenter;

import java.lang.ref.WeakReference;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by wangshizhan on 17/6/29.
 */

public abstract class BasePresenter<V> implements IBasePresenter<V> {

    private WeakReference<V> mViewRef;
    protected CompositeDisposable mCompositeSubscription = new CompositeDisposable();


    protected void onAttached(){

    }

    protected void onDetached() {

    }

    /**
     * 判断界面是否销毁
     */
    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void attachView(V v) {
        this.mViewRef = new WeakReference<>(v);
        this.onAttached();
    }

    @Override
    public V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    @Override
    public void detachView() {
        mCompositeSubscription.dispose();
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        this.onDetached();
    }

}
