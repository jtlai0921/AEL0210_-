package com.demo.sms_br;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkForSmsPermission();

        finish();
    }

    /**
     * 檢查應用程序是否具有SMS權限。
     */
    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            // 尚未授予權限。 使用requestPermissions（）。
            // MY_PERMISSIONS_REQUEST_SEND_SMS是一個應用程序定義的int常量。
            // 回調方法獲取請求的結果。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
        }
    }
}
