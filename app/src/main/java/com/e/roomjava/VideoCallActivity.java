package com.e.roomjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class VideoCallActivity extends AppCompatActivity {

    VideoCallActionBroadcast videoCallActionBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppUtils.stopSound();
    }
}