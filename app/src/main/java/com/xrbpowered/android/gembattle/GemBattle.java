package com.xrbpowered.android.gembattle;

import android.content.res.Resources;
import android.graphics.Typeface;

import com.xrbpowered.android.gembattle.effects.EffectSet;
import com.xrbpowered.android.gembattle.ui.GamePane;
import com.xrbpowered.android.gembattle.ui.PopupMessageFloat;
import com.xrbpowered.android.zoomui.RepaintHandler;

public class GemBattle {

	public static Resources resources;
	public static Typeface boldFont;

	public static float time = 0f;
	public static GamePane gamePane;
	public static PopupMessageFloat popupMessageFloat;

	public static EffectSet particles;
	public static EffectSet attackEffects;

}
