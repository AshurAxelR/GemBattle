package com.xrbpowered.android.gembattle.game;

import static com.xrbpowered.android.gembattle.game.Board.isInside;
import static com.xrbpowered.android.gembattle.game.Board.size;

public class MatchResult {

	private static final double baseChargeValue = 0.1f;

	public final Board board;
	public final boolean match[][] = new boolean[size][size];
	public final int count[] = new int[Gem.values().length];

	public MatchResult(Board board) {
		this.board = board;
	}

	public void reset() {
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				match[x][y] = false;
			}
	}

	public void updateCounts() {
		for(int i=0; i<count.length; i++)
			count[i] = 0;
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				if(match[x][y])
					count[board.gems[x][y].ordinal()]++;
			}
	}

	public static int matchesToChareges(int c) {
		return (c>=3) ? c*2 -3 : c;
	}

	public double getValue(BattlePlayer player) {
		double value = 0;
		for(Gem gem : Gem.values()) {
			int c = matchesToChareges(count[gem.ordinal()]);
			value += baseChargeValue * c;
			Spell spell = player.spellForElement(gem);
			if(spell!=null)
				value += spell.valuePerCharge() * c;
		}
		return value;
	}

	public int countMatches() {
		int total = 0;
		for(int c : count)
			total += c;
		return total;
	}

	private boolean count(int x, int y, int dx, int dy, boolean checkOnly) {
		Gem gem = board.gems[x][y];
		int count=1;
		for(int i=1; i<size; i++) {
			int tx = x+i*dx;
			int ty = y+i*dy;
			if(isInside(tx, ty) && board.gems[tx][ty]==gem) {
				count++;
				if(checkOnly && count>=3)
					return true;
			}
			else
				break;
		}
		if(count>=3) {
			for(int i=0; i<count; i++) {
				int tx = x+i*dx;
				int ty = y+i*dy;
				match[tx][ty] = true;
			}
			return true;
		}
		else
			return false;
	}

	private boolean find(boolean checkOnly) {
		if(!checkOnly)
			reset();
		boolean res = false;
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				if(board.gems[x][y]!=Gem.empty) {
					res |= count(x, y, 1, 0, checkOnly);
					res |= count(x, y, 0, 1, checkOnly);
				}
				if(checkOnly && res)
					return true;
			}
		return res;
	}

	public boolean find() {
		return find(false);
	}

	public boolean check() {
		return find(true);
	}
}
