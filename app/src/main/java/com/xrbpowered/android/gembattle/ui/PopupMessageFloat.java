package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import com.xrbpowered.android.gembattle.GemBattle;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

public class PopupMessageFloat extends UIElement {

	private static final int width = 512;
	private static final int height = 128;

	private static final long startDuration = 500L;
	private static final long fadeDuration = 500L;

	private String message = null;
	private long timeStarted = 0L;

	public PopupMessageFloat(UIContainer parent) {
		super(parent);
		setSize(width, height);
	}

	public void show(String message) {
		this.message = message;
		timeStarted = System.currentTimeMillis();
	}

	@Override
	public void paint(Canvas canvas) {
		if(message==null)
			return;

		long time = System.currentTimeMillis() - timeStarted;
		if(time>=startDuration+fadeDuration) {
			message = null;
			return;
		}

		float s = 1f;
		if(time>startDuration)
			s = 1f - (float)(time-startDuration)/fadeDuration;


		paint.setTypeface(GemBattle.boldFont);
		paint.setTextSize(60);
		RenderUtils.drawStrokeText(canvas, message, getWidth()/2, getHeight()/2, paint, Color.argb(s, 1, 1, 1), Color.argb(s, 0, 0, 0));
	}

}
