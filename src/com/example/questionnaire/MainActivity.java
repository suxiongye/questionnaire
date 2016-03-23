package com.example.questionnaire;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 开始按钮
		Button startBtn = (Button) findViewById(R.id.startButton);
		// 判断是否插入SD卡
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) == false) {
			startBtn.setText("请插入SD卡");
		}
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 进入调查
				Intent intent = new Intent(MainActivity.this, InformationActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
		// 退出按钮
		Button exitBtn = (Button) findViewById(R.id.exitButton);
		exitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 退出程序
				MainActivity.this.finish();
			}
		});

		// 存储题库数据库地址和名称
		String DB_PATH = Environment.getDataDirectory() + "/data/com.example.questionnaire/databases/";
		String DB_NAME = "questionnaire.db";

		if ((new File(DB_PATH + DB_NAME).exists()) == false) {
			// 若不存在则生成目录
			File dir = new File(DB_PATH);
			if (!dir.exists()) {
				dir.mkdir();
			}
		}
		// 复制文件
		try {
			InputStream is = getBaseContext().getAssets().open(DB_NAME);
			OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
			byte[] buffer = new byte[1024];
			int length;

			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			os.flush();
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
