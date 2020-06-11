package com.xrbpowered.android.gembattle.effects;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class WaitEffects implements Effect {

	public final EffectSet effects;

	public WaitEffects(EffectSet effects) {
		this.effects = effects;
	}

	@Override
	public Effect update(float dt) {
		if(effects.isEmpty())
			return finish();
		else
			return this;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
	}
}
