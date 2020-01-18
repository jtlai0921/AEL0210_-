package com.demo.testactivity_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity_A extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(Activity_A.this, Activity_B.class);
                intent.putExtra("NAME", "paul");
                intent.putExtra("PASSWD", 1234);
                startActivityForResult(intent, 0);
            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                // get return parameters
                String hobby = b.getString("HOBBY");
                int age = b.getInt("AGE");
                TextView tv1 = (TextView) findViewById(R.id.textView1);
                tv1.setText(tv1.getText() + "\n" + "Data returned:\nHobby: "
                        + hobby + "\n" + "Age: "+ age);
            }
        }
    }
}
