package com.xrbpowered.android.zoomui;

import android.graphics.Canvas;

import java.util.ArrayList;

public class UIContainer extends UIElement {
	protected ArrayList<UIElement> children = new ArrayList<>();

	public UIContainer(UIContainer parent) {
		super(parent);
	}

	protected void addChild(UIElement c) {
		children.add(c);
		invalidateLayout();
	}

	public void removeChild(UIElement c) {
		if(children.remove(c))
			invalidateLayout();
	}

	public void removeAllChildren() {
		children.clear();
		invalidateLayout();
	}

	@Override
	public void layout() {
		for(UIElement c : children) {
			c.layout();
		}
	}

	protected void paintSelf(Canvas canvas) {
	}

	protected void paintChildren(Canvas canvas) {
		for(UIElement c : children) {
			if(c.isVisible()) {
				float cx = c.getX();
				float cy = c.getY();
				canvas.translate(cx, cy);
				c.paint(canvas);
				canvas.translate(-cx, -cy);
			}
		}
	}

	@Override
	public void paint(Canvas canvas) {
		paintSelf(canvas);
		paintChildren(canvas);
	}

	public UIElement getElementAt(float x, float y) {
		if(!isVisible())
			return null;
		float cx = parentToLocalX(x);
		float cy = parentToLocalY(y);
		for(int i=children.size()-1; i>=0; i--) {
			UIElement e = children.get(i).getElementAt(cx, cy);
			if(e!=null)
				return e;
		}
		return super.getElementAt(x, y);
	}

	@Override
	public UIElement notifyTouchDown(float x, float y) {
		if(!isVisible())
			return null;
		float cx = parentToLocalX(x);
		float cy = parentToLocalY(y);
		for(int i=children.size()-1; i>=0; i--) {
			UIElement e = children.get(i).notifyTouchDown(cx, cy);
			if(e!=null)
				return e;
		}
		return super.notifyTouchDown(x, y);
	}

}
