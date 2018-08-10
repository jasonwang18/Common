package com.supcon.common.com_router.api;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */

public interface IRouterManager {

    void register(String name, Class<?> clazz);
    void unregister(String name);

}
