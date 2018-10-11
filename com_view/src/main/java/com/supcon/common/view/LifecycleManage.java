package com.supcon.common.view;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshizhan on 16/12/2.
 */
public class LifecycleManage implements Lifecycle {
    private Map<String,Lifecycle> iifecycleMap;

    public LifecycleManage() {
        iifecycleMap = new HashMap<String,Lifecycle>();
    }


    public void register(String key, Lifecycle lifecycle) {
        iifecycleMap.put(key, lifecycle);
    }

    public void unregister(String key) {
        iifecycleMap.remove(key);
    }

    @Override
    public void onInit() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onInit();
        }
    }

    @Override
    public void initView() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initView();
        }
    }

    @Override
    public void initListener() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initListener();
        }
    }

    @Override
    public void initData() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().initData();
        }
    }

    @Override
    public void onStart() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onStart();
        }
    }

    @Override
    public void onStop() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onStop();
        }
    }

    @Override
    public void onResume() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onResume();
        }
    }

    @Override
    public void onPause() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onPause();
        }
    }

    @Override
    public void onDestroy() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onDestroy();
        }

    }

    @Override
    public void onRetry() {
        for (Map.Entry<String, Lifecycle> entry : iifecycleMap.entrySet()) {
            entry.getValue().onRetry();
        }
    }

    public Lifecycle get(String key) {
        return iifecycleMap.get(key);
    }
}
