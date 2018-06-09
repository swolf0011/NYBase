package com.swolf.librarybase.dialogPopup.popupWindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.swolf.librarybase.R;


/**
 * PopupWindow工具
 * Created by LiuYi-15973602714
 */
public class NYPopupWindowSex {


    public void show(Context context, View clickParentView,
                     final IHandlerCallback callback, OnDismissListener listener) {
        final NYPopupWindowUtil pop = new NYPopupWindowUtil();
        View view = LayoutInflater.from(context).inflate(R.layout.ny_popupwindow_sex, null);
        TextView tv_female = (TextView) view.findViewById(R.id.tv_female);
        TextView tv_male = (TextView) view.findViewById(R.id.tv_male);

        tv_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
                if (callback != null) {
                    callback.female("男");
                }
            }
        });
        tv_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                pop.dismiss();
                if (callback != null) {
                    callback.male("女");
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
        void male(String val);

        void female(String female);
    }

}
