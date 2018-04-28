package com.swolf.libraryzxing.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ISBNParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ProductParsedResult;
import com.google.zxing.client.result.TextParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.common.Scanner;
import com.mylhyl.zxing.scanner.result.AddressBookResult;
import com.mylhyl.zxing.scanner.result.ISBNResult;
import com.mylhyl.zxing.scanner.result.ProductResult;
import com.mylhyl.zxing.scanner.result.URIResult;
import com.swolf.libraryzxing.R;

/**
 * 扫描
 */
public class NYScannerActivity extends AppCompatActivity implements OnScannerCompletionListener {
    private ScannerView mScannerView;
    private Result mLastResult;
    private int permissionRequestCode = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ny_activity_scanner);

        int p = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (p != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, permissionRequestCode);
        }else{
            initView();
        }
    }
    private void initView(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        mScannerView = (ScannerView) findViewById(R.id.scanner_view);
        mScannerView.setOnScannerCompletionListener(this);

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mScannerView.toggleLight(isChecked);
            }
        });

        ScannerOptions so = new ScannerOptions.Builder().
                setScanMode(Scanner.ScanMode.QR_CODE_MODE).//二维码
                setScanFullScreen(true).//全屏识别
                setFrameHide(false).//隐藏扫描框
//                setScanInvert(true).//扫描反色二维码
                setLaserMoveSpeed(1).//速度
                setLaserLineHeight(5).//设置扫描线高度
                build();

        mScannerView.setScannerOptions(so);
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        resetStatusView();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mLastResult != null) {
                    restartPreviewAfterDelay(0L);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void restartPreviewAfterDelay(long delayMS) {
        mScannerView.restartPreviewAfterDelay(delayMS);
        resetStatusView();
    }

    private void resetStatusView() {
        mLastResult = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permissionRequestCode ){
            if(grantResults[0] == -1){
                Toast.makeText(this,"你禁用了摄像头权限",Toast.LENGTH_SHORT).show();
                finish();
            }else{
                initView();
            }
        }
    }

    @Override
    public void OnScannerCompletion(final Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        if (rawResult == null) {
            Toast.makeText(this, "未发现二维码", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
            return;
        }
        final Bundle bundle = new Bundle();
        final ParsedResultType type = parsedResult.getType();
        switch (type) {
            case ADDRESSBOOK:
                AddressBookParsedResult addressBook = (AddressBookParsedResult) parsedResult;
                bundle.putSerializable(Scanner.Scan.RESULT, new AddressBookResult(addressBook));
                break;
            case PRODUCT:
                ProductParsedResult product = (ProductParsedResult) parsedResult;
                bundle.putSerializable(Scanner.Scan.RESULT, new ProductResult(product));
                break;
            case ISBN:
                ISBNParsedResult isbn = (ISBNParsedResult) parsedResult;
                bundle.putSerializable(Scanner.Scan.RESULT, new ISBNResult(isbn));
                break;
            case URI:
                URIParsedResult uri = (URIParsedResult) parsedResult;
                bundle.putSerializable(Scanner.Scan.RESULT, new URIResult(uri));
                break;
            case TEXT:
                TextParsedResult textParsedResult = (TextParsedResult) parsedResult;
                bundle.putString(Scanner.Scan.RESULT, textParsedResult.getText());
                break;
            case GEO:
                break;
            case TEL:
                break;
            case SMS:
                break;
        }
        onResultActivity(rawResult, type, bundle);

    }

    void onResultActivity(Result result, ParsedResultType type, Bundle bundle) {
        if (bundle != null) {
            String value = bundle.getString(Scanner.Scan.RESULT);
            Intent intent = new Intent();
            intent.putExtra("result",value);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
