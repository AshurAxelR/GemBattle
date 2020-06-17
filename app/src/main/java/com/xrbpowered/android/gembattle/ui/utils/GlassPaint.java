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
	private float[] shades;
	private LinearGradient[] shaders;
	private int[][] shadeColors;
	private LinearGradient glass;

	private GlassPaint(UIElement ui, int defaultHeight, int shadesCount) {
		this.ui = ui;
		shaders = new LinearGradient[shadesCount];
		shadeColors = new int[shadesCount][];
		this.height = defaultHeight;
	}

	public GlassPaint(UIElement ui, int defaultHeight, int[] colors, float[] shades) {
		this(ui, defaultHeight, shades.length);
		setColors(colors, shades);
	}

	public GlassPaint(UIElement ui, int defaultHeight, int color, float[] shades) {
		this(ui, defaultHeight, shades.length);
		int[] colors = new int[shades.length];
		for(int i=0; i<colors.length; i++)
			colors[i] = color;
		setColors(colors, shades);
	}

	private int[] createColorMap(int color, float brightness) {
		int[] colors = new int[4];
		colors[0] = changeBrightness(color, 0.4f*brightness);
		colors[1] = changeBrightness(color, 0.6f*brightness);
		colors[2] = changeBrightness(color, brightness);
		colors[3] = changeBrightness(color, 0.5f*brightness);
		return colors;
	}

	public void setColor(int shadeIndex, int color, float shade) {
		this.colors[shadeIndex] = color;
		this.shades[shadeIndex] = shade;
		shadeColors[shadeIndex] = createColorMap(color, shade);
		createGradient(shadeIndex, height);
	}

	public void setColors(int[] colors, float[] shades) {
		this.colors = colors;
		this.shades = shades;
		for(int i=0; i<shades.length; i++)
			shadeColors[i] = createColorMap(colors[i], shades[i]);
		createGradients(height);
	}

	private void createGradient(int shadeIndex, float h) {
		shaders[shadeIndex] = new LinearGradient(0, 0, 0, h, shadeColors[shadeIndex], gradientPositions, Shader.TileMode.CLAMP);
	}

	private void createGradients(float h) {
		for(int i=0; i<shades.length; i++)
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
