package com.example.newsspeaker;

import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;

public class Channel {
	//private String name;
	//private String src;
	public ArrayAdapter<String> adapter;

	public Channel(String name, String src, MainActivity mainActivity) {
		//this.name = name;
		//this.src = src;
		this.adapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1);
		parse(src);
	}
	
	private void parse(String string) {
		try{
			XmlPullParser xmlPullParser = Xml.newPullParser();

			URL url = new URL(string);
			URLConnection connection = url.openConnection();
			xmlPullParser.setInput(connection.getInputStream(), "UTF-8");

			int eventType;
			while ((eventType = xmlPullParser.next()) != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG && "title".equals(xmlPullParser.getName())) {
					String text = xmlPullParser.nextText();
					Log.d("XmlPullParserSampleUrl", text);
					adapter.add(text);
				}
			}
		} catch (Exception e){
			Log.d("XmlPullParserSampleUrl", "Error");
		}
		adapter.notifyDataSetChanged();
	}


	public void play(TextToSpeech mTts) {
		String text;
		for(int i=0;i<this.adapter.getCount();++i){
			text = this.adapter.getItem(i);
			mTts.speak(text, TextToSpeech.QUEUE_ADD, null);
		}
	}
}
