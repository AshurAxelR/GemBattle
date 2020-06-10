package com.xrbpowered.android.gembattle.game;

import java.util.ArrayList;

import static com.xrbpowered.android.gembattle.game.Board.size;

public class DropColumn {

	public final Board board;
	public final int x;
	public int maxy = -1;
	public int maxDist = 0;

	public final ArrayList<DropGem> drops = new ArrayList<>();

	public DropColumn(Board board, int x) {
		this.board = board;
		this.x = x;
	}

	public void reset() {
		maxy = -1;
		maxDist = 0;
		drops.clear();
	}

	public boolean find() {
		reset();
		int ty = size;
		for(int y=size-1; y>=0; y--) {
			if(board.gems[x][y]!=Gem.empty) {
				ty = ty-1;
				if(ty!=y) {
					if(maxy<0)
						maxy = ty;
					DropGem drop = new DropGem(x, y, ty, board.gems[x][y]);
					drops.add(drop);
					int dist = drop.end - drop.start;
					if(dist>maxDist)
						maxDist = dist;
				}
			}
		}
		return maxy>=0;
	}

	public boolean fill() {
		reset();
		int sy = 0;
		for(int y=size-1; y>=0; y--) {
			if(board.gems[x][y]==Gem.empty) {
				sy = sy-1;
				if(maxy<0)
					maxy = y;
				board.fill(x, y);
				DropGem drop = new DropGem(x, sy, y, board.gems[x][y]);
				drops.add(drop);
				int dist = drop.end - drop.start;
				if(dist>maxDist)
					maxDist = dist;
			}
		}
		return maxy>=0;
	}

	public void clearBoard() {
		for(int y=0; y<=maxy; y++) {
			board.gems[x][y] = Gem.empty;
		}
	}

	public void apply() {
		for(DropGem drop : drops) {
			board.gems[x][drop.end] = drop.gem;
			if(drop.start>=0)
				board.gems[x][drop.start] = Gem.empty;
		}
	}

}
