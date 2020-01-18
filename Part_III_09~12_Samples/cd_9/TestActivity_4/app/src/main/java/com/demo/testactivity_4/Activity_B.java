package com.demo.testactivity_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity_B extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        TextView tv1 = (TextView) findViewById(R.id.textView1);
        // get parameters
        Bundle b = getIntent().getExtras();
        String name = b.getString("NAME");
        int passwd = b.getInt("PASSWD");
        tv1.setText(tv1.getText() + "\n" + "Data received:\nName: "
                + name + "\n" + "Passwd: "+ passwd);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("HOBBY", "看電影");
        intent.putExtra("AGE", 25);

        //回應信息，回到Activity_A，resultCode == RESULT_OK
        setResult(RESULT_OK, intent);

        finish();
    }
}
