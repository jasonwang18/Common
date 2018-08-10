package com.supcon.common.com_router.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author weishu
 * @dete 16/1/7.
 */
/* package */
public class IActivityManagerHandler implements InvocationHandler {

    private static final String TAG = "Common_Router";

    Object mBase;

    private static ComponentName mTargetComponentName;

    public IActivityManagerHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

//        Log.i("GameService", "method:"+method);

        if ("startActivity".equals(method.getName())) {
/*            // 找到参数里面的第一个Intent 对象
            Pair<Integer, Intent> integerIntentPair = foundFirstIntentOfArgs(args);
            Intent newIntent = new Intent();

            if(mTargetComponentName == null){
                return method.invoke(mBase, args);
            }
            newIntent.setComponent(mTargetComponentName);

            // 把我们原始要启动的TargetService先存起来
            newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT, integerIntentPair.second);

            Log.i(TAG,"---oldIntent---"+integerIntentPair.first.toString());
            // 替换掉Intent, 达到欺骗AMS的目的
            args[integerIntentPair.first] = newIntent;

            Log.v(TAG, "hook method startActivity success");*/

            Intent raw;
            int index = 0;

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }
            raw = (Intent) args[index];

            Intent newIntent = new Intent();

            // 替身Activity的包名, 也就是我们自己的包名
            // 这里我们把启动的Activity临时替换为 StubActivity
            if(mTargetComponentName == null){
                return method.invoke(mBase, args);
            }
            newIntent.setComponent(mTargetComponentName);

            // 把我们原始要启动的TargetActivity先存起来
            newIntent.putExtra(AMSHookHelper.EXTRA_TARGET_INTENT, raw);

            // 替换掉Intent, 达到欺骗AMS的目的
            args[index] = newIntent;

        }

        return method.invoke(mBase, args);
    }

    private Pair<Integer, Intent> foundFirstIntentOfArgs(Object... args) {
        int index = 0;

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Intent) {
                index = i;
                break;
            }
        }
        return Pair.create(index, (Intent) args[index]);
    }

    public static void setTargetComponentName(String pkgName, String activityName){

        if(TextUtils.isEmpty(pkgName) || TextUtils.isEmpty(activityName)){
            return;
        }

        mTargetComponentName = new ComponentName(pkgName, activityName);

    }


}
