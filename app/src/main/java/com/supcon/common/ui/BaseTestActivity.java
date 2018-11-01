package com.supcon.common.ui;

import android.view.View;

import com.app.annotation.BindByTag;
import com.app.annotation.custom.OnChild;
import com.app.annotation.custom.OnDateChange;
import com.app.annotation.custom.OnItemSelect;
import com.app.annotation.custom.OnTextChange;
import com.supcon.common.model.bean.TestEntity;
import com.supcon.common.ui.view.CustomView;
import com.supcon.common.view.base.activity.BaseControllerActivity;
import com.supcon.common.view.util.LogUtil;
import com.supcon.common.view.util.LogUtils;

import static com.app.annotation.custom.OnItemSelect.BIG;

/**
 * Created by wangshizhan on 2018/10/31
 * Email:wangshizhan@supcom.com
 */
public abstract class BaseTestActivity extends BaseControllerActivity {

    protected String[] values = null;
    protected TestEntity mTestEntity = null;
    @BindByTag("customView")
    @OnItemSelect(values = "values", param = "mTestEntity.itemResult", cancelOutsideEnable = true, cycleEnable = true, dividerVisble = true)
    CustomView customView;

    @BindByTag("customView2")
//    @OnTextChange(param = "mTestEntity.textChangeResult")
    @OnDateChange(param = "mTestEntity.date2",cancelOutsideEnable = true, cycleEnable = true, dividerVisble = true)
    CustomView customView2;

//    @OnChild(views = "customView")
    public void onChild(View childView, int action, Object obj){
        LogUtil.w("RouterTest", "onChild action:"+action+" childView:"+childView);
        LogUtils.warn("RouterTest", ""+mTestEntity.textChangeResult);
    }
}
