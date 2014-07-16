package com.imcore.yunmingtea.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.imcore.application.YunmingApplication;
import com.imcore.yunming.model.ToplineImg;
import com.imcore.yunmingtea.R;
import com.imcore.yunmingtea.http.HttpHelper;
import com.imcore.yunmingtea.http.HttpMethod;
import com.imcore.yunmingtea.http.RequestEntity;
import com.imcore.yunmingtea.http.ResponseJsonEntity;
import com.imcore.yunmingtea.image.ImageFetcher;
import com.imcore.yunmingtea.util.ConnectivityUtil;
import com.imcore.yunmingtea.util.JsonUtil;
import com.imcore.yunmingtea.util.ToastUtil;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟3秒

	private List<ToplineImg> mTopImgList;
	private List<ImageView> imgList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		View view = findViewById(R.id.loading_page);

		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.anim_alpha_load);
		view.startAnimation(animation);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(SplashActivity.this,
						LoginActivity.class);
				startActivity(mainIntent);
				finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
		// 调用图片初始化下载的方法
		initialize();
	}

	/**
	 * 首页图片初始化下载
	 */

	private void initialize() {

		if (ConnectivityUtil.isOnline(this)) {

			new LoadImgTask().execute();

		} else {
			ToastUtil.showToast(this, "请先连接网络！");
			return;
		}
	}

	/**
	 * 图片异步加载任务类
	 * 
	 * @author Administrator
	 * 
	 */
	private class LoadImgTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... Params) {// 执行下载头条图片的耗时操作
			// 头条图片接口路径
			String url = "/topline/list.do";
			// 包装头条图片请求实体
			RequestEntity imgEntity = new RequestEntity(url, HttpMethod.GET,
					null);
			// 利用网络帮助类发送实体,服务器返回图片数据结果
			// 并以String类型的参数传递给onPostExecute方法
			String jsonRespone = null;
			try {
				jsonRespone = new HttpHelper().execute(imgEntity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonRespone;

		}

		@Override
		protected void onPostExecute(String result) {// 得到服务器返回的数据，更新UI

			// 解析图片实体JSON数据 ，并更新UI
			ResponseJsonEntity imgResEntity = ResponseJsonEntity
					.fromJSON(result);
			if (imgResEntity.getStatus() == 200) {
				String jsonData = imgResEntity.getData();
				mTopImgList = JsonUtil.toObjectList(jsonData, ToplineImg.class);
				YunmingApplication.getImgUrlList().clear();
				for (ToplineImg topImg : mTopImgList) {
					fetchImage(topImg);

				}
			}

			super.onPostExecute(result);
		}

		/**
		 * 图片下载功能子线程
		 * 
		 * @param img
		 */
		private void fetchImage(final ToplineImg img) {

			new Thread(new Runnable() {

				@Override
				public void run() {

					// 拼接图片路径
					String TopimgUrl = HttpHelper.IMAGE_URL + img.imageUrl;
					ImageView mImageView = new ImageView(SplashActivity.this);
					mImageView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					new ImageFetcher().fetch(TopimgUrl, mImageView);
					// 将获得的图片添加到应用中
					// YunmingApplication ymApp = (YunmingApplication)
					// getApplication();
					imgList = YunmingApplication.getImgUrlList();
					imgList.add(mImageView);

				}
			}).start();
		}

	}
}
