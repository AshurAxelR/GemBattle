package com.xrbpowered.android.gembattle.effects.board;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.TimedEffect;
import com.xrbpowered.android.gembattle.game.AILogic;
import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.game.SwitchGem;

public class AIThinkEffect extends TimedEffect {

	public static final float duration = 1f;

	public final Board board;
	public final SwitchGem aiSwitch;

	public AIThinkEffect(Board board) {
		this.board = board;
		aiSwitch = AILogic.findSwitch(board, board.aiPlayer);
	}

	@Override
	public float getDuration() {
		return duration;
	}

	@Override
	public Effect finish() {
		if(aiSwitch!=null)
			return new SwitchGemEffect(board, aiSwitch);
		else
			return null;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		if(aiSwitch!=null && s>0.5f) {
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(4);
			paint.setColor(0xffffffff);
			canvas.drawRect(aiSwitch.sx * Board.gemSize - 3, aiSwitch.sy * Board.gemSize - 3,
					(aiSwitch.sx + 1) * Board.gemSize + 3, (aiSwitch.sy + 1) * Board.gemSize + 3, paint);
		}
	}
}
