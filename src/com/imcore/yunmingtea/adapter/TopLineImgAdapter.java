package com.imcore.yunmingtea.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 头条图片适配器
 * 
 * @author Administrator
 * 
 */
public class TopLineImgAdapter extends PagerAdapter {
	// private ImageFetcher mImageFetcher;
	private Context context;
	private List<ImageView> mList;

	public TopLineImgAdapter(Context context, List<ImageView> list) {
		super();
		this.mList = list;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	// 是否是同一张图片
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {
		view.removeView(mList.get(position));

	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		view.addView(mList.get(position));
		return mList.get(position);
	}

}
