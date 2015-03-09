package org.kyledef.sittingwearnotify.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.kyledef.sittingwearnotify.utils.ActivityApiUtil;

public class StartServiceAtBootReceiver extends BroadcastReceiver {

    private static final String TAG = "StartServiceAtBoot";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start Listening for Activity Changes
        Log.d(TAG, "Started Activity Service");
        (ActivityApiUtil.getInstance(context)).registerActivityIntent();
    }
}
