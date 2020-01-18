package com.demo.testnotification_1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showNotification();
                finish();
            }});
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
