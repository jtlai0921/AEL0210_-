package com.demo.testnotification_4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class NotificationService_2 extends Service {

	private NotificationManager barManager = null;
	
	private int NOTIFICATION = R.string.local_service_started;
	
	public class LocalBinder extends Binder {
		NotificationService_2 getService() {
            return NotificationService_2.this;
        }
    }
	
	@Override
	public void onCreate() {
		barManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		showNotification();
	}
	
	@Override
    public void onDestroy() {
        // Cancel the persistent notification.
		barManager.cancel(NOTIFICATION);

        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	// 此物件用來接收用戶端的互動訊息
	private final IBinder mBinder = new LocalBinder();

	private void showNotification() {

        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.stat_sys_warning)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.local_service_label))  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        barManager.notify(NOTIFICATION, notification);
    }
}
