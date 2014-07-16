package com.imcore.yunmingtea.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast弹出提示框工具类，用于简化Toast提示代码
 * 
 * @author Li Bin
 */
public class ToastUtil {

	/**
	 * 默认方式显示Toast
	 * 
	 * @param context
	 *            指定的Context实例
	 * @param message
	 *            要经过Toast显示的文本信息
	 */
	public static void showToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 默认方式显示Toast
	 * 
	 * @param context
	 *            指定的Context实例
	 * @param resId
	 *            要经过Toast显示的文本信息字符串资源
	 */
	public static void showToast(Context context, int resId) {
		Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/**
	 * 显示Toast
	 * 
	 * @param context
	 *            指定的Context实例
	 * @param resId
	 *            要经过Toast显示的文本信息字符串资源
	 * @param time
	 *            设置Toast停留的时时间
	 */
	public static void showToast(Context context, int resId, int time) {
		Toast toast = Toast.makeText(context, resId, time);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}