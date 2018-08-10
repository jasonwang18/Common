package com.supcon.common.view.util;

import android.app.Activity;
import android.view.View;

import com.app.annotation.Bind;
import com.app.annotation.BindByTag;

import java.lang.reflect.Field;

/**
 * Created by wangshizhan on 2018/4/27.
 * Email:wangshizhan@supcon.com
 */

public class ViewBinder {

    public static void bind(Activity activity) {
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(Object target, View source) {
        Field[] fields = target.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) != null) {
                        continue;
                    }

                    Bind bind = field.getAnnotation(Bind.class);
                    if (bind != null) {
                        int viewId = bind.value();
                        field.set(target, source.findViewById(viewId));
                        continue;
                    }

                    BindByTag bindByTag = field.getAnnotation(BindByTag.class);
                    if (bindByTag != null) {
                        String tag = bindByTag.value();
                        field.set(target, source.findViewWithTag(tag));
                        continue;
                    }

                    String fieldName = field.getName();
                    field.set(target, source.findViewWithTag(fieldName));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}