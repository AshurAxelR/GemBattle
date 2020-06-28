package com.xrbpowered.android.gembattle.ui.battle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class DamageTextFloat extends UIElement {

	public static final float textFloatDuration = 1.5f;
	public static final int textSize = 30;
	public static final float floatDist = 220;
	public static final float pivotRange = 120;

	public static final int damageColor = 0xffee0000;

	public static final Random random = new Random();

	public static class TextFloatEffect extends TimedEffect {
		public final String text;
		public final int color;
		public final PointF pivot;

		public TextFloatEffect(String text, int color, PointF pivot) {
			this.text = text;
			this.color = color;
			this.pivot = pivot;
		}

		@Override
		public float getDuration() {
			return textFloatDuration;
		}

		@Override
		public void draw(Canvas canvas, Paint paint) {
			float ty = floatDist*s*s + (paint.descent()+paint.ascent())/2;
			paint.setColor(color);
			if(s>0.5f)
				paint.setAlpha((int)((2-s*2)*255f));
			canvas.drawText(text, pivot.x, pivot.y - ty, paint);
		}
	}

	public final ArrayList<Effect> textFloats = new ArrayList<>();

	public DamageTextFloat(UIContainer parent) {
		super(parent);
	}

	public void addDamageText(int damage) {
		PointF pivot = new PointF(random.nextFloat()*pivotRange*2 - pivotRange, 0);
		Effect effect = new TextFloatEffect(Integer.toString(damage), damageColor, pivot);
		textFloats.add(effect);
	}

	public void updateTime(float dt) {
		for(Iterator<Effect> i = textFloats.iterator(); i.hasNext();) {
			if(i.next().update(dt)==null)
				i.remove();
		}
	}

	@Override
	public void paint(Canvas canvas) {
		paint.setTypeface(RenderUtils.fontBlack);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(textSize);
		paint.setTextAlign(Paint.Align.CENTER);

		for(Effect effect : textFloats) {
			effect.draw(canvas, paint);
		}
	}
}
