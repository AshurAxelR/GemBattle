package com.xrbpowered.android.gembattle.effects.attack;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Gem;
import com.xrbpowered.android.gembattle.game.MatchResult;
import com.xrbpowered.android.gembattle.ui.GamePane;
import com.xrbpowered.android.gembattle.ui.SpellChargeBubble;

public class SpellChargeEffect implements Effect {

	public static final float speed = 10f;

	public final SpellChargeBubble ui;

	private int pendingCharges = 0;
	private float fracCharge = 0f;

	public SpellChargeEffect(SpellChargeBubble ui) {
		this.ui = ui;
	}

	public void addCharges(int charges) {
		this.pendingCharges += charges;
	}

	public boolean isCharging() {
		return pendingCharges>0;
	}

	public float getLevel() {
		return (ui.player.spellCharge[ui.spellSlot] + fracCharge) / ui.spell.maxCharges;
	}

	public void attack() {
		PointF sp = new PointF(ui.localToBaseX(ui.getWidth()/2), ui.localToBaseY(ui.getHeight()/2));
		Effect missile = new MissileEffect(ui.player.opponent(), ui.spell, sp);
		GamePane.attackEffects.addEffect(missile);
	}

	@Override
	public Effect update(float dt) {
		if(pendingCharges>0) {
			fracCharge += dt * speed;
			if(fracCharge>=1f) {
				fracCharge -= 1f;
				pendingCharges--;
				if(ui.player.addCharge(ui.spellSlot)) {
					attack();
				}
			}
		}
		return this;
	}

	@Override
	public Effect finish() {
		return this;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
	}

	public static void applyCharges(BattlePlayer player, Gem element, int matches) {
		int slot = player.elementSlot(element);
		int charges = MatchResult.matchesToChareges(matches);
		if(slot>=0 && charges>0) {
			SpellChargeBubble ui = GamePane.instance.getPlayerPane(player).spellPane.spells[slot];
			ui.chargeEffect.addCharges(charges);
		}
	}

	public static void applyCharges(MatchResult match) {
		BattlePlayer player = match.board.player;
		for(Gem gem : Gem.values()) {
			applyCharges(player, gem, match.count[gem.ordinal()]);
		}
	}
}
