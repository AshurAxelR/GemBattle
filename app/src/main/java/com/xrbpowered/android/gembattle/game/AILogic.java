package com.xrbpowered.android.gembattle.game;

import android.graphics.Point;

import static com.xrbpowered.android.gembattle.game.Board.size;

public class AILogic {

	private static final int aiMaxLimit = 10;

	private static final AILogic ai = new AILogic(2);

	private final Board pad = new Board();
	private final Board pad2 = new Board();
	private final MatchResult match = new MatchResult(pad2);

	public final AILogic next;

	public SwitchGem switchGem = new SwitchGem();
	public Integer maxValue;

	private BattlePlayer player;
	private BattlePlayer opponent;
	private int maxLimit;
	private boolean countMatches;
	private boolean deep;

	public AILogic(int depth) {
		this.next = depth>1 ? new AILogic(depth-1) : null;
	}

	private Integer analyseSwitch() {
		Integer value = null;
		pad.copyTo(pad2);
		int limit = aiMaxLimit;
		while(match.find()) {
			if(value==null)
				value = 0;
			if(limit<=0)
				break;
			match.updateCounts();
			value += countMatches ? match.countMatches() : match.getValue(player);
			match.board.clear(match);
			pad2.dropAll();
			limit--;
		}

		if(value==null)
			return null;

		if(deep && next!=null) {
			next.analyse(pad2, opponent, player, maxLimit, true, countMatches);
			if(next.maxValue !=null)
				value -= next.maxValue;
		}

		return value;
	}

	private void analyseSwitch(int x, int y, Dir d) {
		pad.switchGem(x, y, x+d.dx, y+d.dy);
		Integer value = analyseSwitch();
		if(value!=null && (maxValue ==null || value> maxValue)) {
			maxValue = value;
			switchGem.set(x, y, d);
		}
		pad.switchGem(x+d.dx, y+d.dy, x, y);
	}

	private void analyse(Board board, BattlePlayer player, BattlePlayer opponent, int maxLimit, boolean deep, boolean countMatches) {
		this.player = player;
		this.opponent = opponent;
		this.countMatches = countMatches;
		this.maxLimit = maxLimit;
		this.deep = deep;

		board.copyTo(pad);
		maxValue = null;
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				if(x>0)
					analyseSwitch(x, y, Dir.left);
				if(y>0)
					analyseSwitch(x, y, Dir.up);
			}
	}

	private boolean checkMove(int x, int y, Dir d) {
		pad2.switchGem(x, y, x+d.dx, y+d.dy);
		boolean res = match.find();
		pad2.switchGem(x+d.dx, y+d.dy, x, y);
		return res;
	}

	private Point findAMove(Board board) {
		board.copyTo(pad2);
		for(int x=0; x<size; x++)
			for(int y=0; y<size; y++) {
				if(x>0 && checkMove(x, y, Dir.left))
					return new Point(x, y);
				if(y>0 && checkMove(x, y, Dir.up))
					return new Point(x, y);
			}
		return null;
	}

	public static SwitchGem findSwitch(Board board, BattlePlayer player) {
		ai.analyse(board, player, board.opponent(player), aiMaxLimit, true, false);
		return ai.maxValue !=null ? ai.switchGem : null;
	}

	public static int targetMatches(Board board, BattlePlayer player) {
		ai.analyse(board, player, board.opponent(player), aiMaxLimit, false, true);
		return ai.maxValue != null ? ai.maxValue : 0;
	}

	public static boolean checkMoves(Board board) {
		return ai.findAMove(board)!=null;
	}

}
