package com.xrbpowered.android.gembattle.effects.particles;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.effects.TimedEffect;

import static com.xrbpowered.android.gembattle.ui.RenderUtils.lerp;

public abstract class Particle extends TimedEffect {

	protected PointF sourcePoint, targetPoint;
	protected float duration;

	@Override
	public float getDuration() {
		return duration;
	}

	protected float tween(float s) {
		return s;
	}

	protected float calcX(float ts) {
		return lerp(sourcePoint.x, targetPoint.x, ts);
	}

	protected float calcY(float ts) {
		return lerp(sourcePoint.y, targetPoint.y, ts);
	}

	protected abstract void draw(Canvas canvas, float x, float y, float ts, Paint paint);

	@Override
	public void draw(Canvas canvas, Paint paint) {
		float ts = tween(s);
		draw(canvas, calcX(ts), calcY(ts), ts, paint);
	}
}
