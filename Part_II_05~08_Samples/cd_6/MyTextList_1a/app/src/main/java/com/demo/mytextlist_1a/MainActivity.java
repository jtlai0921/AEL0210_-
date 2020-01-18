package com.demo.mytextlist_1a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 步驟 1:準備資料
        String [] data = getResources().getStringArray(R.array.name);
        // 步驟 2:準備ListView
        ListView lv1 = (ListView) findViewById(R.id.listView1);

        // 步驟 3:宣告轉接器
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        // 步驟 4:設定轉接器
        lv1.setAdapter(adapter);
    }
}
