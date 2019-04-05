package com.supcon.common.view.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.base.adapter.viewholder.BaseViewHolder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class MicroUpdateListViewAdapter<T> extends BaseListViewAdapter<T> {

    protected Map<BaseViewHolder<T>, T> viewHolders = Collections.synchronizedMap(new WeakHashMap<BaseViewHolder<T>, T>());

    public MicroUpdateListViewAdapter(Context context) {
        super(context);
    }

    public MicroUpdateListViewAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View view1 = super.getView(position, view, viewGroup);
        viewHolders.put((BaseViewHolder<T>) view1.getTag(), getItem(position));
        return view1;
    }

    public void notifyDataSetChanged(T t) {
        BaseViewHolder<T> viewHolder = findViewHolder(t);
        if (viewHolder != null) {
            viewHolder.update(viewHolder.getPosition(), t);
        }
    }

    public BaseViewHolder<T> findViewHolder(T t) {
        Iterator<Map.Entry<BaseViewHolder<T>, T>> entries = viewHolders.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<BaseViewHolder<T>, T> entry = entries.next();
            if (compareEqual(entry.getValue(), t))
                return entry.getKey();
        }
        return null;
    }



    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        viewHolders.clear();
    }

    @Override
    public void notifyDataSetInvalidated() {
        viewHolders.clear();
        super.notifyDataSetInvalidated();
    }

    @Override
    public void notifyDataSetChanged() {
        viewHolders.clear();
        super.notifyDataSetChanged();
    }
}
