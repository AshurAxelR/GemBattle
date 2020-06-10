package com.xrbpowered.android.gembattle.game;

public class Spell {

	public final String name;
	public final Gem element;
	public final int maxCharges;
	public final int damage;

	public Spell(String name, Gem element, int maxCharges, int damage) {
		this.name = name;
		this.element = element;
		this.maxCharges = maxCharges;
		this.damage = damage;
	}

	public int valuePerCharge() {
		return damage/maxCharges;
	}

	private static Spell flash(Gem element) {
		return new Spell(element.name + " Flash", element, 6, 12);
	}

	private static Spell lance(Gem element) {
		return new Spell(element.name + " Lance", element, 12, 36);
	}

	public static Spell metalShard = new Spell("Metal Shard", Gem.metal, 1, 1);

	public static Spell fireFlash = flash(Gem.fire);
	public static Spell waterFlash = flash(Gem.water);
	public static Spell earthFlash = flash(Gem.earth);
	public static Spell airFlash = flash(Gem.air);

	public static Spell firelance = lance(Gem.fire);
	public static Spell lightLance = lance(Gem.light);
}
