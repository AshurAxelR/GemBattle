package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Spell;
import com.xrbpowered.android.zoomui.UIContainer;

import static com.xrbpowered.android.gembattle.ui.SpellChargeBubble.distanceFromPivot;
import static com.xrbpowered.android.gembattle.ui.SpellChargeBubble.radius;

public class SpellPane extends UIContainer {

	public static final int width = 350;
	public static final int height = 400;
	public static final int pivotx = width/2; // (int)(distanceFromPivot*Math.cos(Math.PI/6)) + radius + 10; // =173
	public static final int pivoty = height/2; // distanceFromPivot + radius + 20; // =180

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
		if(!Spell.isLoaded())
			Spell.loadBitmaps();

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xffede6c8);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2f);
		paint.setColor(0xff926721);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
	}
}
