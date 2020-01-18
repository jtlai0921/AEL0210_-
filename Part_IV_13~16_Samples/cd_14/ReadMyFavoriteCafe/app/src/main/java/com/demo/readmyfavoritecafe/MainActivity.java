package com.demo.readmyfavoritecafe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PATH = "/data/data/com.demo.readmyfavoritecafe";
    private static final String DBNAME = "myfavorite";
    private static final String TABLENAME = "info";

    private ListView lv1;
    private SQLiteDatabase dataBase;
    private Cursor cursor;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 準備 ListView, 及 DB 的資料夾, 預備手動拷貝
        lv1 = (ListView) findViewById(R.id.listView1);
        File dbDir = new File(PATH, "databases");
        dbDir.mkdir();
        copyAssets(PATH);
        // 2. 準備資料庫
        dataBase = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
        try {
            cursor = dataBase.query(TABLENAME, null, null, null, null, null, null);
            if(cursor!=null) {
                int iRow = cursor.getCount(); // 取得資料記錄的筆數
                cursor.moveToFirst();
                for(int i=0; i<iRow; i++) { // 第0欄位：no, 第1欄位：name, 第2欄位：addr, 第3欄位：lat, 第4欄位：lng,
                                            // 第5欄位：tel, 第6欄位：business_hour,第7欄位：url
                    String tmp = "";
                    String name = cursor.getString(1);
                    String addr = cursor.getString(2);
                    String lat = cursor.getString(3);
                    String lng = cursor.getString(4);
                    String tel = cursor.getString(5);
                    String business_hour = cursor.getString(6);
                    tmp += name + "\n" + addr + "\n" + lat + "," + lng + "\n" + tel + "\n" + business_hour;
                    list.add(tmp);
                    cursor.moveToNext();
                }
                // 3. 準備adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        list);
                // 4. 設定adapter
                lv1.setAdapter(adapter);
                // 5. 關閉 DB
                dataBase.close();
            }
            else {
                setTitle("Hint 1: 請將db準備好!");
            }
        }
        catch (Exception e) {
            setTitle("Hint 2: 請將db準備好!");
        }
    }

    private void copyAssets(String path) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = getAssets().open(DBNAME);
            out = new FileOutputStream(PATH + "/databases/" + DBNAME);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * 一既有的工具程式，可將來源 InputStream 物件所指向的資料串流
     * 拷貝到OutputStream 物件所指向的資料串流去
     */
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[in.available()];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}
