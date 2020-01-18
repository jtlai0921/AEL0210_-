package com.demo.multimediaplayers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

public class StreamMediaPlayer extends Activity {

	public static Context context; // 讓主程式作切換控制之用
	private VideoView mVideoView;  // 影片播放器
	private MediaPlayer mMediaPlayer; // 媒體播放器，在此負責聲音媒體
	private Button mButtonImage, mButtonVideo, mButtonAudio; // 三個播放鈕
	private ImageView mImageView; // 呈現影像圖片
	private Uri uri; // Uniform resource locator，用於標識某一網際網路資源名稱的字元串
	String [] uris = { "http://www.aerael.com/uploads/6/9/2/1/69211847/myimage.jpg",
			"http://www.aerael.com/uploads/6/9/2/1/69211847/myvideo.mp4",
			"http://www.aerael.com/uploads/6/9/2/1/69211847/myaudio.mp3"}; // getDuration() > 0
	public static Bitmap myBitmap; // 提供給ImageView作圖片設定
	//
	private int iDuration; // 用來記錄 媒體長度
	private SeekBar mTimebar; // 用來顯示聲音媒體之播放進度
	private Handler mHandler = new Handler();
	private Runnable runVideo = new Runnable() {
		public void run() {
			mVideoView.start();
		}
	};
	private Runnable runAudio = new Runnable() {
		public void run() {
			mMediaPlayer.start();
		}
	};
	private Runnable run = new Runnable() {
		public void run() { // 以另一線程更新播放進度
			if(mTimebar!=null && mMediaPlayer!=null)
				mTimebar.setProgress( mMediaPlayer.getCurrentPosition() );
			mHandler.postDelayed(this, 100); // 以遞迴方式更新播放進度
		}
	};
	public void stopMedia() { // 停止播放並恢復視圖元件之顯示
		if(mMediaPlayer!=null) {
			if(mMediaPlayer.isPlaying())
				mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		if(mVideoView!=null) {
			mHandler.removeCallbacks(runVideo);
			mVideoView.setVisibility(View.INVISIBLE);
		}
		if(mTimebar!=null) {
			mHandler.removeCallbacks(run);
			mHandler.removeCallbacks(runAudio);
			mTimebar.setVisibility(View.GONE);
		}
		if(mImageView!=null) mImageView.setVisibility(View.GONE);
		showButtons();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopMedia();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 0. 允許從網路下載
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		StreamMediaPlayer.context = this;
		setContentView(R.layout.sub);
		initViews(); // 視圖元件初始化

		mVideoView.setMediaController(new MediaController(this)); // 啟動內建之播放面版
		mVideoView.setOnCompletionListener(new OnCompletionListener(){ // 播放完畢恢復視圖元件之顯示
			@Override
			public void onCompletion(MediaPlayer mp) {
				mVideoView.setVisibility(View.INVISIBLE);
				showButtons();
			}});
		mVideoView.setOnErrorListener(new OnErrorListener(){ // 播放錯誤恢復視圖元件之顯示並訊息告知
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				Log.e("StreamMediaPlayer", "Some Errors Happens!");
				Toast.makeText(StreamMediaPlayer.this, "Some Errors Happens!", Toast.LENGTH_LONG).show();
				mVideoView.setVisibility(View.INVISIBLE);
				showButtons();
				return true;
			}});

		// Play Image ...
		mButtonImage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				hideButtons();
				myBitmap = getBitmapFromURL(uris[MainActivity.IMAGE]);
				mImageView.setVisibility(View.VISIBLE);
				mImageView.setImageBitmap(myBitmap);
				mImageView.setOnTouchListener(new DragZoomListener());
			}});
		mImageView.setOnClickListener(new OnClickListener(){ // 點擊圖片恢復視圖元件之顯示
			@Override
			public void onClick(View v) {
				mImageView.setVisibility(View.GONE);
				showButtons();
			}});

		// Play Video ...
		mButtonVideo.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				hideButtons();
				mVideoView.setVisibility(View.VISIBLE);
				uri = Uri.parse(uris[MainActivity.VIDEO]);
				mVideoView.setVideoURI(uri);
				mHandler.post(runVideo);
			}});

		// Play Audio ...
		mButtonAudio.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				mMediaPlayer = new MediaPlayer();
				try {
					mMediaPlayer.setDataSource(uris[MainActivity.AUDIO]);
					mMediaPlayer.prepare();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				hideButtons();
				mTimebar.setVisibility(View.VISIBLE);

				mMediaPlayer.setOnPreparedListener(new OnPreparedListener(){
					@Override
					public void onPrepared(MediaPlayer mp) {
						iDuration = mMediaPlayer.getDuration();
						mTimebar.setMax(iDuration);
						Log.i("StreamMediaPlayer", iDuration+"");
						Toast.makeText(StreamMediaPlayer.this, iDuration+" 毫秒", Toast.LENGTH_SHORT).show();

						mHandler.post(runAudio);
						mHandler.postDelayed(run, 100);
					}});

				mMediaPlayer.setOnCompletionListener(new OnCompletionListener(){
					@Override
					public void onCompletion(MediaPlayer mp) {
						mHandler.removeCallbacks(run);
						mTimebar.setVisibility(View.GONE);
						showButtons();
					}});
			}});
	}
	private void initViews() {
		mVideoView = (VideoView) findViewById(R.id.videoView1);
		mButtonImage = (Button) findViewById(R.id.button1);
		mButtonVideo = (Button) findViewById(R.id.button2);
		mButtonAudio = (Button) findViewById(R.id.button3);
		mImageView = (ImageView) findViewById(R.id.imageview1);
		mTimebar = (SeekBar) findViewById(R.id.timebar);
	}
	private void showButtons() {
		mButtonImage.setVisibility(View.VISIBLE);
		mButtonVideo.setVisibility(View.VISIBLE);
		mButtonAudio.setVisibility(View.VISIBLE);
	}
	private void hideButtons() {
		mButtonImage.setVisibility(View.INVISIBLE);
		mButtonVideo.setVisibility(View.INVISIBLE);
		mButtonAudio.setVisibility(View.INVISIBLE);
	}
	private Bitmap getBitmapFromURL(String src) {
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