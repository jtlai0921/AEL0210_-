package com.demo.mygsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String SHEET_ID = "1fKlkx8yea3_1r8n_MUo78YC5Hm2o-gWb_G-2hLMkozY";		/* Google 試算表的 Id 識別代號 */
    static final boolean DEBUG = true;

    SpreadsheetService service;															/* SpreadsheetService 物件變數，用來執行試算表的相關功能 */

    URL SPREADSHEET_FEED_URL;															/* 需填入 Spreadsheet官方指定網址 */
    URL WORKSHEET_FEED_URL;																/* 需填入所要操作的試算表的 Id至Spreadsheet官方指定網址 */
    String[] SCOPESArray = {"https://spreadsheets.google.com/feeds", 					/* Google Gdata Client認證時的需要告知使用範圍 */
            "https://spreadsheets.google.com/feeds/spreadsheets/private/full",
            "https://docs.google.com/feeds"};

    List<String> textInfo = new ArrayList<String>();										/* 為了儲存最愛名稱之用 */
    List<String> latLng = new ArrayList<String>();										/* 為了儲存最愛內容之用 */
    ListView mListView;																	/* 為了顯示最愛名稱之用 */
    ArrayAdapter<String> adapter;														/* ListView 專用的轉接器 */

    private void authService() throws AuthenticationException, MalformedURLException, IOException, ServiceException, URISyntaxException, GeneralSecurityException {
        SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
        WORKSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/worksheets/"+SHEET_ID+"/private/full");
        File p12 = getTempPkc12File("My Project-2799755a370a.p12");						/* 採用 Java的 File類別，此處為要處理 Google認證所需的p12檔 */

        HttpTransport httpTransport = new NetHttpTransport();							/* Google Gdata Client專用的Http傳輸類別 */
        JacksonFactory jsonFactory = new JacksonFactory();								/* Google Gdata Client專用的Jackson格式剖析類別 */
        List<String> SCOPES = Arrays.asList(SCOPESArray);								/* 預備將String[]轉成 List<String>格式儲存 */
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId("core-yew-151602@appspot.gserviceaccount.com")
                .setServiceAccountPrivateKeyFromP12File(p12)
                .setServiceAccountScopes(SCOPES)
                .build();																/* Google Gdata Client認證時專用的類別，作認證資料的準備 */
        System.out.println("new SpreadsheetService(MyService)");

        // 1. 認證
        service =  new SpreadsheetService("MyService");					/* SpreadsheetService物件初始化 */
        service.setOAuth2Credentials(credential); 										/* 執行 OAuth2 認證 */
    }

    private File getTempPkc12File(String filename) throws IOException {					/* 利用 Android Asset機制，處理App內所夾帶的檔案並回傳 */
        InputStream pkc12Stream = this.getAssets().open(filename);
        File tempPkc12File = File.createTempFile("P12File", "p12");
        OutputStream tempFileStream = new FileOutputStream(tempPkc12File);

        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = pkc12Stream.read(bytes)) != -1) {
            tempFileStream.write(bytes, 0, read);
        }
        tempFileStream.close();
        return tempPkc12File;
    }

    public int getData(final int sheetNum)
            throws AuthenticationException, MalformedURLException, IOException, ServiceException, URISyntaxException  {

        // 2. 取得 worksheet (工作表) of spreadsheet (試算表)，此處的 SHEET_ID 是 SPREAD_SHEET_ID
        service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class); 					/* Google Gdata 取得Spreadsheet的資訊，這道敘述不作無法取得Worksheet */
        WorksheetFeed worksheetFeed = service.getFeed(
                new URL("https://spreadsheets.google.com/feeds/worksheets/"+SHEET_ID+"/private/full"),
                WorksheetFeed.class);													/* Google Gdata 取得Worksheet的專用類別 */
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();					/* Google Gdata 取得WorksheetEntry之後，會以List形式儲存 */
        if(DEBUG) {
            for(int i=0; i<worksheets.size(); i++) {									/* 可以取出所有工作表的標題 */
                WorksheetEntry ws = worksheets.get(i);
                System.out.println(i + "'s tag = " + ws.getTitle().getPlainText());
            }
        }

        // 3. 取得某工作表之內容
        if(sheetNum < worksheets.size()) {
            WorksheetEntry worksheet = worksheets.get(sheetNum); 						/* 指定某個工作表編號sheetNum */
            URL listFeedUrl = worksheet.getListFeedUrl(); 								/* 取得下載的 URL點 */
            ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);			/* 下載Worksheet資料 */

            for (ListEntry row : listFeed.getEntries()) {
                Object [] tag = row.getCustomElements().getTags().toArray();			/* 以row為單位，取得整列資料*/
                String name = row.getCustomElements().getValue((String)tag[1]);
                String addr = row.getCustomElements().getValue((String)tag[2]);
                String lat = row.getCustomElements().getValue((String)tag[3]);
                String lng = row.getCustomElements().getValue((String)tag[4]);
                String tel = row.getCustomElements().getValue((String)tag[5]);
                String business_hour = row.getCustomElements().getValue((String)tag[6]);
                String tmp = name + "\n" +                      /* 儲存該row的第1行(欄)，此處為最愛名稱 */
                             addr + "\n" +                      /* 儲存該row的第2行(欄)，此處為最愛地址 */
                             lat + ","  + lng + "\n" +          /* 儲存該row的第3~4行(欄)，此處為最愛經緯度 */
                             tel + "\n" +                       /* 儲存該row的第5行(欄)，此處為最愛電話 */
                             business_hour;                     /* 儲存該row的第6行(欄)，此處為最愛電話 */
                textInfo.add(tmp);		                        /* 儲存該row的第1~6行(欄)，此處為最愛資訊清單內容 */
                latLng.add(lat + ","  + lng);    				/* 儲存該row的第2~6行(欄)，此處為最愛內容 */

                if(DEBUG) {
                    for(int i=0; i<textInfo.size(); i++) {
                        System.out.print(textInfo.get(i) + ", ");
                    }
                    System.out.println();
                    for(int i=0; i<latLng.size(); i++) {
                        System.out.print(latLng.get(i) + ", ");
                    }
                    System.out.println();
                }
            }
            return listFeed.getEntries().size();
        }
        else
            return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 0. 允許從網路下載
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        mListView = (ListView) findViewById(R.id.listView1); 									/* 取得 ListView mListView 物件變數 */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, textInfo);	/* 轉接器初始化 */
        mListView.setAdapter(adapter);															/* mListView設定轉接器 */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){								/* mListView設定項目Click之控制器 */

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if(position < latLng.size()) {
                    Toast.makeText(MainActivity.this, latLng.get(position), Toast.LENGTH_LONG).show();
                }
            }});
        new Thread(new Runnable(){																/* 網路行為多半搭配 Thread執行 */

            @Override
            public void run() {
                try {
                    authService();																/* 第一步先做認證 */

                    String lang = Locale.getDefault().getDisplayLanguage();						/* 第二步取得服務內容之前，先判斷語系 */
//                    if(lang.equalsIgnoreCase("中文"))
                    getData(0); // 中文資訊

                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            adapter.notifyDataSetChanged();										/* 第三步透過轉接器，通知mListView更新UI */
                        }});
                } catch (AuthenticationException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (ServiceException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (GeneralSecurityException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }}).start();
    }
}
