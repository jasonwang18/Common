package com.supcon.common.view.view.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.app.annotation.custom.OnDateChange;
import com.app.annotation.custom.OnTextChange;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.supcon.common.view.base.controller.BaseController;
import com.supcon.common.view.listener.OnChildViewClickListener;
import com.supcon.common.view.util.DateUtils;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.view.picker.DatePicker;
import com.supcon.common.view.view.picker.DateTimePicker;
import com.supcon.common.view.view.picker.SinglePicker;
import com.supcon.common.view.view.picker.widget.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private Activity context;
    private SinglePicker<String> picker;
    private DateTimePicker dateTimePicker;
    private Map<String, Object> pickerConfig;

    public CustomViewController(Activity context) {
        this.context = context;

    }

    @Override
    public void initData() {
        super.initData();

    }

    public void setPickerConfig(Map<String, Object> pickerConfig) {
        this.pickerConfig = pickerConfig;
    }

    private Map<String, Object> defaultConfig(){

        if(pickerConfig != null){
            return pickerConfig;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("isCanceledOutsideEnable", false);
        params.put("isCycleEnable", true);
        params.put("isDividerVisible", true);
        params.put("isSecondVisible", false);
        params.put("format", OnDateChange.DATE_TIME);
        params.put("textSize", 18);

        return params;

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

                            if(TextUtils.isEmpty(charSequence)){
                                onResultListener.onResult(null);
                            }
                            else
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

    public CustomViewController addSpinner(ICustomView iCustomView, Map<String, Object> params, final OnContentCallback<String> callback, final OnResultListener<String> resultListener){

//        final String currentValue = (String) params.get("current");
        if(params==null){
            params = defaultConfig();
        }
        String[] values = (String[]) params.get("values");

        final List<String> list = Arrays.asList(values);
        final SinglePicker<String> picker = getPicker(params);
        picker.setItems(list);
//        picker.setSelectedIndex(TextUtils.isEmpty(currentValue)?0:list.indexOf(currentValue));

        iCustomView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (action != -1) {
                    picker.setSelectedIndex(TextUtils.isEmpty(callback.getContent())?0:list.indexOf(callback.getContent()));
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



    public CustomViewController addDate(ICustomView iCustomView, Map<String, Object> params, final OnContentCallback<Object> callback, final OnResultListener<String> resultListener){
        if(params==null){
            params = defaultConfig();
        }
        final boolean isSecondVisible = (boolean) params.get("isSecondVisible");
        final String format = (String) params.get("format");
//        Object currentValue = params.get("current");
//
//        String[] dateStrs = null;
//        if(currentValue != null && currentValue instanceof String){
//            String current = (String) currentValue;
//            if(TextUtils.isDigitsOnly(current)){
//                try {
//                    dateStrs = DateUtils.dateFormat(Long.parseLong(current), "yyyy MM dd HH mm ss").split(" ");
//                }
//                catch (NumberFormatException e){
//                    dateStrs = DateUtils.dateFormat(System.currentTimeMillis(), "yyyy MM dd HH mm ss").split(" ");
//                }
//            }
//            else{
//                dateStrs = DateUtils.dateFormat(DateUtils.dateFormat(current, "yyyy-MM-dd HH:mm:ss"), "yyyy MM dd HH mm ss").split(" ");
//            }
//        }
//        else
//            dateStrs = DateUtils.dateFormat(System.currentTimeMillis(), "yyyy MM dd HH mm ss").split(" ");
//        if(Integer.valueOf(dateStrs[0]) < 2017 || Integer.valueOf(dateStrs[0])>2025){
//            dateStrs[0] = "2018";
//        }
//
//

        final DateTimePicker dateTimePicker = getDateTimePicker(params);

//        if(isSecondVisible)
//            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
//                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
//                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), Integer.valueOf(dateStrs[5]));
//        else
//            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
//                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
//                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]));


        iCustomView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (action != -1) {
                    setDatePickerSelectItem(isSecondVisible, callback.getContent(), dateTimePicker);
                    if(format.equals(OnDateChange.DATE)){
                        ((DatePicker)dateTimePicker).setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                            @Override
                            public void onDatePicked(String year, String month, String day) {
                                String dateStr = year + "-" + month + "-" + day;
                                if (resultListener != null) {
                                    resultListener.onResult(dateStr);
                                }
                            }
                        });
                    }
                    else
                        dateTimePicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                            @Override
                            public void onDateTimePicked(String year, String month, String day, String hour, String minute, String second) {
                                String dateStr = null;
                                if(TextUtils.isEmpty(second))
                                    dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
                                else
                                    dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":"+second;
                                if (resultListener != null) {
                                    resultListener.onResult(dateStr);
                                }
                            }
                        });
                    dateTimePicker.show();
                } else {
                    if (resultListener != null)
                        resultListener.onResult("");
                }
            }
        });

        return this;
    }

    private void setDatePickerSelectItem(boolean isSecondVisible, Object currentValue, DateTimePicker dateTimePicker) {

        String[] dateStrs = null;
        if(currentValue != null){

            if(currentValue instanceof String) {
                String current = (String) currentValue;
                if (TextUtils.isDigitsOnly(current)) {
                    try {
                        dateStrs = DateUtils.dateFormat(Long.parseLong(current), "yyyy MM dd HH mm ss").split(" ");
                    } catch (NumberFormatException e) {
                        dateStrs = DateUtils.dateFormat(System.currentTimeMillis(), "yyyy MM dd HH mm ss").split(" ");
                    }
                } else {
                    dateStrs = DateUtils.dateFormat(DateUtils.dateFormat(current, "yyyy-MM-dd HH:mm:ss"), "yyyy MM dd HH mm ss").split(" ");
                }
            }
            else if(currentValue instanceof Long){
                dateStrs = DateUtils.dateFormat(Long.valueOf(String.valueOf(currentValue)), "yyyy MM dd HH mm ss").split(" ");
            }
            else{
                dateStrs = DateUtils.dateFormat(Long.valueOf(String.valueOf(currentValue)), "yyyy MM dd HH mm ss").split(" ");
            }
        }
        else
            dateStrs = DateUtils.dateFormat(System.currentTimeMillis(), "yyyy MM dd HH mm ss").split(" ");
        if(Integer.valueOf(dateStrs[0]) < 2017 || Integer.valueOf(dateStrs[0])>2025){
            dateStrs[0] = "2018";
        }


        if(isSecondVisible)
            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]), Integer.valueOf(dateStrs[5]));
        else
            dateTimePicker.setSelectedItem(Integer.valueOf(dateStrs[0]),
                    Integer.valueOf(dateStrs[1]), Integer.valueOf(dateStrs[2]),
                    Integer.valueOf(dateStrs[3]), Integer.valueOf(dateStrs[4]));

    }


    @SuppressLint("CheckResult")
    public CustomViewController addDateView(ICustomView customView, final OnContentCallback<Long> callback, final OnResultListener<String> onResultListener){

        customView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (action != -1) {
                    DateTimePicker dateTimePicker = getDateTimePicker(defaultConfig());
                    dateTimePicker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                        @Override
                        public void onDateTimePicked(String year, String month, String day, String hour, String minute, String second) {
                            LogUtil.i(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
                            String dateStr = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00";
//                            long select = DateUtil.dateFormat(dateStr, "yyyy-MM-dd HH:mm:ss");
                            if (onResultListener != null)
                                onResultListener.onResult(dateStr);
                        }
                    });

                    String[] dates = DateUtils.dateFormat(callback.getContent(), "yyyy MM dd HH mm ss").split(" ");

                    dateTimePicker.setSelectedItem(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]), Integer.valueOf(dates[2]),
                            Integer.valueOf(dates[3]), Integer.valueOf(dates[4]), Integer.valueOf(dates[5]));
                    dateTimePicker.show();
                } else {
                    if (onResultListener != null)
                        onResultListener.onResult("");
                }
            }
        });


        return this;
    }

    public CustomViewController addSpinner(ICustomView customView, final List<String> list, final OnContentCallback<String> callback, final OnResultListener<String> onResultListener){

        customView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                if (action != -1) {
                    SinglePicker<String> singlePicker = CustomViewController.this.getPicker(CustomViewController.this.defaultConfig());
                    singlePicker.setItems(list);
                    singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
                        @Override
                        public void onItemPicked(int index, String item) {
                            if (onResultListener != null)
                                onResultListener.onResult(item);
                        }
                    });
                    singlePicker.setSelectedItem(callback.getContent());
                    singlePicker.show();
                } else {
                    if (onResultListener != null)
                        onResultListener.onResult("");
                }
            }
        });
        return this;
    }


    private SinglePicker<String> getPicker(Map<String, Object> params){


        boolean isCanceledOnTouchOutside = (boolean) params.get("isCanceledOutsideEnable");
        boolean isDividerVisible = (boolean) params.get("isDividerVisible");
        boolean isCycleEnable = (boolean) params.get("isCycleEnable");
        int textSize = (int) params.get("textSize");
        int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
        int textColorFocus = WheelView.TEXT_COLOR_FOCUS;

        if(picker == null)
            picker = new SinglePicker<>(context, new ArrayList<String>());
        picker.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        picker.setCycleDisable(!isCycleEnable);

        picker.setDividerVisible(isDividerVisible);
        picker.setTextSize(textSize);
        picker.setTextColor(textColorFocus, textColorNormal);

        return picker;
    }


    public DateTimePicker getDateTimePicker(Map<String, Object> params) {
        boolean isCanceledOnTouchOutside = (boolean) params.get("isCanceledOutsideEnable");
        boolean isDividerVisible = (boolean) params.get("isDividerVisible");
        boolean isCycleEnable = (boolean) params.get("isCycleEnable");
        int textSize = (int) params.get("textSize");
        int textColorNormal = WheelView.TEXT_COLOR_NORMAL;
        int textColorFocus = WheelView.TEXT_COLOR_FOCUS;
        String format = (String) params.get("format");

        if(dateTimePicker == null) {

            if(format.equals(OnDateChange.DATE)){
                dateTimePicker  = new DatePicker(context);
            }
            else {
                dateTimePicker = new DateTimePicker(context, DateTimePicker.HOUR_24);
                dateTimePicker.setTimeRangeStart(0, 0);
                dateTimePicker.setTimeRangeEnd(23, 59);
            }
            dateTimePicker.setDateRangeStart(2017, 1, 1);
            dateTimePicker.setDateRangeEnd(2025, 11, 11);

        }

        dateTimePicker.setDividerVisible(isDividerVisible);
        dateTimePicker.setCycleDisable(!isCycleEnable);
        dateTimePicker.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        dateTimePicker.setTextSize(textSize);
        dateTimePicker.setTextColor(textColorFocus, textColorNormal);

        return dateTimePicker;
    }
}
