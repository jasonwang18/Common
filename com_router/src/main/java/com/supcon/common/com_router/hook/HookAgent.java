package com.supcon.common.com_router.hook;

import android.content.Context;
import android.util.Log;

import com.supcon.common.com_router.hook.classloader.BaseDexClassLoaderHookHelper;
import com.supcon.common.com_router.hook.classloader.LoadedApkClassLoaderHookHelper;

import java.io.File;

/**
 * Created by wangshizhan on 2017/10/24.
 * Email:wangshizhan@supcon.com
 */

public class HookAgent {

    private static final int PATCH_BASE_CLASS_LOADER = 1;

    private static final int CUSTOM_CLASS_LOADER = 2;

    private static final int HOOK_METHOD = CUSTOM_CLASS_LOADER;

    private static class HookAgentHolder{
        private static HookAgent instance = new HookAgent();
    }


    public static HookAgent getInstance() {

        return HookAgentHolder.instance;
    }

    private HookAgent() {
    }

    public void hook(Context context, String fileName){

        try {
            // 拦截startService, stopService等操作
//            AMSHookHelper.hookActivityManagerNative();

            Utils.extractAssets(context, fileName);
//            File apkFile = context.getFileStreamPath(fileName);
//            File odexFile = context.getFileStreamPath("classes.dex");
//
//            // Hook ClassLoader, 让插件中的类能够被成功加载
//            BaseDexClassLoaderHookHelper.patchClassLoader(context.getClassLoader(), apkFile, odexFile);

            if (HOOK_METHOD == PATCH_BASE_CLASS_LOADER) {
                File dexFile = context.getFileStreamPath("test.apk");
                File optDexFile = context.getFileStreamPath("test.dex");
                BaseDexClassLoaderHookHelper.patchClassLoader(context.getClassLoader(), dexFile, optDexFile);
            } else {
                LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(context, context.getFileStreamPath(fileName));
            }

            AMSHookHelper.hookActivityManagerNative();
            AMSHookHelper.hookActivityThreadHandler();
            Log.i("Com_Router","hook success");
        } catch (Exception e) {
            throw new RuntimeException("hook failed");
        }
    }

    public HookAgent component(String pkgName, String activityName){

        IActivityManagerHandler.setTargetComponentName(pkgName, activityName);

        return this;
    }


}
