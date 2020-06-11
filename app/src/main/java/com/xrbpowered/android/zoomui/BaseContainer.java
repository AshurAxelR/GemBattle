package com.xrbpowered.android.zoomui;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class BaseContainer extends UILayersContainer {

	private final View view;

	protected BaseContainer(View view) {
		super(null);
		this.view = view;
	}

	@Override
	public BaseContainer getBase() {
		return this;
	}

	public View getView() {
		return view;
	}

	private UIElement uiInitiator = null;
	private final PointF touchStart = new PointF();

	protected boolean invalidLayout = true;

	@Override
	public void repaint() {
		view.invalidate();
	}

	public void invalidateLayout() {
		invalidLayout = true;
	}

	@Override
	public UIElement notifyTouchDown(float x, float y) {
		UIElement ui = super.notifyTouchDown(x, y);
		if(ui!=uiInitiator && uiInitiator!=null) {
			uiInitiator.onTouchUp(touchStart, uiInitiator.baseToLocalX(x), uiInitiator.baseToLocalY(y));
			uiInitiator = null;
		}
		if(uiInitiator==null && ui!=null) {
			touchStart.set(ui.baseToLocalX(x), ui.baseToLocalY(y));
		}
		uiInitiator = ui;
		return this;
	}

	public void handleTouchAction(int action, float x, float y) {
		if(action==MotionEvent.ACTION_MOVE) {
			if(uiInitiator!=null)
				uiInitiator.onTouchMoved(touchStart, uiInitiator.baseToLocalX(x), uiInitiator.baseToLocalY(y));
		}
		else if(action==MotionEvent.ACTION_DOWN) {
			notifyTouchDown(x, y);
		}
		else if(action==MotionEvent.ACTION_UP) {
			if(uiInitiator!=null) {
				uiInitiator.onTouchUp(touchStart, uiInitiator.baseToLocalX(x), uiInitiator.baseToLocalY(y));
				uiInitiator = null;
			}
		}
	}

	/*@Override
	public UIElement notifyTouchDown(float x, float y, int button, int mods) {
		prevMouseX = getWindow().baseToScreenX(x);
		prevMouseY = getWindow().baseToScreenY(y);
		initiatorButton = button;
		initiatorMods = mods;
		UIElement ui = super.notifyTouchDown(x, y, button, mods);
		if(ui!=uiInitiator && uiInitiator!=null)
			uiInitiator.onMouseReleased();
		uiInitiator = ui;
		return this;
	}

	@Override
	public UIElement notifyTouchUp(float x, float y, int button, int mods, UIElement initiator) {
		if(super.notifyTouchUp(x, y, button, mods, uiInitiator)!=uiInitiator && uiInitiator!=null)
			uiInitiator.onMouseReleased(); // FIXME release for multi-button scenarios
		return this;
	}

	@Override
	public void onMouseOut() {
		if(uiUnderMouse!=null) {
			if(uiUnderMouse!=this)
				uiUnderMouse.onMouseOut();
			uiUnderMouse = null;
		}
	}

	private void updateMouseMove(float x, float y) {
		UIElement ui = getElementAt(x, y);
		if(ui!=uiUnderMouse) {
			if(uiUnderMouse!=null && uiUnderMouse!=this)
				uiUnderMouse.onMouseOut();
			uiUnderMouse = ui;
			if(uiUnderMouse!=null && uiUnderMouse!=this)
				uiUnderMouse.onMouseIn();
		}
	}

	@Override
	public void onTouchMoved(float x, float y, int mods) {
		updateMouseMove(x, y);
		if(uiUnderMouse!=null && uiUnderMouse!=this)
			uiUnderMouse.onTouchMoved(uiUnderMouse.baseToLocalX(x), uiUnderMouse.baseToLocalY(y), mods);
	}

	public void onMouseDragged(float x, float y) {
		int sx = getWindow().baseToScreenX(x);
		int sy = getWindow().baseToScreenY(y);
		if(drag==null && uiInitiator!=null) {
			drag = uiInitiator.acceptDrag(getWindow().screenToBaseX(prevMouseX), getWindow().screenToBaseY(prevMouseY), initiatorButton, initiatorMods);
		}
		if(drag!=null) {
			if(!drag.notifyMouseMove(sx-prevMouseX, sy-prevMouseY))
				drag = null;
			prevMouseX = sx;
			prevMouseY = sy;
		}
		updateMouseMove(x, y);
	}*/

	@Override
	public float getPixelScale() {
		return 1f;
	}

	@Override
	protected float parentToLocalX(float x) {
		return x;
	}

	@Override
	protected float parentToLocalY(float y) {
		return y;
	}

	@Override
	protected float localToParentX(float x) {
		return x;
	}

	@Override
	protected float localToParentY(float y) {
		return y;
	}

	@Override
	public void layout() {
		super.layout();
		invalidLayout = false;
	}

	@Override
	public float getWidth() {
		return view.getWidth();
	}

	@Override
	public float getHeight() {
		return view.getHeight();
	}

	@Override
	public float getX() {
		return 0;
	}

	@Override
	public float getY() {
		return 0;
	}

	@Override
	public boolean isInside(float x, float y) {
		return true;
	}

	@Override
	public void paint(Canvas canvas) {
		if(invalidLayout)
			layout();
		paint.setAntiAlias(true);
		super.paint(canvas);
	}

}
