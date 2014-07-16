package com.imcore.yunmingtea.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.imcore.yunmingtea.R;

public class MineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine, menu);
		return true;
	}

}
