package com.swolf.librarybase.dialogPopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.Window;

/**
 * 对话框工具
 * Created by LiuYi-15973602714
 */
public class NYDialogUtil {
    private static Dialog dialog;

    /**
     * @param context
     * @param view
     * @param cancelable      返回键取消对话框
     * @param theme           对话框样式
     * @param dismissListener 解散事件
     */
    public static void show(Context context,
                            View view,
                            boolean cancelable,
                            int theme,
                            OnDismissListener dismissListener) {
        if (theme > 0) {
            dialog = new Dialog(context, theme);
        } else {
            dialog = new Dialog(context);
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.setOnDismissListener(dismissListener);
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
