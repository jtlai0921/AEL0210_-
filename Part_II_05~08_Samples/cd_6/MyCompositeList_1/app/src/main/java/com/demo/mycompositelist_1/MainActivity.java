package com.demo.mycompositelist_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 步驟 1:準備資料
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
                TextView tv1 = (TextView) getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
                tv1.setText(data[position]);
                return tv1;
            }};
        // 步驟 4:設定轉接器
        lv1.setAdapter(adapter);
    }
}
