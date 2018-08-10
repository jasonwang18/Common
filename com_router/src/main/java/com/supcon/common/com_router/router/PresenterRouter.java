package com.supcon.common.com_router.router;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangshizhan on 2017/7/28.
 */

public class PresenterRouter implements InvocationHandler{

    private Map<String, Object> mPresenterMap = new HashMap<>();

//    private static class PresenterRouterHolder{
//        private static PresenterRouter instance = new PresenterRouter();
//    }
//
//
//    public static PresenterRouter getInstance() {
//
//        return PresenterRouterHolder.instance;
//    }

    public PresenterRouter() {
    }


    public <T> T getPresenter(Class<T> clazz){

        Class<?>[] interfaces = clazz.getSuperclass().getInterfaces();

        if(interfaces == null && interfaces.length ==0){
            return null;
        }

        if(mPresenterMap.containsKey(interfaces[0].getName())){
            return (T) mPresenterMap.get(interfaces[0].getName());
        }

        return null;
    }

    public <T> boolean hasPresenter(Class<T> clazz){

        Class<?>[] interfaces = clazz.getSuperclass().getInterfaces();

        if(interfaces == null && interfaces.length ==0){
            return false;
        }


        return mPresenterMap.containsKey(interfaces[0].getName());
    }

    /**
     * 注册一个服务，提供给业务层使用
     * 服务必须继承BasePresenter
     * @param presenter
     */
    public void register(Object presenter){

        Class<?>[] interfaces = presenter.getClass().getSuperclass().getInterfaces();

        if(interfaces == null && interfaces.length ==0){
            return;
        }

        if(mPresenterMap.containsKey(interfaces[0].getName())) {
            mPresenterMap.remove(interfaces[0].getName());
        }
        mPresenterMap.put(interfaces[0].getName(), presenter);
    }


    /**
     * 注销一个服务，提供给业务层使用
     * 服务必须继承BasePresenter
     * @param presenter
     */
    public void unRegister(Object presenter){

        Class<?>[] interfaces = presenter.getClass().getSuperclass().getInterfaces();

        if(interfaces == null && interfaces.length ==0){
            return;
        }

        if(mPresenterMap.containsKey(interfaces[0].getName())) {
            mPresenterMap.remove(interfaces[0].getName());
        }

    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        String presenterName = o.getClass().getInterfaces()[0].getName();
        Object presenter = mPresenterMap.get(presenterName);

        if(presenter!=null){
            return method.invoke(presenter, objects);
        }


        return null;
    }

    public <T> T create(final Class<T> presenter) {
        return (T) Proxy.newProxyInstance(presenter.getClassLoader(), new Class<?>[] { presenter },
                this);
    }


}
