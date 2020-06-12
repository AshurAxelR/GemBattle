package com.xrbpowered.android.gembattle.ui;

import android.graphics.Canvas;

import com.xrbpowered.android.gembattle.ui.utils.GlassPaint;
import com.xrbpowered.android.zoomui.UIContainer;

public class GlassButton extends TapButton {

	public static final int defaultHeight = 80;
	public static final int defaultColor = 0xff55ddff;
	public static final int disabledColor = 0xffdddddd;

	private static final float[] shades = {1f, 0.8f, 1f};
	private static final int normal = 0;
	private static final int pressed = 1;
	private static final int disabled = 2;

	public String label;

	private final GlassPaint glass;

	public GlassButton(UIContainer parent, String label, int color, int height) {
		super(parent);
		glass = new GlassPaint(this, height, new int[] {color, color, disabledColor}, shades);
		setSize(0, height);
		this.label = label;
	}

	public GlassButton(UIContainer parent, String label, int color) {
		this(parent, label, color, defaultHeight);
	}

	public GlassButton(UIContainer parent, String label) {
		this(parent, label, defaultColor, defaultHeight);
	}

	@Override
	public void setSize(float width, float height) {
		glass.setHeight(height);
		super.setSize(width, height);
	}

	public String getLabel() {
		return label;
	}

	@Override
	public void paint(Canvas canvas) {
		glass.paintGradient(canvas, !isEnabled() ? disabled : down ? pressed : normal);
		glass.paintGlass(canvas);
		glass.paintBorder(canvas, 0xffe4d9ad);
		glass.paintText(canvas, getLabel(), 0xffffffff, 40);
	}
}
