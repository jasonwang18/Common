package com.app.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * CustomSpinner/CustomVerticalSpinner edit result
 * Created by wangshizhan on 2018/10/26
 * Email:wangshizhan@supcom.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OnItemSelect {

    int DEFAULT = 18;
    int BIG = 22;
    int small = 16;

    int textSize() default DEFAULT;
    boolean dividerVisble() default false;
    boolean cycleEnable() default true;
    boolean cancelOutsideEnable() default false;
    String[] values();
    String current();
    String param();

}
