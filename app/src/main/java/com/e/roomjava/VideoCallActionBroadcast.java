package com.e.roomjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.e.roomjava.AppUtils.cancelShowMissedCallNotification;
import static com.e.roomjava.AppUtils.hideNotification;

public class VideoCallActionBroadcast extends BroadcastReceiver {
    private static final String TAG = "VideoCallActionBroadcas";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getStringExtra("com.jc_code_ACTION"));
        if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(AppUtils.ACCEPT)) {
            Log.d(TAG, "onReceiveCall Accepted !! : ");

            AppUtils.updateVideoCalNotification("Call is on progress", context);
            Intent fullScreenIntent = new Intent(context, VideoCallActivity.class);
            fullScreenIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(fullScreenIntent);
            Toast.makeText(context, "Call Accepted !!", Toast.LENGTH_SHORT).show();
        }
        if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(AppUtils.REJECT)) {
            hideNotification(context);
            cancelShowMissedCallNotification();
            Log.d(TAG, "onReceiveCall rejected !! : ");
            Toast.makeText(context, "Call Rejected !!", Toast.LENGTH_SHORT).show();
        }
        if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(AppUtils.DISMISS)) {
            AppUtils.hideCallOnProgressNotification(context);
            Log.d(TAG, "onReceiveCall Disconnected !! : ");
            Toast.makeText(context, "Call Disconnected !!", Toast.LENGTH_SHORT).show();
        }

    }
}
