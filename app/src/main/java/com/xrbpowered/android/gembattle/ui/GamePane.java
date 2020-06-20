package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;

import com.xrbpowered.android.gembattle.effects.EffectSet;
import com.xrbpowered.android.gembattle.game.BattlePlayer;
import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.game.Gem;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.gembattle.ui.utils.Strings;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

public class GamePane extends UIContainer {

	public static final int targetWidth = 1680;
	public static final int targetHeight = 1080;

	public static GamePane instance;
	public static float time = 0f;

	public static EffectSet particles;
	public static EffectSet attackEffects;

	public final BoardPane boardPane;
	public final ProgressBar turnTimerProgress;
	public final BattlePlayerPane humanPlayerPane;
	public final BattlePlayerPane aiPlayerPane;
	public final GlassButton pauseButton;
	public final GlassButton skipButton;
	public final UIElement attackEffectPane;
	public final PopupMessageFloat popupMessageFloat;

	private boolean paused = false;

	private final LinearGradient bgFill = new LinearGradient(0, 0, targetWidth, 0,
			new int[] {0xff373833, 0xff000000, 0xff373833},
			new float[] {0f, 0.5f, 1f},
			Shader.TileMode.CLAMP);

	private long prevt = 0L;

	public GamePane(UIContainer parent) {
		super(parent);
		instance = this;
		particles = new EffectSet();
		attackEffects = new EffectSet();

		boardPane = new BoardPane(this);

		humanPlayerPane = new BattlePlayerPane(this, Paint.Align.LEFT);
		aiPlayerPane = new BattlePlayerPane(this, Paint.Align.RIGHT);

		pauseButton = new GlassButton(this, "Pause") {
			@Override
			public void onClick() {
				setPaused(!paused);
			}
		};
		pauseButton.setSize(350, pauseButton.getHeight());
		skipButton = new GlassButton(this, "Skip") {
			@Override
			public boolean isEnabled() {
				return boardPane.isActive() && !paused;
			}
			@Override
			public void onClick() {
				boardPane.skip();
			}
		};
		skipButton.setSize(350, skipButton.getHeight());

		turnTimerProgress = new ProgressBar(this, 0xff77ff00) {
			@Override
			public float getProgress() {
				return boardPane.board.getTurnTimer()/Board.turnTime;
			}
			@Override
			public String getText() {
				return Integer.toString((int) boardPane.board.getTurnTimer());
			}
			@Override
			public void paint(Canvas canvas) {
				if(boardPane.board.player.human)
					super.paint(canvas);
			}
		};

		attackEffectPane = new UIElement(this) {
			@Override
			public void paint(Canvas canvas) {
				particles.draw(canvas, paint);
				attackEffects.draw(canvas, paint);
			}
		};

		humanPlayerPane.damageText = new DamageTextFloat(this);
		aiPlayerPane.damageText = new DamageTextFloat(this);

		popupMessageFloat = new PopupMessageFloat(this);

		startBoard(new Board());
	}

	public void startBoard(Board board) {
		humanPlayerPane.setPlayer(board.humanPlayer);
		aiPlayerPane.setPlayer(board.aiPlayer);
		boardPane.startBoard(board);
	}

	public BattlePlayerPane getPlayerPane(BattlePlayer player) {
		if(humanPlayerPane.getPlayer()==player)
			return humanPlayerPane;
		else if(aiPlayerPane.getPlayer()==player)
			return aiPlayerPane;
		else
			return null;
	}

	@Override
	public void layout() {
		boardPane.setLocation((getWidth()-boardPane.getWidth())/2, BoardPane.marginTop);

		humanPlayerPane.setLocation(10, 10);
		humanPlayerPane.damageText.setLocation(humanPlayerPane.getX()+humanPlayerPane.getWidth()/2, humanPlayerPane.getY()+360);
		aiPlayerPane.setLocation(getWidth()-aiPlayerPane.getWidth()-10, 10);
		aiPlayerPane.damageText.setLocation(aiPlayerPane.getX()+aiPlayerPane.getWidth()/2, aiPlayerPane.getY()+360);

		pauseButton.setLocation(10, 1020-pauseButton.getHeight()/2);
		skipButton.setLocation(getWidth()-skipButton.getWidth()-10, pauseButton.getY());

		turnTimerProgress.setSize(boardPane.getWidth()+12, turnTimerProgress.getHeight());
		turnTimerProgress.setLocation(boardPane.getX()-6, boardPane.getY()/2 - turnTimerProgress.getHeight()/2);

		popupMessageFloat.setLocation(
			(getWidth()-popupMessageFloat.getWidth())/2,
			(getHeight()-popupMessageFloat.getHeight())/2);

		attackEffectPane.setSize(getWidth(), getHeight());
		attackEffectPane.setLocation(0, 0);

		super.layout();
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		if(this.paused && !paused)
			prevt = System.currentTimeMillis();
		this.paused = paused;
	}

	public boolean spellsCharging() {
		return humanPlayerPane.spellPane.isCharging() || aiPlayerPane.spellPane.isCharging();
	}

	public void updateTime() {
		if(paused)
			return;

		long t = System.currentTimeMillis();
		if(prevt==0L)
			prevt = t;
		float dt = (t-prevt)/1000f;
		time += dt;

		particles.update(dt);
		attackEffects.update(dt);
		humanPlayerPane.updateTime(dt);
		aiPlayerPane.updateTime(dt);
		boardPane.updateTime(dt);

		prevt = t;
	}

	@Override
	protected void paintSelf(Canvas canvas) {
		if(!Gem.isLoaded())
			Gem.loadBitmaps();

		updateTime();

		paint.setColor(0xff000000);
		paint.setStyle(Paint.Style.FILL);
		paint.setShader(bgFill);
		canvas.drawRect(-getX(), -getY(), getWidth()+getX(), getHeight()+getY(), paint);
		paint.setShader(null);

		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xffffffff);
		paint.setTypeface(RenderUtils.fontBlack);

		paint.setTextSize(25);
		paint.setTextAlign(Paint.Align.RIGHT);
		canvas.drawText(Strings.clock(), getWidth()-10, 25, paint);

		if(boardPane.board.player.human) {
			paint.setTypeface(Typeface.SANS_SERIF);
			paint.setColor(0xff999999);
			paint.setTextSize(30);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(Strings.format("You can match %d", boardPane.board.targetMatches), getWidth()/2, getHeight()-50, paint);
		}
	}
}
