package com.supcon.common.view.listener;

import android.view.View;

/**
 * Created by wangshizhan on 16/12/1.
 */
public interface OnChildViewClickListener {

    /**
     * view 内子控件点击事件监听回调
     * @param childView  子控件
     * @param action 活动类型
     * @param obj  额外数据
     */
   void onChildViewClick(View childView, int action, Object obj);
}
