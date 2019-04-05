package com.supcon.common.view.view.loader.base;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.supcon.common.view.R;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.view.MyPopupWindow;

import java.lang.ref.WeakReference;

/**
 * Created by wangshizhan on 2018/1/12.
 * Email:wangshizhan@supcon.com
 */

public abstract class BaseLoader implements ILoader{

    protected MyPopupWindow mloader;
    protected Context context;

    private WeakReference<View> rootView;

    public BaseLoader(Context context, View rootView) {
        init(context, rootView);
        initPop();
    }


    protected void initPop() {

        if(contentViewId()!=0){
            View contentView = LayoutInflater.from(context).inflate(contentViewId(), null);
            if (mloader == null) {
                mloader = new MyPopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                mloader.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_loader));
                mloader.setFocusable(true);
                mloader.setCancelOutSide(false);
                mloader.update();
            }
        }
    }

    abstract protected int contentViewId();

    private void init(Context context, View rootView){
        this.rootView = new WeakReference<>(rootView);
        this.context = context;
    }


    protected View getRootView(){
        if(rootView!=null && rootView.get()!=null){
            return rootView.get();
        }
        return null;
    }

    protected void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = f;
        ((Activity)context).getWindow().setAttributes(lp);
    }

    @Override
    public void closeLoader() {
        if(mloader!=null)
            mloader.dismiss(true);
        backgroundAlpha(1.0f);
    }
}
