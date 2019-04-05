package com.supcon.common.view.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class DisplayUtil {

    public static DisplayMetrics getMetrics(Context mContext) {

        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);

        return metric;
    }

    public static int getScreenWidth(Context mContext) {
        return getMetrics(mContext).widthPixels;
    }

    public static int getScreenHeight(Context mContext) {
        return getMetrics(mContext).heightPixels;
    }

    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (ev.getX() < location[0] || ev.getX() > (location[0] + view.getWidth()) || ev.getY() < location[1] || ev.getY() > (location[1] + view.getHeight())) {
            return false;
        }
        return true;
    }

    public static boolean newInRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (ev.getRawX() < location[0] || ev.getRawX() > (location[0] + view.getWidth()) || ev.getRawY() < location[1] || ev.getRawY() > (location[1] + view.getHeight())) {
            return false;
        }
        return true;
    }

    public static void calcDPI(Context mContext) {
        DisplayMetrics metric = getMetrics(mContext);

        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
    }

    /**
     * 将px或dp值转换为dip值，保证尺寸大小不变
     */
    public static int px2dip(float pxValue, Context mContext) {
        final float scale = getMetrics(mContext).density;
        int dip = (int) (pxValue / scale + 0.5f);
        return dip;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(float dipValue, Context mContext) {
        final float scale = getMetrics(mContext).density;
        int px = (int) (dipValue * scale + 0.5f);
        // LogUtil.d(DisplayUtil.class, "dip2px-->" + px);
        return px;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue, Context mContext) {
        final float fontScale = getMetrics(mContext).scaledDensity;
        int sp = (int) (pxValue / fontScale + 0.5f);
        return sp;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue, Context mContext) {
        final float fontScale = getMetrics(mContext).scaledDensity;
        int px = (int) (spValue * fontScale + 0.5f);
        return px;
    }

}
