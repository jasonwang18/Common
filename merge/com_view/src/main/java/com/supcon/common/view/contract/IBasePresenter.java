package com.supcon.common.view.contract;

/**
 * Created by wangshizhan on 17/1/4.
 */

public interface IBasePresenter<V>{

    void attachView(V v);
    V getView();
    void detachView();

}
