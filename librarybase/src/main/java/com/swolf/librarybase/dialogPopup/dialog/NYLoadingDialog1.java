package com.swolf.librarybase.dialogPopup.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.swolf.librarybase.R;


/**
 * 进度条对话框工具
 * Created by LiuYi-15973602714
 */
public class NYLoadingDialog1 {
    private static Dialog dialog;

    public static void show(final Activity activity) {
        show(activity, false);
    }

    public static void show(final Activity activity, boolean isCancelable) {
        int wrap_content = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int match_parent = RelativeLayout.LayoutParams.MATCH_PARENT;
        RelativeLayout.LayoutParams pl0 = new RelativeLayout.LayoutParams(match_parent, match_parent);
        RelativeLayout rl = new RelativeLayout(activity);
        rl.setLayoutParams(pl0);

        ImageView iv = new ImageView(activity);
        RelativeLayout.LayoutParams pl = new RelativeLayout.LayoutParams(wrap_content * 4, wrap_content * 4);
        pl.addRule(RelativeLayout.CENTER_IN_PARENT);

        rl.addView(iv);

        iv.setImageResource(R.drawable.ny_loading_dialog_anim);

        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();

        dialog = new Dialog(activity, R.style.ny_loading_dialog);
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(rl);

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (metric.widthPixels * 0.2);//这改变dialog大小。0.2倍大小
        lp.height = (int) (metric.heightPixels * 0.2);//这改变dialog大小。0.2倍大小
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    public static void dismiss() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
