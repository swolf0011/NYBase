package com.swolf.librarycontact.contact.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * 头像
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressWarnings("serial")
public class PhotoBean extends ContactIdBean implements Serializable {
	public String id;// 头像ID
	public Bitmap photo;// 头像
	public String idBackup;// 头像ID备份
	public Bitmap photoBackup;// 头像备份

	public boolean isUpdate() {

		if (photoBackup == null) {
			if (photo == null) {
				return false;
			} else {
				return true;
			}
		} else {
			if (photo == null) {
				return true;
			} else {
				return !compare2Bitmaps(photo, photoBackup);
			}
		}
	}

	/**
	 * 比较两个Bitmap是否相同
	 * 
	 * @return true,相同；fasle，不同。
	 */
	private boolean compare2Bitmaps(Bitmap bit1, Bitmap bit2) {

		int width1 = bit1.getWidth();
		int height1 = bit1.getHeight();
		int width2 = bit2.getWidth();
		int height2 = bit2.getHeight();
		if (width1 == width2 && height1 == height2) {
			for (int i = 0; i < width1; i++) {
				for (int j = 0; j < height1; j++) {
					if (bit1.getPixel(i, j) != bit2.getPixel(i, j)) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

}

// /:~