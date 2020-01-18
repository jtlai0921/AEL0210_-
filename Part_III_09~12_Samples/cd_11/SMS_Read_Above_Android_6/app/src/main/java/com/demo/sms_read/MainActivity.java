package com.demo.sms_read;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) { // We need to request runtime permission for Android 6.0 (API level 23) above
            checkForSmsPermission();
        }

//        List<String> msgList = getSMS();
//
//        setContentView(makeMyList(msgList));
//        setTitle(getTitle() + ": 共 " + msgList.size() + " 則簡訊");
    }

    /**
     * 檢查應用程序是否具有SMS權限。
     */
    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            // 尚未授予權限。 使用requestPermissions（）。
            // MY_PERMISSIONS_REQUEST_READ_SMS是一個應用程序定義的int常量。
            // 回調方法獲取請求的結果。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
        }
        else {
            showList();
        }
    }

    private void showList() {
        List<String> msgList = getSMS();

        setContentView(makeMyList(msgList));
        setTitle(getTitle() + ": 共 " + msgList.size() + " 則簡訊");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS:
                if (grantResults.length == 0) {
                    Toast.makeText(this, "Permission request is cancelled", Toast.LENGTH_SHORT).show();
                } else {
                    boolean granted = true;
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            granted = false;
                            break;
                        }
                    }
                    if (!granted) {
                        Toast.makeText(this, "Access to read SMS is denied.\nSome functionalities may not work", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        showList();
                    }
                }
                break;
        }
    }

    private List<String> getSMS() {
        List<String> list = new ArrayList<String>(); // 參閱8.2節之『集合框架之ArrayList』
        Uri uri = Uri.parse("content://sms/inbox");  // 系統存放簡訊之收件夾位置
        Cursor c = getContentResolver().query(uri, null, null ,null,null); // SQL查詢指令
        if(c!=null) { // 若無資料則返回 null
            String sPhoneNo, sMsgBody;
            for (boolean hasData = c.moveToFirst(); hasData; hasData = c.moveToNext()) {
                sPhoneNo = c.getString(c.getColumnIndex("address"));
                sMsgBody = c.getString(c.getColumnIndexOrThrow("body"));
                list.add("簡訊發送人號碼: " + sPhoneNo + "\n簡訊內容: " + sMsgBody + "\n");
            }
            c.close();
        }
        return list;
    }

    private ScrollView makeMyList(List<String> msgList) {
        ScrollView sv=new ScrollView(this);       // 自訂捲軸視圖
        LinearLayout ll=new LinearLayout(this);   // 自訂線性版面視圖
        ll.setOrientation(LinearLayout.VERTICAL); // 設定以垂直方向
        sv.addView(ll,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,   // 與螢幕同寬
                LinearLayout.LayoutParams.WRAP_CONTENT));// 高度隨內容而定
        for(int i=0;i<msgList.size();i++){
            String msg=msgList.get(i);  // 以 ArrayList 之 get 搭配迴圈索引
            TextView tv=new TextView(this); // 處理文字之專用視圖元件
            tv.setText(i + ": " + msg);     // 設定字串內容
            tv.setTextSize(20.0f);          // 設定文字尺寸
            ll.addView(tv, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        return sv;
    }
}
