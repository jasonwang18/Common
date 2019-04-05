package com.supcon.common.view.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.listener.OnItemChildViewClickListener;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder<T>> {
    protected Context context;
    protected ViewGroup parent;
    protected OnItemChildViewClickListener onItemChildViewClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        super();
        this.context = context;
    }

    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    /**
     * 返回 ViewHolder
     *
     * @param viewType ViewHolder类型
     * @return
     */
    protected abstract BaseRecyclerViewHolder<T> getViewHolder(int viewType);

    /**
     * 返回数据
     *
     * @param position 行索引
     * @return
     */
    public abstract T getItem(int position);

    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;//解决inflate时，parent=null布局显示不正常问题
        BaseRecyclerViewHolder<T> viewHolder = getViewHolder(viewType);
        //监听
        viewHolder.setOnItemChildViewClickListener(onItemChildViewClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder<T> holder, int position) {
        holder.notifyChanged(getItem(position));
    }
}
