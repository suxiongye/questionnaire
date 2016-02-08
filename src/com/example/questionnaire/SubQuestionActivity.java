package com.example.questionnaire;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SubQuestionActivity extends Activity {

	// 控件声明
	private static TextView tv_label;
	private static TextView tv_content;
	private static RadioGroup scoreRadioGroup;
	private static RadioButton[] scoreRadios;
	private static Button btn_next;
	private static Button btn_pre;

	// 当前页数;
	private static int current;
	// 题目总数
	private static int count;

	// 题目
	private static List<SubQuestion> list;
	private static QuestionFile questionFile;

	// 低于6分的题目ID
	private static List<String> worstList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subquestion);
		// 绑定控件
		tv_label = (TextView) findViewById(R.id.subQuestionLabel);
		tv_content = (TextView) findViewById(R.id.subQuestionContentTextView);
		scoreRadioGroup = (RadioGroup) findViewById(R.id.subChooseRadioGroup);
		scoreRadios = new RadioButton[11];
		scoreRadios[1] = (RadioButton) findViewById(R.id.subChooseRadio1);
		scoreRadios[2] = (RadioButton) findViewById(R.id.subChooseRadio2);
		scoreRadios[3] = (RadioButton) findViewById(R.id.subChooseRadio3);
		scoreRadios[4] = (RadioButton) findViewById(R.id.subChooseRadio4);
		scoreRadios[5] = (RadioButton) findViewById(R.id.subChooseRadio5);
		scoreRadios[6] = (RadioButton) findViewById(R.id.subChooseRadio6);
		scoreRadios[7] = (RadioButton) findViewById(R.id.subChooseRadio7);
		scoreRadios[8] = (RadioButton) findViewById(R.id.subChooseRadio8);
		scoreRadios[9] = (RadioButton) findViewById(R.id.subChooseRadio9);
		scoreRadios[10] = (RadioButton) findViewById(R.id.subChooseRadio10);
		btn_next = (Button) findViewById(R.id.subBtnNext);
		btn_pre = (Button) findViewById(R.id.subBtnPre);

		// 出现答题前提示
		new AlertDialog.Builder(SubQuestionActivity.this).setTitle("提示")
				.setMessage("下面我们有一些您刚刚觉得不太满意的指标想再深入了解一下。【回答选项小于等于6的指标】")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();

		// 读取题目
		DBService dbService = new DBService();
		worstList = (List<String>) getIntent().getStringArrayListExtra(
				"worstList");
		list = dbService.getSubQuestions(worstList);

		// 设置当前题目信息
		current = 0;
		count = list.size();

		tv_label.setText(list.get(0).label);
		tv_content.setText(list.get(0).ID + ". " + list.get(0).question);

		// 设置上下翻页
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 判断是否答题
				boolean completed = false;
				for (int i = 1; i <= 10; i++) {
					if (scoreRadios[i].isChecked() == true) {
						completed = true;
					}
				}
				if (completed == false)
					return;
				// 所有题目是否做完标志
				if (current < count - 1) {
					nextPage();
				} else {
					boolean allCheck = true;
					// 如果是最后一页则判断是否全部题目完成
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).selectedAnswer == -1) {
							toPage(i);
							allCheck = false;
							break;
						}
					}
					// 若题目全部做完则进入个人信息
					if (allCheck == true) {
						// 存储题目
						// 获取题目对象
						questionFile = (QuestionFile) getIntent()
								.getSerializableExtra("questionFile");
						questionFile.saveSubQuestion(list);
						Intent intent = new Intent(SubQuestionActivity.this,
								PersonInformationActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("questionFile", questionFile);
						intent.putExtras(bundle);
						startActivity(intent);
						SubQuestionActivity.this.finish();
					}
				}
			}
		});

		btn_pre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				prePage();
			}
		});
		// 设置选项监听器
		scoreRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {
						// TODO Auto-generated method stub
						// 判断哪个选项被选中
						for (int i = 1; i <= 10; i++) {
							if (scoreRadios[i].isChecked() == true) {
								list.get(current).selectedAnswer = i;
								break;
							}
						}
					}
				});
	}

	// 跳转道上一页函数
	private static void prePage() {
		// 如果不是第一页
		if (current > 0) {
			current--;
			SubQuestion q = list.get(current);
			tv_label.setText(q.label);
			tv_content.setText(q.ID + " . " + q.question);
			// 清空上一题选项
			scoreRadioGroup.clearCheck();
			// 若题目被选中则显示原来选择
			if (q.selectedAnswer != -1) {
				scoreRadios[q.selectedAnswer].setChecked(true);
			}
		}
	}

	// 跳转到下一页函数
	private static void nextPage() {
		current++;
		SubQuestion q = list.get(current);
		tv_label.setText(q.label);
		tv_content.setText(q.ID + " . " + q.question);
		// 清空上一题选项
		scoreRadioGroup.clearCheck();
		// 若题目被选中则显示原来选择
		if (q.selectedAnswer != -1) {
			scoreRadios[q.selectedAnswer].setChecked(true);
		}
	}

	// 跳转到固定页
	private static void toPage(int n) {
		if (n > -1 && n < count) {
			current = n;
			SubQuestion q = list.get(current);
			tv_label.setText(q.label);
			tv_content.setText(q.ID + " . " + q.question);
			// 清空上一题选项
			scoreRadioGroup.clearCheck();
			// 若题目被选中则显示原来选择
			if (q.selectedAnswer != -1) {
				scoreRadios[q.selectedAnswer].setChecked(true);
			}
		}
	}

}
