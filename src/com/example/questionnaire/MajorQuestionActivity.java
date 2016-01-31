package com.example.questionnaire;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MajorQuestionActivity extends Activity {
	// 控件声明
	private TextView tv_label;
	private TextView tv_content;
	private RadioGroup scoreRadioGroup;
	private RadioButton[] scoreRadios;
	private Button btn_next;
	private Button btn_pre;

	// 当前页数;
	private int current;
	// 题目总数
	private int count;

	// 题目
	private List<MajorQuestion> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_majorquestion);

		// 绑定控件
		tv_label = (TextView) findViewById(R.id.majorQuestionLabel);
		tv_content = (TextView) findViewById(R.id.majorQuestionContentTextView);
		scoreRadioGroup = (RadioGroup) findViewById(R.id.majorChooseRadioGroup);
		scoreRadios = new RadioButton[11];
		scoreRadios[1] = (RadioButton) findViewById(R.id.majorChooseRadio1);
		scoreRadios[2] = (RadioButton) findViewById(R.id.majorChooseRadio2);
		scoreRadios[3] = (RadioButton) findViewById(R.id.majorChooseRadio3);
		scoreRadios[4] = (RadioButton) findViewById(R.id.majorChooseRadio4);
		scoreRadios[5] = (RadioButton) findViewById(R.id.majorChooseRadio5);
		scoreRadios[6] = (RadioButton) findViewById(R.id.majorChooseRadio6);
		scoreRadios[7] = (RadioButton) findViewById(R.id.majorChooseRadio7);
		scoreRadios[8] = (RadioButton) findViewById(R.id.majorChooseRadio8);
		scoreRadios[9] = (RadioButton) findViewById(R.id.majorChooseRadio9);
		scoreRadios[10] = (RadioButton) findViewById(R.id.majorChooseRadio10);
		btn_next = (Button) findViewById(R.id.majorBtnNext);
		btn_pre = (Button) findViewById(R.id.majorBtnPre);

		// 读取题目
		DBService dbService = new DBService();
		list = dbService.getMajorQuestions();

		// 设置当前题目信息
		current = 0;
		count = list.size();

		tv_label.setText(list.get(0).label);
		tv_content.setText(list.get(0).question);

		// 设置上下翻页
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 如果不是最后一页
				if (current < count - 1) {
					current++;
					tv_label.setText(list.get(current).label);
					tv_content.setText(list.get(current).question);
				}
				else{
					//如果是最后一页则跳转
					Intent intent = new Intent(MajorQuestionActivity.this, SubQuestionActivity.class);
					startActivity(intent);
				}
			}
		});
		
		btn_pre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//如果不是第一页
				if(current > 0){
					current--;
					tv_label.setText(list.get(current).label);
					tv_content.setText(list.get(current).question);
				}
			}
		});
	}
}
