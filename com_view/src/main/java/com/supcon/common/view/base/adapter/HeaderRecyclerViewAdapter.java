package com.supcon.common.view.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.listener.OnItemChildViewClickListener;

import java.util.List;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class HeaderRecyclerViewAdapter<T> extends BaseListDataRecyclerViewAdapter<T> {
    protected BaseRecyclerViewHolder<T> headerViewHolder;
    private static final int VIEW_HEADER = -1;

    public HeaderRecyclerViewAdapter(Context context) {
        super(context);
    }

    public HeaderRecyclerViewAdapter(Context context, List<T> list) {
        super(context);
        this.list = list;
    }


    public void addHeaderView(View headerView) {
        if (headerView != null) {
            headerViewHolder = new HeaderViewHolder<>(headerView);
        } else {
            headerViewHolder = null;
        }
    }

    protected class HeaderViewHolder<T> extends BaseRecyclerViewHolder<T> {

        public HeaderViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        @Override
        protected int layoutId() {
            return 0;
        }

        @Override
        protected void update(T data) {

        }
    }

    protected int getPositionForHolder(BaseRecyclerViewHolder<T> holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            return position;
        } else if (headerViewHolder != null) {
            return position - 1;
        } else
            return position;
    }


    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if (headerViewHolder != null) {
            return ++count;
        } else
            return count;
    }

    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return super.onCreateViewHolder(parent, viewType);
        } else if (viewType == VIEW_HEADER) {
            return headerViewHolder;
        }
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    public int getItemViewType(int position) {
        if (headerViewHolder != null && position == 0) {
            return VIEW_HEADER;
        }
        return super.getItemViewType(headerViewHolder == null ? position : position - 1);
    }


    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder<T> holder, int position) {
        super.onBindViewHolder(holder, getPositionForHolder(holder, position));
    }

    @Override
    public void setOnItemChildViewClickListener(final OnItemChildViewClickListener onItemChildViewClickListener) {
        super.setOnItemChildViewClickListener(new OnItemChildViewClickListener() {
            @Override
            public void onItemChildViewClick(View childView, int position, int action, Object obj) {
                onItemChildViewClickListener.onItemChildViewClick(
                        childView,
                        HeaderRecyclerViewAdapter.this.headerViewHolder != null ? position - 1 : position,
                        action,
                        obj);
            }
        });
    }
}
