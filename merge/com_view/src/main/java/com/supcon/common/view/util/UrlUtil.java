package com.supcon.common.view.util;

import android.net.Uri;

/**
 * Created by wangshizhan on 2019/1/10
 * Email:wangshizhan@supcom.com
 */
public class UrlUtil {

    public static long parseIdFormUrl(String url, String key){

        Uri uri = Uri.parse(url);
        String parameter = uri.getQueryParameter(key);
        try {
            if (parameter != null) {

                return Long.parseLong(parameter);
            }
        }
        catch (Exception e){
            return 0;
        }

        return 0;
    }

    public static String parseParamFormUrl(String url, String key){

        Uri uri = Uri.parse(url);
        String parameter = uri.getQueryParameter(key);
        try {
            if (parameter != null) {

                return parameter;
            }
        }
        catch (Exception e){
            return null;
        }

        return null;
    }
}
