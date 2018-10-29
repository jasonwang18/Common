package com.supcon.common.view.util;

import android.app.Activity;
import android.graphics.ColorSpace;
import android.util.Log;
import android.view.View;

import com.app.annotation.Bind;
import com.app.annotation.BindByTag;
import com.app.annotation.custom.OnChild;
import com.app.annotation.custom.OnItemChild;
import com.app.annotation.custom.OnItemSelect;
import com.app.annotation.custom.OnTextChange;
import com.supcon.common.view.base.adapter.BaseRecyclerViewAdapter;
import com.supcon.common.view.base.controller.CustomViewController;
import com.supcon.common.view.base.controller.RefreshListController;
import com.supcon.common.view.listener.ICustomView;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.listener.OnResultListener;
import com.supcon.common.view.view.picker.SinglePicker;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshizhan on 2018/4/27.
 * Email:wangshizhan@supcon.com
 */

public class ViewBinder {

    public static void bind(Activity activity) {
        bind(activity, activity.getWindow().getDecorView());
    }

    public static void bind(final Object target, View source) {
        Field[] fields = target.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (final Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(target) != null) {
                        continue;
                    }

                    Bind bind = field.getAnnotation(Bind.class);
                    if (bind != null) {
                        int viewId = bind.value();
                        field.set(target, source.findViewById(viewId));
                        if(field.get(target)!=null){
                            bindCustomView(target, field, source);
                        }
                        continue;
                    }

                    BindByTag bindByTag = field.getAnnotation(BindByTag.class);
                    if (bindByTag != null) {
                        String tag = bindByTag.value();
                        field.set(target, source.findViewWithTag(tag));
                        if(field.get(target)!=null){
                            bindCustomView(target, field, source);
                        }
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

    private static void bindCustomView(final Object target, Field field, View source){
        try {
            field.setAccessible(true);
            if (field.get(target) == null) {
                return;
            }

            CustomViewController customViewController = new CustomViewController(source.getContext());

            final OnItemSelect onItemSelect = field.getAnnotation(OnItemSelect.class);
            if(onItemSelect!=null){
                Object view = field.get(target);
                if(view!=null && view instanceof ICustomView){
                    ICustomView customView = (ICustomView) view;
                    String[] values = onItemSelect.values();
                    final String current = onItemSelect.current();
                    boolean isDividerVisible = onItemSelect.dividerVisble();
                    boolean isCancelOutsideEnable = onItemSelect.cancelOutsideEnable();
                    boolean isCycleEnable = onItemSelect.cycleEnable();
                    int textSize = onItemSelect.textSize();

                    Map<String, Object> params = new HashMap<>();
                    params.put("isCanceledOutsideEnable", isCancelOutsideEnable);
                    params.put("isCycleEnable", isCycleEnable);
                    params.put("isDividerVisible", isDividerVisible);
                    params.put("current", current);
                    params.put("values", values);
                    params.put("textSize", textSize);

                    customViewController.addSpinner(customView, params, new OnResultListener<String>() {
                        @Override
                        public void onResult(String result) {
                            String param = onItemSelect.param();
                            setParam(target, result,  param);
                        }
                    });

                }
            }

            final OnTextChange onTextChange = field.getAnnotation(OnTextChange.class);
            if(onTextChange!=null){
                Object view = field.get(target);
                if(view!=null && view instanceof ICustomView){
                    final ICustomView customView = (ICustomView) view;
                    customViewController
                            .addEditView(customView.editText(), onTextChange.debouce(), new OnResultListener<String>() {
                                @Override
                                public void onResult(String result) {
                                    String param = onTextChange.param();
                                    setParam(target, result, param);
                                }
                            });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setParam(Object target, String result, String param){
        try {

            Field paramField = null;
            if(param.contains(".")){
                Field entityField = target.getClass().getDeclaredField(param.split("\\.")[0]);
                entityField.setAccessible(true);
                Object entity = entityField.get(target);
                if(entity!=null){
                    paramField = entity.getClass().getDeclaredField(param.split("\\.")[1]);

                    if(paramField!= null){
                        paramField.setAccessible(true);
                        paramField.set(entity, result);
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void bindListener(Object target, View source) {
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
                                    .getMethod("setOnChildViewClickListener", OnChildViewClickListener.class);
                            if (m != null) {
                                m.invoke(custom, new DeclaredOnChildViewClickListener(method, target));
                            }
                        }
                    }


                    OnItemChild onItemChild = method.getAnnotation(OnItemChild.class);
                    if (onItemChild != null) {
                        Field refreshListControllerField = getOneField(target, "refreshListController");

                        refreshListControllerField.setAccessible(true);
                        RefreshListController refreshListController = (RefreshListController) refreshListControllerField.get(target);
                        Object adapter = refreshListController.getListAdapter();
                        Method m = adapter.getClass()
                                .getMethod("setOnItemChildViewClickListener", OnItemChildViewClickListener.class);
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
            mMethod.setAccessible(true);

            try {
                mMethod.invoke(mObject, childView, action, obj);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject);
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
            mMethod.setAccessible(true);

            try {
                mMethod.invoke(mObject, childView, position, action, obj);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    private static List<Field> getAllFields(Object target){
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = target.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass .getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }

    private static Field getOneField(Object target, String name){
        Field field = null;
        Class tempClass = target.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            try {
                field = tempClass.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
            }
            if(field != null){
               return field;
           }
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己

        }
        return field;
    }
}
