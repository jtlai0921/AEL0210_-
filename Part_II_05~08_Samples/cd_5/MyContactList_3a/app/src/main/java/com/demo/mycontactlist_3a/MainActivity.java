package com.demo.mycontactlist_3a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int [] resId = {R.drawable.icon_01, R.drawable.icon_02, R.drawable.icon_03,
            R.drawable.icon_04, R.drawable.icon_05, R.drawable.icon_06, R.drawable.icon_07,
            R.drawable.icon_08, R.drawable.icon_09, R.drawable.icon_10};
    String [] data = {"陳一", "林二", "張三", "李四", "王五",
            "小六", "川七", "老八", "馬九", "全十"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        View v = getLayoutInflater().inflate(R.layout.activity_main, null);
        LinearLayout layout1 = (LinearLayout) v.findViewById(R.id.linearLayout1);
        for(int i=0; i<resId.length; i++) {
            // 以 LayoutInflater的方式，實體化LinearLayout
            LinearLayout layout2 = (LinearLayout) getLayoutInflater().inflate(
                    R.layout.my_image_and_text, null);
            layout2.setGravity(Gravity.CENTER_VERTICAL);
			/* 以 LayoutInflater的方式，實體化TextView, 並設定 TextView 的內容 */
            TextView tv = (TextView) layout2.findViewById(R.id.textView1);
            tv.setText(data[i]);
            tv.setTextSize(20);
            tv.setPadding(10, 10, 10, 10);
			/* 以 LayoutInflater的方式，實體化ImageView, 並設定 ImageView 的內容 */
            ImageView iv = (ImageView) layout2.findViewById(R.id.imageView1);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId[i]);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
            iv.setImageBitmap(bitmap);
            iv.setPadding(10, 10, 10, 10);
			/* 將每一筆 layout2 都加進 LinearLayout1內*/
            layout1.addView(layout2);
            // 新增一條分隔線，加進 LinearLayout1內
            ImageView segment = new ImageView(this);
            segment.setImageResource(R.drawable.segment);
            segment.setScaleType(ScaleType.FIT_XY);
            layout1.addView(segment, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
        // 最後以 Root Layout 作為整個視窗的內容視圖
        setContentView(v);
    }
}
