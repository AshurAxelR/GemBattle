package com.xrbpowered.android.gembattle.effects;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class EffectSet implements Effect {

	private final ArrayList<Effect> effects = new ArrayList<>();

	public void clearEffects() {
		effects.clear();
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public boolean isEmpty() {
		return effects.isEmpty();
	}

	@Override
	public Effect update(float dt) {
		for(int i=0; i<effects.size(); i++) { // effects is volatile
			Effect effect = effects.get(i);
			Effect next = effect.update(dt);
			if(next==null) {
				effects.remove(i);
				i--;
			}
			else if(next!=effect){
				effects.set(i, next);
			}
		}

		if(effects.isEmpty())
			return finish();
		else
			return this;
	}

	@Override
	public TimedEffect finish() {
		return null;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		for(int i=0; i<effects.size(); i++) { // effects is volatile
			effects.get(i).draw(canvas, paint);
		}
	}
}
