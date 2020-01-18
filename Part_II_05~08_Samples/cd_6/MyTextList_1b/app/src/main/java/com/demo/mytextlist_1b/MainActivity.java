package com.demo.mytextlist_1b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 步驟 1:準備資料
        String [] data = null;
        try {
            // 1. 讀asset
            InputStream is = getAssets().open("name.txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // 2. 轉成String
            String text = new String(buffer);
            // 3. 萃取每一行
            data = text.split("\n");
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // 步驟 2:準備ListView
        ListView lv1 = (ListView) findViewById(R.id.listView1);

        // 步驟 3:宣告轉接器
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        // 步驟 4:設定轉接器
        lv1.setAdapter(adapter);
    }
}
