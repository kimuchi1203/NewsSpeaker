package com.example.newsspeaker;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ClickEvent implements OnItemClickListener {

	private MainActivity parent;

	public ClickEvent(MainActivity mainActivity) {
		parent = mainActivity;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		parent.currentChannelId = pos;
		parent.selectChannel();
	}

}
