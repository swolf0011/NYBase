package com.swolf.librarybase.dialogPopup.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

/**
 * Alert对话框工具
 * Created by LiuYi-15973602714
 */
@SuppressLint("NewApi")
public class NYAlertDialogUtil {
    private static AlertDialog dialog;

    /**
     * @param context
     * @param Title          标题：可以为null
     * @param view           视图
     * @param cancelable     返回键取消对话框
     * @param theme          对话框样式
     * @param cancel         取消：可以为null
     * @param cancelListener 取消监听：可以为null
     */
    public static void show(Context context,
                            String Title,
                            View view,
                            boolean cancelable,
                            int theme,
                            String cancel,
                            DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder b = null;
        if (theme > 0) {
            b = new AlertDialog.Builder(context, theme);
        } else {
            b = new AlertDialog.Builder(context);
        }
        b.setTitle(Title);
        b.setView(view);
        b.setCancelable(cancelable);
        if (cancel != null && cancel.length() > 0) {
            b.setNegativeButton(cancel, cancelListener);
        }
        dialog = b.create();
        if (Title == null || "".equals(Title)) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.show();
    }

    /**
     * @param context
     * @param Title           标题：可以为null
     * @param message         消息
     * @param cancelable      返回键取消对话框
     * @param theme           对话框样式
     * @param cancel          取消：可以为null
     * @param cancelListener  取消监听：可以为null
     * @param confirm         确定：可以为null
     * @param confirmListener 确定监听：可以为null
     */
    public static void show(Context context,
                            String Title,
                            String message,
                            boolean cancelable,
                            int theme,
                            String cancel,
                            DialogInterface.OnClickListener cancelListener,
                            String confirm,
                            DialogInterface.OnClickListener confirmListener) {
        AlertDialog.Builder b = null;
        if (theme > 0) {
            b = new AlertDialog.Builder(context, theme);
        } else {
            b = new AlertDialog.Builder(context);
        }
        b.setTitle(Title);
        b.setMessage(message);
        b.setCancelable(cancelable);
        if (cancel != null && cancel.length() > 0) {
            b.setNegativeButton(cancel, cancelListener);
        }
        if (cancel != null && cancel.length() > 0) {
            b.setPositiveButton(confirm, confirmListener);
        }
        dialog = b.create();
        if (Title == null || "".equals(Title)) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
