package com.supcon.common.view.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by Benjamin on 16/5/24.
 */
public class ViewUtils {

    public static int getViewLocationScreenX(View view) {
        return getViewLocationScreenXY(view, 0);
    }

    public static int getViewLocationScreenY(View view) {
        return getViewLocationScreenXY(view, 1);
    }

    public static int getViewLocationWindowX(View view) {
        return getViewLocationWindowXY(view, 0);
    }

    public static int getViewLocationWindowY(View view) {
        return getViewLocationWindowXY(view, 1);
    }

    private static int getViewLocationScreenXY(View view, int index) {
        int[] xy = new int[2];
        view.getLocationOnScreen(xy);
        return xy[index];
    }

    private static int getViewLocationWindowXY(View view, int index) {
        int[] xy = new int[2];
        view.getLocationInWindow(xy);
        return xy[index];
    }

    public static int getId(Context paramContext, String className, String resName)
    {
        try
        {
            Class localClass = Class.forName(paramContext.getPackageName() + ".R$" + className);
            Field localField = localClass.getField(resName);
            int i = Integer.parseInt(localField.get(localField.getName()).toString());
            return i;
        } catch (Exception localException)
        {
            Log.e("getIdByReflection error", localException.getMessage());
        }

        return 0;
    }


}
