package com.demo.testnotification_2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "Notification by Receiver";

    private class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNotification();
            System.out.println("Notification by Receiver");
        }
    }
    //宣告BroadcastReceiver程式receiver
    private final NotificationReceiver receiver = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 送出信息給廣播接收程式：改由 NotificationReceiver
                // 收到 Action 之後來作 showNotification() 的動作
                showNotification();
                finish();
            }});

        //註冊廣播接收receiver
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    protected void showNotification () {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
// 在您的應用中為Activity創建一個明確的Intent
        Intent resultIntent = new Intent(this, MainActivity.class);
// 堆棧構建器對象將包含啟動的Activity的人工後退堆棧。
// 這確保了從Activity導航到您的應用程序導航到主屏幕。
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
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
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }
}
