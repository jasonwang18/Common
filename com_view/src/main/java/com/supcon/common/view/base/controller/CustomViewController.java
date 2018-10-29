package com.supcon.common.view.base.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.view.listener.ICustomView;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.listener.OnRefreshListener;
import com.supcon.common.view.listener.OnResultListener;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.view.picker.SinglePicker;
import com.supcon.common.view.view.picker.widget.WheelView;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by wangshizhan on 2018/10/20
 * Email:wangshizhan@supcom.com
 */

public class CustomViewController extends BaseController {

    private Context context;

    public CustomViewController(Context context) {
        this.context = context;
    }

    @SuppressLint("CheckResult")
    public CustomViewController addEditView(EditText editText, int debounce, final OnResultListener<String> onResultListener){

        RxTextView.textChanges(editText)
                .skipInitialValue()
                .debounce(debounce, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if(onResultListener!= null){
                            onResultListener.onResult(charSequence.toString());
                        }

                    }
                });

        return this;
    }

    @SuppressLint("CheckResult")
    public CustomViewController addEditView(EditText editText, OnResultListener<String> onResultListener){

        if(editText == null) {
            LogUtil.e("editText == null");
            return this;
        }

        return addEditView(editText, 500, onResultListener);
    }

    public CustomViewController addSpinner(ICustomView iCustomView, Map params, final OnResultListener<String> resultListener){

        boolean isCanceledOnTouchOutside = (boolean) params.get("isCanceledOutsideEnable");
        boolean isDividerVisible = (boolean) params.get("isDividerVisible");
        boolean isCycleEnable = (boolean) params.get("isCycleEnable");
        int textSize = (int) params.get("textSize");
        String currentValue = (String) params.get("current");
        String[] values = (String[]) params.get("values");
        int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
        int textColorFocus = WheelView.TEXT_COLOR_FOCUS;

        List<String> list = Arrays.asList(values);

        final SinglePicker<String> picker = new SinglePicker<>((Activity) context, list);
        picker.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        picker.setCycleDisable(!isCycleEnable);

        picker.setDividerVisible(isDividerVisible);
        picker.setTextSize(textSize);
        picker.setTextColor(textColorFocus, textColorNormal);
        picker.setSelectedIndex(TextUtils.isEmpty(currentValue)?0:list.indexOf(currentValue));

        iCustomView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (action != -1) {
                    picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
                        @Override
                        public void onItemPicked(int index, String item) {
                            if (resultListener != null) {
                                resultListener.onResult(item);
                            }
                        }
                    });
                    picker.show();
                } else {
                    if (resultListener != null)
                        resultListener.onResult("");
                }
            }
        });

        return this;
    }

}
