package com.demo.listonratio_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    final int RATIO = 5;
    int [] iResDrawableId = {R.drawable.coffee, R.drawable.donut, R.drawable.ham,
            R.drawable.juice, R.drawable.milk};
    Bitmap[] bitmap = new Bitmap[iResDrawableId.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 0. 先將圖片比例設好
        initBmp();
        // 開始塞入List項目
        LinearLayout linearLayout1 = (LinearLayout)findViewById(R.id.linearLayout1);
        for(int i=0; i<iResDrawableId.length; i++) {
            // 1. 照次序加入圖片
            ImageView iv = new ImageView(this);
            // 從 ImageResource 改成 ImageBitmap，因為Image透過Bitmap完成縮放
            iv.setImageBitmap(bitmap[i]);
            iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            linearLayout1.addView(iv);
            // 2. 圖片後面加一條分隔線
            ImageView segment = new ImageView(this);
            segment.setImageResource(R.drawable.segment);
            segment.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            segment.setScaleType(ImageView.ScaleType.FIT_XY);
            linearLayout1.addView(segment);
        }
    }

    private void initBmp() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        for(int i=0; i<bitmap.length; i++) {
            bitmap[i] = BitmapFactory.decodeResource(getResources(), iResDrawableId[i]);
            bitmap[i] = Bitmap.createScaledBitmap(bitmap[i],
                    dm.heightPixels/RATIO, dm.heightPixels/RATIO, false);
        }
    }
}
