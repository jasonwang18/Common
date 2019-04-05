package com.supcon.common;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.app.annotation.apt.Instance;
import com.supcon.common.view.App;

/**
 * Created by wangshizhan on 2018/1/11.
 * Email:wangshizhan@supcon.com
 */
@Instance
public class TextApplication extends App {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        AppConfig.DEBUG_ENABLE = false;
    }
}
