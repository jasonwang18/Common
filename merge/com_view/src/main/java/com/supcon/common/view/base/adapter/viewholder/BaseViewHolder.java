package com.supcon.common.view.base.adapter.viewholder;

import android.content.Context;
import android.view.View;

import com.supcon.common.view.listener.OnItemChildViewClickListener;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseViewHolder<T> extends BaseBaseViewHolder<T> {

    protected OnItemChildViewClickListener onItemChildViewClickListener;

    public BaseViewHolder(Context context) {
        super(context);
    }


    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }


    /**
     * 更新数据
     *
     * @param position 索引
     * @param data     数据
     */
    public void update(int position, T data) {
//		AnimationSet set = new AnimationSet(true);
//		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
//		set.addAnimation(alphaAnimation);
//		TranslateAnimation translateAnimation = new TranslateAnimation(DisplayUtil.getScreenWidth(rootView.getContext()) - rootView.getX(), 0, 0, 0);
//		set.addAnimation(translateAnimation);
//		set.setDuration(1000);
//		rootView.setAnimation(set);
//		set.start();
        this.position = position;
        update(data);
    }


    /**
     * 子控件点击事件
     *
     * @param childView 事件子控件
     * @param action    活动类型
     * @param obj       额外数据
     */
    protected void onItemChildViewClick(View childView, int action, Object obj) {

        if (onItemChildViewClickListener != null)
            onItemChildViewClickListener.onItemChildViewClick(childView, position, action, obj);
    }

}
