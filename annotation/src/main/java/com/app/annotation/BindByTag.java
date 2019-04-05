package com.app.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by wangshizhan on 2017/12/8.
 * Email:wangshizhan@supcon.com
 */

@Retention(CLASS) @Target(FIELD)
public @interface BindByTag {
    /** View ID to which the field will be bound. */
    String value();
}