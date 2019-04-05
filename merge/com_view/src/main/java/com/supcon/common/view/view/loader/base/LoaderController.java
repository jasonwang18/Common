package com.supcon.common.view.view.loader.base;

import android.content.Context;
import android.view.View;

import com.supcon.common.view.base.controller.BaseController;
import com.supcon.common.view.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangshizhan on 2018/1/12.
 * Email:wangshizhan@supcon.com
 */

public class LoaderController extends BaseController implements ILoaderController{

    private ILoader customLoader;
    private ILoader defautLoader;

    private Disposable lock ;
    private static long DEFAULT_LOCK_GAP_TIME = 500;
    private long intervalTime = DEFAULT_LOCK_GAP_TIME;

    public static final int MSG_TYPE_LOADING = 0;
    public static final int MSG_TYPE_LOAD_SUCCESS = MSG_TYPE_LOADING+1;
    public static final int MSG_TYPE_LOAD_FAILED = MSG_TYPE_LOAD_SUCCESS+1;
    public static final int MSG_TYPE_LOAD_CLOSE = MSG_TYPE_LOAD_FAILED+1;

    private List<Msg> mMsgList = new ArrayList<>();
    private OnLoaderFinishListener mOnLoaderFinishListener;

    public LoaderController(Context context, View rootView){
        defautLoader = new DefautLoader(context, rootView);
    }

    public void setIntervalTime(long intervalTime){
        this.intervalTime = intervalTime;
    }

    private void resetLock(){
//        LogUtil.d("resetLock");
        lock = Flowable.timer(intervalTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
//                        LogUtil.d("isDisposed");
                        if(mMsgList.size()!=0) {
                            handleMsg(mMsgList.get(0));
                        }
                    }
                });
    }


    private synchronized boolean isLocked(){

        if(lock == null){
            resetLock();
            return false;
        }

        if(lock.isDisposed()){
            resetLock();
            return false;
        }

        return true;

    }


    private void submit(Msg msg){
        Flowable.just(msg)
                .delay(intervalTime, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Msg>() {
                    @Override
                    public void accept(Msg m) throws Exception {
                        if(mMsgList.size()!=0) {
                            handleMsg(m);
                        }
                    }
                });

    }


    private void handleMsg(Msg m){
        LogUtil.d("handleMsg msg:"+m.mMsg);

        if(m.mMsgType == MSG_TYPE_LOADING) {
            if (customLoader != null) {
                customLoader.showLoader(m.mMsg);
            } else {
                defautLoader.showLoader(m.mMsg);
            }
        }
        else if(m.mMsgType == MSG_TYPE_LOAD_CLOSE){
            closeLoader();
        }
        else if(m.mMsgType == MSG_TYPE_LOAD_SUCCESS){
            if (customLoader != null) {
                customLoader.showResultMsg(m.mMsg, true);
            } else {
                defautLoader.showResultMsg(m.mMsg, true);
            }
            delayToFinish(m.timeDelayToFinish, m.mOnLoaderFinishListener);
        }
        else if(m.mMsgType == MSG_TYPE_LOAD_FAILED){
            if (customLoader != null) {
                customLoader.showResultMsg(m.mMsg, false);
            } else {
                defautLoader.showResultMsg(m.mMsg, false);
            }
            delayToFinish(m.timeDelayToFinish, m.mOnLoaderFinishListener);
        }
        if(mMsgList.size()!=0)
            mMsgList.remove(0);
        if(mMsgList.size()!=0){
            submit(mMsgList.get(0));
//            resetLock();
        }
    }

    private void delayToFinish(long delayTime, final OnLoaderFinishListener loaderFinishListener) {
        Flowable.timer(delayTime, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        closeLoader();
                        if(loaderFinishListener!=null){
                            loaderFinishListener.onLoaderFinished();
                        }
                    }
                });
    }

    public void setCustomLoader(ILoader customLoader) {
        this.customLoader = customLoader;
    }


    public void showLoader(){
        showLoader("");
    }

    public void showLoader(String msg){

        if(!isLocked()){
            if(customLoader!=null){
                customLoader.showLoader(msg);
            }
            else{
                defautLoader.showLoader(msg);
            }
        }
        else{
            mMsgList.add(new Msg(msg, MSG_TYPE_LOADING));
        }

    }


    @Override
    public void closeLoader() {

        if(!isLocked()){
            if(customLoader!=null){
                customLoader.closeLoader();
            }
            else{
                defautLoader.closeLoader();
            }

            mMsgList.clear();
        }
        else{
            mMsgList.add(new Msg("", MSG_TYPE_LOAD_CLOSE));
        }



    }

    @Override
    public void showMsg(String msg, long timeDelayToFinish) {

        if(!isLocked()){
            if(customLoader!=null){
                customLoader.showMsg(msg);
            }
            else{
                defautLoader.showMsg(msg);
            }

            if(timeDelayToFinish!=0)
                delayToFinish(timeDelayToFinish, null);
        }
        else{
            mMsgList.add(new Msg(msg, MSG_TYPE_LOADING));
        }

    }

    @Override
    public void showMsgAndclose(String msg, boolean isSuccess, long timeDelayToFinish) {
        showMsgAndclose(msg, isSuccess, timeDelayToFinish, null);



    }

    @Override
    public void showMsgAndclose(String msg, boolean isSuccess, long timeDelayToFinish, OnLoaderFinishListener listener) {
        if(!isLocked()) {
            if (customLoader != null) {
                customLoader.showResultMsg(msg, isSuccess);
            } else {
                defautLoader.showResultMsg(msg, isSuccess);
            }

            if(timeDelayToFinish!=0)
                delayToFinish(timeDelayToFinish, listener);
        }
        else{
            mMsgList.add(new Msg(msg, isSuccess?MSG_TYPE_LOAD_SUCCESS:MSG_TYPE_LOAD_FAILED, timeDelayToFinish, listener));
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(lock != null &&!lock.isDisposed())
            lock.dispose();
        closeLoader();

    }

    class Msg {

        private int mMsgType;
        private String mMsg;
        private OnLoaderFinishListener mOnLoaderFinishListener;
        private long timeDelayToFinish;

        public Msg(String msg, int type){
            this.mMsg = msg;
            this.mMsgType = type;
        }

        public Msg(String msg, int type, long timeDelayToFinish){
            this(msg, type);
            this.timeDelayToFinish = timeDelayToFinish;
        }

        public Msg(String msg, int type, long timeDelayToFinish, OnLoaderFinishListener listener){
            this(msg, type, timeDelayToFinish);
            mOnLoaderFinishListener = listener;
        }


    }
}
