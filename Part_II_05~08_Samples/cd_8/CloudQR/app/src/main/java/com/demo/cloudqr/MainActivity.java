package com.demo.cloudqr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText et1;
    ImageView iv1;
    LinearLayout linear1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 允許執行緒從網路下載
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // 2. find views
        et1 = (EditText) findViewById(R.id.editText1);
        iv1 = (ImageView) findViewById(R.id.imageView1);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
    }

    public void convert(View view) {
        final String inputText = et1.getText().toString();
        if(inputText.length()==0) {
            Toast.makeText(this, "請輸入內容!", Toast.LENGTH_SHORT).show();
        }
        else {
            linear1.setVisibility(View.VISIBLE);
            iv1.setVisibility(View.INVISIBLE);
            iv1.postDelayed(new Runnable(){
                @Override
                public void run() {
                    String imagePath = "";
                    try {
                        imagePath = "http://chart.apis.google.com/chart?cht=qr&chs=300x300&chl="+
                                URLEncoder.encode(inputText, "UTF-8") ;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Bitmap bimage=  getBitmapFromURL(imagePath);
                    iv1.setImageBitmap(bimage);
                    linear1.setVisibility(View.INVISIBLE);
                    iv1.setVisibility(View.VISIBLE);
                }}, 100);
        }

    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
