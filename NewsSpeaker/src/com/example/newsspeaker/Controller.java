package com.example.newsspeaker;

import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;

public class Controller implements OnUtteranceCompletedListener {
	private int currentChannelId = 0;
	private boolean isPlay = false;
	private MainActivity mainActivity;

	// <- ->  : move channel
	// button : play/stop
	
	public Controller(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void channelMove(int i) {
		currentChannelId = (currentChannelId+i) % mainActivity.channelList.getCount();
		mainActivity.selectChannel(currentChannelId);
	}
	
	public void setChannel(int pos) { // for click event
		currentChannelId = pos % mainActivity.channelList.getCount();
		mainActivity.selectChannel(currentChannelId);
	}

	public void changePlayPause() {
		if (isPlay) {
			mainActivity.pauseTts();
			mainActivity.showList();
			isPlay = false;
		} else {
			mainActivity.selectChannel(currentChannelId);
			isPlay = true;
		}
	}

	@Override
	public void onUtteranceCompleted(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
