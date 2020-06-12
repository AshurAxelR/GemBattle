package com.xrbpowered.android.gembattle.ui;

import android.graphics.PointF;

import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

public abstract class TapButton extends UIElement {

	public boolean down = false;
	public boolean enabled = true;

	public TapButton(UIContainer parent) {
		super(parent);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void onClick() {
	}

	@Override
	public boolean onTouchDown(float x, float y) {
		if(isEnabled())
			down = true;
		return true;
	}

	@Override
	public void onTouchUp(PointF start, float x, float y) {
		if(down) {
			onClick();
			down = false;
		}
	}

	@Override
	public void onTouchMoved(PointF start, float x, float y) {
		down &= isInside(localToParentX(x), localToParentY(y));
	}
}
