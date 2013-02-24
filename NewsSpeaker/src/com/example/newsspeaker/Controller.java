package com.example.newsspeaker;

import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;

public class Controller implements OnUtteranceCompletedListener {
	private static final int MODE_MENU = 0;
	private static final int MODE_CHANNEL = 1;
	private int currentChannelId = 0;
	private boolean isPlay = false;
	private MainActivity mainActivity;
	private int mode = MODE_MENU;

	// <- ->  : move channel
	// button : play/stop
	
	public Controller(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void channelMove(int i) {
		currentChannelId = (currentChannelId+i) % mainActivity.channelList.getCount();
		mainActivity.selectChannel(currentChannelId);
		mode = MODE_CHANNEL;
	}
	
	public void setChannel(int pos) { // for click event
		if(MODE_MENU==mode) {
			currentChannelId = pos % mainActivity.channelList.getCount();
			mainActivity.selectChannel(currentChannelId);
			mode = MODE_CHANNEL;
		}else{
			mainActivity.pauseTts();
			mainActivity.showList();
			isPlay = false;
			mode = MODE_MENU;
		}
	}

	public void changePlayPause() {
		if (isPlay) {
			mainActivity.pauseTts();
			mainActivity.showList();
			isPlay = false;
			mode = MODE_MENU;
		} else {
			mainActivity.selectChannel(currentChannelId);
			isPlay = true;
			mode = MODE_CHANNEL;
		}
	}

	@Override
	public void onUtteranceCompleted(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
