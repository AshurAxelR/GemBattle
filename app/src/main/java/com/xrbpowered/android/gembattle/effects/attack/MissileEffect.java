package com.xrbpowered.android.gembattle.effects.attack;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.particles.DotParticle;
import com.xrbpowered.android.gembattle.effects.particles.GemParticleInfo;
import com.xrbpowered.android.gembattle.effects.particles.Particle;
import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Spell;
import com.xrbpowered.android.gembattle.ui.battle.GamePane;
import com.xrbpowered.android.zoomui.UIElement;

import java.util.Random;

import static com.xrbpowered.android.gembattle.ui.utils.RenderUtils.lerp;

public class MissileEffect extends Particle {

	private static final float minF = 0.5f;
	private static final float maxF = 2f;
	private static final float minAmp = 100f;
	private static final float maxAmp = 300f;
	private static final float missileRadius = 25f;
	private static final float particleDist = 250f;

	private static final float debugDurationScale = 1f;

	public static class Properties {
		public float scale = 1f;
		public float duration = 1f;

		public GemParticleInfo particleInfo = null;
		public int particlesPerSecond = 210;
	}

	private static final Random random = new Random();

	public final BattlePlayer target;
	public final Spell spell;
	public final PointF targetPointBase;

	private final Properties props;
	private final float phi, f, amp;

	private float prevx, prevy;
	private float remParticle = 0f;

	public MissileEffect(BattlePlayer target, Spell spell, PointF sourcePointBase) {
		this.target = target;
		this.spell = spell;

		UIElement targetPane = GamePane.instance.getPlayerPane(target);
		this.targetPointBase = new PointF(targetPane.localToBaseX(targetPane.getWidth()/2), 300);

		UIElement ui = GamePane.instance.attackEffectPane;
		this.sourcePoint = new PointF(ui.baseToLocalX(sourcePointBase.x), ui.baseToLocalY(sourcePointBase.y));
		this.targetPoint = new PointF(ui.baseToLocalX(targetPointBase.x), ui.baseToLocalY(targetPointBase.y));
		prevx = this.sourcePoint.x;
		prevy = this.sourcePoint.y;

		this.props = spell.missileProps;
		this.duration = (random.nextFloat()*0.1f+1f)*props.duration * debugDurationScale;
		phi = random.nextFloat();
		f = random.nextFloat()*(maxF-minF) + minF;
		amp = random.nextFloat()*(maxAmp-minAmp) + minAmp;
	}

	@Override
	public Effect update(float dt) {
		float ts = tween(s);
		float newx = calcX(ts);
		float newy = calcY(ts);

		if(props.particleInfo!=null) {
			float countf = props.particlesPerSecond * dt / debugDurationScale + remParticle;
			int count = (int) countf;
			remParticle = countf - count;

			for (int i = 0; i < count; i++) {
				float si = (float) i / count;
				float x0 = lerp(prevx, newx, si);
				float y0 = lerp(prevy, newy, si);
				float d = (random.nextFloat()+0.5f) * particleDist * props.scale;
				float r = random.nextFloat() * (float) Math.PI * 2f;
				float dx = (float) Math.cos(r);
				float dy = -(float) Math.sin(r);
				float x1 = x0 + d * dx;
				float y1 = y0 + d * dy;
				x0 += 0.1f * d * dx;
				y0 += 0.1f * d * dy;
				Particle p = new DotParticle(x0, y0, x1, y1, 3f, 1f, 2.5f, props.particleInfo.color);
				GamePane.particles.addEffect(p);
			}
		}

		prevx = newx;
		prevy = newy;

		return super.update(dt);
	}

	@Override
	public Effect finish() {
		target.receiveDamage(spell.damage);
		GamePane.instance.getPlayerPane(target).damageText.addDamageText(spell.damage);
		return null;
	}

	private float wave(float s) {
		float y = amp*(float)Math.sin((s+phi)*f*Math.PI);
		return y*(float)Math.sin(s*Math.PI);
	}

	@Override
	protected float calcY(float ts) {
		return super.calcY(ts) + wave(ts);
	}

	@Override
	public void draw(Canvas canvas, float x, float y, float ts, Paint paint) {
		paint.setColor(0xffffffff);
		//canvas.drawCircle(x, y, 10, paint);

		canvas.save();
		canvas.translate(x, y);
		canvas.scale(props.scale, props.scale);
		float a;
		if(props.particleInfo.aligned) {
			float dx = calcX(ts+0.01f)-x;
			float dy = calcY(ts+0.01f)-y;
			a = (float)Math.toDegrees(Math.atan2(dy, dx));
		}
		else {
			a = time * props.particleInfo.rotationSpeed * 180f;
		}
		canvas.rotate(a);

		Bitmap bmp = props.particleInfo.bitmap();
		canvas.drawBitmap(bmp, -bmp.getWidth()/2, -bmp.getHeight()/2, paint);

		canvas.restore();
	}
}
