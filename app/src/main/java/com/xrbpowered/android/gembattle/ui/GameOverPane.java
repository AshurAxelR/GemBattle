package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;

import com.xrbpowered.android.gembattle.game.Board;
import com.xrbpowered.android.gembattle.ui.common.GlassButton;
import com.xrbpowered.android.gembattle.ui.common.OverlayPane;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.gembattle.ui.utils.Strings;
import com.xrbpowered.android.zoomui.UIContainer;

public class GameOverPane extends OverlayPane {

	public static int countVictories = 0;
	public static int countLosses = 0;

	public final GlassButton continueButton;

	public final String message;
	public final String counterMessage;

	public GameOverPane(UIContainer parent, boolean victory) {
		super(parent, false);
		this.message = victory ? "Victory!" : "Defeat!";

		if(victory)
			countVictories++;
		else
			countLosses++;
		this.counterMessage = Strings.format("%d victor%s, %d loss%s",
			countVictories, countVictories==1 ? "y" : "ies",
			countLosses, countLosses==1 ? "" : "es"
		);

		continueButton = new GlassButton(this, "Restart") {
			@Override
			public void onClick() {
				dismiss();
				GamePane.instance.startBoard(new Board());
			}
		};
		continueButton.setSize(400, continueButton.getHeight());
	}

	@Override
	public void layout() {
		super.layout();
		continueButton.setLocation((getWidth()-continueButton.getWidth())/2, getHeight()*0.75f-continueButton.getHeight()/2);
	}

	@Override
	protected void paintSelf(Canvas canvas) {
		paint.setTypeface(RenderUtils.fontBlack);
		paint.setTextSize(60);
		RenderUtils.drawStrokeText(canvas, message, getWidth()/2, getHeight()/2, paint);
		paint.setColor(0xffcccccc);
		paint.setTextSize(30);
		canvas.drawText(counterMessage, getWidth()/2, continueButton.getY() - 100, paint);
	}
}
