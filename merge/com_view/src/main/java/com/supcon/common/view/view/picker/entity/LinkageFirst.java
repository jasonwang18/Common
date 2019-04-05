package com.supcon.common.view.view.picker.entity;

import java.util.List;

/**
 * 用于联动选择器展示的第一级条目
 *
 * @see com.supcon.common.view.view.picker.LinkagePicker
 */
public interface LinkageFirst<Snd> extends LinkageItem {

    List<Snd> getSeconds();

}