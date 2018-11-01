package com.supcon.common.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.annotation.BindByTag;
import com.app.annotation.apt.Router;
import com.app.annotation.custom.OnChild;
import com.app.annotation.custom.OnItemChild;
import com.app.annotation.custom.OnTextChange;
import com.supcon.common.R;
import com.supcon.common.model.bean.TestEntity;
import com.supcon.common.ui.adapter.TestAdapter;
import com.supcon.common.ui.view.CustomView;
import com.supcon.common.view.base.activity.BaseActivity;
import com.supcon.common.view.base.activity.BaseRefreshRecyclerActivity;
import com.supcon.common.view.base.adapter.IListAdapter;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.LogUtils;
import com.supcon.mes.mbap.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshizhan on 2018/4/28.
 * Email:wangshizhan@supcon.com
 */
@Router("routerTest2")
public class RouterTestActivity2 extends BaseRefreshRecyclerActivity<TestEntity>{

    @BindByTag("contentView")
    RecyclerView contentView;





    @Override
    protected int getLayoutID() {
        return R.layout.activity_router_test;
    }

    @Override
    protected void onInit() {
        super.onInit();
        refreshListController.setAutoPullDownRefresh(false);
        refreshListController.setPullDownRefreshEnabled(false);
    }

    @Override
    protected void initView() {
        super.initView();
        contentView.setLayoutManager(new LinearLayoutManager(context));
        contentView.addItemDecoration(new SpaceItemDecoration(1));
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected IListAdapter<TestEntity> createAdapter() {
        return new TestAdapter(context);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<TestEntity> list = new ArrayList<>();
        TestEntity testEntity = new TestEntity();
        list.add(testEntity);
        refreshListController.refreshComplete(list);
    }

    @OnItemChild
    public void onChild(View childView, int position, int action, Object obj){
        LogUtil.w("mTestAdapter", "OnItemChild action:"+action+" childView:"+childView+" position:"+position);
        LogUtils.warn("mTestAdapter", "OnItemChild action:"+action+" childView:"+childView+" position:"+position);
    }
}
