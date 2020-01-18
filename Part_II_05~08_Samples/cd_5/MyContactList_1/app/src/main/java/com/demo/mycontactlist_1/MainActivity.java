package com.demo.mycontactlist_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String [] data = {"陳一", "林二", "張三", "李四", "王五",
            "小六", "川七", "老八", "馬九", "全十"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        LinearLayout layout1 = (LinearLayout) v.findViewById(R.id.linearLayout1);
        for(int i=0; i<data.length; i++) {
            // 以 LayoutInflater的方式，實體化TextView
            TextView tv = (TextView) getLayoutInflater().inflate(R.layout.my_text_view, null);
            // 設定 TextView 的內容
            tv.setText(data[i]);
            tv.setTextSize(20);
            tv.setPadding(10, 10, 10, 10);
            // 將 TextView 加進 LinearLayout內
            layout1.addView(tv);
        }
        // 最後以 Root Layout 作為整個視窗的內容視圖
        setContentView(v);
    }
}
