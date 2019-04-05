package com.app.annotation.apt;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */

@Retention(RetentionPolicy.CLASS)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface Route
{
    int extras();
    String group();
    String name();
    String path();
    int priority();
}
