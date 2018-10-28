package com.supcon.common.view.base.controller;

import android.view.View;

import com.supcon.common.view.util.ViewBinder;

/**
 * Created by wangshizhan on 2018/7/23
 * Email:wangshizhan@supcom.com
 */
public class BaseViewController extends BaseDataController {

    private View rootView;

    public BaseViewController(View rootView){
        super(rootView.getContext());
        this.rootView = rootView;
        bindView();
    }


    private void bindView(){

        if(rootView!=null)
            ViewBinder.bind(this, rootView);

    }

    public void attachView(View rootView){
        attachContext(rootView.getContext());
        this.rootView = rootView;
        bindView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        rootView = null;
    }
}
