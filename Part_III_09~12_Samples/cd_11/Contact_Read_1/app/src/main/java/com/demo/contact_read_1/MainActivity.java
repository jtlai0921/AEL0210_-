package com.demo.contact_read_1;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // 取得聯絡人資料
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        // 取得筆數
        int c = cursor.getCount();
        if (c == 0) {
            Toast.makeText(this, "無聯絡人資料", Toast.LENGTH_LONG)
                    .show();
        }

        // 用Activity管理Cursor
        startManagingCursor(cursor);

        // 欲顯示的欄位名稱
        String[] columns = { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER };

        // 欲顯示欄位名稱的 view
        int[] entries = { android.R.id.text1, android.R.id.text2 };

        mListAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, columns, entries);

        // 設定Adapter
        setListAdapter(mListAdapter);
    }
}
