package com.demo.imagemove_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MyView mView;
    private Bitmap mBitmap;
    private Matrix mtx = new Matrix();
    // For 位置與方向相關屬性
    private float iWidth, iHeight;
    private float imageX = -1.0f, imageY = -1.0f;
    private float fAngle;
    // For 計時器
    private Handler mHandler  = new Handler();
    private Runnable runDancing = new Runnable() {
        public void run() {
            moving();
        }
    };
    private Timer timer;
    class Task extends TimerTask {
        public void run(){
            execute();
        }
        public synchronized void execute() {
            mHandler.removeCallbacks(runDancing);
            mHandler.post(runDancing);
        }
    };// Task
    public Task task = new Task();

    private float speedY=-10;
    private float speedX=-10;

    private synchronized void moving() {
        boolean aChanged=false;

        if(imageY < 0 ||
                imageY + mBitmap.getHeight()  > iHeight){
            this.speedY=(-1)*this.speedY; // 碰到上下邊界時，y反向
            aChanged=true;
        }
        imageY+=this.speedY;

        if(imageX < 0 ||
                imageX + mBitmap.getWidth()  > this.iWidth){
            this.speedX=(-1)*this.speedX; // 碰到左右邊界時，x反向
            aChanged=true;
        }
        imageX+=this.speedX;

        if(aChanged){
            this.genAngle();
        }

        mView.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null) {
            timer.cancel();
            timer = null;
        }
        if(task!=null) task = null;
        if(mtx!=null) mtx = null;
        if(mBitmap!=null) mBitmap = null;
        if(mView!=null) mView = null;
        System.exit(0);
    }

    public void initAngle(){
        fAngle = Math.round(360 * Math.random());
        //e.g. fAngle = 45;
        setTitle(fAngle+"度");
    }

    public void genSpeed(){
        this.speedX=(float)Math.cos(this.fAngle/180*Math.PI)*10;
        this.speedY=(float)Math.sin(this.fAngle/180*Math.PI)*10*(-1);
    }

    public void genAngle(){
        this.fAngle=(float)((Math.atan2(this.speedY, (-1)*this.speedX)+Math.PI)/Math.PI*180);
        setTitle(fAngle+"度");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);
        mView = new MyView(this);
        setContentView(mView);

        this.initAngle();
        this.genSpeed();

        timer = new Timer();
        task = new Task();
        timer.schedule(task, 10, 50);
    }
    //View.onDraw()方法
    private class MyView extends View {
        private Paint mPaint;
        //
        public MyView(Context context) {
            super(context);
            mPaint = new Paint();
        }
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            iWidth=w;
            iHeight=h;

            imageX = Math.round((w-mBitmap.getWidth()) * Math.random())
                    + 0.5f * mBitmap.getWidth();
            imageY = Math.round((h-mBitmap.getHeight()) * Math.random())
                    + 0.5f * mBitmap.getHeight();

            super.onSizeChanged(w, h, oldw, oldh);
        }
        //onDraw() callback方法
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            mPaint.setColor(Color.argb(128, 0, 0, 255));
            canvas.drawCircle(iWidth/3, iHeight/3, 20, mPaint);
            if(imageX==-1.0f) {
                imageX = iWidth*2/3;
                imageY = iHeight*2/3;
            }

            mtx.reset();
            float dg=fAngle;
            dg*=(-1);
            mtx.postRotate(dg+90); // 再轉至目的方向

            Bitmap rotatedBMP = Bitmap.createBitmap(mBitmap,0, 0,
                    mBitmap.getWidth(), mBitmap.getHeight(), mtx, true);
            canvas.drawBitmap(rotatedBMP,
                    imageX,
                    imageY,
                    null);
        }
    }
}
