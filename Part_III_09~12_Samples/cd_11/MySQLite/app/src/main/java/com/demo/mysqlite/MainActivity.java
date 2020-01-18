package com.demo.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    DBConnection helper;
    SQLiteDatabase db;

    interface MediaSchema {
        String TABLE_NAME = "MediaTable";     //Table Name
        String _ID = "_id";                    //_ID
        String MEDIA_NAME = "media_name";     //Media Name
        String MIME_TYPE = "mime_type";       //MIME Type
    }

    String [][] mediaData = { {"myimage.jpg", "image/jpeg"},
            {"myvideo.mp4", "video/mp4"},
            {"myaudio.mp3", "audio/mp3"} };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //建立資料庫MediaDB和表單MediaTable
        helper = new DBConnection(this);
        deleteAll(); // 新增前先歸零
        for(int i=0; i<mediaData.length; i++)
            insertDB(mediaData[i]);
        //取得所有資料的MEDIA_NAME
        db = helper.getReadableDatabase();
        Cursor c = db.query(MediaSchema.TABLE_NAME, null, null, null, null, null, null);
        if(c!=null) {
            c.moveToFirst();
            System.out.println("Row count = " + c.getCount());
            for (int i = 0; i < c.getCount(); i++) {
                System.out.println(c.getString(1));
                c.moveToNext();
            }
            c.close();
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(MediaSchema.TABLE_NAME, null ,null);
        db.close();
    }

    public void insertDB(String[] mediaData) {
        ContentValues values = new ContentValues();
        values.put(MediaSchema.MEDIA_NAME, mediaData[0]);
        values.put(MediaSchema.MIME_TYPE, mediaData[1]);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(MediaSchema.TABLE_NAME, null, values);
        db.close();
    }

    //SQLiteOpenHelper-建立資料庫MediaDB和MediaTable
    public static class DBConnection extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MediaDB";
        private static final int DATABASE_VERSION = 1;
        private DBConnection(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE " + MediaSchema.TABLE_NAME + " ("
                    + MediaSchema._ID  + " INTEGER primary key autoincrement, "
                    + MediaSchema.MEDIA_NAME + " text not null, "
                    + MediaSchema.MIME_TYPE + " text not null "+ ");";
            db.execSQL(sql);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MediaSchema.TABLE_NAME);
        }
    }
}
