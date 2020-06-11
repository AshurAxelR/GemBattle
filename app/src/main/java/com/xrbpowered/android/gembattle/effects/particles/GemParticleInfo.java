package com.xrbpowered.android.gembattle.effects.particles;

import android.graphics.Bitmap;

public class GemParticleInfo {

	public final Bitmap bitmap = null;
	public final ColorTween color;

	public GemParticleInfo(ColorTween color) {
		this.color = color;
	}

	public static final GemParticleInfo metalParticle = new GemParticleInfo(new ColorTween(new int[] {0xffffefd5, 0xff99979e, 0}, new float[] {0, 0.25f, 1}));
	public static final GemParticleInfo fireParticle = new GemParticleInfo(new ColorTween(0xffffc000, 0xffe14500, 0));
	public static final GemParticleInfo waterParticle = new GemParticleInfo(new ColorTween(0xffc3e2ff, 0xff359dff, 0x00ffffff));
	public static final GemParticleInfo earthParticle = new GemParticleInfo(new ColorTween(0xffc2ee59, 0xff69b20a, 0x0069b20a));
	public static final GemParticleInfo airParticle = new GemParticleInfo(new ColorTween(0xfff6fdff, 0xff96e8ff, 0x00dddddd));
	public static final GemParticleInfo lightParticle = new GemParticleInfo(new ColorTween(0xffffffff, 0xfffff7cc, 0x00ffe34e));

}
