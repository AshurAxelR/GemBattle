package com.xrbpowered.android.gembattle.effects.particles;

import android.graphics.Color;

import static com.xrbpowered.android.gembattle.ui.RenderUtils.lerp;

public class ColorTween {

	private class Marker {
		float s, a, r, g, b;
	}

	private final Marker[] markers;

	public ColorTween(int[] colors, float[] s) {
		markers = new Marker[colors.length];
		for(int i=0; i<markers.length; i++) {
			Marker m = new Marker();
			m.a = Color.alpha(colors[i]) / 255f;
			m.r = Color.red(colors[i]) / 255f;
			m.g = Color.green(colors[i]) / 255f;
			m.b = Color.blue(colors[i]) / 255f;
			m.s = s==null ? (float)i/markers.length : s[i];
			markers[i] = m;
		}
	}

	public ColorTween(int... colors) {
		this(colors, null);
	}

	public int get(float s) {
		for(int i=1; i<markers.length; i++) {
			Marker m0 = markers[i-1];
			Marker m1 = markers[i];
			if(s<=m1.s) {
				s = lerp(m0.s, m1.s, s);
				return Color.argb(
					lerp(m0.a, m1.a, s),
					lerp(m0.r, m1.r, s),
					lerp(m0.g, m1.g, s),
					lerp(m0.b, m1.b, s)
				);
			}
		}
		return 0;
	}
}
