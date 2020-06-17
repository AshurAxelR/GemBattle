package com.xrbpowered.android.gembattle.ui.utils;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.xrbpowered.android.zoomui.UIElement;

public class RenderUtils {

	public static final BitmapFactory.Options noScale = new BitmapFactory.Options();
	public static Resources resources;
	public static Typeface fontBlack;

	public static float lerp(float x0, float x1, float s) {
		return x0 * (1-s) + x1 * s;
	}

	public static float wave(float s) {
		return (float)Math.sin(s*Math.PI*2.0)*0.5f+0.5f;
	}

	public static int darkenColor(int color, float s) {
		return Color.argb(
				Color.alpha(color),
				(int)(Color.red(color)*s),
				(int)(Color.green(color)*s),
				(int)(Color.blue(color)*s)
		);
	}

	public static int lightenColor(int color, float s) {
		return Color.argb(
				Color.alpha(color),
				(int)lerp(Color.red(color), 255, s),
				(int)lerp(Color.green(color), 255, s),
				(int)lerp(Color.blue(color), 255, s)
		);
	}

	public static int changeBrightness(int color, float scale) {
		return scale>1f ? lightenColor(color, scale-1f) : darkenColor(color, scale);
	}

	public static void drawStrokeText(Canvas canvas, String message, float x, float y, Paint paint, int fill, int stroke) {
		y -= (paint.descent()+paint.ascent())/2;
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setColor(stroke);
		canvas.drawText(message, x, y, paint);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(fill);
		canvas.drawText(message, x, y, paint);
	}

	public static void drawStrokeText(Canvas canvas, String message, float x, float y, Paint paint) {
		drawStrokeText(canvas, message, x, y, paint, 0xffffffff, 0xff000000);
	}

	static {
		noScale.inScaled = false;
	}
}
