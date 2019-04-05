package com.supcon.common.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.supcon.common.R;
import com.supcon.common.view.base.view.BaseLinearLayout;
import com.supcon.common.view.view.custom.ICustomView;

/**
 * Created by wangshizhan on 2018/10/29
 * Email:wangshizhan@supcom.com
 */
public class CustomView extends BaseLinearLayout implements ICustomView{

    EditText customEdit;
    TextView customSpinner;
    TextView customDate;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int layoutId() {
        return R.layout.v_custom;
    }

    @Override
    protected void initListener() {
        super.initListener();

        findViewById(R.id.customText).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView.this.onChildViewClick(CustomView.this.findViewById(R.id.customText), 0, "customText");
            }
        });
        customSpinner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView.this.onChildViewClick(customSpinner, 0);
            }
        });
        customDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView.this.onChildViewClick(customDate, 0);
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        customEdit = findViewById(R.id.customEdit);
        customDate = findViewById(R.id.customDate);
        customSpinner = findViewById(R.id.customSpinner);

    }

    @Override
    public void setEditable(boolean isEditable) {

    }

    @Override
    public void setNecessary(boolean isNecessary) {

    }

    @Override
    public void setIntercept(boolean isIntercept) {

    }

    @Override
    public boolean isNecessary() {
        return false;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isIntercept() {
        return false;
    }

    @Override
    public void setInputType(int type) {

    }

    @Override
    public void setKey(String key) {

    }

    @Override
    public void setKey(int keyResId) {

    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getContent() {
        return null;
    }

    @Override
    public void setContent(String content) {
        customEdit.setText(content);
        customSpinner.setText(content);
    }

    @Override
    public void setContent(int contentResId) {

    }

    @Override
    public void setContentTextStyle(int textStyle) {

    }

    @Override
    public void setTextFont(Typeface newFont) {

    }

    @Override
    public void setKeyTextSize(int textSize) {

    }

    @Override
    public void setContentTextSize(int textSize) {

    }

    @Override
    public void setKeyTextColor(int color) {

    }

    @Override
    public void setContentTextColor(int color) {

    }

    @Override
    public void setContentPadding(int left, int top, int right, int bottom) {

    }

    @Override
    public void setKeyTextStyle(int textStyle) {

    }

    @Override
    public void setEditIcon(int resId) {

    }

    @Override
    public void setClearIcon(int resId) {

    }

    @Override
    public void setKeyWidth(int width) {

    }

    @Override
    public void setKeyHeight(int height) {

    }

    @Override
    public EditText editText() {
        return customEdit;
    }

    @Override
    public TextView contentView() {
        return null;
    }

    @Override
    public TextView keyView() {
        return null;
    }

    @Override
    public void setContentGravity(int gravity) {

    }
}
