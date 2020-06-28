package com.xrbpowered.android.gembattle.ui.battle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.xrbpowered.android.gembattle.effects.attack.SpellChargeEffect;
import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Spell;
import com.xrbpowered.android.gembattle.ui.TopPane;
import com.xrbpowered.android.gembattle.ui.common.TapButton;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.zoomui.UIContainer;

public class SpellChargeBubble extends TapButton {

	public static final int radius = 55;
	public static final int distanceFromPivot = 125;
	public static final int bitmapSize = 120;

	public final int spellSlot;
	public final BattlePlayerPane playerPane;
	public final SpellChargeEffect chargeEffect;

	public Spell spell = null;

	private static final Paint gradPaint = new Paint();
	private static final int[] gradColors = {0x00ffffff, 0x00ffffff, 0x44ffffff};
	private static final Path clipSpell = new Path();
	static {
		gradPaint.setStyle(Paint.Style.FILL);
		gradPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
		clipSpell.addCircle(radius, radius, radius, Path.Direction.CCW);
	}

	private final float[] gradStops = {0, 0, 1};
	private Path clipLevel = null;
	private Shader grad = null;
	private float grads = 0;

	public SpellChargeBubble(UIContainer parent, int spellSlot, BattlePlayerPane playerPane) {
		super(parent);
		this.spellSlot = spellSlot;
		this.playerPane = playerPane;
		this.chargeEffect = new SpellChargeEffect(this);

		setSize(radius*2, radius*2);
	}

	public void setPlayer(BattlePlayer player) {
		spell = player.spells[spellSlot];
	}

	@Override
	public boolean isEnabled() {
		return spell!=null;
	}

	@Override
	public void onClick() {
		new SpellInfoPane(TopPane.overlayBase, spell,
			(playerPane.align==Paint.Align.LEFT ? 1 : -1) * SpellInfoPane.battleAnchorX,
			TopPane.overlayBase.baseToLocalY(localToBaseY(radius)));
	}

	@Override
	public void paint(Canvas canvas) {
		if(spell!=null) {
			float bpos = radius-bitmapSize/2;
			if(playerPane.preview) {
				canvas.drawBitmap(spell.bitmapFull, bpos, bpos, paint);
			}
			else {
				canvas.drawBitmap(spell.bitmapEmpty, bpos, bpos, paint);

				float px = parentToLocalX(SpellPane.pivotx);
				float py = parentToLocalY(SpellPane.pivoty);
				float s = chargeEffect.getLevel();

				if (s > 0) {
					float r = distanceFromPivot - radius + s * radius * 2;
					if (grad == null || clipLevel == null || s != grads) {
						clipLevel = new Path();
						clipLevel.addCircle(px, py, r, Path.Direction.CCW);
						gradStops[1] = (r - 15) / r;
						grad = new RadialGradient(px, py, r, gradColors, gradStops, RadialGradient.TileMode.CLAMP);
						grads = s;
					}

					canvas.save();

					canvas.clipPath(clipLevel);
					canvas.drawBitmap(spell.bitmapFull, bpos, bpos, paint);

					float a = RenderUtils.wave(GamePane.time / 2f + spellSlot / 6f);
					gradPaint.setAlpha((int) (255 * a));
					gradPaint.setShader(grad);
					canvas.clipPath(clipSpell);
					canvas.drawCircle(px, py, r, gradPaint);

					canvas.restore();
				}
			}
		}
	}
}
