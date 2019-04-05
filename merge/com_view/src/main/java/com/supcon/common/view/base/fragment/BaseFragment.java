package com.supcon.common.view.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.base.IData;
import com.supcon.common.view.util.LoaderErrorMsgHelper;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ViewBinder;
import com.supcon.common.view.view.loader.base.LoaderController;
import com.supcon.common.view.view.loader.base.OnLoaderFinishListener;

import java.util.Map;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseFragment extends Fragment implements IData{
    protected View rootView;
    protected Context context;

    protected LoaderController loaderController;

    protected abstract int getLayoutID();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutID(), container, false);
        onInit();
        initView();
        initListener();
        initData();
        return rootView;
    }

    protected void onInit() {
        loaderController = new LoaderController(getContext(), rootView);
        ViewBinder.bindTag(this, rootView);
    }

    public View getRootView(){
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        initData();
    }

    /**
     * 初始化view
     */
    protected void initView() {

    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        ViewBinder.bindCustomView(this, rootView);
        ViewBinder.bindListener(this, rootView);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loaderController.onDestroy();
    }

    @Override
    protected void finalize() throws Throwable {
        LogUtil.d("finalize() " + this.toString());
        super.finalize();
    }

    protected void showErrorMsg(String msg){
        LoaderErrorMsgHelper.showErrorMsg(loaderController, msg);
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

    @Override
    public void refresh() {

    }

    @Override
    public boolean checkBeforeSubmit(Map<String, Object> map) {
        return doSave(map);
    }

    @Override
    public boolean doSave(Map<String, Object> map) {
        return true;
    }

    @Override
    public boolean isModified() {
        return false;
    }
}