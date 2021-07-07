package com.e.roomjava;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;


public class AppUtils {
    private static final String TAG = "AppUtils";
    private static final String CHANNEL_ID = "001100";
    public static final String ACCEPT = "Accept";
    public static final String REJECT = "Reject";
    private static final int NOTIFICATION_MESSED_CALL = 102;
    private static final int NOTIFICATION_CALL_ON_PROGRESS = 103;
    public static final String DISMISS = "Dismiss";
    private static NotificationManager mManager;
    private static NotificationCompat.Builder builder;
    public static int SECOND = 1000;
    static Ringtone r;
    static Thread thread;

    public static void showFullScreenNotification(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                    "title", NotificationManager.IMPORTANCE_HIGH);
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager(activity).createNotificationChannel(androidChannel);
        }


        builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Incoming Video Call")
                .setContentText("Dr Rahat Ali Khan is calling .....")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(callAction(AppUtils.ACCEPT, 1, activity))
                .addAction(callAction(AppUtils.REJECT, 2, activity))
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentIntent(getVideoCallScreenIntent(activity))
        ;
        getManager(activity).notify(101, builder.build());
        playNotificationSound();
        updateMissedCallAlert(activity);


    }

    private static PendingIntent getVideoCallScreenIntent(Context activity) {
        Intent fullScreenIntent = new Intent(activity, VideoCallActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(activity, 0,
                fullScreenIntent, 0);
        return fullScreenPendingIntent;
    }

    public static void updateVideoCalNotification(String text, Context context) {
        hideNotification(context);
        if (null != builder) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(getVideoCallScreenIntent(context));
            builder.setContentTitle(text)
                    .setContentText("with Dr. Rahat Ali Khan")
                    .setOngoing(true)
                    .addAction(callAction(AppUtils.DISMISS, 1, context));
            getManager(context).notify(NOTIFICATION_CALL_ON_PROGRESS, builder.build());
        }
        stopSound();
        cancelShowMissedCallNotification();
        /*   startForNotification(context);*/
    }

    public static void hideNotification(Context context) {
        getManager(context).cancel(null, 101);
        stopSound();
    }

    public static void showNotification(Activity activity) {
        playAlertSound();
        Intent fullScreenIntent = new Intent(activity, VideoCallActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(activity, 0,
                fullScreenIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                    "title", NotificationManager.IMPORTANCE_HIGH);
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager(activity).createNotificationChannel(androidChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Missed Video Call")
                .setContentText("Dr. Rehman Ali khan")
                .setContentIntent(fullScreenPendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        getManager(activity).notify(AppUtils.NOTIFICATION_MESSED_CALL, builder.build());


    }

    public static NotificationManager getManager(Context context) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public static NotificationCompat.Action callAction(String action, int i, Context activity) {


        Intent snoozeIntent = new Intent(App.context, VideoCallActionBroadcast.class);
        snoozeIntent.putExtra("com.jc_code_ACTION", action);
        PendingIntent pendingintent = PendingIntent.getBroadcast(App.context, i, snoozeIntent, i == 2 ? PendingIntent.FLAG_CANCEL_CURRENT : PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher_background,
                    HtmlCompat.fromHtml("<font color=\"" + ContextCompat.getColor(activity, i == 1 ? R.color.green : R.color.red) + "\">" + action + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                    pendingintent)
                    .build();
        } else return null;

    }

    public static void playNotificationSound() {
        try {
            Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            r = RingtoneManager.getRingtone(App.context, notificationSoundUri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopSound() {
        if (null != r) {
            r.stop();
        }
    }

    public static void updateMissedCallAlert(Activity activity) {
        SECOND = 1000;
        thread = new Thread(() -> {
            SystemClock.sleep(14 * SECOND);
            if (Thread.currentThread().isInterrupted())
                return;
            hideNotification(activity);
            showNotification(activity);
        });
        thread.start();
    }

    public static void cancelShowMissedCallNotification() {
        thread.interrupt();
    }

    public static void playAlertSound() {
        try {
            Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(App.context, notificationSoundUri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideCallOnProgressNotification(Context context) {
        getManager(context).cancel(null, NOTIFICATION_CALL_ON_PROGRESS);
    }
}
