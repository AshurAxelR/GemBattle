package com.xrbpowered.android.gembattle.effects.particles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xrbpowered.android.gembattle.R;
import com.xrbpowered.android.gembattle.ui.utils.RenderUtils;

public class GemParticleInfo {

	private final int bitmapId;
	private Bitmap bitmap = null;
	public final ColorTween color;

	public boolean aligned = false;
	public float rotationSpeed = 1f;

	public GemParticleInfo(int bitmapId, ColorTween color) {
		this.bitmapId = bitmapId;
		this.color = color;
	}

	public GemParticleInfo setAligned() {
		this.aligned = true;
		return this;
	}

	public GemParticleInfo setRotationSpeed(float speed) {
		this.rotationSpeed = speed;
		return this;
	}

	public Bitmap bitmap() {
		if(bitmap==null)
			bitmap = BitmapFactory.decodeResource(RenderUtils.resources, bitmapId, RenderUtils.noScale);
		return bitmap;
	}

	public static final GemParticleInfo metalParticle = new GemParticleInfo(
			R.drawable.missile_metal,
			new ColorTween(new int[] {0xffffefd5, 0xff99979e, 0}, new float[] {0, 0.25f, 1})
		).setAligned();
	public static final GemParticleInfo fireParticle = new GemParticleInfo(
			R.drawable.missile_metal,
			new ColorTween(0xffffc000, 0xffe14500, 0)
		).setAligned();
	public static final GemParticleInfo waterParticle = new GemParticleInfo(
			R.drawable.missile_metal,
			new ColorTween(0xffc3e2ff, 0xff359dff, 0x00ffffff)
		).setAligned();
	public static final GemParticleInfo earthParticle = new GemParticleInfo(
			R.drawable.missile_metal,
			new ColorTween(0xffc2ee59, 0xff69b20a, 0x0069b20a)
		).setAligned();
	public static final GemParticleInfo airParticle = new GemParticleInfo(
			R.drawable.missile_metal,
			new ColorTween(0xfff6fdff, 0xff96e8ff, 0x00dddddd)
		).setAligned();
	public static final GemParticleInfo lightParticle = new GemParticleInfo(
			R.drawable.missile_metal,
			new ColorTween(0xffffffff, 0xfffff7cc, 0x00ffe34e)
		).setAligned();

}
