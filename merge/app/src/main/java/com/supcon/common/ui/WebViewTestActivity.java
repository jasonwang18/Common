package com.supcon.common.ui;

import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.app.annotation.apt.Router;
import com.supcon.common.BaseConstant;
import com.supcon.common.IntentRouter;
import com.supcon.common.R;
import com.supcon.common.view.base.activity.BaseWebViewActivity;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.StatusBarUtils;
import com.supcon.common.view.view.js.CallBackFunction;

/**
 * Created by wangshizhan on 2018/12/21
 * Email:wangshizhan@supcom.com
 */
@Router("webview")
public class WebViewTestActivity extends BaseWebViewActivity {



    @Override
    protected int getLayoutID() {
        return R.layout.ac_web;
    }

    @Override
    protected void onInit() {
        super.onInit();

    }

    @Override
    protected void initView() {
        super.initView();
        ((ViewGroup)findViewById(R.id.leftBtn).getParent()).setBackgroundResource(R.color.mobileValueColor);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.mobileValueColor);
//        ((TextView)findViewById(R.id.titleText)).setText("百度一下");

    }

    @Override
    protected void initListener() {
        super.initListener();


    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isList)
            onReload();
    }

    @Override
    public void goNext(String url) {

        Bundle bundle = new Bundle();
        bundle.putString(BaseConstant.WEB_COOKIE, "JSESSIONID=3C470C1FA42852EC8B26EAB83EF8DACA; CASTGC=TGT-68-ujPARdeppM2AjlEB2imz3Mz3gxMp42BBULGbsKOMehubKnKF0p");
        bundle.putString(BaseConstant.WEB_AUTHORIZATION, "CASTGC TGT-68-ujPARdeppM2AjlEB2imz3Mz3gxMp42BBULGbsKOMehubKnKF0p");
        bundle.putString(BaseConstant.WEB_URL, url);
        bundle.putBoolean(BaseConstant.WEB_HAS_REFRESH, true);
        IntentRouter.go(context, "webview", bundle);
        if(!isList){
            back();
            executeBackwardAnim();
        }

    }
}
