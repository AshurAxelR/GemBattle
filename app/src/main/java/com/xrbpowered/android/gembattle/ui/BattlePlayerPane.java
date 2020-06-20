package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.ui.common.ProgressBar;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.zoomui.UIContainer;

public class BattlePlayerPane extends UIContainer {

	public final ProgressBar healthBar;
	public final SpellPane spellPane;
	public DamageTextFloat damageText;

	public final Paint.Align align;

	private BattlePlayer player;

	public BattlePlayerPane(UIContainer parent, Paint.Align align) {
		super(parent);
		this.align = align;
		setSize(SpellPane.width, 1000);

		healthBar = new ProgressBar(this, 0xffee0000) {
			@Override
			public float getProgress() {
				return (float)BattlePlayerPane.this.player.health/BattlePlayerPane.this.player.maxHealth;
			}
			@Override
			public String getText() {
				return Integer.toString(BattlePlayerPane.this.player.health);
			}
		};

		spellPane = new SpellPane(this);
	}

	public BattlePlayer getPlayer() {
		return player;
	}

	public void setPlayer(BattlePlayer player) {
		this.player = player;
		for(SpellChargeBubble spell : spellPane.spells) {
			spell.setPlayer(player);
		}
	}

	@Override
	public void layout() {
		healthBar.setSize(getWidth(), healthBar.getHeight());
		healthBar.setLocation(0, 400);
		spellPane.setLocation(0, healthBar.getY()+healthBar.getHeight() + 40);
		super.layout();
	}

	public void updateTime(float dt) {
		damageText.updateTime(dt);
		for(SpellChargeBubble spell : spellPane.spells) {
			spell.chargeEffect.update(dt);
		}
	}

	@Override
	protected void paintSelf(Canvas canvas) {
		paint.setTypeface(RenderUtils.fontBlack);
		paint.setTextSize(40);
		RenderUtils.drawStrokeText(canvas, player.human ? "Player" : "AI", getWidth()/2, 50, paint);
	}
}
