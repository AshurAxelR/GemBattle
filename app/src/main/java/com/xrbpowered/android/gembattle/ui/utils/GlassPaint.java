package com.xrbpowered.android.gembattle.ui.utils;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.xrbpowered.android.zoomui.UIElement;

import static com.xrbpowered.android.gembattle.ui.utils.RenderUtils.changeBrightness;

public class GlassPaint {

	public static final float[] gradientPositions = {0f, 0.15f, 0.85f, 1f};
	public static final int[] glassColors = {0x11ffffff, 0x55ffffff};

	public final UIElement ui;

	private float height;
	private int[] colors;
	private LinearGradient[] shaders;
	private int[][] shadeColors;
	private LinearGradient glass;

	public GlassPaint(UIElement ui, int defaultHeight, int... colors) {
		this.ui = ui;
		this.height = defaultHeight;
		setColors(colors);
	}

	private int[] createColorMap(int color) {
		int[] colors = new int[4];
		colors[0] = changeBrightness(color, 0.4f);
		colors[1] = changeBrightness(color, 0.6f);
		colors[2] = changeBrightness(color, 1f);
		colors[3] = changeBrightness(color, 0.5f);
		return colors;
	}

	public void setColor(int shadeIndex, int color) {
		this.colors[shadeIndex] = color;
		shadeColors[shadeIndex] = createColorMap(color);
		createGradient(shadeIndex, height);
	}

	public void setColors(int[] colors) {
		this.colors = colors;
		shaders = new LinearGradient[colors.length];
		shadeColors = new int[colors.length][];
		for(int i=0; i<colors.length; i++)
			shadeColors[i] = createColorMap(colors[i]);
		createGradients(height);
	}

	private void createGradient(int shadeIndex, float h) {
		shaders[shadeIndex] = new LinearGradient(0, 0, 0, h, shadeColors[shadeIndex], gradientPositions, Shader.TileMode.CLAMP);
	}

	private void createGradients(float h) {
		for(int i=0; i<colors.length; i++)
			createGradient(i, h);
		glass = new LinearGradient(0, 0, 0, h/2, glassColors[0], glassColors[1], Shader.TileMode.CLAMP);
		this.height = h;
	}

	public void setHeight(float height) {
		if(this.height!=height)
			createGradients(height);
	}

	public void paintGradient(Canvas canvas, float left, float right, int shadeIndex) {
		Paint paint = UIElement.paint;
		paint.setStyle(Paint.Style.FILL);
		paint.setShader(shaders[shadeIndex]);
		canvas.drawRect(left, 0, right, height, paint);
		paint.setShader(null);
	}

	public void paintGradient(Canvas canvas, int shadeIndex) {
		paintGradient(canvas, 0, ui.getWidth(), shadeIndex);
	}

	public void paintGlass(Canvas canvas) {
		Paint paint = UIElement.paint;
		paint.setStyle(Paint.Style.FILL);
		paint.setShader(glass);
		canvas.drawRect(0, 0, ui.getWidth(), height/2, paint);
		paint.setShader(null);
	}

	public void paintBorder(Canvas canvas, int color) {
		Paint paint = UIElement.paint;
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1f);
		paint.setColor(color);
		canvas.drawRect(0, 0, ui.getWidth(), ui.getHeight(), paint);
	}

	public void paintText(Canvas canvas, String text, int color, int size) {
		if(text!=null) {
			Paint paint = UIElement.paint;
			paint.setTypeface(RenderUtils.fontBlack);
			paint.setTextSize(size);
			RenderUtils.drawStrokeText(canvas, text, ui.getWidth()/2, ui.getHeight()/2, paint, color, 0xff000000);
		}
	}

}
