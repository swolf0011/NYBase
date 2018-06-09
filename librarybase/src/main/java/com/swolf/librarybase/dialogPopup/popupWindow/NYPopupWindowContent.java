package com.swolf.librarybase.dialogPopup.popupWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.swolf.librarybase.R;


/**
 * PopupWindow工具
 * Created by LiuYi-15973602714
 */
public class NYPopupWindowContent {


    public void show(Context context, View clickParentView, String title,
                     final IHandlerCallback callback, OnDismissListener listener) {
        final NYPopupWindowUtil pop = new NYPopupWindowUtil();
        View view = LayoutInflater.from(context).inflate(R.layout.ny_popupwindow_content, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_title.setText(title);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                pop.dismiss();
                if (callback != null) {
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();

                if (callback != null) {
                }
            }
        });

        pop.showScreenBottom(clickParentView,
                view,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                0,
                listener);
    }


    public interface IHandlerCallback {
        void cancel(String val);

        void confirm(String female);
    }

}
