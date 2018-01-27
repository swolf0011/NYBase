package com.swolf.librarybase.adapter;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 通用适配器Demo
 * Created by LiuYi-15973602714
 */
public class NYAdapterDemo {

    public ArrayList<String> list = new ArrayList<String>();

    NYAdapter.IHandlerView handlerView = new NYAdapter.IHandlerView() {
        @Override
        public View getView(int position, View convertView) {
            String item = list.get(position);
            // int textView_nameId = R.id.imageView_img;
            int textView_nameId = 1;
            TextView textView_name = NYAdapter.ViewHolder.get(convertView, textView_nameId);
            textView_name.setText(item);
            return convertView;
        }
    };
}
