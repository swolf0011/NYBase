package com.swolf.librarymedia.camera.image;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Size;

import java.util.Comparator;

/**
 * 比较大小关于面积
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYCompareSizesByArea implements Comparator<Size> {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int compare(Size lhs, Size rhs) {
        // We cast here to ensure the multiplications won't overflow
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
    }
}
