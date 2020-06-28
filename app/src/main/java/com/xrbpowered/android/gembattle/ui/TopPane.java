package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.xrbpowered.android.gembattle.ui.battle.BattlePreviewPane;
import com.xrbpowered.android.gembattle.ui.battle.GamePane;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;
import com.xrbpowered.android.zoomui.UIFitScaleContainer;

public class TopPane extends UIFitScaleContainer {

	public static final int targetWidth = 1680;
	public static final int targetHeight = 1080;

	public static TopPane instance;
	public static UIContainer overlayBase;

	private final LinearGradient bgFill = new LinearGradient(0, 0, targetWidth, 0,
		new int[] {0xff373833, 0xff000000, 0xff373833},
		new float[] {0f, 0.5f, 1f},
		Shader.TileMode.CLAMP);

	public final BattlePreviewPane battlePreview;

	private UIElement active = null;

	public TopPane(UIContainer parent) {
		super(parent, targetWidth, targetHeight);
		instance = this;

		battlePreview = new BattlePreviewPane(this);
		new GamePane(this).setVisible(false);

		overlayBase = new UIContainer(this);
		setActive(battlePreview);
	}

	public boolean isActive(UIElement el) {
		return active==el;
	}

	public void setActive(UIElement active) {
		if(this.active!=null)
			this.active.setVisible(false);
		this.active = active;
		active.setVisible(true);
	}

	@Override
	protected void paintSelf(Canvas canvas) {
		paint.setColor(0xff000000);
		paint.setStyle(Paint.Style.FILL);
		paint.setShader(bgFill);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
		paint.setShader(null);
	}
}
