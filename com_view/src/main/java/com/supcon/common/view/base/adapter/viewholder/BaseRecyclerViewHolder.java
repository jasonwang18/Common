package com.supcon.common.view.base.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.listener.OnItemChildViewClickListener;
import com.supcon.common.view.util.ViewBinder;

import java.lang.reflect.Field;

/**
 * Created by wangshizhan on 16/12/1.
 */
public  abstract class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {
    protected OnItemChildViewClickListener onItemChildViewClickListener;

    public BaseRecyclerViewHolder(Context context) {
        this(context,null);
    }

    public BaseRecyclerViewHolder(Context context, ViewGroup parent) {
        super(new View(context));
        View view = LayoutInflater.from(context).inflate(layoutId(), parent,false);
        try {
            Field field =  getClass().getField("itemView");
            field.setAccessible(true);
            field.set(this,view);
            field.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        init();
    }

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        init();
    }

    private void init() {
        if( itemView != null ) {
            initBind();
            initView();
            initListener();
        }
    }

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    public void notifyChanged(T data) {
        update(data);
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
        ViewBinder.bind(this, itemView);
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
        ViewBinder.bindListener(this, itemView);

    }
    /**
     * 子控件点击事件
     *
     * @param childView 事件子控件
     * @param action    活动类型
     * @param obj       额外数据
     */
    protected void onItemChildViewClick(View childView, int action, Object obj) {

        if (onItemChildViewClickListener != null)
            onItemChildViewClickListener.onItemChildViewClick(childView, getAdapterPosition(), action, obj);
    }

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
