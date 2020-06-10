package com.xrbpowered.android.gembattle.effects.attack;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.GemBattle;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.ui.RenderUtils;
import com.xrbpowered.android.zoomui.UIElement;

import java.util.Random;

import static com.xrbpowered.android.gembattle.ui.RenderUtils.lerp;

public class MissileEffect extends TimedEffect {

	private static final float minF = 0.5f;
	private static final float maxF = 2f;
	private static final float minAmp = 100f;
	private static final float maxAmp = 300f;
	private static final float missileRadius = 25f;

	public static final Random random = new Random();

	public final BattlePlayer target;
	public final int spellDamage;
	public final PointF targetPointBase;

	private PointF sourcePoint, targetPoint;

	private int color;
	private float scale;
	private float duration;
	private float phi, f, amp;

	public MissileEffect(BattlePlayer target, int spellDamage, PointF sourcePointBase, PointF targetPointBase, int color, float scale, float duration) {
		this.target = target;
		this.spellDamage = spellDamage;
		this.targetPointBase = targetPointBase;

		UIElement ui = GemBattle.gamePane.missileEffectPane;
		this.sourcePoint = new PointF(ui.baseToLocalX(sourcePointBase.x), ui.baseToLocalY(sourcePointBase.y));
		this.targetPoint = new PointF(ui.baseToLocalX(targetPointBase.x), ui.baseToLocalY(targetPointBase.y));

		this.color = color;
		this.scale = scale;
		this.duration = (random.nextFloat()*0.1f+1f)*duration;
		phi = random.nextFloat();
		f = random.nextFloat()*(maxF-minF) + minF;
		amp = random.nextFloat()*(maxAmp-minAmp) + minAmp;
	}

	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public TimedEffect finish() {
		GemBattle.gamePane.getPlayerPane(target).damageText.addDamageText(spellDamage);
		return null;
	}

	private float wave(float s) {
		float y = amp*(float)Math.sin((s+phi)*f*Math.PI);
		return y*(float)Math.sin(s*Math.PI);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		float ts = 0.5f*s + 0.5f*s*s;
		float x = lerp(sourcePoint.x, targetPoint.x, ts);
		float y = lerp(sourcePoint.y, targetPoint.y, ts) + wave(ts);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		canvas.drawCircle(x, y, missileRadius*scale, paint);
	}
}
