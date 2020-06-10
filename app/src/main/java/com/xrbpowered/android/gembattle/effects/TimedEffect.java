package com.xrbpowered.android.gembattle.effects;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class TimedEffect implements Effect {

	protected float s = 0f;
	protected float time = 0f;

	public abstract float getDuration();

	@Override
	public TimedEffect update(float dt) {
		float duration = getDuration();
		time += dt;
		if(time >=duration) {
			time = duration;
			s = 1;
			return finish();
		}
		else {
			s = time / duration;
			return this;
		}
	}

	@Override
	public TimedEffect finish() {
		return null;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
	}

}
