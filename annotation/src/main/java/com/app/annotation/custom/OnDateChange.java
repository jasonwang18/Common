package com.app.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CustomDateView/CustomVerticalDateView edit result
 * Created by wangshizhan on 2018/10/26
 * Email:wangshizhan@supcom.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OnDateChange {

    int DEFAULT = 18;
    int BIG = 22;
    int SMALL = 16;

    int TYPE_LONG = 0;
    int TYPE_DATE = 1;

    int type() default TYPE_LONG;


    int textSize() default DEFAULT;
    boolean dividerVisble() default false;
    boolean cycleEnable() default true;
    boolean cancelOutsideEnable() default false;
    boolean secondVisible() default false;
    String param();
}
