package com.xrbpowered.android.gembattle.effects.board;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.game.Gem;
import com.xrbpowered.android.gembattle.game.SwitchGem;

import static com.xrbpowered.android.gembattle.ui.utils.RenderUtils.lerp;

public class SwitchGemEffect extends TimedEffect {

	public static final float duration = 0.25f;

	public final boolean match;
	public final Board board;
	public final SwitchGem switchGem;

	private final Gem sgem, tgem;

	public SwitchGemEffect(Board board, SwitchGem switchGem) {
		this(board, switchGem, true);
	}

	public SwitchGemEffect(Board board, SwitchGem switchGem, boolean match) {
		this.match = match;
		this.board = board;
		this.switchGem = switchGem;

		sgem = board.gems[switchGem.sx][switchGem.sy];
		board.gems[switchGem.sx][switchGem.sy] = Gem.empty;
		tgem = board.gems[switchGem.tx][switchGem.ty];
		board.gems[switchGem.tx][switchGem.ty] = Gem.empty;
	}

	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public Effect finish() {
		board.gems[switchGem.sx][switchGem.sy] = tgem;
		board.gems[switchGem.tx][switchGem.ty] = sgem;

		if(match) {
			Effect next = MatchGemsEffect.checkMatches(board);
			return (next!=null) ? next : new SwitchGemEffect(board, switchGem.reverse(), false);
		}

		return null;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		tgem.drawBoard(canvas,
			lerp(switchGem.tx, switchGem.sx, s),
			lerp(switchGem.ty, switchGem.sy, s),
			paint);
		sgem.drawBoard(canvas,
			lerp(switchGem.sx, switchGem.tx, s),
			lerp(switchGem.sy, switchGem.ty, s),
			paint);
	}
}
