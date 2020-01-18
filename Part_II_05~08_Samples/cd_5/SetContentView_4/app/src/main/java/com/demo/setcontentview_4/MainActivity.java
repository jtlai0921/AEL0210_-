package com.demo.setcontentview_4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        RelativeLayout layout1 = (RelativeLayout) v.findViewById(R.id.relativeLayout1);
        //
        TextView tv = (TextView) getLayoutInflater().inflate(R.layout.my_text_view, null);
        // 將 TextView 加進 RelativeLayout內
        layout1.addView(tv);
        // 最後以 Parent Layout 作為整個視窗的內容視圖
        setContentView(v);
    }
}
