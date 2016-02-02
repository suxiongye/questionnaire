package com.example.questionnaire;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 该类显示所有第一部分题目
 * 
 * @author su
 * 
 */
public class MajorQuestionActivity extends Activity {
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
	private static List<MajorQuestion> list;
	private static QuestionFile questionFile;
	
	// 低于6分的题目
	private static List<String> worstList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_majorquestion);

		// 获取题目对象
		questionFile = (QuestionFile) getIntent().getSerializableExtra(
				"questionFile");
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
					// 若题目全部做完则进入细化环节
					if (allCheck == true) {
						//存入数据库
						questionFile.saveMajorQuestion(list);
						Intent intent = null;
						// 放入低于6分的题目数组
						worstList = getWorstList(list);
						// 如果存在低于6分的题目
						if (worstList.size() != 0) {
							intent = new Intent(MajorQuestionActivity.this,
									SubQuestionActivity.class);
							Bundle bundle = new Bundle();
							bundle.putStringArrayList("worstList",
									(ArrayList<String>) worstList);
							bundle.putSerializable("questionFile", questionFile);
							intent.putExtras(bundle);
							startActivity(intent);
						}
						// 如果不存在低于6分的题目
						else {
							intent = new Intent(MajorQuestionActivity.this,
									PersonInformationActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("questionFile", questionFile);
							intent.putExtras(bundle);
							startActivity(intent);
						}
						MajorQuestionActivity.this.finish();
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
			MajorQuestion q = list.get(current);
			tv_label.setText(q.label);
			tv_content.setText(q.question);
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
		// 如果不是最后一页
		current++;
		MajorQuestion q = list.get(current);
		tv_label.setText(q.label);
		tv_content.setText(q.question);
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
			MajorQuestion q = list.get(current);
			tv_label.setText(q.label);
			tv_content.setText(q.question);
			// 清空上一题选项
			scoreRadioGroup.clearCheck();
			// 若题目被选中则显示原来选择
			if (q.selectedAnswer != -1) {
				scoreRadios[q.selectedAnswer].setChecked(true);
			}
		}
	}

	// 选出需要细化调查的题目
	private static List<String> getWorstList(List<MajorQuestion> list) {
		List<String> worstList = new ArrayList<String>();
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).selectedAnswer < 6) {
				worstList.add(list.get(i).ID);
			}
		}
		return worstList;
	}
}
