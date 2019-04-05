package com.supcon.common.view.view.custom;

import android.graphics.Typeface;
import android.text.InputFilter;
import android.widget.EditText;

import com.supcon.common.view.view.custom.ICustomView;

/**
 * 用于对自定义view的批量操作
 * Created by wangshizhan on 2018/10/18
 * Email:wangshizhan@supcom.com
 */
public class CustomViewUtil {

    public static void setInputType(int inputType, ICustomView... views){

        for(ICustomView iCustomView : views){
            iCustomView.setInputType(inputType);
        }
    }

    public static void setFilters(InputFilter[] filters, ICustomView... views){

        for(ICustomView iCustomView : views){

            EditText editText = iCustomView.editText();
            if(editText!=null)
                editText.setFilters(filters);
        }

    }

    public static void setEditable(boolean isEditable, ICustomView... views){

        for(ICustomView iCustomView : views){
            iCustomView.setEditable(isEditable);
        }

    }

    public static void setNecessary(boolean isNecessary, ICustomView... views){

        for(ICustomView iCustomView : views){
            iCustomView.setNecessary(isNecessary);
        }

    }

    public static void setTextFont(Typeface newFont, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setTextFont(newFont);
        }
    }
    public static void setKeyTextStyle(int textStyle, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setKeyTextStyle(textStyle);
        }
    }
    public static void setContentTextStyle(int textStyle, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setContentTextStyle(textStyle);
        }
    }
    public static void setKeyTextSize(int textSize, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setKeyTextSize(textSize);
        }
    }
    public static void setContentTextSize(int textSize, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setContentTextSize(textSize);
        }
    }
    public static void setKeyTextColor(int color, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setKeyTextColor(color);
        }
    }
    public static void setContentTextColor(int color, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setContentTextColor(color);
        }
    }
    public static void setKeyWidth(int width, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setKeyWidth(width);
        }
    }
    public static void setKeyHeight(int height, ICustomView... views){
        for(ICustomView iCustomView : views){
            iCustomView.setKeyHeight(height);
        }
    }

    public static ICustomView isEmpty(ICustomView... views){

        for(ICustomView iCustomView : views){

            if(iCustomView.isEmpty()){
                return iCustomView;
            }
        }

        return null;
    }

}
