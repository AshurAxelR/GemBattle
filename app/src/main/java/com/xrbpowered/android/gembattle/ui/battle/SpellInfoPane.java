package com.xrbpowered.android.gembattle.ui.battle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.xrbpowered.android.gembattle.game.Spell;
import com.xrbpowered.android.gembattle.ui.common.OverlayPane;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.gembattle.ui.utils.Strings;
import com.xrbpowered.android.zoomui.UIContainer;

public class SpellInfoPane extends UIContainer {

	public static final int width = BoardPane.screenSize-120;
	public static final int height = 180;
	public static final int battleAnchorX = 60;

	public final Spell spell;

	private final float anchorX, anchorY;

	public SpellInfoPane(UIContainer parent, Spell spell, float anchorX, float anchorY) {
		super(new OverlayPane(parent, true));
		this.spell = spell;
		this.anchorX = anchorX;
		this.anchorY = anchorY;

		setSize(width, height);
	}

	@Override
	public void layout() {
		float x = (anchorX<0) ? getParent().getWidth() - getWidth() + anchorX : anchorX;
		float y = anchorY - getHeight()/2;
		setLocation(x, y);
		super.layout();
	}

	@Override
	protected void paintSelf(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xffede6c8);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2f);
		paint.setColor(0xff926721);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

		canvas.drawBitmap(spell.bitmapFull, 20, 30, paint);

		int x = SpellChargeBubble.bitmapSize+40;
		int y = 60;
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xff000000);
		paint.setTypeface(RenderUtils.fontBlack);
		paint.setTextSize(40);
		paint.setTextAlign(Paint.Align.LEFT);
		canvas.drawText(spell.name, x, y, paint);
		y += 50;
		paint.setColor(0xff555555);
		paint.setTextSize(30);
		paint.setTypeface(Typeface.SANS_SERIF);
		canvas.drawText(Strings.format("%d damage", spell.damage), x, y, paint);
		y += 35;
		canvas.drawText(Strings.format("Requires %d charge%s", spell.maxCharges, spell.maxCharges==1 ? "" : "s"), x, y, paint);
	}
}
