package com.supcon.common.view.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.supcon.common.view.R;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseListDataRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T> implements IListAdapter<T> {
    protected List<T> list;
    protected boolean isEnableLoadMore = false;
    protected BaseRecyclerViewHolder<T> moreViewHolder;
    public BaseListDataRecyclerViewAdapter(Context context) {
        super(context);
    }

    public BaseListDataRecyclerViewAdapter(Context context, List<T> list) {
        super(context);
        this.list = list;
    }

    public void setLoadMoreView(View moreView) {
        isEnableLoadMore = moreView != null;
        if( moreView != null ) {
            moreViewHolder  = new BaseRecyclerViewHolder<T>(moreView)
            {

                @Override
                protected int layoutId() {
                    return 0;
                }

                @Override
                protected void update(T data) {
                    itemView.callOnClick();
                }
            };
        }
        else {
            moreViewHolder = null;
        }
        if( getListSize() > 0 )
            notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        int count = getListSize();
        if ( isEnableLoadMore && count > 0  )
            ++count;
        return count;
    }

    protected int getListSize() {
        return  list != null ? list.size() : 0;
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setList(List<T> list) {
        this.list = list;
    }


    @Override
    public T getItem(int i) {
        if (list != null && i >= 0 && i < list.size())
            return list.get(i);
        return null;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * 获取数据列表
     *
     * @return 数据列表
     */
    public List<T> getList() {
        return list;
    }

    /**
     * add 数据
     *
     * @param t 追加的数据
     */
    public void addData(T t) {
        if (this.list == null)
            this.list = new ArrayList<T>();
        this.list.add(t);
    }

    /**
     * add 数据
     *
     * @param list 追加的数据
     */
    public void addList(List<T> list) {
        if (this.list == null)
            this.list = list;
        else if (list != null)
            this.list.addAll(list);
    }




    /**
     * 移除一项数据
     *
     * @param index 数据在list的索引
     */
    public void remove(int index) {
        if (this.list != null && index >= 0 && this.list.size() > index)
            list.remove(index);
    }

    /**
     * 移除一项数据
     *
     * @param t 数据实体
     */
    public void remove(T t) {
        for (int i = 0; list != null && i < list.size(); ++i) {
            if (compareEqual(list.get(i), t)) {
                list.remove(i);
                break;
            }
        }
    }

    @Override
    public void clear() {
        if( list != null )
            list.clear();
    }
    /**
     * 比较两数据实体是否相等 默认为对象引用地址比较，如果换比较方式重载本函数
     *
     * @param t  list 中的实体
     * @param t1 比较实体
     * @return
     */
    protected boolean compareEqual(T t, T t1) {
        return t == t1;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder<T> holder, int position) {
        if( position == getListSize() ) {

        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if( position == getListSize() ) {
            return -2;
        }
        return getItemViewType(position,list.get(position));
    }

    @Override
    public BaseRecyclerViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType == -2 ) {
            return  moreViewHolder;
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    public int getItemViewType(int position,T t) {
        return super.getItemViewType(position);
    }




}
