package com.xrbpowered.android.gembattle.ui.common;

import android.graphics.Canvas;

import com.xrbpowered.android.gembattle.ui.utils.GlassPaint;
import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

public abstract class ProgressBar extends UIElement {

	public static final int defaultHeight = 40;

	private final GlassPaint glass;

	public ProgressBar(UIContainer parent, int color) {
		super(parent);
		glass = new GlassPaint(this, defaultHeight, color);
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
		glass.paintGradient(canvas, 0, getWidth(), 0);
		if(w<getWidth()) {
			paint.setColor(0xcc000000);
			canvas.drawRect(w, 0, getWidth(), getHeight(), paint);
		}
		glass.paintGlass(canvas);
		glass.paintBorder(canvas, 0xffe4d9ad);
		glass.paintText(canvas, getText(), 0xffffffff, 40);
	}

}
