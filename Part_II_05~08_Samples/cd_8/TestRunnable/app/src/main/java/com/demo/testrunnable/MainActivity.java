package com.demo.testrunnable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

class TestRunnable implements Runnable {

    @Override
    public void run() {
        for(int i = 0;i<3; i++)
            Log.d("TestRunnable", "Runnable says: "+ new Date().toLocaleString());
    }
    public static void newInstance() {
        Runnable rr = new TestRunnable();
        Thread tt = new Thread(rr);
        tt.start();
        for(int i = 0;i<3; i++)
            Log.d("TestRunnable","Main says: "+ new Date().toLocaleString());
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TestRunnable().newInstance();
    }
}
