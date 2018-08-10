package com.supcon.common.view.ptr;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.supcon.common.view.util.ViewUtils;

public abstract class PtrDefaultHandler implements PtrHandler {

    public static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            if (view != null && view instanceof ListView && ((ListView) view).getHeaderViewsCount() > 0) {
                View header = ((ListView) view).getChildAt(0);
                if (header != null) {
                    // && contentView.canScrollVertically(-1),只用前一段会出现中途就返回true的情况。
                    return ViewUtils.getViewLocationScreenY(header) < ViewUtils.getViewLocationScreenY(view) || view.canScrollVertically(-1);
                }
            }
            return view.canScrollVertically(-1);
        }
    }

    /**
     * Default implement for check can perform pull to refresh
     *
     * @param frame
     * @param content
     * @param header
     * @return
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return checkContentCanBePulledDown(frame, content, header);
    }
}