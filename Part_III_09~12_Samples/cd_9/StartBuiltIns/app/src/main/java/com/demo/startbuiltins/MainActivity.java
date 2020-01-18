package com.demo.startbuiltins;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int [] resId = {R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6};
    Button [] btn = new Button[resId.length];

    private static final int ACTIVITY_RESULT_QR_DRDROID = 0;
    public static final String SCAN = "la.droid.qr.scan";
    public static final String COMPLETE = "la.droid.qr.complete";
    public static final String RESULT = "la.droid.qr.result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        for(int i=0; i<btn.length; i++)
            btn[i] = (Button) findViewById(resId[i]);
    }

    public void clickToAction(View v) {
        Uri uri;
        Intent intent;
        if(v==btn[0]) {
            uri = Uri.parse("tel:5554");
            intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);
        }
        else if(v==btn[1]) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.putExtra("sms_body", "簡訊內容");
            intent.setType("vnd.android-dir/mms-sms");
            startActivity(intent);
        }
        else if(v==btn[2]) {
            uri = Uri.parse("smsto:0800000123");
            intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", "簡訊內容");
            startActivity(intent);
        }
        else if(v==btn[3]) {
            uri = Uri.parse("geo:25.047924,121.517081?z=15");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if(v==btn[4]) {
            uri = Uri.parse("http://www.google.com");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if(v==btn[5]) {
            //Set action "la.droid.qr.scan"
            intent = new Intent( SCAN );
            intent.putExtra( COMPLETE , true);
            //Send intent and wait result
            try {
                startActivityForResult(intent, ACTIVITY_RESULT_QR_DRDROID);
            } catch (ActivityNotFoundException activity) {
                Toast.makeText(this, "ActivityNotFoundException", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( ACTIVITY_RESULT_QR_DRDROID==requestCode && null!=data && data.getExtras()!=null ) {
            //Read result from QR Droid (it's stored in la.droid.qr.result)
            String result = data.getExtras().getString(RESULT);
            this.setTitle("掃瞄結果："+result);
        }
    }
}
