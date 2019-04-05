package com.supcon.common.view.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LogUtil {
    /**
     * 日志打印开关，开发与调试为true，在正式版发布时为false
     */
    public static boolean showLog = true;

    /**
     * 统一的标签
     */
    private final static String TAG = "common";

    /**
     * 获取日志开关
     */
    public static boolean isShowlog() {
        return showLog;
    }

    /**
     * 打印出错堆栈信息
     */
    public static void showException(Exception e) {
        if (e == null) {
            return;
        }
        if (showLog)
            e.printStackTrace();
    }

    /**
     * 打印出错堆栈信息
     */
    public static void showException(OutOfMemoryError e) {
        if (e == null) {
            return;
        }

        if (showLog)
            e.printStackTrace();
    }

    /**
     * VERBOSE日志，显示为黑色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void v(String tag, String content) {
        if (showLog)
            Log.v(tag, content);
    }

    /**
     * INFO日志，显示为绿色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void i(String tag, String content) {
        if (showLog)
            Log.i(tag, content);
    }

    /**
     * DEBUG日志，显示为蓝色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void d(String tag, String content) {
        if (showLog)
            Log.d(tag, content);
    }

    /**
     * WARNING日志，显示为橘色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void w(String tag, String content) {
        if (showLog)
            Log.w(tag, content);
    }

    /**
     * ERROR日志，显示为红色
     *
     * @param tag     标签，可以为类名，可以为对象名，也可以为自己自定义的标签
     * @param content 日志内容
     */
    public static void e(String tag, String content) {
            Log.e(tag, content);
    }

    /**
     * VERBOSE日志，显示为黑色
     *
     * @param content 日志内容
     */
    public static void v(String content) {
        v(TAG, content);
    }

    /**
     * INFO日志，显示为绿色
     *
     * @param content 日志内容
     */
    public static void i(String content) {
        i(TAG, content);
    }

    /**
     * DEBUG日志，显示为蓝色
     *
     * @param content 日志内容
     */
    public static void d(String content) {
        d(TAG, content);
    }

    /**
     * WARNING日志，显示为橘色
     *
     * @param content 日志内容
     */
    public static void w(String content) {
        w(TAG, content);
    }

    /**
     * ERROR日志，显示为红色
     *
     * @param content 日志内容
     */
    public static void e(String content) {
        e(TAG, content);
    }

    /**
     * 显示列表（int数组）
     */
    public static String showIntArr(int[] list) {
        if (list == null) {
            return "null";
        }
        if (list.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            result.append("" + list[i] + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（byte数组）
     */
    public static String showByteArr(byte[] list) {
        if (list == null) {
            return "null";
        }
        if (list.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            result.append(list[i] + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示列表（float数组）
     */
    public static String showFloatArr(float[] list) {
        if (list == null) {
            return "null";
        }
        if (list.length == 0) {
            return "";
        }

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < list.length; i++) {
            result.append("" + list[i] + ",");
        }
        result.delete(result.length() - 1, result.length());
        return result.toString();
    }

    /**
     * 显示填充字符串
     *
     * @param original 填充前的字符串，以$1s,$2s表示要填充的地方
     * @param params   要填充的字符，对应$1s,$2s
     * @return 填充后的字符串<br>
     * <p/>
     * 比如，original传入“今天是$1s月$2s号”,params传入{6,4}，返回字符串为"今天是6月4号".
     */
    public static String getString(String original, String[] params) {
        if (original == null || original.length() <= 0 || params == null
                || params.length <= 0)
            return original;

        String result = original;

        for (int i = 0; i < params.length; i++) {
            String old = "$" + (i + 1) + "s";
            result = result.replace(old, params[i]);
        }

        return result;
    }

    /**
     * 显示填充字符串
     *
     * @param original 填充前的字符串，以$1s,$2s表示要填充的地方
     * @param params   要填充的字符，对应$1s,$2s
     * @return 填充后的字符串<br>
     * <p/>
     * 比如，original传入“今天是$1s月$2s号”,params传入{6,4}，返回字符串为"今天是6月4号".
     */
    public static String getString(String original, int[] params) {
        if (original == null || original.length() <= 0 || params == null
                || params.length <= 0)
            return original;

        String result = new String(original);

        for (int i = 0; i < params.length; i++) {
            String old = "$" + (i + 1) + "s";
            String newS = String.valueOf(params[i]);
            result = result.replace(old, newS);
        }

        return result;
    }

    /**
     * 显示时间（long转化为String）
     *
     * @param time
     * @return 返回为"yyyy-MM-dd HH:mm:ss"格式的时间显示
     */
    public static String showTimeFromMilis(long time) {
        return showTimeFromMilis(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 显示时间（long转化为String）
     *
     * @param time
     * @param formatStr 需要显示的时间格式，比如"yyyy-MM-dd HH:mm:ss"
     * @return 返回指定格式的时间显示
     */
    public static String showTimeFromMilis(long time, String formatStr) {
        String res = "";
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr,
                    Locale.ENGLISH);
            res = sdf.format(cal.getTime());
        } catch (Exception e) {
            // TODO: handle exception
        }

        return res;
    }

    public class MyLogArr<T> {
        /**
         * 显示列表（对象数组）
         */
        public String showArr(T[] list) {
            if (list == null) {
                return "null";
            }
            if (list.length == 0) {
                return "";
            }

            StringBuffer result = new StringBuffer();

            for (int i = 0; i < list.length; i++) {
                result.append(list[i] + ",");
            }
            result.delete(result.length() - 1, result.length());
            return result.toString();
        }

        /**
         * 显示列表
         */
        public String showList(List<T> list) {
            if (list == null) {
                return "null";
            }
            if (list.isEmpty()) {
                return "";
            }

            StringBuffer result = new StringBuffer();

            for (int i = 0; i < list.size(); i++) {
                result.append(list.get(i) + ",");
            }
            result.delete(result.length() - 1, result.length());
            return result.toString();
        }
    }
}
