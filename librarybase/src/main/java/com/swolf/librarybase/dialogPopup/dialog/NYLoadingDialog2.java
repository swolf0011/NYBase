package com.swolf.librarybase.dialogPopup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.swolf.librarybase.R;


/**
 * 进度条对话框工具
 * Created by LiuYi-15973602714
 */
public class NYLoadingDialog2 extends Dialog {

    private AppsLoadingDialogListener listener;
    private TextView loadingTextView;
    private String message;
    private AppsLoadingDialogBackInterfaceListener backlistener;

    public NYLoadingDialog2(Context context, AppsLoadingDialogListener listener) {
        super(context);
        this.listener = listener;
    }

    public NYLoadingDialog2(Context context, int theme) {
        super(context, theme);
    }

    public NYLoadingDialog2(Context context, int theme, AppsLoadingDialogListener listener) {
        super(context, theme);
        this.listener = listener;
    }

    public void setListener(AppsLoadingDialogListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ny_dialog_loading);

        this.loadingTextView = (TextView) this.findViewById(R.id.loadingTextView);
        this.loadingTextView.setText(this.message);
        this.setCanceledOnTouchOutside(false);
    }

    public void show(String text) {
        this.message = text;
        super.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (backlistener != null) {
                backlistener.callBack();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface AppsLoadingDialogBackInterfaceListener {
        public void callBack();
    }

    public void setAppsLoadingDialogBackInterfaceListen(
            AppsLoadingDialogBackInterfaceListener backlistener) {
        this.backlistener = backlistener;
    }

    public void dismiss() {
        if (this.isShowing()) {
            super.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (this.listener != null) {
            this.listener.onCancelLoadingDialog();
        }
    }

    public interface AppsLoadingDialogListener {
        public void onCancelLoadingDialog();
    }

}