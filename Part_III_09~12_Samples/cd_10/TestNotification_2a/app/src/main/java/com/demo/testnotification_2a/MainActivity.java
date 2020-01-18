package com.demo.testnotification_2a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "Notification by Receiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 送出信息給廣播接收程式：觀察TestNotification_2
                // 收到 Action 之後有無作 showNotification() 的動作
                sendBroadcast(new Intent(ACTION));
            }});
    }
}
