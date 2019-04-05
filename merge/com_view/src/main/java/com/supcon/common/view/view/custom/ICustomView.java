package com.supcon.common.view.view.custom;

import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.TextView;

import com.supcon.common.view.listener.OnChildViewClickListener;

/**
 * Created by wangshizhan on 2018/10/18
 * Email:wangshizhan@supcom.com
 */
public interface ICustomView {

    void setEditable(boolean isEditable);
    void setNecessary(boolean isNecessary);
    void setIntercept(boolean isIntercept);
    boolean isNecessary();
    boolean isEditable();
    boolean isEmpty();
    boolean isIntercept();
    void setInputType(int type);
    void setKey(String key);
    void setKey(int keyResId);
    String getKey();
    String getContent();
    void setContent(String content);
    void setContent(int contentResId);
    void setContentTextStyle(int textStyle);
    void setTextFont(Typeface newFont);
    void setKeyTextSize(int textSize);
    void setContentTextSize(int textSize);
    void setKeyTextColor(int color);
    void setContentTextColor(int color);
    void setContentPadding(int left, int top, int right, int bottom);
    void setKeyTextStyle(int textStyle);
    void setEditIcon(int resId);
    void setClearIcon(int resId);
    void setKeyWidth(int width);
    void setKeyHeight(int height);
    EditText editText();
    TextView contentView();
    TextView keyView();

    void setContentGravity(int gravity);

    void setOnChildViewClickListener(OnChildViewClickListener onChildViewClickListener);
}
