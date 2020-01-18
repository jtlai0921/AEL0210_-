package com.demo.multimediaplayers;;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DragZoomListener implements OnTouchListener {

	private static final String TAG = "DragZoomListener";

    private ImageView iv1;

	// We can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
	private static final int CLICK = 3;
    private int mode = NONE;

	// Remember some things for zooming
	private PointF start = new PointF();
	private PointF mid = new PointF();
	private float oldDist = 1f;
	private PointF startRef = new PointF();

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		iv1 = (ImageView) v;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				start.set(event.getX(), event.getY());
				startRef.set(iv1.getX()+event.getX(), iv1.getY()+event.getY());
				Log.d(TAG, "ACTION_DOWN: " + (iv1.getX()+event.getX()) + ", " + (iv1.getY()+event.getY()) );
				mode = DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
				Log.d(TAG, "oldDist=" + oldDist);
				if (oldDist > 10f) {
					midPoint(mid, event);
					mode = ZOOM;
//					Log.d(TAG, "mode=ZOOM: " + iv1.getX() + ", " + iv1.getY());
				}
				break;
			case MotionEvent.ACTION_UP:
				double dxDiff = (iv1.getX()+event.getX()) - startRef.x;
				int xDiff = (int) Math.abs(dxDiff);
				double dyDiff = (iv1.getY()+event.getY()) - startRef.y;
				int yDiff = (int) Math.abs(dyDiff);
				if (mode == DRAG && xDiff < 2 && yDiff < 2){
					mode = CLICK;
					Log.d(TAG, "mode= CLICK");
					iv1.performClick();
				}
			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
//				Log.d(TAG, "mode=NONE");
				break;
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					// 重點在於 setScaleX,Y 之後的縮放比要反應到 setX,Y 去，反應方式如下
					iv1.setX(iv1.getX() + (event.getX() - start.x)*iv1.getScaleX());
					iv1.setY(iv1.getY() + (event.getY() - start.y)*iv1.getScaleY());
				}
				else if (mode == ZOOM) {
					float newDist = spacing(event);
//					Log.d(TAG, "newDist=" + newDist);
					if (newDist > 10f) {
						float scale = newDist / oldDist;
						iv1.setScaleX(iv1.getScaleX()*scale);
						iv1.setScaleY(iv1.getScaleY()*scale);
					}
				}
				break;
		}
		return true; // indicate event was handled
	}

	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}
