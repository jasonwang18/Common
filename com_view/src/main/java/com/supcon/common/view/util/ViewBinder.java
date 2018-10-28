package com.supcon.common.view.util;

import android.app.Activity;
import android.view.View;

import com.app.annotation.Bind;
import com.app.annotation.BindByTag;
import com.app.annotation.custom.OnChild;
import com.app.annotation.custom.OnItemChild;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.listener.OnItemChildViewClickListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by wangshizhan on 2018/4/27.
 * Email:wangshizhan@supcon.com
 */

public class ViewBinder {

    public static void bind(Activity activity) {
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(Object target, View source) {
        Field[] fields = target.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) != null) {
                        continue;
                    }

                    Bind bind = field.getAnnotation(Bind.class);
                    if (bind != null) {
                        int viewId = bind.value();
                        field.set(target, source.findViewById(viewId));
                        continue;
                    }

                    BindByTag bindByTag = field.getAnnotation(BindByTag.class);
                    if (bindByTag != null) {
                        String tag = bindByTag.value();
                        field.set(target, source.findViewWithTag(tag));
                        continue;
                    }

                    String fieldName = field.getName();
                    field.set(target, source.findViewWithTag(fieldName));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bindMethods(Object target, View source) {
        Method[] methods = target.getClass().getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                try {
                    method.setAccessible(true);

                    OnChild onChild = method.getAnnotation(OnChild.class);
                    if (onChild != null) {
                        String[] views = onChild.views();
                        for (String viewTag : views) {
                            View custom = source.findViewWithTag(viewTag);

                            Method m = custom.getClass()
                                    .getMethod("setOnChildViewClickListener", new Class[]{OnChildViewClickListener.class});
                            if (m != null) {
                                m.invoke(custom, new DeclaredOnChildViewClickListener(method, target));
                            }
                        }
                    }


                    OnItemChild onItemChild = method.getAnnotation(OnItemChild.class);
                    if (onItemChild != null) {
                        String adapterName = onItemChild.adapter();
                        Object adapter = target.getClass().getField(adapterName);

                        Method m = adapter.getClass()
                                .getMethod("setOnItemChildViewClickListener", new Class[]{OnItemChildViewClickListener.class});
                        if (m != null) {
                            m.invoke(adapter, new DeclaredOnItemChildViewClickListener(method, target));
                        }
                    }

                    continue;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class DeclaredOnChildViewClickListener implements OnChildViewClickListener {

        private Method mMethod;
        private Object mObject;

        public DeclaredOnChildViewClickListener(Method method, Object object) {
            this.mMethod = method;
            this.mObject = object;
        }

        @Override
        public void onChildViewClick(View childView, int action, Object obj) {
            mMethod.setAccessible(true); //这样就不需要强制用户将方法设置为public权限了

            try {
                mMethod.invoke(mObject, childView, action, obj); //注意，这里注入的方法，必须包含Viw v
            } catch (Exception e) {
                e.printStackTrace();//这句可以注释掉，如果上面的出现异常，就在下面捕获吧！
                try {
                    mMethod.invoke(mObject); //在这里注入一个无参的方法 :)
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static class DeclaredOnItemChildViewClickListener implements OnItemChildViewClickListener {

        private Method mMethod;
        private Object mObject;

        public DeclaredOnItemChildViewClickListener(Method method, Object object) {
            this.mMethod = method;
            this.mObject = object;
        }


        @Override
        public void onItemChildViewClick(View childView, int position, int action, Object obj) {
            mMethod.setAccessible(true); //这样就不需要强制用户将方法设置为public权限了

            try {
                mMethod.invoke(mObject, childView, position, action, obj); //注意，这里注入的方法，必须包含Viw v
            } catch (Exception e) {
                e.printStackTrace();//这句可以注释掉，如果上面的出现异常，就在下面捕获吧！
                try {
                    mMethod.invoke(mObject); //在这里注入一个无参的方法 :)
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
