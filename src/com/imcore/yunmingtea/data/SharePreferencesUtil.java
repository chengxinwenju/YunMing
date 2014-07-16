package com.imcore.yunmingtea.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.imcore.yunmingtea.model.User;

public class SharePreferencesUtil {
	public static void putUser(User user) {
		//
	}

	public static User getUser() {
		return null;
	}

	/**
	 * 使用SharedPreferences保存用户登录信息
	 * 
	 * @param context
	 * @param username
	 * @param password
	 */
	public static void saveLoginInfo(Context context, String username,
			String password) {
		// 获取SharedPreferences对象
		SharedPreferences sp = context.getSharedPreferences("config",
				context.MODE_PRIVATE);
		// 获取Editor对象
		Editor editor = sp.edit();
		// 设置参数
		editor.putString("username", username);
		editor.putString("password", password);
		// 提交
		editor.commit();
	}
}
