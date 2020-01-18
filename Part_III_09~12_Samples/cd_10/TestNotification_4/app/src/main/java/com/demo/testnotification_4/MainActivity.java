package com.demo.testnotification_4;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "Notification by Receiver";

    private class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            doBindService();
            System.out.println("bindService by Receiver");
            doUnbindService();
            System.out.println("unbindService by Receiver");
        }
    }
    //宣告BroadcastReceiver程式receiver
    private final NotificationReceiver receiver = new NotificationReceiver();

    private NotificationService_2 mBoundService;
    private boolean mIsBound;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //註冊廣播接收receiver
        IntentFilter filter = new IntentFilter(ACTION);
        registerReceiver(receiver, filter);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // 取得所要的服務物件，用以和服務作溝通
            mBoundService = ((NotificationService_2.LocalBinder)service).getService();

            // Tell the user about this for our demo.
            Toast.makeText(MainActivity.this, R.string.local_service_connected,
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // 這個函式會在與服務的連結在不預期中斷時被呼叫
            mBoundService = null;
            Toast.makeText(MainActivity.this, R.string.local_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };

    void doBindService() {
        // 建立與服務的連線
        bindService(new Intent(MainActivity.this,
                NotificationService_2.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            // 進行離線
            unbindService(mConnection);
            mIsBound = false;
        }
    }
}
