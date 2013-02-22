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

	private BTReceiver _receiver;

	private boolean ttsReady;
	private TextToSpeech mTts;
	private ArrayAdapter<String> channelList;
	private ArrayList<Channel> channels;
	public int currentChannelId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_receiver = new BTReceiver();
		_receiver.registerSelf(this);
		
		ttsReady = false;
		mTts = new TextToSpeech( this, this );

		channelList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		channels = new ArrayList<Channel>();
		channels.add(new XMLChannel("Reuters", "http://feeds.reuters.com/reuters/JPTopNews", this));
		channelList.add("reuters");
		channels.add(new XMLChannel("Google News", "http://news.google.com/news?hl=ja&ned=us&ie=UTF-8&oe=UTF-8&output=rss", this));
		channelList.add("Google News");
		channels.add(new PlainChannel("Reuters", "http://citrus-unshiu.appspot.com/plain?stream_name=reuters", this));
		channelList.add("Reuters plain");
		currentChannelId = 0;
        
		((ListView) findViewById(R.id.listView1)).setOnItemClickListener(new ClickEvent(this));
		showList();
	}

	@Override
	public void onDestroy() {
		if ( mTts != null ) {
			ttsReady = false;
			mTts.stop();
			mTts.shutdown();
		}
		_receiver.unregisterSelf(this);
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
			int result = mTts.setLanguage( Locale.JAPAN );
			if ( result == TextToSpeech.LANG_MISSING_DATA ||
					result == TextToSpeech.LANG_NOT_SUPPORTED ) {
				// locale not support
				Log.e("MainActivity#onInit", "set lang error with "+result);
			}
			else {
				// OK
				ttsReady = true;
				Log.d("MainActivity#onInit", "ttsReady = "+ttsReady);
				mTts.speak("€”õŠ®—¹‚µ‚Ü‚µ‚½", TextToSpeech.QUEUE_FLUSH, null);
			}
		}
		else {
			// error init
			Log.e("MainActivity#onInit", "init error with "+status);
		}
	}
	
	private void showList() {
		ListView lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(channelList);
	}
	
	public void zapping(int i) {
		showList();
		Log.d("MainActivity#zapping", "currentChannelId "+currentChannelId+" i "+i+" -> "+(currentChannelId+i)%channelList.getCount());
		currentChannelId = (currentChannelId+i)%channelList.getCount();
		if(currentChannelId<0) {
			currentChannelId = channelList.getCount() + currentChannelId;
		}
		if(ttsReady) mTts.speak(channelList.getItem(currentChannelId), TextToSpeech.QUEUE_FLUSH, null);
		Log.d("MainActivity#zapping", channelList.getItem(currentChannelId));
	}
	
	public void selectChannel() {
		ListView lv = (ListView) findViewById(R.id.listView1);
		Channel channel = channels.get(currentChannelId);
        lv.setAdapter(channel.adapter);
        if(ttsReady) channel.play(mTts);
	}
}
