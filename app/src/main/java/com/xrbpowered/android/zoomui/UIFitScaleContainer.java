package com.xrbpowered.android.zoomui;

import android.graphics.Canvas;

public class UIFitScaleContainer extends UIContainer {
	protected float scale = 1f;
	protected float targetWidth, targetHeight;

	public UIFitScaleContainer(UIContainer parent, float targetWidth, float targetHeight) {
		super(parent);
		this.targetWidth = targetWidth;
		this.targetHeight = targetHeight;
	}

	@Override
	public void layout() {
		float scalew = getWidth() / targetWidth;
		float scaleh = getHeight() / targetHeight;
		scale = Math.min(scalew, scaleh);

		float cx = (getWidth() / scale - targetWidth)/2;
		float cy = (getHeight() / scale - targetHeight)/2;
		for(UIElement c : children) {
			c.setLocation(cx, cy);
			c.setSize(targetWidth, targetHeight);
			c.layout();
		}
	}

	@Override
	public float getPixelScale() {
		return super.getPixelScale()/scale;
	}

	@Override
	protected float parentToLocalX(float x) {
		return super.parentToLocalX(x)/scale;
	}

	@Override
	protected float parentToLocalY(float y) {
		return super.parentToLocalY(y)/scale;
	}

	@Override
	protected float localToParentX(float x) {
		return super.localToParentX(x*scale);
	}

	@Override
	protected float localToParentY(float y) {
		return super.localToParentY(y*scale);
	}

	@Override
	protected void paintChildren(Canvas canvas) {
		canvas.save();
		canvas.scale(scale, scale);
		super.paintChildren(canvas);
		canvas.restore();
	}
}
