package com.e.roomjava;

import android.app.Application;

public class App extends Application {

    public static App context;
    VideoCallActionBroadcast videoCallActionBroadcast;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


}
