package com.app.annotation.apt;

/**
 * Created by wangshizhan on 17/12/28.
 * 根据API生成Contract
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Contract {
    Class<?>[] entites();
}
