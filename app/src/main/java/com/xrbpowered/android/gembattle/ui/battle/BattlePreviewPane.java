package com.xrbpowered.android.gembattle.ui.battle;

import android.graphics.Paint;

import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.ui.TopPane;
import com.xrbpowered.android.gembattle.ui.common.GlassButton;
import com.xrbpowered.android.zoomui.UIContainer;

public class BattlePreviewPane extends UIContainer {

	public final BattlePlayerPane humanPlayerPane;
	public final BattlePlayerPane aiPlayerPane;
	public final GlassButton inventoryButton;
	public final GlassButton startButton;
	public final GlassButton leaveButton;

	public Board board = null;

	public BattlePreviewPane(UIContainer parent) {
		super(parent);

		humanPlayerPane = new BattlePlayerPane(this, Paint.Align.LEFT, true);
		aiPlayerPane = new BattlePlayerPane(this, Paint.Align.RIGHT, true);

		inventoryButton = new GlassButton(this, "Equipment") {
			@Override
			public void onClick() {
			}
		};
		inventoryButton.setSize(350, inventoryButton.getHeight());
		leaveButton = new GlassButton(this, "Retreat") {
			@Override
			public boolean isEnabled() {
				return false;
			}
		};
		leaveButton.setSize(350, leaveButton.getHeight());
		startButton = new GlassButton(this, "Fight", 0xffee4422) {
			@Override
			public void onClick() {
				GamePane.instance.startBoard(board);
				TopPane.instance.setActive(GamePane.instance);
			}
		};
		startButton.setSize(400, startButton.getHeight());

		setBoard(new Board());
	}

	@Override
	public void layout() {
		humanPlayerPane.setLocation(10, 10);
		aiPlayerPane.setLocation(getWidth()-aiPlayerPane.getWidth()-10, 10);

		inventoryButton.setLocation(10, 1020-inventoryButton.getHeight()/2);
		leaveButton.setLocation(getWidth()-leaveButton.getWidth()-10, inventoryButton.getY());
		startButton.setLocation((getWidth()-startButton.getWidth())/2, 800);

		super.layout();
	}
	public void setBoard(Board board) {
		humanPlayerPane.setPlayer(board.humanPlayer);
		aiPlayerPane.setPlayer(board.aiPlayer);
		this.board = board;
	}
}
