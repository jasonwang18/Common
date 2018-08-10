package com.supcon.common.view.view.loader.base;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.supcon.common.view.R;
import com.supcon.common.view.util.DisplayUtil;
import com.supcon.common.view.view.MyPopupWindow;
import com.supcon.common.view.view.loader.CircularLoaderView;

/**
 * Created by wangshizhan on 2018/1/12.
 * Email:wangshizhan@supcon.com
 */

public class DefautLoader extends BaseLoader{

    public DefautLoader(Context context, View rootView) {
        super(context, rootView);
    }

    @Override
    protected void initPop() {
        super.initPop();
        if(mloader!=null) {
            View contentView = mloader.getContentView();
            CircularLoaderView circularButtonLoaderView = contentView.findViewById(R.id.common_loader);
            circularButtonLoaderView.setDoneColor(ContextCompat.getColor(context, R.color.bapThemeBlue));
            circularButtonLoaderView.setInitialHeight(100);
            circularButtonLoaderView.setSpinningBarColor(ContextCompat.getColor(context, R.color.bapThemeOrange));
            mloader.update();
        }
    }

    @Override
    protected int contentViewId() {
        return R.layout.ly_loader_lol;
    }

    @Override
    public void showLoader(String msg){

        if(mloader != null && mloader.isShowing())
        {
            showMsgOnly(msg);
        }
        else {
            showDefaultLoader(msg);
        }
    }


    private void showDefaultLoader(String msg){
        try {
            CircularLoaderView circularButtonLoaderView = null;
            View contentView = null;
            if (mloader == null) {
                contentView = LayoutInflater.from(context).inflate(R.layout.ly_loader_lol, null);
                circularButtonLoaderView = contentView.findViewById(R.id.common_loader);
                circularButtonLoaderView.setDoneColor(ContextCompat.getColor(context, R.color.bapThemeBlue));
                circularButtonLoaderView.setInitialHeight(100);
                circularButtonLoaderView.setSpinningBarColor(ContextCompat.getColor(context, R.color.bapThemeOrange));
                mloader = new MyPopupWindow(DisplayUtil.dip2px(120, context),
                        DisplayUtil.dip2px(120, context));
                mloader.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_loader_stroke));
                mloader.setFocusable(true);
                mloader.setCancelOutSide(false);
                mloader.setContentView(contentView);
                mloader.update();
            }
            else{
                contentView = mloader.getContentView();
                circularButtonLoaderView = contentView.findViewById(R.id.common_loader);
            }

            circularButtonLoaderView.revertAnimation();
            circularButtonLoaderView.startAnimation();

            ((TextView)contentView.findViewById(R.id.common_msg)).setText(msg!=null?msg:"正在处理...");

            mloader.showAtLocation(getRootView(), Gravity.CENTER, 0, 0);
            backgroundAlpha(0.6f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeLoader() {
        super.closeLoader();
    }

    @Override
    public void showResultMsg(String msg, boolean isSuccess) {

        if(isSuccess){
            doneSuccess(msg);
        }
        else{
            doneFailed(msg);
        }

    }

    @Override
    public void showMsg(String msg) {

        if(mloader != null && mloader.isShowing())
        {
            showMsgOnly(msg);
        }
        else {
            showDefaultLoader(msg);
        }
    }

    private void showMsgOnly(String msg){
        try {
            View contentView = mloader.getContentView();
            ((TextView)contentView.findViewById(R.id.common_msg)).setText(TextUtils.isEmpty(msg)?"正在处理...":msg);
            mloader.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doneSuccess(String msg){
        if(mloader!=null && mloader.isShowing()) {
            View contentView = mloader.getContentView();
            boolean hasMsg = !TextUtils.isEmpty(msg);
            CircularLoaderView circularButtonLoaderView = contentView.findViewById(R.id.common_loader);
            ((TextView)contentView.findViewById(R.id.common_msg)).setText(hasMsg?msg:"操作成功");
            circularButtonLoaderView.doneLoadingAnimation(ContextCompat.getColor(context, R.color.bapThemeBlue),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_done_success3));
        }
    }

    private void doneFailed(String msg){
        if(mloader!=null && mloader.isShowing()) {
            View contentView = mloader.getContentView();
            boolean hasMsg = !TextUtils.isEmpty(msg);
            CircularLoaderView circularButtonLoaderView = contentView.findViewById(R.id.common_loader);
            TextView textView = contentView.findViewById(R.id.common_msg);
            textView.setVisibility(View.VISIBLE);
            textView.setText(msg);
            circularButtonLoaderView.doneLoadingAnimation(ContextCompat.getColor(context, R.color.bapThemeOrange),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_done_failed3));
        }
    }

    private void closeDefaultLoader(String msg, boolean isSuccess){
        if(mloader!=null && mloader.isShowing()){
//            ((RotateLoading)loader.findViewById(R.id.common_loader)).stop();
            View contentView = mloader.getContentView();
            boolean hasMsg = !TextUtils.isEmpty(msg);
            CircularLoaderView circularButtonLoaderView = contentView.findViewById(R.id.common_loader);
            if(!isSuccess){
                TextView textView = contentView.findViewById(R.id.common_msg);
                textView.setVisibility(View.VISIBLE);
                textView.setText(msg);
                circularButtonLoaderView.doneLoadingAnimation(ContextCompat.getColor(context, R.color.bapThemeOrange),
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_done_failed3));
            }
            else{
                ((TextView)contentView.findViewById(R.id.common_msg)).setText(hasMsg?msg:"操作成功");
                circularButtonLoaderView.doneLoadingAnimation(ContextCompat.getColor(context, R.color.bapThemeBlue),
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_done_success3));
            }


        }
    }




}
