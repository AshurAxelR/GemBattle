package com.xrbpowered.android.zoomui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BaseView extends View {

	protected final BaseContainer container;

	public BaseView(Context context) {
		super(context);
		container = createContainer();
	}

	public BaseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		container = createContainer();
	}

	public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		container = createContainer();
	}

	protected BaseContainer createContainer() {
		return new BaseContainer(this);
	}

	public BaseContainer getContainer() {
		return container;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getActionIndex()==0)
			container.handleTouchAction(event.getAction(), event.getX(), event.getY());
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		container.paint(canvas);
	}
}
