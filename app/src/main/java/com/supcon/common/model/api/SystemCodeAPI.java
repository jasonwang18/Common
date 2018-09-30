package com.supcon.common.model.api;

import com.app.annotation.apt.ContractFactory;
import com.supcon.common.com_http.NullEntity;
import com.supcon.common.model.bean.TestEntity;

/**
 * Created by wangshizhan on 2018/7/18
 * Email:wangshizhan@supcom.com
 */
@ContractFactory(entites = {String.class, NullEntity.class, TestEntity.class})
public interface SystemCodeAPI {

    void getSystemCodeList(String entityCode);
    void getNullEntity(String entityCode);
    void getTestEntity(String entityCode);
}