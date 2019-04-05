package com.supcon.common.view.view.loader.base;

/**
 * Created by wangshizhan on 2018/1/12.
 * Email:wangshizhan@supcon.com
 */

public interface ILoaderController {

    void showLoader(String msg);
    void closeLoader();
    void showMsg(String msg, long timeDelayToFinish);
    void showMsgAndclose(String msg, boolean isSuccess, long timeDelayToFinish);
    void showMsgAndclose(String msg, boolean isSuccess, long timeDelayToFinish, OnLoaderFinishListener listener);
}
