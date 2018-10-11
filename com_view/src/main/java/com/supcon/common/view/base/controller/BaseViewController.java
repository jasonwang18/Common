package com.supcon.common.view.base.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.supcon.common.view.util.ViewBinder;

/**
 * Created by wangshizhan on 2018/7/23
 * Email:wangshizhan@supcom.com
 */
public class BaseViewController extends BasePresenterController {

    private View rootView;
    private Activity mActivity;
    protected Context context;

    public BaseViewController(View rootView){
        super();
        this.rootView = rootView;
        context = rootView.getContext();
        bindView();
    }

    public BaseViewController(Activity activity){
        super();
        this.mActivity = activity;
        context = activity.getBaseContext();
        bindView();
    }

    private void bindView(){

        if(rootView!=null)
            ViewBinder.bind(this, rootView);
        else if(mActivity!=null){
            ViewBinder.bind(mActivity);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        rootView = null;
    }
}
