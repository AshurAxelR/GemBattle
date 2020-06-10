package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Gem;
import com.xrbpowered.android.zoomui.UIContainer;

import static com.xrbpowered.android.gembattle.ui.SpellChargeBubble.distanceFromPivot;
import static com.xrbpowered.android.gembattle.ui.SpellChargeBubble.radius;

public class SpellPane extends UIContainer {

	public static final int pivotx = (int)(distanceFromPivot*Math.cos(Math.PI/6)) + radius + 10;
	public static final int pivoty = distanceFromPivot + radius + 20;

	public static final int width = pivotx*2;
	public static final int height = pivoty*2;

	public final SpellChargeBubble[] spells = new SpellChargeBubble[BattlePlayer.spellSlotCount];

	public SpellPane(UIContainer parent, BattlePlayer player) {
		super(parent);

		setSize(width, height);

		for(int i=0; i<spells.length; i++) {
			spells[i] = new SpellChargeBubble(this, i, player);
			spells[i].setLocation(
				pivotx + distanceFromPivot*(float)Math.sin(i*Math.PI/3) - radius,
				pivoty - distanceFromPivot*(float)Math.cos(i*Math.PI/3) - radius
			);
		}
	}

	@Override
	protected void paintSelf(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xffc2b890);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2f);
		paint.setColor(0xff736d55);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
	}
}
