package com.imcore.yunmingtea.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

import com.imcore.yunmingtea.R;

public class MainActivity extends TabActivity implements
		OnCheckedChangeListener {

	private TabHost mTabHost;
	private TabWidget mTabWidget;
	private RadioGroup mRadioGroup;
	private RadioButton radioHome, radioMall, radioScan, radioMine, radioMore;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTabHost = getTabHost();
		mTabHost.setup(getLocalActivityManager());
		mTabWidget = mTabHost.getTabWidget();

		radioHome = (RadioButton) findViewById(R.id.home_radiobutton_home);
		radioMall = (RadioButton) findViewById(R.id.home_radiobutton_mall);
		radioScan = (RadioButton) findViewById(R.id.home_radiobutton_scan);
		radioMine = (RadioButton) findViewById(R.id.home_radiobutton_mine);
		radioMore = (RadioButton) findViewById(R.id.home_radiobutton_more);

		mRadioGroup = (RadioGroup) findViewById(R.id.home_radiogroup);

		TabSpec mTabSpec1 = mTabHost.newTabSpec("HOME").setIndicator("home");
		mTabSpec1.setContent(new Intent(this, HomeActivity.class));
		mTabHost.addTab(mTabSpec1);

		TabSpec mTabSpec2 = mTabHost.newTabSpec("MALL").setIndicator("mall");
		mTabSpec2.setContent(new Intent(this, MallActivity.class));
		mTabHost.addTab(mTabSpec2);

		TabSpec mTabSpec3 = mTabHost.newTabSpec("SCAN").setIndicator("scan");
		mTabSpec3.setContent(new Intent(this, ScanActivity.class));
		mTabHost.addTab(mTabSpec3);

		TabSpec mTabSpec4 = mTabHost.newTabSpec("MINE").setIndicator("mine");
		mTabSpec4.setContent(new Intent(this, MineActivity.class));
		mTabHost.addTab(mTabSpec4);

		TabSpec mTabSpec5 = mTabHost.newTabSpec("MORE").setIndicator("more");
		mTabSpec5.setContent(new Intent(this, MoreActivity.class));
		mTabHost.addTab(mTabSpec5);

		mRadioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.home_radiobutton_home:
			mTabHost.setCurrentTabByTag("HOME");
			Log.i("aa", "home");
			break;
		case R.id.home_radiobutton_mall:
			mTabHost.setCurrentTabByTag("MALL");
			Log.i("aa", "mall");
			break;
		case R.id.home_radiobutton_scan:
			mTabHost.setCurrentTabByTag("SCAN");
			Log.i("aa", "scan");
			break;
		case R.id.home_radiobutton_mine:
			mTabHost.setCurrentTabByTag("MINE");
			Log.i("aa", "mine");
			break;
		case R.id.home_radiobutton_more:
			mTabHost.setCurrentTabByTag("MORE");
			Log.i("aa", "more");
			break;
		}
	}
/**
 * 在TabActivity中不能用到按两次退出程序
 */
//	/**
//	 * 按两次返回键退出
//	 */
//	private static Boolean isExit = false;
//	private static Boolean hasTask = false;
//	Timer tExit = new Timer();
//	TimerTask task = new TimerTask() {
//
//		@Override
//		public void run() {
//			isExit = false;
//			hasTask = true;
//		}
//	};
//
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			// System.out.println("user back down");
//			if (isExit == false) {
//				isExit = true;
//				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//				if (!hasTask) {
//					tExit.schedule(task, 2000);
//				}
//			} else {
//				// finish();
//				System.exit(0);
//			}
//		}
//		return false;
//	}
}
