package com.example.questionnaire;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.questionnaire.R.id;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * 该类主要显示调查人信息
 * 
 * @author su
 * 
 */

public class InformationActivity extends Activity {
	// 声明GUI控件
	private static Button infoBtn;
	private static EditText nameEditText;
	private static EditText dateEditText;
	private static EditText timeEditText;
	private static EditText zoneEditText;
	private static EditText lineEditText;
	private static EditText stationNameEditText;
	private static RadioButton[] busStaTypeRadios;
	private static RadioButton[] busTypeRadios;
	private static RadioButton[] sexTypeRadios;
	private static QuestionFile questionFile;
	// 存储信息
	private static String[] infoList;

	// 设置默认存储调查人相关信息
	private static final String SEARCHERS = "searchers";
	private static SharedPreferences saveSearchers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);
		// GUI初始化
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		dateEditText = (EditText) findViewById(R.id.dateEditText);
		timeEditText = (EditText) findViewById(R.id.timeEditText);
		zoneEditText = (EditText) findViewById(R.id.zoneEditText);
		lineEditText = (EditText) findViewById(R.id.lineEditText);
		stationNameEditText = (EditText) findViewById(id.stationNameEditText);
		busStaTypeRadios = new RadioButton[4];
		busStaTypeRadios[0] = (RadioButton) findViewById(R.id.busStaType1Radio);
		busStaTypeRadios[1] = (RadioButton) findViewById(R.id.busStaType2Radio);
		busStaTypeRadios[2] = (RadioButton) findViewById(R.id.busStaType3Radio);
		busStaTypeRadios[3] = (RadioButton) findViewById(R.id.busStaType4Radio);
		busTypeRadios = new RadioButton[4];
		busTypeRadios[0] = (RadioButton) findViewById(R.id.busType1Radio);
		busTypeRadios[1] = (RadioButton) findViewById(R.id.busType2Radio);
		busTypeRadios[2] = (RadioButton) findViewById(R.id.busType3Radio);
		busTypeRadios[3] = (RadioButton) findViewById(R.id.busType4Radio);
		sexTypeRadios = new RadioButton[2];
		sexTypeRadios[0] = (RadioButton) findViewById(R.id.sexManRadio);
		sexTypeRadios[1] = (RadioButton) findViewById(R.id.sexWomanRadio);
		infoList = new String[9];

		// 初始化各个控件信息
		// 获取已经填入过的信息
		saveSearchers = getSharedPreferences(SEARCHERS, MODE_PRIVATE);
		// 默认上次填入的信息
		if (saveSearchers.getString("name", null) != null) {
			nameEditText.setText(saveSearchers.getString("name", ""));
		}
		if (saveSearchers.getString("zone", null) != null) {
			zoneEditText.setText(saveSearchers.getString("zone", ""));
		}
		if (saveSearchers.getString("line", null) != null) {
			lineEditText.setText(saveSearchers.getString("line", ""));
		}
		if (saveSearchers.getString("stationname", null) != null) {
			stationNameEditText.setText(saveSearchers.getString("stationname", ""));
		}

		// 日期初始化
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
		Date date = new Date(System.currentTimeMillis());
		dateEditText.setText(dateFormater.format(date));
		dateEditText.setEnabled(false);
		// 时间初始化
		SimpleDateFormat timeFormater = new SimpleDateFormat("HHmmss", Locale.CHINA);
		timeEditText.setText(timeFormater.format(date));
		timeEditText.setEnabled(false);
		// 生成问卷文件
		questionFile = new QuestionFile(date);

		// 进入下一个调查界面
		infoBtn = (Button) findViewById(R.id.informationBtn);
		infoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 如果个人信息完善则可以开始调查
				if (checkIfComplete() == true) {
					// 在xml中存储个人信息
					SharedPreferences.Editor preferencesEditor = saveSearchers.edit();
					preferencesEditor.putString("name", nameEditText.getText().toString());
					preferencesEditor.putString("zone", zoneEditText.getText().toString());
					preferencesEditor.putString("line", lineEditText.getText().toString());
					preferencesEditor.putString("stationname", stationNameEditText.getText().toString());
					preferencesEditor.apply();

					// 在文件中存储个人信息
					questionFile.saveColumn();
					questionFile.saveInformation(infoList);
					Intent intent = new Intent(InformationActivity.this, MajorQuestionActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("questionFile", questionFile);
					intent.putExtras(bundle);
					startActivity(intent);
					InformationActivity.this.finish();
				} else {
					// 出现提示
					new AlertDialog.Builder(InformationActivity.this).setTitle("提示").setMessage("信息未填全！")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
				}
			}
		});
	}

	// 存储并查看信息是否齐全
	private static boolean checkIfComplete() {
		boolean completed = true;
		if (nameEditText.getText() != null && nameEditText.getText().toString().trim().equals("") != true) {
			infoList[0] = nameEditText.getText().toString().replace("\t", "");
		} else {
			return false;
		}
		if (dateEditText.getText() != null && dateEditText.getText().toString().trim().equals("") != true) {
			infoList[1] = dateEditText.getText().toString().replace("\t", "");
		} else {
			return false;
		}
		if (timeEditText.getText() != null && timeEditText.getText().toString().trim().equals("") != true) {
			infoList[2] = timeEditText.getText().toString().replace("\t", "");
		} else {
			return false;
		}
		if (zoneEditText.getText() != null && zoneEditText.getText().toString().trim().equals("") != true) {
			infoList[3] = zoneEditText.getText().toString().replace("\t", "");
		} else {
			return false;
		}
		if (lineEditText.getText() != null && lineEditText.getText().toString().trim().equals("") != true) {
			infoList[4] = lineEditText.getText().toString().replace("\t", "");
		} else {
			return false;
		}
		if (stationNameEditText.getText() != null
				&& stationNameEditText.getText().toString().trim().equals("") != true) {
			infoList[5] = stationNameEditText.getText().toString().replace("\t", "");
		} else {
			return false;
		}

		if (busStaTypeRadios[0].isChecked() == false && busStaTypeRadios[1].isChecked() == false
				&& busStaTypeRadios[2].isChecked() == false&& busStaTypeRadios[3].isChecked() == false) {
			return false;
		} else {
			for (int i = 0; i < 4; i++) {
				if (busStaTypeRadios[i].isChecked() == true) {
					infoList[6] = Integer.toString(i + 1);
					break;
				}
			}
		}
		if (busTypeRadios[0].isChecked() == false && busTypeRadios[1].isChecked() == false
				&& busTypeRadios[2].isChecked() == false && busTypeRadios[3].isChecked() == false) {
			return false;
		} else {
			for (int i = 0; i < 4; i++) {
				if (busTypeRadios[i].isChecked() == true) {
					infoList[7] = Integer.toString(i + 1);
					break;
				}
			}
		}
		if (sexTypeRadios[0].isChecked() == false && sexTypeRadios[1].isChecked() == false) {
			return false;
		} else {
			for (int i = 0; i < 2; i++) {
				if (sexTypeRadios[i].isChecked() == true) {
					infoList[8] = Integer.toString(i + 1);
					break;
				}
			}
		}
		return completed;
	}
}
