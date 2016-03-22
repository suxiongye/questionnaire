package com.example.questionnaire;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
	private static List<SubQuestion> list_sub;
	private static QuestionFile questionFile;

	// 分数标准
	private static int standard = 6;

	// 低于6分的题目
	private static String worstList;

	// 返回结果码
	private final int REQUESTCODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_majorquestion);

		// 出现答题前提示
		new AlertDialog.Builder(MajorQuestionActivity.this).setTitle("提示")
				.setMessage("现在，我们要您问几个问题，来了解您对北京公交的体验。" + "请尽量回忆您的使用体验，时间不限于过去1周。没有正确或错误的答案，"
						+ "您可以放心作答。您的观点对我们的分析非常重要，感谢您的参与")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
		// 获取题目对象
		questionFile = (QuestionFile) getIntent().getSerializableExtra("questionFile");
		// 绑定控件
		tv_label = (TextView) findViewById(R.id.majorQuestionLabel);
		tv_content = (TextView) findViewById(R.id.majorQuestionContentTextView);
		scoreRadioGroup = (RadioGroup) findViewById(R.id.majorChooseRadioGroup);
		scoreRadios = new RadioButton[12];
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
		scoreRadios[11] = (RadioButton) findViewById(R.id.majorChooseRadio11);
		btn_next = (Button) findViewById(R.id.majorBtnNext);
		btn_pre = (Button) findViewById(R.id.majorBtnPre);

		// 读取题目
		DBService dbService = new DBService();
		list = dbService.getMajorQuestions();
		list_sub = dbService.getSubQuestions();

		// 设置当前题目信息
		current = 0;
		count = list.size();

		setTitle("一级指标");

		tv_label.setText(Html.fromHtml("您对各项<b>" + list.get(0).label + "</b>指标的满意程度？"));
		tv_content.setText(list.get(0).ID + " . " + list.get(0).question);

		// 设置上下翻页
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 判断是否答题
				boolean completed = false;
				int i = 1;
				for (i = 1; i <= 11; i++) {
					if (scoreRadios[i].isChecked() == true) {
						completed = true;
						break;
					}
				}
				if (completed == false) {
					// 出现提示
					new AlertDialog.Builder(MajorQuestionActivity.this).setTitle("提示").setMessage("请至少选中一个选项！")
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).show();
					return;
				}
				// 判断题目是否达到标准
				if (i < standard && current != 0) {
					worstList = list.get(current).ID;
					// 进入对应子题目进行作答
					Intent intent = null;
					intent = new Intent(MajorQuestionActivity.this, SubQuestionActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("worstList", worstList);
					intent.putExtras(bundle);
					startActivityForResult(intent, REQUESTCODE);

				}
				// 所有题目是否做完标志
				if (current < count - 1) {
					nextPage();
				} else {
					boolean allCheck = true;
					// 如果是最后一页
					// 则判断是否全部题目完成
					for (i = 0; i < list.size(); i++) {
						if (list.get(i).selectedAnswer == 0) {
							toPage(i);
							allCheck = false;
							break;
						}
					}
					// 若题目全部做完则保存题目
					if (allCheck == true) {
						// 存储父题目
						questionFile.saveMajorQuestion(list);
						// 若不用进入细化调查则直接进入下一环节
						if (list.get(list.size() - 1).selectedAnswer >= standard) {
							// 存储子题目
							questionFile.saveSubQuestion(list_sub);
							Intent intent = null;
							intent = new Intent(MajorQuestionActivity.this, PersonInformationActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("questionFile", questionFile);
							intent.putExtras(bundle);
							startActivity(intent);
							MajorQuestionActivity.this.finish();
						}
					}

				}
				setTitle("二级指标");
			}
		});

		btn_pre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				prePage();
				if (current == 0)
					setTitle("一级指标");
			}
		});
		// 设置选项监听器
		scoreRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				// 判断哪个选项被选中
				for (int i = 1; i <= 11; i++) {
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
			tv_label.setText(Html.fromHtml("您对各项<b>" + q.label + "</b>的满意程度？"));
			tv_content.setText(q.ID + " . " + q.question);
			// 清空上一题选项
			scoreRadioGroup.clearCheck();
			// 若题目被选中则显示原来选择
			if (q.selectedAnswer != 0) {
				scoreRadios[q.selectedAnswer].setChecked(true);
			}
		}
	}

	// 跳转到下一页函数
	private static void nextPage() {
		// 如果不是最后一页
		current++;
		MajorQuestion q = list.get(current);
		tv_label.setText(Html.fromHtml("您对各项<b>" + q.label + "</b>的满意程度？"));
		tv_content.setText(q.ID + " . " + q.question);
		// 清空上一题选项
		scoreRadioGroup.clearCheck();
		// 若题目被选中则显示原来选择
		if (q.selectedAnswer != 0) {
			scoreRadios[q.selectedAnswer].setChecked(true);
		}
	}

	// 跳转到固定页
	private static void toPage(int n) {
		if (n > -1 && n < count) {
			current = n;
			MajorQuestion q = list.get(current);
			tv_label.setText(Html.fromHtml("您对各项<b>" + q.label + "</b>的满意程度？"));
			tv_content.setText(q.ID + " . " + q.question);
			// 清空上一题选项
			scoreRadioGroup.clearCheck();
			// 若题目被选中则显示原来选择
			if (q.selectedAnswer != 0) {
				scoreRadios[q.selectedAnswer].setChecked(true);
			}
		}
	}

	// 接收返回数据
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// 获取答题结果
		if (resultCode == 2) {
			List<SubQuestion> request = (List<SubQuestion>) (data.getSerializableExtra("subquestion"));
			fixSubQuestion(request);
		}
		// 若是答题结束则进入个人信息
		if (resultCode == 3) {
			// 存入数据库
			List<SubQuestion> request = (List<SubQuestion>) (data.getSerializableExtra("subquestion"));
			fixSubQuestion(request);

			// 存储子题目
			questionFile.saveSubQuestion(list_sub);
			Intent intent = null;
			intent = new Intent(MajorQuestionActivity.this, PersonInformationActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("questionFile", questionFile);
			intent.putExtras(bundle);
			startActivity(intent);
			this.finish();
		}
	}

	// 将两个子题目答案List重合
	private static void fixSubQuestion(List<SubQuestion> request) {
		int copyTag = 0;
		for (int i = 0; i < request.size(); i++) {
			for (int j = copyTag; j < list_sub.size(); j++) {
				// 如果相等则获取选项
				if (request.get(i).ID.equals(list_sub.get(j).ID)) {
					list_sub.get(j).selectedAnswer = request.get(i).selectedAnswer;
					copyTag = j;
					break;
				}
			}
		}
	}
}
