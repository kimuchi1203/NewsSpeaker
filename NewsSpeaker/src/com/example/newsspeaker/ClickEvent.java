package com.example.newsspeaker;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ClickEvent implements OnItemClickListener {

	private MainActivity mainActivity;

	public ClickEvent(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		mainActivity.controller.setChannel(pos);
	}

}
