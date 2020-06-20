package com.xrbpowered.android.gembattle.ui;

import com.xrbpowered.android.zoomui.UIContainer;

public class OverlayPane extends UIContainer {

	protected boolean dismissOnTouch;

	public OverlayPane(UIContainer parent, boolean dismissOnTouch) {
		super(parent);
		this.dismissOnTouch = dismissOnTouch;
	}

	@Override
	public void layout() {
		setLocation(0, 0);
		setSize(getParent().getWidth(), getParent().getHeight());
		super.layout();
	}

	public void dismiss() {
		getParent().removeChild(this);
	}

	@Override
	public boolean onTouchDown(float x, float y) {
		if(dismissOnTouch)
			dismiss();
		return true;
	}
}
