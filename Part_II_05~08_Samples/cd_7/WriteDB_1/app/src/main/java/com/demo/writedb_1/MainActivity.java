package com.demo.writedb_1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PATH = "/data/data/com.demo.writedb_1";
    private static final String DBNAME = "contact0.db";
    private static final String TABLENAME = "info";

    private ListView lv1;
    private SQLiteDatabase dataBase;
    private Cursor cursor;
    private List<String> list = new ArrayList<>();

    View writeView1;
    EditText et1, et2, et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 0. 準備 寫資料庫相關的 View
        writeView1 = findViewById(R.id.writeView1);
        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        // 1. 準備 ListView, 及 DB 的資料夾, 預備手動拷貝
        lv1 = findViewById(R.id.listView1);
        File dbDir = new File(PATH, "databases");
        dbDir.mkdir();
        File dbFile = new File(PATH+"/databases", DBNAME);
        if(dbFile.exists() && dbFile.isFile())
            setTitle("DB 已經存在!!");
        else
            copyAssets(PATH);
    }

    public void clickToReadDB(View view) {
        writeView1.setVisibility(View.GONE);
        lv1.setVisibility(View.VISIBLE);
        // 2. 準備資料庫
        dataBase = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        try {
            cursor = dataBase.query(TABLENAME, null, null, null,
                    null, null, null);
            if(cursor!=null) {
                int iRow = cursor.getCount(); // 取得資料記錄的筆數
                cursor.moveToFirst();
                list.clear();
                for(int i=0; i<iRow; i++) { // 第 0 欄位：_id, 第 1欄位：name, 第 2 欄位：email
                    String name = cursor.getString(1);
                    list.add(name);
                    cursor.moveToNext();
                }
                // 3. 準備adapter：利用 SimpleCursorAdapter 之 DB 須包含 _id 欄位
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
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
            e.printStackTrace();
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
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public void clickToShowForm(View view) {
        writeView1.setVisibility(View.VISIBLE);
        lv1.setVisibility(View.GONE);
    }

    public void clickToWriteDB(View view) {
        // 1. 寫入資料庫前，先檢查是否有填完整
        String data1 = et1.getText().toString();
        String data2 = et2.getText().toString();
        String data3 = et3.getText().toString();
        if(data1.equals("")) { setTitle("請將 Name 準備好!"); return; }
        if(data2.equals("")) { setTitle("請將 Id 準備好!"); return; }
        if(data3.equals("")) { setTitle("請將 Email 準備好!"); return; }
        // 2. 利用 ContentValues 物件暫存所要寫入的資料
        ContentValues values = new ContentValues();
        values.put("name", data1);
        values.put("_id", data2);
        values.put("email", data3);
        // 3. 以 dataBase 開檔，insert()方法寫入
        dataBase = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        long result = dataBase.insert(TABLENAME, null, values);
        if(result!=-1L) {		// 若是回傳值不等於 -1，表示寫入成功。
            dataBase.close();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            Toast.makeText(getApplicationContext(), "新增項目成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "新增項目失敗", Toast.LENGTH_SHORT).show();
        }
    }
}
