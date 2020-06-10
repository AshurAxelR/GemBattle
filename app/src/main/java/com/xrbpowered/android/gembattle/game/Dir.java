package com.xrbpowered.android.gembattle.game;

public enum Dir {
	up(0, -1),
	down(0, 1),
	left(-1, 0),
	right(1, 0);

	public final int dx, dy;

	Dir(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public static Dir find(int dx, int dy) {
		int adx = Math.abs(dx);
		if(adx>0) dx /= adx;
		int ady = Math.abs(dy);
		if(ady>0) dy /= ady;

		for(Dir d : values()) {
			if (d.dx == dx && d.dy == dy)
				return d;
		}
		return null;
	}
}
