package com.app.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CustomEditText/CustomVerticalEditText edit result
 * Created by wangshizhan on 2018/10/26
 * Email:wangshizhan@supcom.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OnTextChange {

    int DEFAULT = 500;
    int LONG = 2000;
    int SHORT = 200;


    int debouce() default DEFAULT;
    String param();

}
