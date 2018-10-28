package com.supcon.common.view.util;

import java.lang.reflect.Constructor;

/**
 * Created by wangshizhan on 16/4/30.
 */
public class InstanceUtil {

    /**
     * 通过实例工厂去实例化相应类
     *
     * @param <T> 返回实例的泛型类型
     * @return
     */
    public static <T> T getInstance(Class<T> clazz) {
        try {
//            return (T) InstanceFactory.create(clazz);
//            Constructor<T> constructor = clazz.getConstructor();
            return clazz.newInstance();

//            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
