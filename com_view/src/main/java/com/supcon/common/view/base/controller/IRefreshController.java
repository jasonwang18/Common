package com.supcon.common.view.base.controller;

import android.view.View;

import com.supcon.common.view.Lifecycle;
import com.supcon.common.view.listener.OnRefreshListener;

/**
 * Created by wangshizhan on 17/6/29.
 */

public  interface IRefreshController extends Lifecycle {

    void setOnRefreshListener(OnRefreshListener onRefreshListener);
    void setPullDownRefreshEnabled(boolean enabled);
    void setAutoPullDownRefresh(boolean autoPullDownRefresh);

    void setHeader(View header);
    void setPinContent(boolean isPinContent);

    void refreshBegin();
    void refreshComplete();

}
