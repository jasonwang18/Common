package com.supcon.common.com_router.router;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshizhan on 2017/7/28.
 */

public class ServiceRouter implements InvocationHandler{

    private static Map<String, Object> mServiceMap = new HashMap<>();


/*    private static class ServiceRouterHolder{
        private static ServiceRouter instance = new ServiceRouter();
    }


    public static ServiceRouter getInstance() {

        return ServiceRouterHolder.instance;
    }*/

    public ServiceRouter() {
    }



    /**
     * 注册一个服务，提供给业务层使用
     * 服务必须继承API中的接口
     * @param service
     */
    public static void register(Object service){

        Class<?>[] interfaces = service.getClass().getInterfaces();

        if(interfaces == null && interfaces.length ==0){
            return;
        }

        if(!mServiceMap.containsKey(interfaces[0].getName()))
            mServiceMap.put(interfaces[0].getName(), service);

    }



    /**
     * 注销一个服务，提供给业务层使用
     * 服务必须继承API中的接口
     * @param service
     */
    public static void unRegister(Object service){

        Class<?>[] interfaces = service.getClass().getInterfaces();

        if(interfaces == null && interfaces.length ==0){
            return;
        }

        if(mServiceMap.containsKey(interfaces[0].getName()))
            mServiceMap.remove(interfaces[0].getName());

    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        String serviceName = o.getClass().getInterfaces()[0].getName();

        Object service = mServiceMap.get(serviceName);

        if(service!=null){
            return method.invoke(service, objects);
        }


        return null;
    }

    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
                this);
    }
}
