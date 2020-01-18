package com.demo.mycompositelist_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 步驟 1:準備資料
        final int [] resId = {R.drawable.icon_01, R.drawable.icon_02, R.drawable.icon_03,
                R.drawable.icon_04, R.drawable.icon_05, R.drawable.icon_06, R.drawable.icon_07,
                R.drawable.icon_08, R.drawable.icon_09, R.drawable.icon_10};
        final String [] data = {"陳一", "林二", "張三", "李四", "王五",
                "小六", "川七", "老八", "馬九", "全十"};
        // 步驟 2:準備ListView
        ListView lv1 = (ListView) findViewById(R.id.listView1);
        // 步驟 3:宣告轉接器
        BaseAdapter adapter = new BaseAdapter(){

            @Override
            public int getCount() {
                return data.length;
            }

            @Override
            public Object getItem(int arg0) {
                return null;
            }

            @Override
            public long getItemId(int arg0) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup arg2) {
                View view = getLayoutInflater().inflate(R.layout.my_composite, null);
                // 設定圖片
                ImageView iv1 = (ImageView) view.findViewById(R.id.imageView1);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId[position]);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                iv1.setImageBitmap(bitmap);
                // 設定文字
                TextView tv1 = (TextView) view.findViewById(android.R.id.text1);
                tv1.setText(data[position]);
                return view;
            }};
        // 步驟 4:設定轉接器
        lv1.setAdapter(adapter);
    }
}
