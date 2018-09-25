package com.swolf.librarybase.dialogPopup.popupWindow;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.swolf.librarybase.R;


/**
 * PopupWindow工具
 * Created by LiuYi-15973602714
 */
public class NYPopupWindowUtil {
     PopupWindow popupWindow;

    public NYPopupWindowUtil() {
    }

    /**
     * 获取PopupWindow
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressWarnings("deprecation")
    private void initPopupWindow(View popupView,
                                 int width,
                                 int height,
                                 int animationStyle,
                                 OnDismissListener listener) {
        popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(listener);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(animationStyle);
    }

    /**
     * 显示View下方的PopupWindow
     */
    public void showViewBottom(View clickParentView,
                               View view,
                               int width,
                               int height,
                               int xoff,
                               int yoff,
                               OnDismissListener listener) {
        initPopupWindow(view, width, height, -1, listener);
        int[] location = new int[2];
        clickParentView.getLocationOnScreen(location);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();

        popupWindow.showAsDropDown(clickParentView, location[0]+(clickParentView.getWidth()-popupWidth)/2 + xoff, yoff);
    }

    /**
     * 显示View上方的PopupWindow
     */
    public void showViewTop(View clickParentView,
                            View view,
                            int width,
                            int height,
                            int xoff,
                            int yoff,
                            OnDismissListener listener) {
        initPopupWindow(view, width, height, -1, listener);
        int[] location = new int[2];
        clickParentView.getLocationOnScreen(location);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();
        popupWindow.showAtLocation(clickParentView, Gravity.NO_GRAVITY, location[0]+(clickParentView.getWidth()-popupWidth)/2 + xoff,
                location[1]-popupHeight+ yoff);
    }

    /**
     * 显示屏幕底部的PopupWindow
     */
    public void showScreenBottom(View clickParentView,
                                 View view,
                                 int width,
                                 int height,
                                 int xoff,
                                 int yoff,
                                 OnDismissListener listener) {
        initPopupWindow(view, width, height, R.style.ny_screen_bottombar_popupwindow_anim, listener);
        popupWindow.showAtLocation(clickParentView, Gravity.BOTTOM, xoff, yoff);
    }

    /**
     * 显示屏幕中间的PopupWindow
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void showScreenCenter(View clickParentView,
                                 View view,
                                 int width,
                                 int height,
                                 int xoff,
                                 int yoff,
                                 OnDismissListener listener) {
        initPopupWindow(view, width, height, R.style.ny_screen_bottombar_popupwindow_anim, listener);
        popupWindow.showAtLocation(clickParentView, Gravity.CENTER, xoff, yoff);
    }

    /**
     * 解散dismissPopupWindow
     */
    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

}
