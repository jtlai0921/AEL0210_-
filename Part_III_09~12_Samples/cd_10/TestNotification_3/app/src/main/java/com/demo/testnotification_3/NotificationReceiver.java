package com.demo.testnotification_3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String ACTION = "Notification by Receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
        System.out.println("Notification by Receiver");
    }

    protected void showNotification (Context context) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// 在您的應用中為Activity創建一個明確的Intent
        Intent resultIntent = new Intent(context, MainActivity.class);

// 堆棧構建器對象將包含啟動的Activity的人工後退堆棧。
// 這確保了從Activity導航到您的應用程序導航到主屏幕。
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// 為Intent添加回棧（但不是Intent本身）
        stackBuilder.addParentStack(MainActivity.class);
// 將啟動Activity的Intent添加到堆棧的頂部
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());

    }
}
