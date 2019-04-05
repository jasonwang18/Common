package com.supcon.common.view.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wangshizhan on 2019/3/5
 * Email:wangshizhan@supcom.com
 */
public class CookieUtil {



    public static void saveCookie(Context context, String sessionId, String CASTGC){
        //先建立数据库存储
        SharedPreferences spf = context.getSharedPreferences("Cookie", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("jsessionId", sessionId);
        editor.putString("CASTGC", CASTGC);
        String cookieString = "JSESSIONID="+sessionId+";CASTGC="+CASTGC;
        LogUtil.e( "cookieString:"+cookieString);
        editor.putString("cookieString", cookieString);
        editor.apply();

    }


    public static void syncCookie(Context context, String url){
        SharedPreferences spf = context.getSharedPreferences("Cookie", MODE_PRIVATE);
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieString = spf.getString("cookieString", "");
        cookieManager.setCookie(url, cookieString);
        CookieSyncManager.getInstance().sync();
    }


    public static void clearCookie(Context context){
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除
        cookieManager.removeAllCookie();
    }

}
