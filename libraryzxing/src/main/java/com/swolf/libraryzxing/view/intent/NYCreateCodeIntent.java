package com.swolf.libraryzxing.view.intent;

import android.content.Context;
import android.content.Intent;

import com.google.zxing.client.result.ParsedResultType;
import com.swolf.libraryzxing.R;
import com.swolf.libraryzxing.view.activity.NYMainActivity;

/**
 * Created by ly on 2017/12/17.
 */

public class NYCreateCodeIntent {
    private static final String VALUE = "value";

    private static final String COLORRESID = "colorResId";
    private static final String LOGOBITMAPBYTES = "logoBitmapBytes";
    private static final String BGBITMAPBYTES = "bgBitmapBytes";

    public static void toActivity(Context context, String value, int colorResId,byte[] logoBitmapBytes,byte[] bgBitmapBytes) {
        Intent intent = new Intent(context, NYMainActivity.class);
        intent.putExtra(VALUE, value);
        intent.putExtra(COLORRESID, colorResId);
        intent.putExtra(LOGOBITMAPBYTES, logoBitmapBytes);
        intent.putExtra(BGBITMAPBYTES, bgBitmapBytes);
        context.startActivity(intent);
    }

    public static boolean isToActivity(Intent intent) {
       if(intent.hasExtra(VALUE)&&intent.hasExtra(COLORRESID)&&intent.hasExtra(LOGOBITMAPBYTES)&&intent.hasExtra(BGBITMAPBYTES)){
           return true;
       }else{
           return false;
       }
    }

    public static String getVALUE(Intent intent) {
        String str = intent.getStringExtra(VALUE);
        return str == null ? "" : str;
    }

    public static int getCOLORRESID(Intent intent) {
        int i = intent.getIntExtra(COLORRESID, -1);
        return i == -1 ? R.color.blue : i;
    }

    public static byte[] getLOGOBITMAPBYTES(Intent intent) {
        byte[] bs = intent.getByteArrayExtra(LOGOBITMAPBYTES);
        return bs;
    }

    public static byte[] getBGBITMAPBYTES(Intent intent) {
        byte[] bs = intent.getByteArrayExtra(BGBITMAPBYTES);
        return bs;
    }
}
