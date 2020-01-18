package com.demo.cloudpie_2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView iv1;
    LinearLayout linear1;
    Bitmap [] bimage = new Bitmap[2];
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 允許執行緒從網路下載
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // 2. find views
        iv1 = (ImageView) findViewById(R.id.imageView1);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        // 3. 下載圖片
        iv1.postDelayed(new Runnable(){
            @Override
            public void run() {
                // 3.1 圖片_1
                String imagePath = "";
                imagePath = "http://chart.apis.google.com/chart?cht=p&" +
                        "chs=500x250&chdl=first+legend%7Csecond+legend%7Cthird+legend&" +
                        "chl=first+label%7Csecond+label%7Cthird+label&" +
                        "chco=FF0000|00FFFF|00FF00,6699CC|CC33FF|CCCC33&" +
                        "chp=0.436326388889&chtt=My+Google+Chart&" +
                        "chts=000000,24&chd=t:5,10,50|25,35,45";
                bimage[0] =  getBitmapFromURL(imagePath);
                // 3.2 圖片_2
                imagePath = "http://chart.apis.google.com/chart?cht=bhs&chs=500x250&" +
                        "chco=FF0000|00FFFF|00FF00,6699CC|CC33FF|CCCC33&chxt=x,y&" +
                        "chxr=0,-20,100|1,0,50&chdl=first+legend%7Csecond+legend%7Cthird+legend&" +
                        "chbh=a&chtt=My+Google+Chart&chts=000000,24&chd=t:5,10,50|25,35,45";
                bimage[1] =  getBitmapFromURL(imagePath);
                iv1.setImageBitmap(bimage[0]);
                linear1.setVisibility(View.INVISIBLE);
                iv1.setVisibility(View.VISIBLE);
                // 3.3 開始播圖
                iv1.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        iv1.setImageBitmap(bimage[count%bimage.length]);
                        count++;
                        iv1.postDelayed(this, 2000); // 一再輪播, 播放速度可以調整
                    }}, 100);
            }}, 100);
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
