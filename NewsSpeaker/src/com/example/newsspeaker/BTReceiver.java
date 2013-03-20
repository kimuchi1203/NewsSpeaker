package com.example.newsspeaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
//cf. http://d.hatena.ne.jp/esmasui/20091003/1254553452
public class BTReceiver extends BroadcastReceiver {

	Handler handler = new Handler();

	@Override
	public void onReceive(final Context context, final Intent intent) {
		this.abortBroadcast();

		MainActivity mainActivity = (MainActivity)context;
		KeyEvent key = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
		if(key.getAction()==KeyEvent.ACTION_UP){
			switch(key.getKeyCode()){
			case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
				mainActivity.controller.changePlayPause();
				break;
			case KeyEvent.KEYCODE_MEDIA_NEXT:
				mainActivity.controller.channelMove(1);
				break;
			case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
				mainActivity.controller.channelMove(-1);
				break;
			default:
				Log.d("BTReceiver#onReceive", "KeyEvent "+key.getKeyCode());	
			}
		}
	}

	public void registerSelf(Context context) {

		IntentFilter filter = new IntentFilter();

		String act = "android.intent.action.MEDIA_BUTTON";
		filter.addAction(act);
		context.registerReceiver(BTReceiver.this, filter);
	}

	public void unregisterSelf(Context context) {

		context.unregisterReceiver(BTReceiver.this);
	}
}
