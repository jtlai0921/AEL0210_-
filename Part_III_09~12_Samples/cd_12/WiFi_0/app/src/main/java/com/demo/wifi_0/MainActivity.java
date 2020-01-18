package com.demo.wifi_0;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private WifiManager wmgr;

    private ToggleButton tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        tb = findViewById(R.id.toggleButton);

        wmgr = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        tb.setChecked(wmgr.isWifiEnabled());
        if(wmgr.isWifiEnabled()) {
            tb.setText("關閉");
        }
        else {
            tb.setText("開啟");
        }

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wmgr.setWifiEnabled(isChecked);
                String msg = "";
                if(isChecked) {
                    msg = "正在開啟WiFi";
                    tb.setText("關閉");
                }
                else {
                    msg = "正在關閉WiFi";
                    tb.setText("開啟");
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
