package com.imcore.yunmingtea.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;

import com.imcore.yunmingtea.R;

public class MallActivity extends Activity {
	private ExpandableListView mExpandableListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall);
		mExpandableListView = (ExpandableListView) findViewById(R.id.mall_expandable_lv);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mall, menu);
		return true;
	}

}
