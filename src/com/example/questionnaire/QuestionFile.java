package com.example.questionnaire;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.os.Environment;

/**
 * 该类为存储题目文件类
 * 
 * @author su
 * 
 */

public class QuestionFile implements Serializable {
	/**
	 * 支持序列化用于Intent传输
	 */
	private static final long serialVersionUID = 1L;

	private String INFORMATION_NAME;
	private static String COLUMN_NAME = "title.txt";
	private String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	private String INFORMATION_PATH = SDPATH + "/questions/";

	/**
	 * 根据时间创建问卷名
	 * 
	 * @param date
	 */
	public QuestionFile(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
		INFORMATION_NAME = format.format(date) + ".txt";
		// 存储问卷
		if ((new File(INFORMATION_PATH + INFORMATION_NAME).exists()) == false) {
			// 若不存在则生成目录
			File dir = new File(INFORMATION_PATH);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}

	// 制作调查人信息列项
	public String getInfoColumn() {
		return "调查人姓名\t调查日期\t调查时间\t调查区域\t线路号\t上车站点\t上车站台类型\t车辆类型\t乘客性别\t";
	}

	// 制作个人信息列项
	public String getPersonInfoColumn() {
		return "收入\t年龄\t是否有车\t出行目的\t常坐公交\t建议和意见";
	}

	// 主要问题列项
	public String getMajorQuestionColumn() {
		String majorQuestion = "";
		DBService dbService = new DBService();
		List<MajorQuestion> list = dbService.getMajorQuestions();
		for (int i = 0; i < list.size(); i++) {
			majorQuestion += list.get(i).ID + "\t";
		}
		return majorQuestion;
	}

	// 次要问题列项
	public String getSubQuestionColumn() {
		String subQuestion = "";
		DBService dbService = new DBService();
		List<SubQuestion> list = dbService.getSubQuestions();
		for (int i = 0; i < list.size(); i++) {
			subQuestion += list.get(i).ID + "\t";
		}
		return subQuestion;
	}

	// 存储列项信息
	public void saveColumn() {
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH + COLUMN_NAME), false);
			fw.write(getInfoColumn());
			fw.write(getMajorQuestionColumn());
			fw.write(getSubQuestionColumn());
			fw.write(getPersonInfoColumn());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 存储调查基本信息
	public void saveInformation(String[] list) {
		String infoContent = "";
		for (int i = 0; i < list.length; i++) {
			infoContent += list[i] + "\t";
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH + INFORMATION_NAME), true);
			fw.write(infoContent);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 存储个人信息
	public void savePersonInfo(String[] list) {
		String infoContent = "";
		for (int i = 0; i < list.length; i++) {
			if (i != list.length - 1)
				infoContent += list[i] + "\t";
			else
				infoContent += list[i];
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH + INFORMATION_NAME), true);
			fw.write(infoContent);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 存储第一部分题目
	public void saveMajorQuestion(final List<MajorQuestion> list) {
		String majorContent = "";
		for (int i = 0; i < list.size(); i++) {
			majorContent += list.get(i).selectedAnswer + "\t";
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH + INFORMATION_NAME), true);
			fw.write(majorContent);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据序号存储第二部分题目
	public void saveSubQuestion(final List<SubQuestion> list) {
		String subContent = "";
		// 拷贝出对应ID
		DBService dbService = new DBService();
		List<SubQuestion> allSubQuestionList = dbService.getSubQuestions();
		int copyTag = 0;
		for (int i = 0; i < list.size(); i++) {
			for (int j = copyTag; j < allSubQuestionList.size(); j++) {
				// 如果相等则获取选项
				if (list.get(i).ID.equals(allSubQuestionList.get(j).ID)) {
					allSubQuestionList.get(j).selectedAnswer = list.get(i).selectedAnswer;
					copyTag = j;
					break;
				}
			}
		}

		for (int i = 0; i < allSubQuestionList.size(); i++) {
			subContent += allSubQuestionList.get(i).selectedAnswer + "\t";
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH + INFORMATION_NAME), true);
			fw.write(subContent);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 存储未填的第二部分题目
	public void saveSubQuestion() {
		String subContent = "";
		// 拷贝出对应ID
		DBService dbService = new DBService();
		List<SubQuestion> allSubQuestionList = dbService.getSubQuestions();
		for (int i = 0; i < allSubQuestionList.size(); i++) {
			subContent += allSubQuestionList.get(i).selectedAnswer + "\t";
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH + INFORMATION_NAME), true);
			fw.write(subContent);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
