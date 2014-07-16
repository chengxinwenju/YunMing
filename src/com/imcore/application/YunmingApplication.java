package com.imcore.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.widget.ImageView;
/**
 * 
 * @author Administrator
 *
 */
public class YunmingApplication extends Application {
	private static List<ImageView> mList=new ArrayList<ImageView>();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	public static List<ImageView> getImgUrlList(){
		return mList;
	}
}
