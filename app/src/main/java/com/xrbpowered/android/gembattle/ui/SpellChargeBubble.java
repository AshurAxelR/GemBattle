package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Spell;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

public class SpellChargeBubble extends UIElement {

	public static final int radius = 55;
	public static final int distanceFromPivot = 125;

	private static final Path clip = new Path();
	static {
		clip.addCircle(radius, radius, radius, Path.Direction.CCW);
	}

	public final int spellSlot;

	public BattlePlayer player;
	public Spell spell;

	public float charges = 0f;

	private final int color, darkColor;

	public SpellChargeBubble(UIContainer parent, int spellSlot, BattlePlayer player) {
		super(parent);
		this.player = player;
		this.spellSlot = spellSlot;

		spell = player.spells[spellSlot];
		if(spell!=null) {
			color = spell.element.color;
			darkColor = RenderUtils.darkenColor(color, 0.5f);
		}
		else {
			color = 0;
			darkColor = 0;
		}

		setSize(radius*2, radius*2);
	}

	@Override
	public void paint(Canvas canvas) {
		if(color!=0) {
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(darkColor);
			canvas.drawCircle(radius, radius, radius, paint);

			float px = parentToLocalX(SpellPane.pivotx);
			float py = parentToLocalY(SpellPane.pivoty);
			float s = charges / spell.maxCharges;

			if(s>0) {
				canvas.save();
				canvas.clipPath(clip);
				paint.setColor(color);
				canvas.drawCircle(px, py, distanceFromPivot - radius + s*radius*2, paint);
				canvas.restore();
			}
		}
	}
}
