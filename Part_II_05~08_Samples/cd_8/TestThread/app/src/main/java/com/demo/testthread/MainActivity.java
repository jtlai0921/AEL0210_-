package com.demo.testthread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

class TestThread extends Thread {

    @Override
    public void run() {
        for(int i = 0;i<3; i++)
            Log.d("TestThread", "Thread says: "+ new Date().toLocaleString());
    }
    public static void newInstance() {
        Thread tt = new TestThread();
        tt.start();
        for(int i = 0;i<3; i++)
            Log.d("TestThread","Main says: "+ new Date().toLocaleString());
    }
}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TestThread().newInstance();
    }
}
