package com.swolf.libraryzxing.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResultType;
import com.mylhyl.zxing.scanner.common.Scanner;
import com.mylhyl.zxing.scanner.decode.QRDecode;
import com.mylhyl.zxing.scanner.encode.QREncode;
import com.swolf.libraryzxing.R;
import com.swolf.libraryzxing.view.intent.NYCreateCodeIntent;

public class NYCreateCodeActivity extends NYBaseInDecodeActivity {

    private TextView tvResult;
    private ImageView imageView;
    private EditText editText;
    private Button button2;

    private String value = "";
    private int colorRedId = -1;

    private Bitmap bitmap = null;
    private byte[] logoBitmapBytes;
    private byte[] bgBitmapBytes;

    @Override
    void onResultActivity(Result result, ParsedResultType type, Bundle bundle) {
        dismissProgressDialog();
        if (bundle != null) {
            String value = bundle.getString(Scanner.Scan.RESULT);
            Toast.makeText(this, value + "--" + type, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ny_activity_create_code);

        Intent intent = getIntent();

        if (!NYCreateCodeIntent.isToActivity(intent)) {
            Toast.makeText(this, "请使用NYCreateCodeIntent.toActivity()", Toast.LENGTH_LONG).show();
            finish();
        }

        value = NYCreateCodeIntent.getVALUE(intent);
        colorRedId = NYCreateCodeIntent.getCOLORRESID(intent);
        logoBitmapBytes = NYCreateCodeIntent.getLOGOBITMAPBYTES(intent);
        bgBitmapBytes = NYCreateCodeIntent.getBGBITMAPBYTES(intent);


        tvResult = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        button2 = (Button) findViewById(R.id.button2);


        if (value != null && value.length() > 0) {
            editText.setText(value);
            createCode(value);
        }


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = editText.getText().toString();
                if (value != null && value.length() > 0) {
                    createCode(value);
                } else {
                    bitmap = null;
                    imageView.setImageBitmap(bitmap);
                    tvResult.setText("请输入生成二维码");
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inDecode(bitmap);
            }
        });
    }

    private void inDecode(Bitmap bitmap) {
        if (bitmap != null) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            showProgressDialog();
            QRDecode.decodeQR(bitmap, this);
        }
    }

    @SuppressLint("ResourceType")
    private void createCode(String value) {
        Resources res = getResources();
        Bitmap logoBitmap = null;
        Bitmap bgBitmap = null;
        if (logoBitmapBytes != null && logoBitmapBytes.length > 0) {
            logoBitmap = BitmapFactory.decodeByteArray(logoBitmapBytes, 0, logoBitmapBytes.length);
        } else {
            logoBitmap = BitmapFactory.decodeResource(res, R.mipmap.connect_logo);
        }
        if (bgBitmapBytes != null && bgBitmapBytes.length > 0) {
            bgBitmap = BitmapFactory.decodeByteArray(bgBitmapBytes, 0, bgBitmapBytes.length);
        } else {
            bgBitmap = BitmapFactory.decodeResource(res, R.mipmap.wb_wlog_blow_bg_night);
        }

        int leftTop = 0xFF0094FF;
        int leftBottom = 0xFFFED545;
        int rightBottom = 0xFF5ACF00;
        int rightTop = 0xFFFF4081;

        ParsedResultType prt = ParsedResultType.TEXT;

        value = TextUtils.isEmpty(value) ? "Test" : value;

        bitmap = new QREncode.Builder(NYCreateCodeActivity.this)
                .setColor(getResources().getColor(colorRedId))//二维码颜色
//                .setColors(leftTop, leftBottom, rightBottom, rightTop)//二维码彩色
                .setQrBackground(bgBitmap)//二维码背景
                .setMargin(0)//二维码边框
                .setParsedResultType(prt)//二维码类型
                .setContents(value)//二维码内容
                .setSize(500)//二维码等比大小
                .setLogoBitmap(logoBitmap, 90)
                .build().encodeAsBitmap();

        logoBitmap.recycle();
        bgBitmap.recycle();
        imageView.setImageBitmap(bitmap);
        tvResult.setText("单击识别图中二维码");
    }

    @Override
    protected void onDestroy() {
        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
        super.onDestroy();
    }


}
