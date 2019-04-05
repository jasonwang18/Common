package com.supcon.common.view.base.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListViewAdapter<T> extends BaseAdapter<T> implements IListAdapter<T> {

    protected List<T> list;

    public BaseListViewAdapter(Context context) {
        super(context);
    }

    public BaseListViewAdapter(Context context, List<T> list) {
        super(context);
        this.list = list;
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
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
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
     * @param t1  list 中的实体
     * @param t1 比较实体
     * @return
     */
    protected  boolean compareEqual(T t1, T t2) {
        if( t1 != null && t2 != null) {
            return t1.equals(t2);
        }
        return false;
    }


}
