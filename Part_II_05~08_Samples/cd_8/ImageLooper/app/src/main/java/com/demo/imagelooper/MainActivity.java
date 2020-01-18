package com.demo.imagelooper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int [] resId = {R.drawable.spade, R.drawable.heart,
            R.drawable.diamond, R.drawable.club};
    ImageView iv1;
    TextView tv1;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv1 = (ImageView) findViewById(R.id.imageView1);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv1.setTextSize(30);
        tv1.postDelayed(new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                tv1.setText(count+"");
                iv1.setImageResource(resId[count%resId.length]);
                count++;
                tv1.postDelayed(this, 1000); // 1000毫秒=1秒
            }}, 1000); // 1000毫秒=1秒
    }
}
