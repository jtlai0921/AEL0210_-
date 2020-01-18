package com.demo.testactivity_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Activity_A extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
    }

    public void clickToB(View v) {
        Intent intent = new Intent(Activity_A.this, Activity_B.class);
        this.startActivity(intent);
    }
}
