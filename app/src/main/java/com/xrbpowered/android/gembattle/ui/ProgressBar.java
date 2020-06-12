package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;

import com.xrbpowered.android.gembattle.ui.utils.GlassPaint;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

public abstract class ProgressBar extends UIElement {

	public static final int defaultHeight = 40;

	private static final float[] shades = {1f, 0.25f};
	private static final int light = 0;
	private static final int dark = 1;

	private final GlassPaint glass;

	public ProgressBar(UIContainer parent, int color) {
		super(parent);
		glass = new GlassPaint(this, defaultHeight, color, shades);
		setSize(0, defaultHeight);
	}

	public abstract float getProgress();
	public abstract String getText();

	@Override
	public void setSize(float width, float height) {
		glass.setHeight(height);
		super.setSize(width, height);
	}

	@Override
	public void paint(Canvas canvas) {
		float w = getWidth()*getProgress();
		if(w>0)
			glass.paintGradient(canvas, 0, w, light);
		if(w<getWidth())
			glass.paintGradient(canvas, w, getWidth(), dark);
		glass.paintGlass(canvas);
		glass.paintBorder(canvas, 0xffe4d9ad);
		glass.paintText(canvas, getText(), 0xffffffff, 40);
	}

}
