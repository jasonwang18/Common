package com.supcon.common.com_http;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wangshizhan on 2017/8/4.
 */

public class BaseEntity implements Serializable{

    static final long serialVersionUID=536871008L;


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
