package com.header.librarybarcodescanner;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class NYSimpleScannerActivity extends AppCompatActivity {

//    onCreat

    private ZBarScannerView mScannerView;
    private TextView tv_title;
    private TextView tv_close;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.ny_activity_simple_scanner);

        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_close = (TextView)findViewById(R.id.tv_close);
        mScannerView = (ZBarScannerView)findViewById(R.id.abarScannerView);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(new ZBarScannerView.ResultHandler(){
            @Override
            public void handleResult(Result rawResult) {
                String content = rawResult.getContents();
                if(TextUtils.isEmpty(content)){
                    return;
                }
                Toast.makeText(NYSimpleScannerActivity.this,content,Toast.LENGTH_SHORT).show();
            }
        });
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }



}
