package com.supcon.common.view.base.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wangshizhan on 2018/10/24
 * Email:wangshizhan@supcom.com
 */
public class BaseDataController extends BasePresenterController {

    protected Context context;

    public BaseDataController(Context context){
        this.context = context;

    }

    protected Intent getIntent(){

        return ((Activity)context).getIntent();

    }

    public void attachContext(Context context){
        this.context = context;
    }

}
