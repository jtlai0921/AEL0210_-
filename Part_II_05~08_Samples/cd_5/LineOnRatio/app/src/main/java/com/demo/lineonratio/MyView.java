package com.demo.lineonratio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 2017/12/13.
 */

public class MyView extends View {

    final int N = 5;
    int w, h;
    Paint paint = new Paint();

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.draw(canvas);
        for(int i=0; i<N-1; i++) {
            canvas.drawLine(0, (i+1)*h/N, w-1, (i+1)*h/N, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }
}
