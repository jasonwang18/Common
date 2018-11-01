package com.supcon.common.ui.adapter;

import android.content.Context;
import android.view.View;

import com.app.annotation.BindByTag;
import com.app.annotation.custom.OnChild;
import com.supcon.common.R;
import com.supcon.common.model.bean.TestEntity;
import com.supcon.common.ui.view.CustomView;
import com.supcon.common.view.base.adapter.BaseListDataRecyclerViewAdapter;
import com.supcon.common.view.base.adapter.viewholder.BaseRecyclerViewHolder;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.LogUtils;

import java.util.List;

/**
 * Created by wangshizhan on 2018/10/29
 * Email:wangshizhan@supcom.com
 */
public class TestAdapter extends BaseListDataRecyclerViewAdapter<TestEntity> {
    public TestAdapter(Context context) {
        super(context);
    }

    public TestAdapter(Context context, List<TestEntity> list) {
        super(context, list);
    }

    @Override
    protected BaseRecyclerViewHolder getViewHolder(int viewType) {
        return new ViewHolder(context);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<TestEntity>{

        @BindByTag("itemCustom")
        CustomView itemCustom;

        public ViewHolder(Context context) {
            super(context);
        }

        @Override
        protected int layoutId() {
            return R.layout.item_test;
        }

        @Override
        protected void initView() {
            super.initView();
        }

        @Override
        protected void initListener() {
            super.initListener();

        }

        @OnChild(views = "itemCustom")
        public void onChild(View childView, int action, Object obj){
            LogUtil.w("TestAdapter", "onChild action:"+action+" childView:"+childView);
            LogUtils.warn("TestAdapter", "onChild action:"+action+" childView:"+childView);
            onItemChildViewClick(childView, action, obj);
        }

        @Override
        protected void update(TestEntity data) {

        }
    }

}
