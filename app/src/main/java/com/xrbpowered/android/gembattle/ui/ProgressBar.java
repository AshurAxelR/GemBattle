package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.xrbpowered.android.gembattle.GemBattle;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

import static com.xrbpowered.android.gembattle.ui.RenderUtils.darkenColor;

public abstract class ProgressBar extends UIElement {

	public static final int defaultHeight = 40;

	private LinearGradient dark, light, glass;

	private static final float[] gradientPositions = {0f, 0.15f, 0.85f, 1f};
	private static final int[] glassColors = {0x11ffffff, 0x55ffffff};
	private int[] lightColors, darkColors;

	public ProgressBar(UIContainer parent, int color) {
		super(parent);
		super.setSize(0, defaultHeight);
		setColor(color);
	}

	public abstract float getProgress();
	public abstract String getText();

	private int[] createColorMap(int color, float brightness) {
		int[] colors = new int[4];
		colors[0] = darkenColor(color, 0.4f*brightness);
		colors[1] = darkenColor(color, 0.6f*brightness);
		colors[2] = darkenColor(color, brightness);
		colors[3] = darkenColor(color, 0.5f*brightness);
		return colors;
	}

	public void setColor(int color) {
		lightColors = createColorMap(color, 1f);
		darkColors = createColorMap(color, 0.25f);
		createGradients(getHeight());
	}

	private void createGradients(float h) {
		dark = new LinearGradient(0, 0, 0, h, darkColors, gradientPositions, Shader.TileMode.CLAMP);
		light = new LinearGradient(0, 0, 0, h, lightColors, gradientPositions, Shader.TileMode.CLAMP);
		glass = new LinearGradient(0, 0, 0, h/2, glassColors[0], glassColors[1], Shader.TileMode.CLAMP);
	}

	@Override
	public void setSize(float width, float height) {
		if(getHeight()!=height)
			createGradients(height);
		super.setSize(width, height);
	}

	@Override
	public void paint(Canvas canvas) {
		float w = getWidth()*getProgress();
		paint.setStyle(Paint.Style.FILL);
		if(w>0) {
			paint.setShader(light);
			canvas.drawRect(0, 0, w, getHeight(), paint);
		}
		if(w<getWidth()) {
			paint.setShader(dark);
			canvas.drawRect(w, 0, getWidth(), getHeight(), paint);
		}
		paint.setShader(glass);
		canvas.drawRect(0, 0, getWidth(), getHeight()/2, paint);

		paint.setShader(null);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1f);
		paint.setColor(0xffe4d9ad);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

		String s = getText();
		if(s!=null) {
			paint.setTypeface(GemBattle.boldFont);
			paint.setTextSize(40);
			RenderUtils.drawStrokeText(canvas, s, getWidth()/2, getHeight()/2, paint, 0xffffffff, 0xff000000);
		}
	}

}
