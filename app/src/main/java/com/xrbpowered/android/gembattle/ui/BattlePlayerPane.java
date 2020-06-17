package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;

import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.zoomui.UIContainer;

public class BattlePlayerPane extends UIContainer {

	public BattlePlayer player;

	public final ProgressBar healthBar;
	public final SpellPane spellPane;
	public DamageTextFloat damageText;

	public BattlePlayerPane(UIContainer parent, BattlePlayer player) {
		super(parent);
		this.player = player;

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

		spellPane = new SpellPane(this, player);
	}

	@Override
	public void layout() {
		healthBar.setSize(getWidth(), healthBar.getHeight());
		healthBar.setLocation(0, 400);
		spellPane.setLocation(0, healthBar.getY()+healthBar.getHeight() + 40);
		super.layout();
	}

	@Override
	protected void paintSelf(Canvas canvas) {
		paint.setTypeface(RenderUtils.fontBlack);
		paint.setTextSize(40);
		RenderUtils.drawStrokeText(canvas, player.human ? "Player" : "AI", getWidth()/2, 50, paint);
	}
}
