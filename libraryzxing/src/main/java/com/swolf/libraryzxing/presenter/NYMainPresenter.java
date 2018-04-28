package com.swolf.libraryzxing.presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.decode.QRDecode;
import com.swolf.libraryzxing.R;
import com.swolf.libraryzxing.imodel.IMainModel;
import com.swolf.libraryzxing.model.NYMainModel;
import com.swolf.libraryzxing.view.intent.NYCreateCodeIntent;
import com.swolf.libraryzxing.view.intent.NYScannerIntent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ly on 2017/12/17.
 */

public class NYMainPresenter<IMainView> {

    public IMainView view;
    public IMainModel model;

    public NYMainPresenter(IMainView view) {
        this.view = view;
        setModel();
    }

    /**
     * 设置model
     */
    public void setModel() {
        model = new NYMainModel();
    }


    public void toCreateCodeActivity(Context context, String value, int colorResId) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.connect_logo);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] logoBitmapBytes = baos.toByteArray();
        bitmap.recycle();
        baos.reset();

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.wb_wlog_blow_bg_night);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bgBitmapBytes = baos.toByteArray();
        bitmap.recycle();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NYCreateCodeIntent.toActivity(context, value, colorResId, logoBitmapBytes, bgBitmapBytes);
    }

    public void toScannerActivity(Activity activity, int request_code) {
        NYScannerIntent.toActivityForResult(activity, request_code);
    }

    public void toInDeCode(Activity activity, ImageView imageView) {

    }



}
