package com.supcon.common.com_router.util;

import android.util.Log;

import com.supcon.common.com_router.api.IRouterManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */

public class RouterManager implements IRouterManager{


    private static class RouterManagerHolder{
        private static RouterManager instance = new RouterManager();
    }


    public static RouterManager getInstance() {

        return RouterManagerHolder.instance;
    }

    private RouterManager() {
    }


    private Map<String, Class<?>> mRouterMap = new HashMap<>();

    @Override
    public void register(String name, Class<?> clazz) {
        if(!mRouterMap.containsKey(name))
            mRouterMap.put(name, clazz);
    }

    @Override
    public void unregister(String name) {
        if(mRouterMap.containsKey(name))
            mRouterMap.remove(name);
    }

    public Class<?> getDestination(String key){
        return  mRouterMap.get(key);
    }
}
