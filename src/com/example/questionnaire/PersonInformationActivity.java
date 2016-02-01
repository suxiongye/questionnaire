package com.example.questionnaire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PersonInformationActivity extends Activity {
	//GUI控件声明
	private Button submitBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personinfomation);
		submitBtn = (Button)findViewById(R.id.submitBtn);
		//设置提交之后返回主页面
		submitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonInformationActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
