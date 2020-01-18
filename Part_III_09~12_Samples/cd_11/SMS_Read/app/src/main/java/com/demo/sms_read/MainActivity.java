package com.demo.sms_read;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        List<String> msgList = getSMS();

        setContentView(makeMyList(msgList));
        setTitle(getTitle() + ": 共 " + msgList.size() + " 則簡訊");
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
