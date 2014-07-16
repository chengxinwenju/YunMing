package com.imcore.yunmingtea.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.imcore.yunming.scan.MipcaActivityCapture;
import com.imcore.yunmingtea.R;

public class ScanActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	/**
	 * 显示扫描结果
	 */
	private TextView mTextView;

	/**
	 * 显示扫描拍的图片
	 */
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		Button btnScan = (Button) findViewById(R.id.zbar_button);
		mTextView = (EditText) findViewById(R.id.zbar_edit);
		mImageView = (ImageView) findViewById(R.id.img_iv_test);

		btnScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ScanActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);

			}
		});
	}

	// 显示结果s
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String str = bundle.getString("result");
				// 显示扫描到的内容
				mTextView.setText(str);
				// 显示
				mImageView.setImageBitmap((Bitmap) data
						.getParcelableExtra("bitmap"));
				// /
				// 前往扫描到的Url
				Uri content_url = Uri.parse(str);
				final Intent intent = new Intent("android.intent.action.VIEW");
				intent.setData(content_url);
				new AlertDialog.Builder(ScanActivity.this)
						.setTitle("扫码完成")
						.setMessage("是否前往" + content_url + "吗?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										startActivity(intent);

									}
								})
						.setNegativeButton(" 取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// 不要退出

									}
								}).show();
			}
			break;
		}
	}
}
