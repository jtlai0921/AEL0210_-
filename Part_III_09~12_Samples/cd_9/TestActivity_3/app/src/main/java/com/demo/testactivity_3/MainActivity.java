package com.demo.testactivity_3;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String CUSTOM_INTENT = "com.demo.testactivity_3a.main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToAction(View v) {
        // 方法一：Set action "com.demo.testactivity_3a.main"
        Intent intent = new Intent( CUSTOM_INTENT );
        this.startActivity(intent);
        // 方法二：
//		Intent intent = new Intent(Intent.ACTION_MAIN);
//		intent.setComponent(new ComponentName("com.demo.testactivity_3a",
//				"com.demo.testactivity_3a.MainActivity"));
//		startActivity(intent);
        // 方法三：
//		Intent intent = getPackageManager().getLaunchIntentForPackage( "com.demo.testactivity_3a" );
//		startActivity(intent);
    }
}
