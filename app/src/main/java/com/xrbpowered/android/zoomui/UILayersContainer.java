package com.xrbpowered.android.zoomui;

public class UILayersContainer extends UIContainer {

	public UILayersContainer(UIContainer parent) {
		super(parent);
	}

	@Override
	public void layout() {
		for(UIElement c : children) {
			c.setLocation(0, 0);
			c.setSize(getWidth(), getHeight());
			c.layout();
		}
	}
}
