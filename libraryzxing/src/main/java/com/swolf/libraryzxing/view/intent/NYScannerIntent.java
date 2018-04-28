package com.swolf.libraryzxing.view.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.mylhyl.zxing.scanner.common.Scanner;
import com.swolf.libraryzxing.view.activity.BasicScannerActivity;
import com.swolf.libraryzxing.view.activity.NYMainActivity;
import com.swolf.libraryzxing.view.activity.NYScannerActivity;

/**
 * Created by ly on 2017/12/17.
 */

public class NYScannerIntent {
    public static void toActivityForResult(Activity activity,int request_code) {
        activity.startActivityForResult(new Intent(Scanner.Scan.ACTION), request_code);
    }
}
