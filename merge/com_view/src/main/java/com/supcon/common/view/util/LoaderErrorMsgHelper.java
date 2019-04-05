package com.supcon.common.view.util;

import android.text.TextUtils;

import com.supcon.common.view.App;
import com.supcon.common.view.view.loader.base.LoaderController;

/**
 * Created by wangshizhan on 2017/8/19.
 * Email:wangshizhan@supcon.com
 */

public class LoaderErrorMsgHelper {



    public static void showErrorMsg(LoaderController loaderController, String msg){


        if(TextUtils.isEmpty(msg)){
            loaderController.showMsgAndclose("操作失败！", false, 2000);
            return;
        }

//        String msg2 = null;
//
//        if(msg.contains("404")){
//            msg2 = "没有找到服务，未部署或者服务器地址错误！";
//        }
//        else if(msg.contains("401")){
//            msg2 = "登陆超时";
//        }
//        else if(msg.contains("500")){
//            msg2 = "服务器错误！";
//        }
//        else if(msg.contains("SocketTimeoutException")){
//            msg2 = "请求超时！";
//        }
//        else if(msg.contains("ConnectException")){
//            if (NetWorkUtil.isWifiConnected(App.getAppContext())){
//                msg2 = "无法连接到服务器!";
//            }
//            else{
//                msg2 = "WIFI已断开!";
//            }
//        }
//        else if(msg.contains("No route to host")){
//            msg2 = "无法连接服务器!";
//        }
//        else if(msg.contains("No address associated with hostname")){
//            msg2 = "无法解析服务器地址，请检查设置！";
//        }
//        else{
//            loaderController.showMsgAndclose(msg, false, 2000);
//            return;
//        }
        loaderController.showMsgAndclose(msg, false, 2000);


    }


}
