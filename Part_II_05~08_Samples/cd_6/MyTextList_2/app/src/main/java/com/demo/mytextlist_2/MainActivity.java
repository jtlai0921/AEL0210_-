package com.demo.mytextlist_2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        // 步驟 4:設定轉接器
        lv1.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data){

            @Override
            public View getView(int position, View convertView,
                                ViewGroup parent) {
                View view =super.getView(position, convertView, parent);
                TextView tv1=(TextView) view.findViewById(android.R.id.text1);

                tv1.setTextColor(Color.BLUE);
                if(position%2==0)
                    tv1.setBackgroundColor(Color.YELLOW);
                else
                    tv1.setBackgroundColor(Color.LTGRAY);
                return view;
            }
        });
    }
}
