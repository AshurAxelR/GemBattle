package com.xrbpowered.android.zoomui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public abstract class FitFrame {

	public final int width, height;
	public final Rect outerRect = new Rect();
	public float scale = 0f;

	protected static final Paint paint = new Paint();

	protected final Point touchStart = new Point();
	protected final Point touchEnd = new Point();
	protected boolean isTouchDown = false;

	private View parentView = null;

	public FitFrame(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setView(View parentView) {
		this.parentView = parentView;
	}

	public void repaint() {
		parentView.invalidate();
	}

	protected void handleAction(int action, int x, int y) {
		if(action==MotionEvent.ACTION_MOVE) {
			touchEnd.set(x, y);
			if(onTouchMove(touchStart.x, touchStart.y, x, y))
				repaint();
		}
		else if(action==MotionEvent.ACTION_DOWN) {
			touchStart.set(x, y);
			touchEnd.set(x, y);
			isTouchDown = true;
			if(onTouchDown(x, y))
				repaint();
		}
		else if(action==MotionEvent.ACTION_UP) {
			touchEnd.set(x, y);
			isTouchDown = false;
			if(onTouchUp(x, y))
				repaint();
		}
	}

	public boolean onTouchDown(int x, int y) {
		return false;
	}

	public boolean onTouchUp(int x, int y) {
		return false;
	}

	public boolean onTouchMove(int x0, int y0, int x, int y) {
		return false;
	}

	public abstract void draw(Canvas canvas);

}
