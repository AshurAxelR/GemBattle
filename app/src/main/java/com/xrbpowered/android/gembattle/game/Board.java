package com.xrbpowered.android.gembattle.game;

import java.util.Random;

public class Board {

	public static final float turnTime = 30;

	public static final int size = 7;

	public final Random random = new Random();
	public final Gem[][] gems = new Gem[size][size];

	public BattlePlayer humanPlayer = new BattlePlayer(this, true);
	public BattlePlayer aiPlayer = new BattlePlayer(this, false);
	public BattlePlayer player = humanPlayer;

	public int targetMatches = 0;

	private float turnTimer = 0;
	private boolean timerStopped = true;

	public void nextTurn() {
		player = opponent(player);
		turnTimer = turnTime;
		timerStopped = !player.human;
	}

	public BattlePlayer opponent(BattlePlayer player) {
		return player==humanPlayer ? aiPlayer : humanPlayer;
	}

	public float getTurnTimer() {
		return turnTimer;
	}

	public void stopTurnTimer() {
		timerStopped = true;
	}

	public boolean updateTurnTime(float dt) {
		if(timerStopped)
			return true;
		turnTimer -= dt;
		if(turnTimer<=0) {
			turnTimer = 0;
			return false;
		}
		else {
			return true;
		}
	}

	@SuppressWarnings("ManualArrayCopy")
	public void copyTo(Board board) {
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				board.gems[x][y] = gems[x][y];
			}
	}

	public void clear() {
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				gems[x][y] = Gem.empty;
			}
	}

	public void clear(MatchResult match) {
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				if(match.match[x][y])
					gems[x][y] = Gem.empty;
			}
	}

	public void fill(int x, int y) {
		if(gems[x][y]!=Gem.empty)
			return;
		for(;;) {
			Gem gem = Gem.getRandom(random);
			boolean valid = true;
			for(Dir d : Dir.values()) {
				int dx = x+d.dx;
				int dy = y+d.dy;
				if(isInside(dx, dy) && gems[dx][dy]==gem) {
					int dx2 = x+2*d.dx;
					int dy2 = y+2*d.dy;
					if(isInside(dx2, dy2) && gems[dx2][dy2]==gem) {
						valid = false;
						break;
					}
					int odx = x-d.dx;
					int ody = y-d.dy;
					if(isInside(odx, ody) && gems[odx][ody]==gem) {
						valid = false;
						break;
					}
				}
			}
			if(valid) {
				gems[x][y] = gem;
				return;
			}
		}
	}

	public void fill() {
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++)
				fill(x, y);
	}

	public void dropAll() {
		for(int x=0; x<size; x++) {
			int ty = size;
			for(int y=size-1; y>=0; y--) {
				if(gems[x][y]!=Gem.empty) {
					ty = ty-1;
					if(ty!=y) {
						gems[x][ty] = gems[x][y];
						gems[x][y] = Gem.empty;
					}
				}
			}
		}
	}

	public void switchGem(int sx, int sy, int tx, int ty) {
		Gem gem = gems[tx][ty];
		gems[tx][ty] = gems[sx][sy];
		gems[sx][sy] = gem;
	}

	public static boolean isInside(int x, int y) {
		return x>=0 && x<size && y>=0 && y<size;
	}

}
