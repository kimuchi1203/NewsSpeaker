package com.example.newsspeaker;

import android.speech.tts.TextToSpeech;
import android.widget.ArrayAdapter;

public class Channel {
	public ArrayAdapter<String> adapter;
	public void play(TextToSpeech mTts) {
		String text;
		for(int i=0;i<this.adapter.getCount();++i){
			text = this.adapter.getItem(i);
			mTts.speak(text, TextToSpeech.QUEUE_ADD, null);
		}
	}
}
