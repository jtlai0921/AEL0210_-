package com.demo.mycontactlist_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    int [] resId = {R.drawable.icon_01, R.drawable.icon_02, R.drawable.icon_03,
            R.drawable.icon_04, R.drawable.icon_05, R.drawable.icon_06, R.drawable.icon_07,
            R.drawable.icon_08, R.drawable.icon_09, R.drawable.icon_10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        LinearLayout layout1 = (LinearLayout) v.findViewById(R.id.linearLayout1);
        for(int i=0; i<resId.length; i++) {
            // 以 LayoutInflater的方式，實體化ImageView
            ImageView iv = (ImageView) getLayoutInflater().inflate(R.layout.my_image_view, null);
            // 設定 ImageView 的內容
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId[i]);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
            iv.setImageBitmap(bitmap);
            iv.setPadding(10, 10, 10, 10);
            // 將 ImageView 加進 LinearLayout內
            layout1.addView(iv);
        }
        // 最後以 Root Layout 作為整個視窗的內容視圖
        setContentView(v);
    }
}
