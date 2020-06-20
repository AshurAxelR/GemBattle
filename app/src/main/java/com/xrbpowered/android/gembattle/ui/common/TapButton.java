package com.xrbpowered.android.gembattle.ui.common;

import com.xrbpowered.android.zoomui.UIContainer;
import com.xrbpowered.android.zoomui.UIElement;

public abstract class TapButton extends UIElement {

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
			onClick();
		return true;
	}

}
