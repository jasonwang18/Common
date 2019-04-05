package com.app.annotation.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 为RecyclerView Adapter 添加 OnItemChildViewClickListener
 * Created by wangshizhan on 2018/10/26
 * Email:wangshizhan@supcom.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnItemChild {

//    String adapter();

}

/*
    @OnItemChild
    public void onChild(View childView, int position, int action, Object obj){

    }

 */
