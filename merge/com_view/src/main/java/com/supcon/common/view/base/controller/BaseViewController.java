package com.supcon.common.view.base.controller;

import android.view.View;

import com.supcon.common.view.util.ToastUtils;
import com.supcon.common.view.util.ViewBinder;

import java.util.Map;

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
            ViewBinder.bindTag(this, rootView);

    }

    public void attachView(View rootView){
        attachContext(rootView.getContext());
        this.rootView = rootView;
        bindView();
    }

    @Override
    public void initListener() {
        super.initListener();
        ViewBinder.bindCustomView(this, rootView);
        ViewBinder.bindListener(this, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rootView = null;
    }

}
