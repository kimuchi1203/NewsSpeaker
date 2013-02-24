package com.example.newsspeaker;

import java.util.ArrayList;
import java.util.Locale;

import com.example.newsspeaker.BTReceiver;

import android.os.Bundle;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

	private BTReceiver receiver;
	public Controller controller;

	private boolean ttsReady;
	private TextToSpeech tts;
	public ArrayAdapter<String> channelList;
	private ArrayList<Channel> channels;
	public int currentChannelId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		receiver = new BTReceiver();
		receiver.registerSelf(this);
		
		ttsReady = false;
		tts = new TextToSpeech( this, this );
		// cf. http://www.techdoctranslator.com/resources/articles/articles-index/tts

		channelList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		channels = new ArrayList<Channel>();
		channels.add(new XMLChannel("Reuters", "http://feeds.reuters.com/reuters/JPTopNews", this));
		channelList.add("reuters");
		channels.add(new XMLChannel("Google News", "http://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss", this));
		channelList.add("Google News");
		channels.add(new PlainChannel("Reuters", "http://citrus-unshiu.appspot.com/plain?stream_name=reuters", this));
		channelList.add("Reuters plain");
		currentChannelId = 0;
        
		controller = new Controller(this);
		tts.setOnUtteranceCompletedListener(controller);
		((ListView) findViewById(R.id.listView1)).setOnItemClickListener(new ClickEvent(this));
		showList();
	}

	@Override
	public void onDestroy() {
		if ( tts != null ) {
			ttsReady = false;
			tts.stop();
			tts.shutdown();
		}
		receiver.unregisterSelf(this);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onInit(int status) {
		if ( status == TextToSpeech.SUCCESS ) {
			int result = tts.setLanguage( Locale.JAPAN );
			if ( result == TextToSpeech.LANG_MISSING_DATA ||
					result == TextToSpeech.LANG_NOT_SUPPORTED ) {
				// locale not support
				Log.e("MainActivity#onInit", "set lang error with "+result);
			}
			else {
				// OK
				ttsReady = true;
				Log.d("MainActivity#onInit", "ttsReady = "+ttsReady);
				tts.speak("準備完了しました", TextToSpeech.QUEUE_FLUSH, null);
			}
		}
		else {
			// error init
			Log.e("MainActivity#onInit", "init error with "+status);
		}
	}
	
	public void showList() {
		ListView lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(channelList);
	}

	public void selectChannel(int channelId) {
		ListView lv = (ListView) findViewById(R.id.listView1);
		Channel channel = channels.get(channelId);
        lv.setAdapter(channel.adapter);
        if(ttsReady) channel.play(tts);
	}

	public void pauseTts() {
		if(ttsReady) tts.stop();
	}

}
