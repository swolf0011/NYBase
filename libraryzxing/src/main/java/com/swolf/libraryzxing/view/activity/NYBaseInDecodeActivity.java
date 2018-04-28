package com.swolf.libraryzxing.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ISBNParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.ProductParsedResult;
import com.google.zxing.client.result.TextParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.common.Scanner;
import com.mylhyl.zxing.scanner.result.AddressBookResult;
import com.mylhyl.zxing.scanner.result.ISBNResult;
import com.mylhyl.zxing.scanner.result.ProductResult;
import com.mylhyl.zxing.scanner.result.URIResult;

/**
 * Created by hupei on 2016/7/7.
 */
public abstract class NYBaseInDecodeActivity extends AppCompatActivity implements
        OnScannerCompletionListener {
    private ProgressDialog progressDialog;

    /**
     * 子类实现，根据 ParsedResultType 处理业务
     *
     * @param result
     * @param type
     * @param bundle
     */
    abstract void onResultActivity(Result result, ParsedResultType type, Bundle bundle);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void OnScannerCompletion(final Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        if (rawResult == null) {
            Toast.makeText(this, "未发现二维码", Toast.LENGTH_SHORT).show();
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
        showProgressDialog();
        onResultActivity(rawResult, type, bundle);
    }



    void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("请稍候...");
        progressDialog.show();
    }

    void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
