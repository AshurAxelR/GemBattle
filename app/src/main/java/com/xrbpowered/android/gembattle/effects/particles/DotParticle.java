package com.xrbpowered.android.gembattle.effects.particles;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;

public class DotParticle extends Particle {

	private static final Paint dotPaint = new Paint();
	static {
		dotPaint.setStyle(Paint.Style.FILL);
		dotPaint.setAntiAlias(false);
	}

	private final ColorTween color;
	private final float r0, r1;

	public DotParticle(float x0, float y0, float x1, float y1, float r0, float r1, float duration, ColorTween color) {
		this.sourcePoint = new PointF(x0, y0);
		this.targetPoint = new PointF(x1, y1);
		this.duration = duration;
		this.color = color;
		this.r0 = r0;
		this.r1 = r1;
	}

	@Override
	protected void draw(Canvas canvas, float x, float y, float ts, Paint paint) {
		dotPaint.setColor(color.get(ts));
		float r = RenderUtils.lerp(r0, r1, ts);
		canvas.drawCircle(x, y, r, dotPaint);
	}
}
