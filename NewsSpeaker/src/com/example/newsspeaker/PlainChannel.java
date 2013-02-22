package com.example.newsspeaker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.widget.ArrayAdapter;

public class PlainChannel extends Channel {

	public PlainChannel(String name, String src, MainActivity mainActivity) {
		this.adapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1);
		parse(src);
	}
	
	private void parse(String string) {
		URL url;
		try {
			url = new URL(string);
			URLConnection connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String str;
			while((str = br.readLine()) != null){
				adapter.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		adapter.notifyDataSetChanged();
	}
}
