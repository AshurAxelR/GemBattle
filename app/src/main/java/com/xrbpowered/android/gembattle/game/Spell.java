package com.xrbpowered.android.gembattle.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xrbpowered.android.gembattle.effects.attack.MissileEffect;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.xrbpowered.android.gembattle.R.drawable.*;

public class Spell {

	public final String name;
	public final Gem element;
	public final int maxCharges;
	public final int damage;

	public final int bitmapEmptyId, bitmapFullId;
	public Bitmap bitmapEmpty = null;
	public Bitmap bitmapFull = null;

	public final MissileEffect.Properties missileProps = new MissileEffect.Properties();

	public Spell(int bitmapEmptyId, int bitmapFullId, String name, Gem element, int maxCharges, int damage) {
		this.bitmapEmptyId = bitmapEmptyId;
		this.bitmapFullId = bitmapFullId;
		this.name = name;
		this.element = element;
		this.maxCharges = maxCharges;
		this.damage = damage;

		this.missileProps.particleInfo = element.particleInfo;
		this.missileProps.duration = 0.8f;

		registerSpell(this);
	}

	public double valuePerCharge() {
		return damage/(double)maxCharges;
	}

	private static boolean loaded = false;

	public static boolean isLoaded() {
		return loaded;
	}

	public static void loadBitmaps() {
		for(ArrayList<Spell> list : spells.values())
			for(Spell spell : list) {
				spell.bitmapEmpty = BitmapFactory.decodeResource(RenderUtils.resources, spell.bitmapEmptyId, RenderUtils.noScale);
				spell.bitmapFull = BitmapFactory.decodeResource(RenderUtils.resources, spell.bitmapFullId, RenderUtils.noScale);
			}
		loaded = true;
	}


	private static Spell flash(int bitmapEmptyId, int bitmapFullId, Gem element) {
		return new Spell(bitmapEmptyId, bitmapFullId, element.name + " Flash", element, 6, 12);
	}

	private static Spell lance(int bitmapEmptyId, int bitmapFullId, Gem element) {
		Spell spell = new Spell(bitmapEmptyId, bitmapFullId, element.name + " Lance", element, 12, 36);
		spell.missileProps.particlesPerSecond = 250;
		spell.missileProps.scale = 1.5f;
		spell.missileProps.duration = 1.25f;
		return spell;
	}

	public static final HashMap<Gem, ArrayList<Spell>> spells = new HashMap<>();

	private static void registerSpell(Spell spell) {
		ArrayList<Spell> list = spells.get(spell.element);
		if(list==null) {
			list = new ArrayList<>();
			spells.put(spell.element, list);
		}
		list.add(spell);
	}

	public static final Spell metalShard = new Spell(spell_metal_shard_empty, spell_metal_shard_full, "Metal Shard", Gem.metal, 1, 1) {
		{
			this.missileProps.particlesPerSecond = 90;
			this.missileProps.scale = 0.5f;
			this.missileProps.duration = 0.65f;
		}
	};

	public static final Spell fireFlash = flash(spell_fire_flash_empty, spell_fire_flash_full, Gem.fire);
	public static final Spell waterFlash = flash(spell_water_flash_empty, spell_water_flash_full, Gem.water);
	public static final Spell earthFlash = flash(spell_earth_flash_empty, spell_earth_flash_full, Gem.earth);
	public static final Spell airFlash = flash(spell_air_flash_empty, spell_air_flash_full, Gem.air);

	public static final Spell fireLance = lance(spell_fire_lance_empty, spell_fire_lance_full, Gem.fire);
	public static final Spell waterLance = lance(spell_water_lance_empty, spell_water_lance_full, Gem.water);
	public static final Spell earthLance = lance(spell_earth_lance_empty, spell_earth_lance_full, Gem.earth);
	public static final Spell airLance = lance(spell_air_lance_empty, spell_air_lance_full, Gem.air);
	public static final Spell lightLance = lance(spell_light_lance_empty, spell_light_lance_full, Gem.light);
}
