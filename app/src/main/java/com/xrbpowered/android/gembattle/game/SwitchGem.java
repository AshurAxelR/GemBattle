package com.xrbpowered.android.gembattle.game;

public class SwitchGem {

	public boolean hasStart = false;
	public int sx, sy;
	public boolean hasTarget = false;
	public int tx, ty;

	public SwitchGem() {
	}

	public SwitchGem(int sx, int sy, int tx, int ty) {
		this.sx = sx;
		this.sy = sy;
		this.tx = tx;
		this.ty = ty;
		this.hasStart = true;
		this.hasTarget = true;
	}

	public void reset() {
		hasStart = false;
		hasTarget = false;
	}

	public void set(int x, int y, Dir dir) {
		sx = x;
		sy = y;
		tx = sx + dir.dx;
		ty = sy + dir.dy;
		hasStart = true;
		hasTarget = true;
	}

	public void setStart(int x, int y) {
		if(Board.isInside(x, y)) {
			sx = x;
			sy = y;
			hasStart = true;
			hasTarget = false;
		}
		else {
			reset();
		}
	}

	public void setDir(Dir dir) {
		if(dir!=null) {
			tx = sx + dir.dx;
			ty = sy + dir.dy;
			hasTarget = Board.isInside(tx, ty);
		}
		else {
			hasTarget = false;
		}
	}

	public SwitchGem reverse() {
		return new SwitchGem(tx, ty, sx, sy);
	}

}
