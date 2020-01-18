package com.demo.testactivity_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String SCAN = "la.droid.qr.scan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToQR(View v) {
        //Set action "la.droid.qr.scan"
        Intent intent = new Intent( SCAN );
        this.startActivity(intent);
    }
}
