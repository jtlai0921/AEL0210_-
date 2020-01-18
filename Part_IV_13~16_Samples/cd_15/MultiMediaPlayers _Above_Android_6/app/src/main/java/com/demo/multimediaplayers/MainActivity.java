package com.demo.multimediaplayers;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    public final static int IMAGE = 0;
    public final static int VIDEO = 1;
    public final static int AUDIO = 2;

    public final static int RESOURCE_TYPE = 0;
    public final static int LOCAL_TYPE = 1;
    public final static int STREAM_TYPE = 2;

    private int iCurrentTab = LOCAL_TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 直式

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) { // We need to request runtime permission for Android 6.0 (API level 23) above
            checkForPermission();
        }
    }

    /**
     * 檢查應用程序是否具有WRITE_EXTERNAL_STORAGE權限。
     */
    private void checkForPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 尚未授予權限。 使用requestPermissions（）。
            // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE是一個應用程序定義的int常量。
            // 回調方法獲取請求的結果。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        else {
            initAll();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length == 0) {
                    Toast.makeText(this, "Permission request is cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    boolean granted = true;
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            granted = false;
                            break;
                        }
                    }
                    if (!granted) {
                        Toast.makeText(this, "Permission request is denied", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        initAll();
                    }
                }
                break;
        }
    }

    private void initAll() {
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, ResMediaPlayer.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("tab0").setIndicator("Resources",
                res.getDrawable(R.drawable.ic_tab1))
                .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the tab2
        intent = new Intent().setClass(this, LocalMediaPlayer.class);

        spec = tabHost.newTabSpec("tab1").setIndicator("Locals",
                res.getDrawable(R.drawable.ic_tab2))
                .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the tab3
        intent = new Intent().setClass(this, StreamMediaPlayer.class);

        spec = tabHost.newTabSpec("tab2").setIndicator("Streams",
                res.getDrawable(R.drawable.ic_tab3))
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(iCurrentTab);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                Log.i("MainTabMenu", tabId);
                switch(iCurrentTab) {
                    case RESOURCE_TYPE:
                        ((ResMediaPlayer) ResMediaPlayer.context).stopMedia();
                        break;
                    case LOCAL_TYPE:
                        ((LocalMediaPlayer) LocalMediaPlayer.context).stopMedia();
                        break;
                    case STREAM_TYPE:
                        ((StreamMediaPlayer) StreamMediaPlayer.context).stopMedia();
                        break;
                }
                iCurrentTab = Integer.parseInt(tabId.substring(3, 4));
            }});
    }
}
