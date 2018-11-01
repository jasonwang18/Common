package com.supcon.common.view.base.adapter.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.supcon.common.view.util.ViewBinder;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseBaseViewHolder<T> {
    protected View rootView;
    protected int position;
    protected Context context;

    public BaseBaseViewHolder(Context context) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(layoutId(), null);
        if (rootView != null) {
            initBind();
            initView();
            initListener();
            rootView.setTag(this);
        }
    }

    public int getPosition() {
        return position;
    }

    public View getRootView() {
        return rootView;
    }

    /**
     * 子类实现返回布局ID
     *
     * @return
     */
    protected abstract int layoutId();

    /**
     * 子类实现更新数据
     *
     * @param data 数据
     */
    protected abstract void update(T data);

    /**
     * 初始化bind, 不然view找不到
     */
    protected void initBind() {
        ViewBinder.bindTag(this, rootView);
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {
        ViewBinder.bindCustomView(this, rootView);
        ViewBinder.bindCustomView(this, rootView);
    }

    /**
     * 子控件点击事件
     *
     * @param childView 事件子控件
     * @param action    活动类型
     * @param obj       额外数据
     */
    protected abstract void onItemChildViewClick(View childView, int action, Object obj);

    /**
     * 子控件点击事件
     *
     * @param childView
     * @param action
     */
    protected void onItemChildViewClick(View childView, int action) {
        onItemChildViewClick(childView, action, null);
    }

}
