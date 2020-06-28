package com.xrbpowered.android.gembattle.game;

import java.util.Random;

public class BattlePlayer {

	public static final int humanHealth = 150;
	public static final int aiHealth = 150;

	public static final int spellSlotCount = 6;

	private static final Spell[] humanSpells = {Spell.metalShard, Spell.airFlash, Spell.earthFlash, Spell.lightLance, Spell.waterFlash, Spell.fireFlash};
	//private static final Spell[] aiSpells = {Spell.metalShard, Spell.airLance, Spell.earthFlash, null, Spell.waterFlash, Spell.fireLance};

	public final Board board;
	public final boolean human;

	public int health, maxHealth;

	public final Spell[] spells;
	public final int[] spellCharge = new int[spellSlotCount];

	private final int[] elementMap;

	public BattlePlayer(Board board, boolean human) {
		this.board = board;
		this.human = human;
		this.spells = human ? humanSpells : createAiSpells();
		this.maxHealth = human ? humanHealth : aiHealth;
		this.health = maxHealth;

		elementMap = new int[Gem.values().length];
		for(int i=0; i<elementMap.length; i++)
			elementMap[i] = -1;
		for(int i=0; i<spells.length; i++) {
			if (spells[i] != null)
				elementMap[spells[i].element.ordinal()] = i;
		}
	}

	public BattlePlayer opponent() {
		return board.opponent(this);
	}

	public boolean addCharge(int slot) {
		spellCharge[slot]++;
		if(spellCharge[slot]==spells[slot].maxCharges) {
			spellCharge[slot] = 0;
			return true;
		}
		else {
			return false;
		}
	}

	public void receiveDamage(int damage) {
		health -= damage;
		if(health<0)
			health = 0;
		if(health>maxHealth)
			health = maxHealth;
	}

	public int elementSlot(Gem element) {
		return elementMap[element.ordinal()];
	}

	public Spell spellForElement(Gem element) {
		int slot = elementMap[element.ordinal()];
		return slot<0 ? null : spells[slot];
	}

	private static Random random = new Random();
	private static Spell[] createAiSpells() {
		Spell[] spells = new Spell[spellSlotCount];

		int l1, l2;
		l1 = random.nextInt(4);
		do {
			l2 = random.nextInt(4);
		} while(l1==l2);

		spells[0] = Spell.metalShard;
		spells[1] = (l1==0 || l2==0) ? Spell.airLance : Spell.airFlash;
		spells[2] = (l1==1 || l2==1) ? Spell.earthLance : Spell.earthFlash;
		spells[3] = null;
		spells[4] = (l1==2 || l2==2) ? Spell.waterLance : Spell.waterFlash;
		spells[5] = (l1==3 || l2==3) ? Spell.fireLance : Spell.fireFlash;

		return spells;
	}
}
