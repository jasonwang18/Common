package com.supcon.common.view.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.supcon.common.view.listener.OnChildViewClickListener;


/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseRelativeLayout extends RelativeLayout {
    protected OnChildViewClickListener onChildViewClickListener;
    protected View rootView;
    protected Context context;

    public BaseRelativeLayout(Context context) {
        super(context);
        init(context, null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener) {
        this.onChildViewClickListener = onChildViewClickListener;
    }

    protected void onChildViewClick(View childView, int action, Object obj) {
        if (onChildViewClickListener != null)
            onChildViewClickListener.onChildViewClick(childView, action, obj);
    }

    protected void onChildViewClick(View childView, int action) {
        onChildViewClick(childView, action, null);
    }

    protected abstract int layoutId();

    protected void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (layoutId() != 0) {
            rootView = LayoutInflater.from(context).inflate(layoutId(), this, true);
            if (rootView != null) {

            }
            if (attrs != null)
                initAttributeSet(attrs);
        }
        else{
            throw new IllegalArgumentException("layoutId cannot return 0!");
        }
        initView();
        initListener();
        initData();
    }

    protected void initAttributeSet(AttributeSet attrs) {

    }

    protected void initView() {

    }

    protected void initListener() {

    }

    protected void initData() {

    }


}
