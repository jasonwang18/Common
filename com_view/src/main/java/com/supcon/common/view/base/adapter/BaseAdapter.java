package com.supcon.common.view.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.base.adapter.viewholder.BaseViewHolder;
import com.supcon.common.view.listener.OnItemChildViewClickListener;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected Context context;
    protected OnItemChildViewClickListener onItemChildViewClickListener;

    @Override
    public abstract T getItem(int i);

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    protected BaseViewHolder<T> getViewHolder(int position) {
        return null;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        BaseViewHolder<T> viewHolder = null;
        if (view == null) {
            viewHolder = getViewHolder(position);
            viewHolder.setOnItemChildViewClickListener(onItemChildViewClickListener);
        } else {
            viewHolder = (BaseViewHolder<T>) view.getTag();
        }
        viewHolder.update(position, getItem(position));
        return viewHolder.getRootView();
    }
}
