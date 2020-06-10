package com.xrbpowered.android.gembattle;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;

import com.xrbpowered.android.gembattle.ui.GamePane;
import com.xrbpowered.android.zoomui.BaseView;
import com.xrbpowered.android.zoomui.RepaintHandler;
import com.xrbpowered.android.zoomui.UIFitScaleContainer;

public class MainActivity extends Activity {

	public static final int frameTime = 25;

	private BaseView base = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GemBattle.resources = getResources();
		GemBattle.boldFont = ResourcesCompat.getFont(this, R.font.montserrat_black);

		setContentView(R.layout.activity_main);
		base = (BaseView) findViewById(R.id.baseview);
		GemBattle.gamePane = new GamePane(new UIFitScaleContainer(base.getContainer(), GamePane.targetWidth, GamePane.targetHeight));

		GemBattle.repaint = new RepaintHandler(base, frameTime);
	}

	@Override
	protected void onPause() {
		if(base!=null) {
			GemBattle.repaint.setFrameTime(0);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(base!=null) {
			GemBattle.repaint.setFrameTime(frameTime);
		}
	}
}
