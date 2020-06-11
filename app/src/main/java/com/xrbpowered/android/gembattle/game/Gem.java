package com.xrbpowered.android.gembattle.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.xrbpowered.android.gembattle.R;
import com.xrbpowered.android.gembattle.effects.particles.ColorTween;
import com.xrbpowered.android.gembattle.effects.particles.GemParticleInfo;

import static com.xrbpowered.android.gembattle.effects.particles.GemParticleInfo.*;

import java.util.Random;

public enum Gem {
	empty(null, 0, 0, null),
	metal("Metal", 0xff99979e, R.drawable.gem_metal, metalParticle),
	fire("Fire", 0xffe14500, R.drawable.gem_fire, fireParticle),
	water("Water", 0xff006ed5, R.drawable.gem_water, waterParticle),
	earth("Earth", 0xff69b20a, R.drawable.gem_earth, earthParticle),
	air("Air", 0xff96e8ff, R.drawable.gem_air, airParticle),
	light("Light", 0xfffff7cc, R.drawable.gem_light, lightParticle),
	dark("Dark", 0xff391859, 0, null);

	public static final int bitmapMargin = 10;

	public final String name;
	public final int color;
	public final int bitmapId;
	public final GemParticleInfo particleInfo;

	public Bitmap bitmap = null;

	Gem(String name, int color, int bitmapId, GemParticleInfo particleInfo) {
		this.name = name;
		this.color = color;
		this.bitmapId = bitmapId;
		this.particleInfo = particleInfo;
	}

	public void draw(Canvas canvas, float x, float y, Paint paint) {
		if(bitmap !=null)
			canvas.drawBitmap(bitmap, x-bitmapMargin, y-bitmapMargin, paint);
		else if(color!=0) {
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(color);
			canvas.drawCircle(x + Board.gemSize/2, y + Board.gemSize/2, Board.gemSize/2, paint);
		}
	}

	public void drawBoard(Canvas canvas, float x, float y, Paint paint) {
		draw(canvas, x*Board.gemSize, y*Board.gemSize, paint);
	}

	private static final RectF r = new RectF();

	public void drawFade(Canvas canvas, float x, float y, float s, Paint paint) {
		float cx = x + Board.gemSize/2;
		float cy = y + Board.gemSize/2;
		if(bitmap !=null) {
			float size = s * (Board.gemSize + 2*bitmapMargin);
			r.set(cx - size/2, cy - size/2, cx + size/2, cy + size/2);
			canvas.drawBitmap(bitmap, null, r, paint);
		}
		else if(color!=0) {
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(color);
			canvas.drawCircle(x + Board.gemSize/2, y + Board.gemSize/2, Board.gemSize*s/2, paint);
		}
	}

	public void drawFadeBoard(Canvas canvas, float x, float y, float s, Paint paint) {
		drawFade(canvas, x*Board.gemSize, y*Board.gemSize, s, paint);
	}

	private static boolean loaded = false;

	public static boolean isLoaded() {
		return loaded;
	}

	public static void loadBitmaps(Resources resources) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inScaled = false;
		for(Gem gem : values()) {
			if(gem.bitmapId !=0) {
				gem.bitmap = BitmapFactory.decodeResource(resources, gem.bitmapId, opt);
			}
		}
		loaded = true;
	}

	public static final Gem[] newGems = {metal, fire, earth, water, air, light};

	public static Gem getRandom(Random random, Gem replaceSrc, Gem replaceDst) {
		Gem gem = getRandom(random);
		return (gem == replaceSrc) ? replaceDst : gem;
	}

	public static Gem getRandom(Random random) {
		return newGems[random.nextInt(newGems.length)];
	}
}
