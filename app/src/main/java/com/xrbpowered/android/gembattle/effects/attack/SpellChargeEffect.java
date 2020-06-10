package com.xrbpowered.android.gembattle.effects.attack;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.GemBattle;
import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Gem;
import com.xrbpowered.android.gembattle.game.MatchResult;
import com.xrbpowered.android.gembattle.ui.SpellChargeBubble;

public class SpellChargeEffect extends TimedEffect {

	public static final float speed = 10f;

	public final BattlePlayer player, target;
	public final int slot;
	public final SpellChargeBubble ui;
	public final int charges;

	private float remaining;
	private int attacks;

	public SpellChargeEffect(BattlePlayer player, BattlePlayer target, int slot, int charges) {
		this.player = player;
		this.target = target;
		this.slot = slot;
		this.ui = GemBattle.gamePane.getPlayerPane(player).spellPane.spells[slot];
		this.charges = charges;
		this.attacks = (player.spellCharge[slot] + charges) / ui.spell.maxCharges;

		ui.charges = player.spellCharge[slot];
		remaining = charges;
	}

	@Override
	public float getDuration() {
		return charges/speed+0.1f;
	}

	public void attack() {
		attacks--;
		// TODO attack animation
		GemBattle.gamePane.getPlayerPane(target).damageText.addDamageText(ui.spell.damage);
		target.health += ui.spell.damage;
	}

	@Override
	public TimedEffect update(float dt) {
		float delta = Math.min(dt*speed, remaining);
		ui.charges += delta;
		if(ui.charges>=ui.spell.maxCharges) {
			ui.charges -= ui.spell.maxCharges;
			attack();
		}
		remaining -= delta;

		return super.update(dt);
	}

	@Override
	public TimedEffect finish() {
		while(attacks>0)
			attack();
		player.spellCharge[slot] = (player.spellCharge[slot] + charges) % ui.spell.maxCharges;
		ui.charges = player.spellCharge[slot];
		return null;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		super.draw(canvas, paint);
	}

	public static void applyCharges(BattlePlayer player, BattlePlayer target, Gem element, int matches) {
		int slot = player.elementSlot(element);
		if(slot<0)
			return;
		int charges = MatchResult.matchesToChareges(matches);
		if(charges<1)
			return;
		Effect effect = new SpellChargeEffect(player, target, slot, charges);
		GemBattle.attackEffects.addEffect(effect);
	}

	public static void applyCharges(MatchResult match) {
		BattlePlayer player = match.board.player;
		BattlePlayer opponent = match.board.opponent(player);
		for(Gem gem : Gem.values()) {
			applyCharges(player, opponent, gem, match.count[gem.ordinal()]);
		}
	}
}
