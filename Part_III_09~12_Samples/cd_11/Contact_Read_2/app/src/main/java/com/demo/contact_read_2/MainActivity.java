package com.demo.contact_read_2;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends ListActivity {

    private ListAdapter mListAdapter;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        // 取得聯絡人欄位
        String[] columns = { ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.Contacts._ID
        };
        // 取得聯絡人資料
        Cursor contactCursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        // 取得筆數
        int c = contactCursor.getCount();
        if (c == 0)
            Toast.makeText(this, "無聯絡人資料", Toast.LENGTH_LONG)
                    .show();

        // 用Activity管理Cursor
        startManagingCursor(contactCursor);

        // 欲顯示欄位名稱的 view
        int[] entries = { android.R.id.text1, android.R.id.text2 };

        mListAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, contactCursor, columns, entries);

        // 設定Adapter
        setListAdapter(mListAdapter);
    }

    public static Bitmap loadContactPhoto(ContentResolver cr, long  id, long photo_id) {

        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        Log.d("Contact_ReadV2", uri.toString());

        if (input != null)
            return BitmapFactory.decodeStream(input);
        else
            Log.d("Contact_ReadV2","first try failed to load photo");

        return null;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // 取得點選的Cursor
        Cursor c = (Cursor) mListAdapter.getItem(position);

        // 取得_id這個欄位得值
        int contactId = c.getInt(c.getColumnIndex(ContactsContract.Contacts._ID));
        int photoId = c.getInt(c.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
        System.out.println("photoId= " + photoId);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        if(photoId == 0)
            dialog.setTitle("缺照片");
        else
            dialog.setTitle("照片");

        ImageView iv = (ImageView) dialog.findViewById(R.id.image);
        Bitmap bimage=  loadContactPhoto(getContentResolver(), contactId, photoId);
        iv.setImageBitmap(bimage);

        dialog.show();
        super.onListItemClick(l, v, position, id);
    }
}
