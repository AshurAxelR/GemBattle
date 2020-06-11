package com.xrbpowered.android.gembattle.effects.board;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.GemBattle;
import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.effects.WaitEffects;
import com.xrbpowered.android.gembattle.game.AILogic;
import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.game.DropColumn;
import com.xrbpowered.android.gembattle.game.DropGem;

import static com.xrbpowered.android.gembattle.game.Board.size;

public class DropGemsEffect extends TimedEffect {

	public static final float speed = 20f;

	public final Board board;
	public final boolean fill;
	public final DropColumn[] columns = new DropColumn[size];

	private int maxDist;

	public DropGemsEffect(Board board, boolean fill) {
		this.board = board;
		this.fill = fill;

		maxDist = 0;
		for(int x=0; x<size; x++) {
			DropColumn drop = new DropColumn(board, x);
			if(fill)
				drop.fill();
			else
				drop.find();
			if(drop.maxDist > maxDist)
				maxDist = drop.maxDist;
			columns[x] = drop;
		}
		for(int x=0; x<size; x++)
			columns[x].clearBoard();
	}

	@Override
	public float getDuration() {
		return (float) Math.sqrt(maxDist / speed);
	}

	@Override
	public Effect finish() {
		for(int x=0; x<size; x++) {
			columns[x].apply();
		}
		Effect next = MatchGemsEffect.checkMatches(board);

		if(next==null) {
			if (!AILogic.checkMoves(board)) {
				board.clear();
				GemBattle.popupMessageFloat.show("No available moves");
				return new DropGemsEffect(board, true);
			} else {
				return new WaitEffects(GemBattle.attackEffects) {
					@Override
					public Effect finish() {
						return endTurn(board, false);
					}
				};
			}
		}
		else {
			return next;
		}
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		float dy = time * time *speed;
		for(int x=0; x<size; x++) {
			for(DropGem drop : columns[x].drops) {
				float y = drop.start + dy;
				if(y>drop.end)
					y = drop.end;
				drop.gem.drawBoard(canvas, x, y, paint);
			}
		}
	}

	public static Effect endTurn(Board board, boolean timeOut) {
		board.nextTurn();
		if(board.player.human) {
			board.targetMatches = AILogic.targetMatches(board, board.player);
			GemBattle.popupMessageFloat.show("Your Turn");
			return null;
		}
		else {
			GemBattle.popupMessageFloat.show(timeOut ? "Time's up" : "Opponent's Turn");
			return new AIThinkEffect(board);
		}
	}
}
