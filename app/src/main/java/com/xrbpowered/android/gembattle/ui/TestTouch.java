package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.GemBattle;
import com.xrbpowered.android.gembattle.MainActivity;
import com.xrbpowered.android.gembattle.game.Gem;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

import java.util.Locale;

public class TestTouch extends UIElement {

	private PointF start = null;
	private PointF end = new PointF();

	public TestTouch(UIContainer parent) {
		super(parent);
	}

	@Override
	public boolean onTouchDown(float x, float y) {
		start = new PointF(x, y);
		end.set(x, y);
		repaint();
		return true;
	}

	@Override
	public void onTouchMoved(PointF start, float x, float y) {
		end.set(x, y);
		repaint();
	}

	@Override
	public void onTouchUp(PointF start, float x, float y) {
		this.start = null;
		repaint();
	}

	@Override
	public void paint(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xff444444);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		//canvas.drawColor(0xff000000);

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xffffffff);
		paint.setTextSize(25);
		canvas.drawText(String.format(Locale.getDefault(), "Test touch: [%.0f, %.0f]", getWidth(), getHeight()), 50, 50, paint);

		if(!Gem.isLoaded())
			Gem.loadBitmaps(GemBattle.resources);
		for(int y=1; y<4; y++) {
			float x = 0;
			for (Gem gem : Gem.values()) {
				gem.draw(canvas, x, y * 120, paint);
				x += 120;
			}
		}

		if(start!=null) {
			paint.setStrokeWidth(2f);
			paint.setColor(0xffaaaaaa);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawLine(start.x, start.y, end.x, end.y, paint);
			paint.setColor(0xffff0000);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(start.x, start.y, 100, paint);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(start.x, start.y, 20, paint);
			paint.setColor(0xffaaaaaa);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(end.x, end.y, 100, paint);
			paint.setStyle(Paint.Style.FILL);
			canvas.drawCircle(end.x, end.y, 20, paint);
		}
	}
}
