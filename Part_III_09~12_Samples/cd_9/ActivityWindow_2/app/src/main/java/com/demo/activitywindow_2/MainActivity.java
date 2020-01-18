package com.demo.activitywindow_2;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 無標題欄
        getSupportActionBar().hide();
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 無通知欄
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 直橫式
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 直式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 橫式

        setContentView(R.layout.activity_main);
    }
}
