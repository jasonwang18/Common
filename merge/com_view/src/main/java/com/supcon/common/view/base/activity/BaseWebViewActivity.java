package com.supcon.common.view.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.supcon.common.BaseConstant;
import com.supcon.common.view.R;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.ToastUtils;
import com.supcon.common.view.util.UriUtil;
import com.supcon.common.view.util.UrlUtil;
import com.supcon.common.view.view.js.BridgeHandler;
import com.supcon.common.view.view.js.BridgeUtil;
import com.supcon.common.view.view.js.BridgeWebView;
import com.supcon.common.view.view.js.CallBackFunction;
import com.supcon.common.view.view.js.DefaultHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by wangshizhan on 2018/12/20
 * Email:wangshizhan@supcom.com
 */
public abstract class BaseWebViewActivity extends BaseActivity {

    protected BridgeWebView webView;
    protected ProgressBar pbProgress;
    protected TextView titleText;
    protected ImageButton leftBtn;
    protected ImageButton rightBtn;

    private String url;
    private String cookie;
    private String authorization;
    private boolean isDialog = false;
    private boolean hasRefresh = false;
    protected boolean isList = false;
    protected long pendingId;
    private boolean isPendingRefresh = false;

    private static final int RESULT_CODE = 0;
    private ValueCallback<Uri> mUploadMessage;
    private CallBackFunction mFileCallBackFunction;

    @Override
    protected void onInit() {
        super.onInit();
        url             = getIntent().getStringExtra(BaseConstant.WEB_URL);
        cookie          = getIntent().getStringExtra(BaseConstant.WEB_COOKIE);
        authorization   = getIntent().getStringExtra(BaseConstant.WEB_AUTHORIZATION);
        hasRefresh      = getIntent().getBooleanExtra(BaseConstant.WEB_HAS_REFRESH, false);
        isList          = getIntent().getBooleanExtra(BaseConstant.WEB_IS_LIST, false);

        if(TextUtils.isEmpty(url) || TextUtils.isEmpty(cookie) || TextUtils.isEmpty(authorization)){
            throw new IllegalArgumentException("传参错误！");
        }
        LogUtil.i("Url:"+url+"\nCookie:"+cookie+"\nAuthorization："+authorization);

        pendingId = UrlUtil.parseIdFormUrl(url, "pendingId");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {
        super.initView();

        webView = rootView.findViewWithTag("webview");
        pbProgress = rootView.findViewWithTag("pb_progress");
        titleText = rootView.findViewWithTag("titleText");
        leftBtn = rootView.findViewWithTag("leftBtn");
        rightBtn = rootView.findViewWithTag("rightBtn");

        if(rightBtn!=null && hasRefresh) {
            rightBtn.setImageResource(R.drawable.sl_top_refresh);
            rightBtn.setVisibility(View.VISIBLE);
        }


        initWebView();
    }

    @Override
    protected void initListener() {
        super.initListener();

        webView.registerHandler("close", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                LogUtil.i("close" + data);
//                function.onCallBack("接收到js调用close方法");

                back();
            }

        });

        webView.registerHandler("showSubmit", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                LogUtil.i("showSubmit" + data);
//                function.onCallBack("接收到js调用showSubmit方法");
            }

        });

        webView.registerHandler("setTitle", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                LogUtil.i("setTitle" + data);
//                function.onCallBack("接收到js调用setTitle方法");
                if(!TextUtils.isEmpty(data)){

                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        titleText.setText(jsonObject.getString("param"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        });

        webView.registerHandler("onloadStart", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("onloadStart" + data);
//                function.onCallBack("接收到js调用onloadStart方法");

            }

        });

        webView.registerHandler("onloadEnd", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("onloadEnd" + data);
//                function.onCallBack("接收到js调用onloadEnd方法");

            }

        });

        webView.registerHandler("pendingRefresh", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("pendingRefresh" + data);
//                function.onCallBack("接收到js调用pendingRefresh方法");
                isPendingRefresh = true;
            }

        });


        webView.registerHandler("supmobileReload", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("supmobileReload" + data);
//                function.onCallBack("接收到js调用onReload方法");
                ToastUtils.show(context, "登陆已失效，请重新登陆！");

            }

        });

        webView.registerHandler("openDialog", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("openDialog" + data);
//                function.onCallBack("接收到js调用openDialog方法");
                setIsDialog(true);
            }

        });

        webView.registerHandler("closeDialog", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("closeDialog" + data);
//                function.onCallBack("接收到js调用closeDialog方法");
                setIsDialog(false);
            }

        });

        webView.registerHandler("supconUpload", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("supconUpload" + data);

                pickFile();
                mFileCallBackFunction = function;
//                function.onCallBack("接收到js调用supconUpload方法");
            }

        });

        webView.registerHandler("mobileReload", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                LogUtil.i("mobileReload" + data);

                webView.reload();
            }

        });


        if(leftBtn!=null) {
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        if(rightBtn!=null){
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onReload();
                }
            });
        }


    }

    protected void onReload() {
        webView.reload();
    }

    public void setPendingRefresh(boolean pendingRefresh) {
        isPendingRefresh = pendingRefresh;
    }

    @Override
    protected void initData() {
        super.initData();

        Map<String, String> header = new HashMap<>();

        if(!TextUtils.isEmpty(cookie)){
            header.put("Cookie", cookie);
        }

        if(!TextUtils.isEmpty(authorization)){
            header.put("Authorization", authorization);
        }

        webView.loadUrl(url, header);

    }

    private void initWebView() {

        if(webView == null){
            throw new IllegalArgumentException("BridgeWebView is null!");
        }
        webView.setDefaultHandler(new DefaultHandler());

//        webView.getSettings().setAppCacheEnabled(true);//启用localstorage本地存储api
//        webView.getSettings().setLightTouchEnabled(true);//启用选中功能
//        webView.getSettings().setDomStorageEnabled(true);//启用dom存储(关键就是这句)，貌似网上twitter显示有问题也是这个属性没有设置的原因
//        webView.getSettings().setDatabaseEnabled(true);//启用html5数据库功能
//        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(dir);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);

        setWebChromeClient(new DefaultWebChromeClient());
    }

    protected void setWebChromeClient(WebChromeClient webChromeClient){
        webView.setWebChromeClient(webChromeClient);
    }


    protected void callJSFunction(final String jsFunctionName, String data){
        webView.callHandler(jsFunctionName, data, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                LogUtil.i("function:"+jsFunctionName+" onCallBack data = " + data);
            }
        });
    }


    protected void registerHandler(String name, BridgeHandler handler){
        webView.registerHandler(name, handler);
    }

    protected void sendMessageToJs(String message){
        webView.send(message, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                LogUtil.i("sendMessageToJs onCallBack data = " + data);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(isDialog){
            return;
        }

        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            super.onBackPressed();
        }

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if(keyCode == KeyEvent.KEYCODE_BACK && !hasHeader){
//            if(webView.canGoBack()){
//                webView.goBack();
//                return false;
//            }
//
//            if(webView.isInEditMode()){
//                LogUtil.d("isInEditMode");
//            }
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }

//    private long parsePendingId(String url){
//        Uri uri = Uri.parse(url);
//        Object parameter = uri.getQueryParameter("pendingId");
//        if(parameter!=null){
//
//            return Long.parseLong(parameter.toString());
//        }
//
//        return 0;
//    }

    public void setTitle(String title){
        if(titleText!=null){
            titleText.setText(title);
        }
    }

    public void setIsDialog(boolean isDialog){
        this.isDialog = isDialog;
        if(isDialog){
            ((ViewGroup)titleText.getParent()).setVisibility(View.GONE);
        }
        else{
            ((ViewGroup)titleText.getParent()).setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("CheckResult")
    public void loginValid(){
        if(isList) {
            webView.reload();
        }
        else {
            ToastUtils.show(context, "页面已失效");
            Flowable.timer(500, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            back();
                        }
                    });
        }

    }

    @SuppressLint("CheckResult")
    public void unauthorized(){
//        ToastUtils.show(context, "登陆已失效，请重新登陆");
        ToastUtils.show(context, "页面已失效");
        Flowable.timer(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        back();
                    }
                });
    }

    public void serverError(){
        ToastUtils.show(context, "服务器错误");
    }

    public void sendRefreshEvent(){

    }

    public  void next(String url){
        goNext(url);
    }

    public  boolean checkUrl(String inUrl){
        long newPendingId = UrlUtil.parseIdFormUrl(inUrl, "pendingId");
        long deploymentId  = UrlUtil.parseIdFormUrl(inUrl, "deploymentId");

        return  pendingId!=0 && newPendingId !=0 && pendingId == newPendingId || deploymentId !=0 && newPendingId==0&&isPendingRefresh;
    }

    protected void goNext(String url){

    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("*/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();

            if(result == null){
                return;
            }

            if (null != mUploadMessage){
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
            else if(mFileCallBackFunction!=null){
                String path = UriUtil.getFilePathByUri(context, result);
                if(TextUtils.isEmpty(path)){
                    return;
                }
                uploadFile(new File(path), mFileCallBackFunction);
            }


        }
    }

    protected void uploadFile(File file, CallBackFunction callBackFunction){

    }

    protected void showDialog(String message, JsResult result){

    }

    public class DefaultWebChromeClient extends WebChromeClient{

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
            this.openFileChooser(uploadMsg);
        }

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
            this.openFileChooser(uploadMsg);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {

            mUploadMessage = uploadMsg;
            pickFile();
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                // 网页加载完成
                pbProgress.setVisibility(View.GONE);
            } else {
                // 加载中
                pbProgress.setVisibility(View.VISIBLE);
                pbProgress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            LogUtil.d("onCreateWindow");
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            LogUtil.d("onHideCustomView");
        }

        @Override
        public void onCloseWindow(WebView window) {
            super.onCloseWindow(window);
            LogUtil.d("onCloseWindow");
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

            if(!TextUtils.isEmpty(title) && title.length() > 10){
                return;
            }
            LogUtil.d("onReceivedTitle:"+title);
            setTitle(title);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            LogUtil.d("onShowCustomView");
        }

        @Override
        public void onRequestFocus(WebView view) {
            super.onRequestFocus(view);
            LogUtil.d("onRequestFocus");
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

            showDialog(message, result);
            return true;
//            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {

            showDialog(message, result);
            return true;
//                return super.onJsConfirm(view, url, message, result);
        }

    }
}
