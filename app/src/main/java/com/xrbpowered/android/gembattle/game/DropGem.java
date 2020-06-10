package com.xrbpowered.android.gembattle.game;

public class DropGem {

	public final int x;
	public final int start;
	public final int end;
	public final Gem gem;

	public DropGem(int x, int start, int end, Gem gem) {
		this.x = x;
		this.start = start;
		this.end = end;
		this.gem = gem;
	}

}
