package com.demo.mybootcompleted;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        Log.i("OnBootCompletedBR", "Received action: " + intent.getAction());
        Log.i("OnBootCompletedBR", "Hi, I'm OnBootCompletedBR!!");
    }
}
