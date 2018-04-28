package com.swolf.libraryzxing.view.intent;

import android.content.Context;
import android.content.Intent;

import com.swolf.libraryzxing.view.activity.NYMainActivity;

/**
 * Created by ly on 2017/12/17.
 */

public class NYMainIntent {

    public static void toActivity(Context context){
        Intent intent = new Intent(context, NYMainActivity.class);
        context.startActivity(intent);
    }

}
