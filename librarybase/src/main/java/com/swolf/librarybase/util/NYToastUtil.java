package com.swolf.librarybase.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具
 * Created by LiuYi-15973602714
 */
public class NYToastUtil {

	private static class NYSubHolder{
		private static NYToastUtil util = new NYToastUtil();
	}

	private NYToastUtil(){}

	public static NYToastUtil getInstance(){
		return NYSubHolder.util;
	}

	public void show(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	public void show(Context context, CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
