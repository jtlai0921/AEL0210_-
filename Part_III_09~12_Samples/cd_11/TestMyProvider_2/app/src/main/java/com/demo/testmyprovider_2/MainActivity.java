package com.demo.testmyprovider_2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    interface MediaSchema {
        String TABLE_NAME = "MediaTable";     //Table Name
        String _ID = "_id";                   //ID
        String MEDIA_NAME = "media_name";     //Media Name
        String MIME_TYPE = "mime_type";       //MIME Type
    }

    String [][] mediaData = { {"myimage.jpg", "image/jpeg"},
            {"myvideo.mp4", "video/mp4"},
            {"myaudio.mp3", "audio/mp3"} };

    Uri[] uri_returned = new Uri [mediaData.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //取得Content Provider的Uri
        getIntent().setData(Uri.parse("content://com.demo.provider.myprovider2"));
        Uri uri = getIntent().getData();
        Log.i("TestMyProvider_2", uri.toString());

        getContentResolver().delete(uri, null, null);
        // 新增全部
        testInsert(uri);
        // 一次查詢全部
        Log.i("TestMyProvider_2", "Query All:");
        testQuery(uri);
        // 分批查詢
        Log.i("TestMyProvider_2", "Query Each:");
        for(int i=0; i<uri_returned.length; i++)
            testQuery(uri_returned[i]);
        // 修改某一筆
        Log.i("TestMyProvider_2", "Update Someitem:");
        testUpdate(0);
        testQuery(uri_returned[0]);
    }

    private void testInsert(Uri uri) {
        ContentValues[] values = new ContentValues[mediaData.length];
        for(int i=0; i<mediaData.length; i++) {
            values[i] = new ContentValues();
            values[i].put(MediaSchema.MEDIA_NAME, mediaData[i][0]);
            values[i].put(MediaSchema.MIME_TYPE, mediaData[i][1]);
            uri_returned[i] = getContentResolver().insert(uri, values[i]);
            Log.i("TestMyProvider_2", uri_returned[i].toString());
        }
    }

    private void testQuery(Uri uri) {
        Cursor cursor = managedQuery(uri, null, null, null, null);
        if(cursor!=null) {
            Log.i("TestMyProvider_2", uri.toString());
            cursor.moveToFirst();
            CharSequence[] list = new CharSequence[cursor.getCount()];
            Log.i("TestMyProvider_2", "cursor's count = " + cursor.getCount());
            for (int i = 0; i < list.length; i++) {
                Log.i("TestMyProvider_2", "_ID = " + cursor.getString(0));        // _ID
                Log.i("TestMyProvider_2", "MEDIA_NAME = " + cursor.getString(1)); // MEDIA_NAME
                Log.i("TestMyProvider_2", "MIME_TYPE = " + cursor.getString(2));  // MIME_TYPE
                cursor.moveToNext();
            }
            cursor.close();
        }
        else
            Log.i("TestMyProvider_2", "cursor==null");
    }

    private void testUpdate(int i) {
        ContentValues values = new ContentValues();
        values = new ContentValues();
        values.put(MediaSchema.MEDIA_NAME, "newimage.jpg");
        values.put(MediaSchema.MIME_TYPE, mediaData[i][1]);
        if(i < uri_returned.length) {
            String selection = uri_returned[i].getPathSegments().get(1);
            int c = getContentResolver().update(uri_returned[i], values, selection, null);
            Log.i("TestMyProvider_2", c+"");
        }
    }
}
