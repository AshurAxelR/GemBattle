package com.xrbpowered.android.gembattle.game;

public class BattlePlayer {

	public static final int spellSlotCount = 6;

	private static final Spell[] humanSpells = {Spell.metalShard, Spell.airFlash, Spell.earthFlash, Spell.lightLance, Spell.waterFlash, Spell.fireFlash};
	private static final Spell[] aiSpells = {Spell.metalShard, Spell.airFlash, Spell.earthFlash, null, Spell.waterFlash, Spell.firelance};

	public final Board board;
	public final boolean human;

	public int health = 0;

	public final Spell[] spells;
	public final int[] spellCharge = new int[spellSlotCount];

	private final int[] elementMap;

	public BattlePlayer(Board board, boolean human) {
		this.board = board;
		this.human = human;
		this.spells = human ? humanSpells : aiSpells;

		elementMap = new int[Gem.values().length];
		for(int i=0; i<elementMap.length; i++)
			elementMap[i] = -1;
		for(int i=0; i<spells.length; i++) {
			if (spells[i] != null)
				elementMap[spells[i].element.ordinal()] = i;
		}
	}

	public void applyCharges(Gem gem, int charges, BattlePlayer opponent) {
		int slot = elementMap[gem.ordinal()];
		if(slot<0)
			return;
		Spell spell = spells[slot];
		if(spell==null)
			return;

		spellCharge[slot] += charges;

		while(spellCharge[slot]>=spell.maxCharges) {
			opponent.health += spell.damage;
			spellCharge[slot] -= spell.maxCharges;
		}
	}

	public int elementSlot(Gem element) {
		return elementMap[element.ordinal()];
	}

	public Spell spellForElement(Gem element) {
		int slot = elementMap[element.ordinal()];
		return slot<0 ? null : spells[slot];
	}
}
