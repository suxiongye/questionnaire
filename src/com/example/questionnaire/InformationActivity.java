package com.example.questionnaire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InformationActivity extends Activity {
	//GUI控件
	private static Button infoBtn; 
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_information);
	        //进入下一个调查界面
	        infoBtn = (Button)findViewById(R.id.informationBtn);
	        infoBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(InformationActivity.this, MajorQuestionActivity.class);
					startActivity(intent);
				}
			});
	  }
}
