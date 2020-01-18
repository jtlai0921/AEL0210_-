package com.demo.setcontentview_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        RelativeLayout layout1 = (RelativeLayout) v.findViewById(R.id.relativeLayout1);
        // 以 Java new 的方式，實體化TextView
        TextView tv1 = new TextView(this);
        // 設定文字內容
        tv1.setText("Hello World!");
        // 定義版面參數，最基本的是寬與高的設定
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 定義置中對齊
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        // 設定版面參數
        tv1.setLayoutParams(params);
        // 將 TextView 加進 RelativeLayout內
        layout1.addView(tv1);
        // 最後以 RelativeLayout 作為整個視窗的內容視圖
        setContentView(layout1);
    }
}
