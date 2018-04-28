package com.swolf.libraryzxing.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResultType;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.common.Scanner;
import com.mylhyl.zxing.scanner.decode.QRDecode;
import com.swolf.libraryzxing.R;
import com.swolf.libraryzxing.iview.IMainView;
import com.swolf.libraryzxing.presenter.NYMainPresenter;

import java.io.ByteArrayOutputStream;

/**
 * Created by ly on 2017/12/17.
 */
public class NYMainActivity extends NYBaseInDecodeActivity implements IMainView {
    private NYMainPresenter presenter;
    private Button buttonCreateCode;//生成二维码
    private Button buttonScanner;//扫一扫
    private ImageView imageViewCode;//识别图片二维码

    private AppCompatActivity activity;
    private Bitmap bitmap = null;
    @Override
    void onResultActivity(Result result, ParsedResultType type, Bundle bundle) {
        dismissProgressDialog();
        if (bundle != null) {
            String value = bundle.getString(Scanner.Scan.RESULT);
            Toast.makeText(this, value +"--"+type, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ny_activity_main);

        activity = this;

        presenter = new NYMainPresenter(activity);

        buttonCreateCode = (Button)findViewById(R.id.buttonCreateCode);
        buttonScanner = (Button)findViewById(R.id.buttonScanner);
        imageViewCode = (ImageView)findViewById(R.id.imageViewCode);

        buttonCreateCode.setOnClickListener(clickListener);
        buttonScanner.setOnClickListener(clickListener);
        imageViewCode.setOnClickListener(clickListener);
    }
    final int request_code = 1000;

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(v == buttonCreateCode){
                presenter.toCreateCodeActivity(activity,"TEST",R.color.blue);
            }else if(v == buttonScanner){
                presenter.toScannerActivity(activity,request_code);
            }else if(v == imageViewCode){
                inDecode(imageViewCode);
            }
        }
    };

    private void inDecode(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);//step 1
        Bitmap bitmap = imageView.getDrawingCache();//step 2
        imageView.setDrawingCacheEnabled(false);//step 5

        if (bitmap != null) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            showProgressDialog();
            QRDecode.decodeQR(bitmap, this);
        }
        bitmap.recycle();
    }

    @Override
    protected void onDestroy() {
        if(bitmap!=null&&bitmap.isRecycled()){
            bitmap.recycle();
        }
        bitmap = null;
        super.onDestroy();
    }
}
