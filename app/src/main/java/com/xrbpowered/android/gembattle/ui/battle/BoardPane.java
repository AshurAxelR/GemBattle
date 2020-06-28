package com.xrbpowered.android.gembattle.ui.battle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.xrbpowered.android.gembattle.effects.Effect;
import com.xrbpowered.android.gembattle.effects.board.DropGemsEffect;
import com.xrbpowered.android.gembattle.effects.board.SwitchGemEffect;
import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.game.Dir;
import com.xrbpowered.android.gembattle.game.SwitchGem;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

import static com.xrbpowered.android.gembattle.game.Board.size;

public class BoardPane extends UIElement {

	public static boolean hideOnPause = true;

	public static final int marginTop = 120;
	public static final int gemSize = 120;
	public static final int screenSize = size * gemSize;

	private static final int switchThreshold = gemSize/2;
	private static final int diagonalThresholdAngle = 30;
	private static final float diagonalThreshold = 1f/(float)Math.cos(Math.toRadians(diagonalThresholdAngle));

	public Board board = null;

	private final SwitchGem switchGem = new SwitchGem();
	private Effect effect;

	public BoardPane(UIContainer parent) {
		super(parent);
		setSize(screenSize, screenSize);
	}

	public void startBoard(Board board) {
		this.board = board;
		board.clear();
		board.player = board.aiPlayer;
		effect = new DropGemsEffect(board, true);
	}

	public boolean isActive() {
		return effect==null && board.player.human && !board.isFinished();
	}

	public void skip() {
		if(isActive()) {
			effect = DropGemsEffect.endTurn(board, false);
		}
	}

	@Override
	public boolean onTouchDown(float x, float y) {
		if(isActive()) {
			int sx = (int) (x / gemSize);
			int sy = (int) (y / gemSize);
			if (Board.isInside(sx, sy)) {
				switchGem.setStart(sx, sy);
			}
		}
		return true;
	}

	@Override
	public void onTouchMoved(PointF start, float x, float y) {
		if(isActive()) {
			float dx = x - start.x;
			float dy = y - start.y;
			float len = (float) Math.sqrt(dx * dx + dy * dy);
			if (len < switchThreshold)
				return;
			dx = diagonalThreshold * dx / len;
			dy = diagonalThreshold * dy / len;
			switchGem.setDir(Dir.find((int) dx, (int) dy));
		}
	}

	@Override
	public void onTouchUp(PointF start, float x, float y) {
		if(isActive()) {
			if (switchGem.hasStart && switchGem.hasTarget)
				effect = new SwitchGemEffect(board, switchGem);
			switchGem.reset();
		}
	}

	public void updateTime(float dt) {
		if(effect!=null)
			effect = effect.update(dt);
		if(!board.updateTurnTime(dt))
			effect = DropGemsEffect.endTurn(board, true);
	}

	@Override
	public void paint(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				paint.setColor((x+y)%2==0 ? 0xff3f4131 : 0xff4e503c);
				canvas.drawRect(x*gemSize, y*gemSize, x*gemSize+gemSize, y*gemSize+gemSize, paint);
			}
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(0xffe4d9ad);
		paint.setStrokeWidth(4f);
		canvas.drawRect(-4, -4, screenSize+4, screenSize+4, paint);

		if(!board.isFinished()) {
			if(GamePane.instance.isPaused() && hideOnPause) {
				paint.setTypeface(RenderUtils.fontBlack);
				paint.setTextSize(60);
				RenderUtils.drawStrokeText(canvas, "Paused", screenSize/2, screenSize/2, paint);
			}
			else {
				canvas.save();
				canvas.clipRect(0, 0, screenSize, screenSize);
				for (int x = 0; x < size; x++)
					for (int y = 0; y < size; y++) {
						board.gems[x][y].draw(canvas, x * gemSize, y * gemSize, paint);
					}

				if (effect != null)
					effect.draw(canvas, paint);
				canvas.restore();

				if (effect == null && switchGem.hasStart) {
					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeWidth(4);
					paint.setColor(0xffffffff);
					canvas.drawRect(switchGem.sx * gemSize - 3, switchGem.sy * gemSize - 3,
							(switchGem.sx + 1) * gemSize + 3, (switchGem.sy + 1) * gemSize + 3, paint);
				}
			}
		}
	}
}
