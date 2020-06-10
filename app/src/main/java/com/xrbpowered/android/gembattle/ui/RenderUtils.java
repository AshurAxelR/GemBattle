package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RenderUtils {
	public static float lerp(float x0, float x1, float s) {
		return x0 * (1-s) + x1 * s;
	}

	public static int darkenColor(int color, float s) {
		return Color.argb(
				Color.alpha(color),
				(int)(Color.red(color)*s),
				(int)(Color.green(color)*s),
				(int)(Color.blue(color)*s)
		);
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
}
