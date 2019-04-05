package com.supcon.common.view.base.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by wangshizhan on 16/12/1.
 */
public abstract class BaseDataLinearLayout<T> extends BaseLinearLayout {
    public BaseDataLinearLayout(Context context) {
        super(context);
    }

    public BaseDataLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void update(T data);
}
