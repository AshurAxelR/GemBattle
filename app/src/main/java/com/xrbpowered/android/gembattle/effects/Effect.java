package com.xrbpowered.android.gembattle.effects;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Effect {

	Effect update(float dt);
	Effect finish();
	void draw(Canvas canvas, Paint paint);

}
