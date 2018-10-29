package com.supcon.common.view.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import com.supcon.common.view.util.LoaderErrorMsgHelper;
import com.supcon.common.view.util.ViewBinder;
import com.supcon.common.view.view.loader.base.LoaderController;
import com.supcon.common.view.view.loader.base.OnLoaderFinishListener;


/**
 * Created by wangshizhan on 16/12/12.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected View rootView;
    protected Context context;
    protected LoaderController loaderController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        rootView = LayoutInflater.from(this).inflate(getLayoutID(), null);
        setContentView(rootView);
        onInit();
        initView();
        initListener();
        initData();
    }

    protected void onInit() {
        loaderController = new LoaderController(this, rootView);
        ViewBinder.bind(this);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        initData();
    }

    public View getRootView(){
        return rootView;
    }


    protected abstract int getLayoutID();

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        ViewBinder.bindListener(this, rootView);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    public void back() {
        finish();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaderController.onDestroy();
    }

    /**
     * 操作成功。显示遮罩
     * @param msg loader信息
     */
    public void onLoading(String msg){
        loaderController.showLoader(msg);
    }


    public void onLoading(){
        onLoading("");
    }

    /**
     * 操作成功。去掉遮罩
     */
    public void onLoadSuccess(){
        onLoadSuccess("");
    }

    /**
     * 操作成功。去掉遮罩
     * @msg 加载成功的消息
     */
    public void onLoadSuccess(String msg){
//        loaderController.showMsgAndclose(msg, true, 800);
        onLoadSuccessAndExit(msg, null);
    }

    /**
     * 操作成功。去掉遮罩
     * @param msg 加载成功的消息
     * @param listener 加载框消失的回调
     */
    public void onLoadSuccessAndExit(String msg, OnLoaderFinishListener listener){
        loaderController.showMsgAndclose(msg, true, 500, listener);
    }

    /**
     * 操作失败，去掉遮罩
     * @param msg 错误信息，用于loader显示
     */
    public void onLoadFailed(String msg){
        showErrorMsg(msg);
    }

    /**
     * 直接关闭加载框
     */
    public void closeLoader(){
        loaderController.closeLoader();
    }

    protected void showErrorMsg(String msg){
        LoaderErrorMsgHelper.showErrorMsg(loaderController, msg);
    }

    protected void delayFinish(long timeDelay, final OnActivityFinishListener listener){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(listener!=null){
                    listener.onBeforFinish();
                }
                finish();
            }
        }, timeDelay);
    }

    public interface OnActivityFinishListener{

        void onBeforFinish();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }


}
