package com.demo.testmyprovider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //取得Content Provider的Uri
        getIntent().setData(Uri.parse("content://com.demo.provider.myprovider"));
        Uri uri = getIntent().getData();
        Log.i("TestMyProviderActivity", uri.toString());

        //以Content Provider來查詢
        Cursor cursor = managedQuery(uri, null, null, null, null);
        if(cursor!=null)
            Log.i("TestMyProviderActivity", uri.toString());
        else
            Log.i("TestMyProviderActivity", "cursor==null");
    }
}
