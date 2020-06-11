package com.xrbpowered.android.zoomui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public abstract class UIElement {

	public static final Paint paint = new Paint();

	private final UIContainer parent;
	private final BaseContainer base;

	private boolean visible = true;
	private float x, y;
	private float width, height;

	public UIElement(UIContainer parent) {
		this.parent = parent;
		this.base = (parent!=null) ? parent.getBase() : null;
		if(parent!=null)
			parent.addChild(this);
	}

	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public BaseContainer getBase() {
		return base;
	}

	protected float parentToLocalX(float x) {
		return x - this.x;
	}

	protected float parentToLocalY(float y) {
		return y - this.y;
	}

	protected float localToParentX(float x) {
		return x + this.x;
	}

	protected float localToParentY(float y) {
		return y + this.y;
	}

	public float baseToLocalX(float x) {
		return parentToLocalX(parent==null ? x : parent.baseToLocalX(x));
	}

	public float baseToLocalY(float y) {
		return parentToLocalY(parent==null ? y : parent.baseToLocalY(y));
	}

	public float localToBaseX(float x) {
		return parent==null ? localToParentX(x) : parent.localToBaseX(localToParentX(x));
	}

	public float localToBaseY(float y) {
		return parent==null ? localToParentY(y) : parent.localToBaseY(localToParentY(y));
	}

	public float getPixelScale() {
		if(parent!=null)
			return parent.getPixelScale();
		else
			return 1f;
	}

	public void repaint() {
		if(parent!=null)
			parent.repaint();
	}

	public void invalidateLayout() {
		getBase().invalidateLayout();
	}

	public void layout() {
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean isInside(float x, float y) {
		return isVisible() && x>=getX() && y>=getY() && x<=getX()+getWidth() && y<=getY()+getHeight();
	}

	public UIContainer getParent() {
		return parent;
	}

	public abstract void paint(Canvas canvas);

	public UIElement getElementAt(float x, float y) {
		if(isInside(x, y))
			return this;
		else
			return null;
	}

	public UIElement notifyTouchDown(float x, float y) {
		if(isInside(x, y) && onTouchDown(parentToLocalX(x), parentToLocalY(y)))
			return this;
		else
			return null;
	}

	public boolean onTouchDown(float x, float y) {
		return false;
	}

	public void onTouchUp(PointF start, float x, float y) {
	}

	public void onTouchMoved(PointF start, float x, float y) {
	}

}
