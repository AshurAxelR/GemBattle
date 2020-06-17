package com.xrbpowered.android.gembattle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;

import com.xrbpowered.android.gembattle.ui.GamePane;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;
import com.xrbpowered.android.zoomui.BaseView;
import com.xrbpowered.android.zoomui.RepaintHandler;
import com.xrbpowered.android.zoomui.UIFitScaleContainer;

public class MainActivity extends Activity {

	public static final int frameTime = 15;

	private RepaintHandler repaint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RenderUtils.resources = getResources();
		RenderUtils.fontBlack = ResourcesCompat.getFont(this, R.font.montserrat_black);

		setContentView(R.layout.activity_main);
		BaseView base = findViewById(R.id.baseview);
		new GamePane(new UIFitScaleContainer(base.getContainer(), GamePane.targetWidth, GamePane.targetHeight));

		repaint = new RepaintHandler(base, frameTime);
	}

	@Override
	protected void onPause() {
		if(repaint!=null) {
			repaint.setFrameTime(0);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(repaint!=null) {
			repaint.setFrameTime(frameTime);
		}
	}
}
