package com.app.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为自定义视图添加OnChildViewClickListener
 * Created by wangshizhan on 2018/10/26
 * Email:wangshizhan@supcom.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnChild {
    String[] views();
}
/*
    @OnChild(views = "itemCustom")
    public void onChild(View childView, int action, Object obj){

    }

 */
