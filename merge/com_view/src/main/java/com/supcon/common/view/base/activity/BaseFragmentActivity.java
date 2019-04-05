package com.supcon.common.view.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.supcon.common.view.R;
import com.supcon.common.view.base.IData;
import com.supcon.common.view.util.KeyboardUtil;
import com.supcon.common.view.util.LoaderErrorMsgHelper;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.StatusBarUtils;
import com.supcon.common.view.util.ViewBinder;
import com.supcon.common.view.view.loader.base.LoaderController;
import com.supcon.common.view.view.loader.base.OnLoaderFinishListener;
import com.supcon.common.view.view.swipeback.SwipeBackController;
import com.supcon.common.view.view.swipeback.SwipeBackLayout;

import java.util.Map;

/**
 * Created by wangshizhan on 16/12/12.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements IData, SwipeBackController.Delegate{


    protected Context context;
    protected FragmentManager fragmentManager;
    protected View rootView;
    protected LoaderController loaderController;
    protected SwipeBackController swipeBackController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        rootView = LayoutInflater.from(this).inflate(getLayoutID(), null);
        setContentView(rootView);
        fragmentManager = this.getSupportFragmentManager();
        onInit();
        initView();
        initListener();
        initData();   //初始化数据放在视图加载完成之后，防止loader显示不出来
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        initData();
    }

    protected abstract int getLayoutID();

    protected void onInit() {
        loaderController = new LoaderController(this, rootView);
        ViewBinder.bindTag(this);
    }

    /**
     * 初始化 view
     */
    protected void initView() {
        initSwipeBackController();
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        ViewBinder.bindCustomView(this, rootView);
        ViewBinder.bindListener(this, rootView);
    }


    public View getRootView() {
        return rootView;
    }



    public void back() {
        finish();
    }

    @Override
    public void onBackPressed() {
        back();
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


    public void setStatusBarColor(int colorResId){
        StatusBarUtils.setWindowStatusBarColor(this, colorResId);
    }

    /**
     * 滑动返回是否可用
     */
    @Override
    public boolean isSwipeBackEnable() {
        return swipeBackController!=null&&swipeBackController.isSwipeBackEnable();
    }

    public void setSwipeBackEnable(boolean swipeBackEnable) {

        if(swipeBackController!=null){
            swipeBackController.setSwipeBackEnable(swipeBackEnable);
        }

    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackController() {
        swipeBackController = new SwipeBackController(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 SwipeBackController.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        swipeBackController.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        swipeBackController.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        swipeBackController.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        swipeBackController.setShadowResId(R.drawable.bg_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        swipeBackController.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        swipeBackController.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        swipeBackController.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        swipeBackController.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackCancel() {
    }

    /**
     * 滑动返回执行受阻
     * @param errorCode  错误码
     */
    @Override
    public void onSwipeBackStop(int errorCode) {
        if(errorCode == SwipeBackLayout.ERROR_MSG_MODIFIED){
//            LogUtil.e("error:ERROR_MSG_MODIFIED");
            onBackPressed();
        }
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackExecuted() {
        KeyboardUtil.closeKeyboard(this);
        back();
        executeBackwardAnim();
    }

    /**
     * 执行回到到上一个 Activity 的动画。这里弄成静态方法，方便在 Fragment 中调用
     */
    public void executeBackwardAnim() {
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
    }

}
