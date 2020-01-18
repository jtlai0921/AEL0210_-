package com.demo.testnotification_3;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class NotificationService extends Service {

    private NotificationReceiver mNotificationReceiver = null;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNotificationReceiver);
    }

    @Override
    public void onCreate() {

        mNotificationReceiver = new NotificationReceiver();

        //註冊廣播接收receiver
        IntentFilter filter = new IntentFilter(NotificationReceiver.ACTION);
        registerReceiver(mNotificationReceiver, filter);
    }
}
