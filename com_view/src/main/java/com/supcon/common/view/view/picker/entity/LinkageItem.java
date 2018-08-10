package com.supcon.common.view.view.picker.entity;

/**
 * 用于联动选择器展示的条目
 * @see com.supcon.common.view.view.picker.LinkagePicker
 */
interface LinkageItem extends WheelItem {

    /**
     * 唯一标识，用于判断两个条目是否相同
     */
    Object getId();

}
