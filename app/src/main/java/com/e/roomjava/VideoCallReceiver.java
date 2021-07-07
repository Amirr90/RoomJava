package com.e.roomjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class VideoCallReceiver extends BroadcastReceiver {
    private static final String TAG = "VideoCallReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getData());
    }
}
