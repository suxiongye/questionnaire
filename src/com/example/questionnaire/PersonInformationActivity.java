package com.example.questionnaire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class PersonInformationActivity extends Activity {
	// GUI控件声明
	private Button submitBtn;
	private static RadioButton[] incomeRadios;
	private static RadioButton[] ageRadios;
	private static RadioButton[] carRadios;
	private static RadioButton[] purposeRadios;
	private static EditText favoriteLineEditText;

	// 存储功能
	private static String[] infoList;
	private static QuestionFile questionFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personinfomation);

		// 存储选项
		infoList = new String[5];

		// 绑定GUI
		submitBtn = (Button) findViewById(R.id.submitBtn);
		incomeRadios = new RadioButton[5];
		incomeRadios[0] = (RadioButton) findViewById(R.id.incomeRadio1);
		incomeRadios[1] = (RadioButton) findViewById(R.id.incomeRadio2);
		incomeRadios[2] = (RadioButton) findViewById(R.id.incomeRadio3);
		incomeRadios[3] = (RadioButton) findViewById(R.id.incomeRadio4);
		incomeRadios[4] = (RadioButton) findViewById(R.id.incomeRadio5);
		ageRadios = new RadioButton[7];
		ageRadios[0] = (RadioButton) findViewById(R.id.ageRadio1);
		ageRadios[1] = (RadioButton) findViewById(R.id.ageRadio2);
		ageRadios[2] = (RadioButton) findViewById(R.id.ageRadio3);
		ageRadios[3] = (RadioButton) findViewById(R.id.ageRadio4);
		ageRadios[4] = (RadioButton) findViewById(R.id.ageRadio5);
		ageRadios[5] = (RadioButton) findViewById(R.id.ageRadio6);
		ageRadios[6] = (RadioButton) findViewById(R.id.ageRadio7);
		carRadios = new RadioButton[2];
		carRadios[0] = (RadioButton) findViewById(R.id.carYesRadio);
		carRadios[1] = (RadioButton) findViewById(R.id.carNoRadio);
		purposeRadios = new RadioButton[7];
		purposeRadios[0] = (RadioButton) findViewById(R.id.purposeRadio1);
		purposeRadios[1] = (RadioButton) findViewById(R.id.purposeRadio2);
		purposeRadios[2] = (RadioButton) findViewById(R.id.purposeRadio3);
		purposeRadios[3] = (RadioButton) findViewById(R.id.purposeRadio4);
		purposeRadios[4] = (RadioButton) findViewById(R.id.purposeRadio5);
		purposeRadios[5] = (RadioButton) findViewById(R.id.purposeRadio6);
		purposeRadios[6] = (RadioButton) findViewById(R.id.purposeRadio7);
		favoriteLineEditText = (EditText) findViewById(R.id.favoriteLineEditText);

		// 获取题目对象
		questionFile = (QuestionFile) getIntent().getSerializableExtra(
				"questionFile");

		// 设置提交之后返回主页面
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (checkIfComplete() == true) {
					questionFile.savePersonInfo(infoList);
					Intent intent = new Intent(PersonInformationActivity.this,
							MainActivity.class);
					startActivity(intent);
					PersonInformationActivity.this.finish();
				}
			}
		});
	}

	// 存储并查看信息是否齐全
	private static boolean checkIfComplete() {
		boolean completed = true;
		if (incomeRadios[0].isChecked() == false
				&& incomeRadios[1].isChecked() == false
				&& incomeRadios[2].isChecked() == false
				&& incomeRadios[3].isChecked() == false
				&& incomeRadios[3].isChecked() == false) {
			return false;
		} else {
			for (int i = 0; i < 5; i++) {
				if (incomeRadios[i].isChecked() == true) {
					infoList[0] = Integer.toString(i + 1);
					break;
				}
			}
		}
		if (ageRadios[0].isChecked() == false
				&& ageRadios[1].isChecked() == false
				&& ageRadios[2].isChecked() == false
				&& ageRadios[3].isChecked() == false
				&& ageRadios[4].isChecked() == false
				&& ageRadios[5].isChecked() == false
				&& ageRadios[6].isChecked() == false) {
			return false;
		} else {
			for (int i = 0; i < 7; i++) {
				if (ageRadios[i].isChecked() == true) {
					infoList[1] = Integer.toString(i + 1);
					break;
				}
			}
		}

		if (carRadios[0].isChecked() == false
				&& carRadios[1].isChecked() == false) {
			return false;
		} else {
			for (int i = 0; i < 2; i++) {
				if (carRadios[i].isChecked() == true) {
					infoList[2] = Integer.toString(i + 1);
					break;
				}
			}
		}
		if (purposeRadios[0].isChecked() == false
				&& purposeRadios[1].isChecked() == false
				&& purposeRadios[2].isChecked() == false
				&& purposeRadios[3].isChecked() == false
				&& purposeRadios[4].isChecked() == false
				&& purposeRadios[5].isChecked() == false
				&& purposeRadios[6].isChecked() == false) {
			return false;
		} else {
			for (int i = 0; i < 7; i++) {
				if (purposeRadios[i].isChecked() == true) {
					infoList[3] = Integer.toString(i + 1);
					break;
				}
			}
		}
		if (favoriteLineEditText.getText() != null
				&& favoriteLineEditText.getText().toString().trim().equals("") != true) {
			infoList[4] = favoriteLineEditText.getText().toString();
		} else {
			return false;
		}
		return completed;
	}
}
