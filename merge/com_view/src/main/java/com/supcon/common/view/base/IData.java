package com.supcon.common.view.base;

import java.util.Map;

/**
 * Created by wangshizhan on 2018/11/16
 * Email:wangshizhan@supcom.com
 */
public interface IData {

    String NEW_DATA = "NEW_DATA";

    void refresh();
    boolean checkBeforeSubmit(Map<String, Object> map);
    boolean doSave(Map<String, Object> map);
    boolean isModified();
}
