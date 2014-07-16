package com.imcore.yunmingtea.ui;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imcore.application.YunmingApplication;
import com.imcore.yunmingtea.R;
import com.imcore.yunmingtea.adapter.TopLineImgAdapter;

public class HomeActivity extends Activity {

	private ViewPager mTopImgViewPager;// 头条图片视图分页控件
	private List<ImageView> mList;
	private ViewGroup mGroup;
	private ImageView[] mPoints;// 连续点
	private TopLineImgAdapter mImgAdapter;

	private Button btnContactStore;
	private Button btnTeaInfo;
	private Button btnTopSellers;
	private Button btnNewArrival;
	private int oldPosition = 0;// 记录上一次点的位置
	private int currentItem; // 当前页面
	private ScheduledExecutorService scheduledExecutorService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mGroup = (ViewGroup) findViewById(R.id.view_group);
		mTopImgViewPager = (ViewPager) findViewById(R.id.vp_home_topImg);
		if (YunmingApplication.getImgUrlList() != null) {
			mList = YunmingApplication.getImgUrlList();

		}

		// 实例化头条图片数据适配器
		if (mImgAdapter == null) {
			mImgAdapter = new TopLineImgAdapter(HomeActivity.this, mList);
			mTopImgViewPager.setAdapter(mImgAdapter);
		} else {
			mImgAdapter.notifyDataSetChanged();
		}
		mTopImgViewPager.setOnPageChangeListener(pageChangeListener);
		// 页面连续点调用
		animationPoints();
	}

	@Override
	public void onStop() {
		for (View v : mList) {
			ViewGroup p = (ViewGroup) v.getParent();
			if (p != null) {
				p.removeAllViewsInLayout();
			}
		}
		super.onStop();
	}

	/**
	 * 图片变化监听
	 */
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			mTopImgViewPager.setCurrentItem(position);
			setImageBackground(position);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int status) {

		}

	};

	/**
	 * 滑动连续点指示
	 */
	private void animationPoints() {

		mPoints = new ImageView[mList.size()];
		for (int i = 0; i < mPoints.length; i++) {
			ImageView iv = new ImageView(HomeActivity.this);
			iv.setLayoutParams(new LayoutParams(10, 10));
			mPoints[i] = iv;
			if (i == 0) {
				mPoints[i].setBackgroundResource(R.drawable.circle_white);
			} else {
				mPoints[i].setBackgroundResource(R.drawable.circle_gray);
			}

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			mGroup.addView(iv, layoutParams);
		}

	}

	/**
	 * 设置连续点背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < mPoints.length; i++) {
			if (i == selectItems) {
				mPoints[i].setBackgroundResource(R.drawable.circle_white);
			} else {
				mPoints[i].setBackgroundResource(R.drawable.circle_gray);
			}
		}
	}

	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 每隔2秒钟切换一张图片
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 2,
				2, TimeUnit.SECONDS);
		super.onStart();

	}

	/**
	 * 
	 * 切换图片异步任务
	 */

	private class ViewPagerTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			currentItem = (currentItem + 1) % mList.size();
			// 更新界面
			// handler.sendEmptyMessage(0);
			handler.obtainMessage().sendToTarget();
		}

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// 设置当前页面
			mTopImgViewPager.setCurrentItem(currentItem);
		}

	};
}