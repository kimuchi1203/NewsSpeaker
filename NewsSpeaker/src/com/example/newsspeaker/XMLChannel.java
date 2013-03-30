package com.example.newsspeaker;

import java.net.URL;
import java.net.URLConnection;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;
import android.widget.ArrayAdapter;

public class XMLChannel extends Channel {
	public XMLChannel(String name, final String src, MainActivity mainActivity) {
		this.adapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1);
		new Thread(new Runnable(){
		@Override
		public void run() {
			parse(src);
		}
		}).start();
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
			e.printStackTrace();
			Log.d("XmlPullParserSampleUrl", "Error");
		}
		adapter.notifyDataSetChanged();
	}
}
