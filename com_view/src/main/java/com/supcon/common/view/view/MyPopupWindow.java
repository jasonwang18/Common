package com.supcon.common.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by wangshizhan on 2018/1/3.
 * Email:wangshizhan@supcon.com
 */

public class MyPopupWindow extends PopupWindow {

    private boolean cancelOutSide = true;

    public void setCancelOutSide(boolean cancelOutSide) {
        this.cancelOutSide = cancelOutSide;
    }

    public boolean isCancelOutSide() {
        return cancelOutSide;
    }

    @Override
    public void dismiss() {

        if(!cancelOutSide){
            return;
        }
        super.dismiss();
    }

    public void dismiss(boolean force){

        if(force){
            super.dismiss();
        }
        else{
            dismiss();
        }

    }

    public MyPopupWindow(Context context) {
        super(context);
    }

    public MyPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyPopupWindow() {
    }

    public MyPopupWindow(View contentView) {
        super(contentView);
    }

    public MyPopupWindow(int width, int height) {
        super(width, height);
    }

    public MyPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public MyPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

}
