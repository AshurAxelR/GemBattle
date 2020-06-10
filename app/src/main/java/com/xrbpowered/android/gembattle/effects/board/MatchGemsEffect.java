package com.xrbpowered.android.gembattle.effects.board;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.effects.attack.SpellChargeEffect;
import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.game.Gem;
import com.xrbpowered.android.gembattle.game.MatchResult;

import static com.xrbpowered.android.gembattle.game.Board.size;

public class MatchGemsEffect extends TimedEffect {

	public static final float duration = 0.1f;

	public final MatchResult match;
	private final Gem[][] gems = new Gem[size][size];

	public MatchGemsEffect(MatchResult match) {
		this.match = match;

		match.updateCounts();
		//match.applyCharges();
		SpellChargeEffect.applyCharges(match);

		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				if(match.match[x][y])
					gems[x][y] = match.board.gems[x][y];
			}
		match.board.clear(match);
	}

	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public TimedEffect finish() {
		return new DropGemsEffect(match.board, false);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				if(gems[x][y]!=null)
					gems[x][y].drawFadeBoard(canvas, x, y, 1f-s, paint);
			}
	}

	public static TimedEffect checkMatches(Board board) {
		MatchResult match = new MatchResult(board);
		if (match.find()) {
			match.board.stopTurnTimer();
			return new MatchGemsEffect(match);
		}
		else {
			TimedEffect fill = new DropGemsEffect(board, true);
			return fill.getDuration()>0f ? fill : null;
		}
	}
}
