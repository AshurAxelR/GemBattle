package com.xrbpowered.android.zoomui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class FitFrameView extends View {

	private FitFrame fitFrame = null;
	private RepaintHandler repaintHandler = null;

	public FitFrameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FitFrameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FitFrameView(Context context) {
		super(context);
	}

	public void setFrame(FitFrame frame) {
		this.fitFrame = frame;
		frame.setView(this);
	}

	public void setFrameTime(int frameTime) {
		if(frameTime>0) {
			if(repaintHandler==null)
				repaintHandler = new RepaintHandler(this, frameTime);
			else
				repaintHandler.setFrameTime(frameTime);
		}
		else {
			if(repaintHandler!=null) {
				repaintHandler.setFrameTime(0);
				repaintHandler = null;
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(fitFrame==null)
			return false;

		int x = (int)(event.getX()/fitFrame.scale + fitFrame.outerRect.left);
		int y = (int)(event.getY()/fitFrame.scale + fitFrame.outerRect.top);

		fitFrame.handleAction(event.getAction(), x, y);

		return true;
	}

	private int updWidth = 0;
	private int updHeight = 0;

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		if(fitFrame!=null) {
			int w = getWidth();
			int h = getHeight();
			if(w!=updWidth || h!=updHeight) {
				updWidth = w;
				updHeight = h;

				float scale =
					((float) fitFrame.width / (float) fitFrame.height < (float) w / (float) h) ?
					(float) h / (float) fitFrame.height :
					(float) w / (float) fitFrame.width;
				fitFrame.scale = scale;

				int ow = (int)(w / scale);
				int oh = (int)(h / scale);
				int x = (ow - fitFrame.width)/2;
				int y = (oh - fitFrame.height)/2;
				fitFrame.outerRect.set(-x, -y, -x+ow, -y+oh);
			}

			canvas.scale(fitFrame.scale, fitFrame.scale);
			canvas.translate(-fitFrame.outerRect.left, -fitFrame.outerRect.top);
			fitFrame.draw(canvas);
		}
		else {
			canvas.drawColor(0xff000000);
		}
	}
}
