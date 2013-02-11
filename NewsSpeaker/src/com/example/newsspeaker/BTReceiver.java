package com.example.newsspeaker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.KeyEvent;
//cf. http://d.hatena.ne.jp/esmasui/20091003/1254553452
public class BTReceiver extends BroadcastReceiver {

	Handler handler = new Handler();

	@Override
	public void onReceive(final Context context, final Intent intent) {
		this.abortBroadcast();
		
		MainActivity mainActivity = (MainActivity)context;
		KeyEvent key = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
		switch(key.getKeyCode()){
		case 85:
			mainActivity.selectChannel();
			break;
		case 87:
			mainActivity.zapping(-1);
			break;
		case 88:
			mainActivity.zapping(1);
			break;
		}
		/*
		handler.post(new Runnable() {
			public void run() {
				KeyEvent key = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
				Toast.makeText(context, intent.getAction()+" "+key.getAction()+" "+key.getKeyCode(), Toast.LENGTH_SHORT)
						.show();
			}
		});
		*/
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
