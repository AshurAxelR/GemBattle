package com.xrbpowered.android.gembattle.effects.attack;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.GemBattle;
import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Spell;
import com.xrbpowered.android.zoomui.UIElement;

import java.util.Random;

import static com.xrbpowered.android.gembattle.ui.RenderUtils.lerp;

public class MissileEffect extends TimedEffect {

	private static final float minF = 0.5f;
	private static final float maxF = 2f;
	private static final float minAmp = 100f;
	private static final float maxAmp = 300f;
	private static final float missileRadius = 25f;

	public static class Properties {
		public int color;
		public float scale;
		public float duration;
	}

	private static final Random random = new Random();

	public final BattlePlayer target;
	public final Spell spell;
	public final PointF targetPointBase;

	private final PointF sourcePoint, targetPoint;

	private final Properties props;
	private final float duration;
	private final float phi, f, amp;

	public MissileEffect(BattlePlayer target, Spell spell, PointF sourcePointBase) {
		this.target = target;
		this.spell = spell;

		UIElement targetPane = GemBattle.gamePane.getPlayerPane(target);
		this.targetPointBase = new PointF(targetPane.localToBaseX(targetPane.getWidth()/2), 300);

		UIElement ui = GemBattle.gamePane.missileEffectPane;
		this.sourcePoint = new PointF(ui.baseToLocalX(sourcePointBase.x), ui.baseToLocalY(sourcePointBase.y));
		this.targetPoint = new PointF(ui.baseToLocalX(targetPointBase.x), ui.baseToLocalY(targetPointBase.y));

		this.props = spell.missileProps;
		this.duration = (random.nextFloat()*0.1f+1f)*props.duration;
		phi = random.nextFloat();
		f = random.nextFloat()*(maxF-minF) + minF;
		amp = random.nextFloat()*(maxAmp-minAmp) + minAmp;
	}

	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public Effect finish() {
		target.receiveDamage(spell.damage);
		GemBattle.gamePane.getPlayerPane(target).damageText.addDamageText(spell.damage);
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
		paint.setColor(props.color);
		canvas.drawCircle(x, y, missileRadius*props.scale, paint);
	}
}
