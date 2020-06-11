package com.xrbpowered.android.gembattle.game;

import com.xrbpowered.android.gembattle.effects.attack.MissileEffect;

public class Spell {

	public final String name;
	public final Gem element;
	public final int maxCharges;
	public final int damage;

	public final MissileEffect.Properties missileProps = new MissileEffect.Properties();

	public Spell(String name, Gem element, int maxCharges, int damage) {
		this.name = name;
		this.element = element;
		this.maxCharges = maxCharges;
		this.damage = damage;

		this.missileProps.color = element.color;
		this.missileProps.particleInfo = element.particleInfo;
		this.missileProps.duration = 0.75f;
	}

	public int valuePerCharge() {
		return damage/maxCharges;
	}

	private static Spell flash(Gem element) {
		return new Spell(element.name + " Flash", element, 6, 12);
	}

	private static Spell lance(Gem element) {
		Spell spell = new Spell(element.name + " Lance", element, 12, 36);
		spell.missileProps.scale = 1.5f;
		spell.missileProps.duration = 1.2f;
		return spell;
	}

	public static final Spell metalShard = new Spell("Metal Shard", Gem.metal, 1, 1) {
		{
			this.missileProps.particlesPerSecond = 100;
			this.missileProps.scale = 0.5f;
			this.missileProps.color = 0xffffffff;
			this.missileProps.duration = 0.5f;
		}
	};

	public static final Spell fireFlash = flash(Gem.fire);
	public static final Spell waterFlash = flash(Gem.water);
	public static final Spell earthFlash = flash(Gem.earth);
	public static final Spell airFlash = flash(Gem.air);

	public static final Spell fireLance = lance(Gem.fire);
	public static final Spell airLance = lance(Gem.air);
	public static final Spell lightLance = lance(Gem.light);
}
