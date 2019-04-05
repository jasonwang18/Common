package com.supcon.common.view.view.js;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.supcon.common.view.base.activity.BaseWebViewActivity;
import com.supcon.common.view.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * 如果要自定义WebViewClient必须要集成此类
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;
    private boolean isOpenPending = false;
    private boolean isCookieValid = false;
    private BaseWebViewActivity mBaseWebViewActivity;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
//        LogUtil.w( "load url:"+url);
        if(mBaseWebViewActivity==null){
            mBaseWebViewActivity = (BaseWebViewActivity) view.getContext();
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            webView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            webView.flushMessageQueue();
            return true;
        } else {
            LogUtil.d("shouldOverrideUrlLoading url:"+url);

            WebView.HitTestResult hit = webView.getHitTestResult();
            int hitType = hit.getType();
//            if (hitType != WebView.HitTestResult.UNKNOWN_TYPE) {
//                //这里执行自定义的操作
//        @Deprecated
//        public static final int ANCHOR_TYPE = 1;
//        public static final int EDIT_TEXT_TYPE = 9;           打开一个可编辑区域
//        public static final int EMAIL_TYPE = 4;               打开一个邮件地址
//        public static final int GEO_TYPE = 3;                 打开一个map地址
//        /** @deprecated */
//        @Deprecated
//        public static final int IMAGE_ANCHOR_TYPE = 6;
//        public static final int IMAGE_TYPE = 5;               打开一个IMG标签
//        public static final int PHONE_TYPE = 2;               打开一个电话号码
//        public static final int SRC_ANCHOR_TYPE = 7;          打开一个HTML a标签，内容是一个http网址
//        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;    打开一个HTML a标签，内容是由一个http网址及img标签组成
//        public static final int UNKNOWN_TYPE = 0;
//                return true;
//            } else{
//                //重定向时hitType为0 ,执行默认的操作
//                return false;
//            }
            LogUtil.w("hitType:"+hitType);

            //打开待办皆为重定向，为避免打开两次Activity,第一次返回
            if(hitType == 0) {
                if(url.contains("/cas/login")){//非重定向登陆超时，拦截，不跳转Web 登陆
                    mBaseWebViewActivity.loginValid();
                    return true;
                }
                else if (url.contains("open-pending")) {//打开待办第一次重定向, return super.shouldOverrideUrlLoading(view, url);
                    isOpenPending = true;
                } else if(isOpenPending){//打开待办第二次重定向
                    mBaseWebViewActivity.setPendingRefresh(false);
                    newWeb(view.getContext(), url);
                    return true;
                }
                else if(mBaseWebViewActivity.checkUrl(url)){//提交成功
                    mBaseWebViewActivity.back();
                    mBaseWebViewActivity.setPendingRefresh(false);
                    sendRefreshEventToParent(view.getContext());
                    return true;
                }
                else if(isCookieValid){
//                    newWeb(view.getContext(), url);
                    view.reload();
                    isCookieValid = false;
                    return true;
                }
                else{//制定
                    newWeb(view.getContext(), url);
                    return true;
                }
            }
            else if(hitType == 7){//作废
//                newWeb(view.getContext(), url);
                mBaseWebViewActivity.back();
                return true;
            }
            else if(hitType == 9){//重定向时发现登陆已失效
                mBaseWebViewActivity.loginValid();
                return true;
            }


            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    // 增加shouldOverrideUrlLoading在api》=24时
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            LogUtil.d( "shouldOverrideUrlLoading url:" + request.getUrl());
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String url = request.getUrl().toString();
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
                webView.handlerReturnData(url);
                return true;
            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
                webView.flushMessageQueue();
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, request);
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                LogUtil.d( "shouldOverrideUrlLoading url:" + request.getUrl());
            }
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
//        LogUtil.d("onPageStarted");


    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
//        LogUtil.d("onPageCommitVisible");
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        LogUtil.d("onPageFinished");
        BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.MobileJs);
        BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
//        CookieManager cookieManager = CookieManager.getInstance();
//        String CookieStr = cookieManager.getCookie(url);
//        LogUtil.d("CookieStr:"+CookieStr);
//        if (TextUtils.isEmpty(CookieStr)){
//            isCookieValid = true;
//        }

        if (webView.getStartupMessage() != null) {
            for (Message m : webView.getStartupMessage()) {
                webView.dispatchMessage(m);
            }
            webView.setStartupMessage(null);
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogUtil.e("onReceivedHttpError1:" + errorResponse.getReasonPhrase());
            if(!isCookieValid && "Unauthorized".equals(errorResponse.getReasonPhrase())){//登陆已失效，需重新登陆
                ((BaseWebViewActivity)view.getContext()).unauthorized();
                return;
            }

            if("Internal Server Error".equals(errorResponse.getReasonPhrase())){
                mBaseWebViewActivity.serverError();
                return;
            }
        }
        super.onReceivedHttpError(view, request, errorResponse);

    }


    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        LogUtil.e( "onReceivedHttpError2:"+errorCode);

    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);

    }


    private void newWeb(Context context, String url) {

        ((BaseWebViewActivity)context).next(url);

    }

    @SuppressLint("CheckResult")
    private void sendRefreshEventToParent(final Context context){
        Flowable.timer(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mBaseWebViewActivity.sendRefreshEvent();
                    }
                });
    }

    /**
     * 为指定的url添加cookie
     * @param url  url
     * @param cookieContent cookie内容
     */
    private void setCookie(Context context, String url,String cookieContent) {
        CookieManager cm = CookieManager.getInstance();
        CookieSyncManager csm = CookieSyncManager.createInstance(context);
        cm.setAcceptCookie(true);
        cm.setCookie(url, cookieContent);
    }

}