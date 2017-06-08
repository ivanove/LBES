package com.lbes.application.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by next on 26/03/17.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("App", "called receiver method");
        try {
            notif.generateNotification(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}