package com.xrbpowered.android.zoomui;

import android.os.Handler;
import android.os.Message;
import android.view.View;

public class RepaintHandler extends Handler {

	public final View view;
	public int frameTime;

	public RepaintHandler(View view, int frameTime) {
		this.view = view;
		setFrameTime(frameTime);
	}

	public void setFrameTime(int frameTime) {
		this.frameTime = frameTime;
		requestNext();
	}

	@Override
	public void handleMessage(Message msg) {
		view.invalidate();
		requestNext();
	}

	public void requestNext() {
		removeMessages(0);
		if(frameTime>0)
			sendEmptyMessageDelayed(0, frameTime);
	}
}
