package com.supcon.common.view.base.adapter;

import java.util.List;

/**
 * Created by wangshizhan on 16/12/1.
 */
public interface IListAdapter<T> {
    void setList(List<T> list);
    void addData(T t);
    void addList(List<T> list);
    void remove(int index);
    void remove(T t);
    void clear();
    void notifyDataSetChanged();
    List<T> getList();
}
