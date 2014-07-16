package com.imcore.yunmingtea.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.imcore.yunmingtea.R;
import com.imcore.yunmingtea.data.SharePreferencesUtil;
import com.imcore.yunmingtea.http.HttpHelper;
import com.imcore.yunmingtea.http.RequestEntity;
import com.imcore.yunmingtea.http.ResponseJsonEntity;
import com.imcore.yunmingtea.model.User;
import com.imcore.yunmingtea.util.ConnectivityUtil;
import com.imcore.yunmingtea.util.JsonUtil;
import com.imcore.yunmingtea.util.TextUtil;
import com.imcore.yunmingtea.util.ToastUtil;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText etUserName;
	private EditText etPassword;

	private Button btnRegist;
	private Button btnLogin;

	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etUserName = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		btnRegist = (Button) findViewById(R.id.btn_register);
		btnLogin = (Button) findViewById(R.id.btn_login);

		SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
		if (sp != null) {
			String username = sp.getString("username", "");
			String password = sp.getString("password", "");
			etUserName.setText(username);
			etPassword.setText(password);
		}

		btnRegist.setOnClickListener(this);
		btnLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			// register();
			break;
		case R.id.btn_login:
			doLogin();
			break;
		}

	}

	// 登录到Home页面
	private void doLogin() {
		String inputUserName = etUserName.getText().toString();
		String inputPassword = etPassword.getText().toString();

		if (!ConnectivityUtil.isOnline(this)) {
			ToastUtil.showToast(LoginActivity.this, "没有网络连接");
		}
		// 判断登录用户名和密码是否合法
		if (!validateInput(inputUserName, inputPassword)) {
			return;
		}
		// 通过AsyncTask异步请求网络服务,通过构造函数传递参数数据
		new LoginTask(inputUserName, inputPassword).execute();

	}

	// 判断输入的用户名和密码是否合法
	private boolean validateInput(String userName, String pwd) {
		if (TextUtil.isEmptyString(userName)) {
			ToastUtil.showToast(this, R.string.phone_null);
			etUserName.requestFocus();
			return false;
		} else if (!TextUtil.isNumber(userName)) {
			ToastUtil.showToast(this, R.string.phone_wrong);
			etUserName.requestFocus();
			return false;
		} else if (!TextUtil.isPhoneNumber(userName)) {
			ToastUtil.showToast(this, R.string.phone_wrong);
			etUserName.requestFocus();
			return false;
		} else if (TextUtil.isEmptyString(pwd)) {
			ToastUtil.showToast(this, R.string.pw_wrong);
			etPassword.requestFocus();
			return false;
		}
		return true;
	}

	class LoginTask extends AsyncTask<Void, Void, String> {

		private String mUsetName;
		private String mPassword;

		public LoginTask(String userName, String password) {
			mUsetName = userName;
			mPassword = password;
		}

		@Override
		protected void onPreExecute() {
			// 显示登陆通知等待
			dialog = new ProgressDialog(LoginActivity.this);
			dialog.setTitle("正在登陆");
			dialog.setIcon(R.drawable.mine_credit);
			dialog.setMessage("数据加载中…");
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String url = "/passport/login.do";
			// 把请求参数装到Map中
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("phoneNumber", mUsetName);
			args.put("password", mPassword);
			args.put("client", "android");

			// 构造RequestEntity参数(请求实体)
			RequestEntity entity = new RequestEntity(url, args);

			String jsonResponse = null;
			try {
				jsonResponse = HttpHelper.execute(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonResponse;
		}

		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			dialog = null;
			// 响应回来之后构建ResponseEntity(响应实体)
			if (TextUtil.isEmptyString(result)) {
				ToastUtil.showToast(LoginActivity.this, "输入为空");
				return;
			}
			ResponseJsonEntity resEntity = ResponseJsonEntity.fromJSON(result);

			if (resEntity.getStatus() == 200) {
				String jsonData = resEntity.getData();
				Log.i("user", jsonData);
				// String id = JsonUtil.getJsonValueByKey(jsonData, "id");
				// String token = JsonUtil.getJsonValueByKey(jsonData, "token");

				User user = JsonUtil.toObject(jsonData, User.class);
				String id = user.id;
				String token = user.token;
				// 在SharePreferencesUtil中保存
				String phoneNum = JsonUtil.getJsonValueByKey(jsonData,
						"phoneNumber");
				String psw = etPassword.getText().toString();// 从文本框输入得到密码
				SharePreferencesUtil sp = new SharePreferencesUtil();
				sp.saveLoginInfo(LoginActivity.this, phoneNum, psw);

				// 跳转到首页
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
				super.onPostExecute(result);
			} else {
				ToastUtil.showToast(LoginActivity.this, "您的输入有误,请重新输入");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
}
