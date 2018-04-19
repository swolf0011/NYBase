package com.swolf.librarybase.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NYHomeKeyReceiver extends BroadcastReceiver {
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private static final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //按下Home键会发送ACTION_CLOSE_SYSTEM_DIALOGS的广播
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    // 短按home键
//                    Toast.makeText(context, "短按Home键", Toast.LENGTH_SHORT).show();
                    home(context);
//                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
//                    // RECENT_APPS键
//                    Toast.makeText(context, "RECENT_APPS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void home(Context context) {
        Intent intentw = new Intent(Intent.ACTION_MAIN);
        intentw.addCategory(Intent.CATEGORY_HOME);
        intentw.setClassName("android", "com.android.internal.app.ResolverActivity");
        context.startActivity(intentw);
    }


}
