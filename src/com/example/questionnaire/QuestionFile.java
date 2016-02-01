package com.example.questionnaire;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;

import android.os.Environment;

public class QuestionFile implements Serializable {
	/**
	 * 支持序列化用于Intent传输
	 */
	private static final long serialVersionUID = 1L;

	private String INFORMATION_NAME;
	private String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	private String INFORMATION_PATH = SDPATH + "/questions/informations/";

	public QuestionFile() {
		INFORMATION_NAME = Long.toString(System.currentTimeMillis()) + ".txt";
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
		return "调查人姓名,调查日期,调查时间,调查区域,线路号,上车站点,上车站台类型,车辆类型,乘客性别,";
	}

	// 制作个人信息列项
	public String getPersonInfoColumn() {
		return "收入,年龄,是否有车,出行目的,常坐公交,";
	}

	//主要问题列项
	public String getMajorQuestionColumn(){
		String majorQuestion = "";
		DBService dbService = new DBService();
		List<MajorQuestion> list = dbService.getMajorQuestions();
		for(int i = 0; i < list.size(); i++){
				majorQuestion += list.get(i).ID + ",";
		}
		return majorQuestion;
	}
	
	//次要问题列项
	public String getSubQuestionColumn(){
		String subQuestion = "";
		DBService dbService = new DBService();
		List<SubQuestion> list = dbService.getSubQuestions();
		for(int i = 0; i < list.size(); i++){
				subQuestion += list.get(i).ID + ",";
		}
		return subQuestion;
	}
	
	// 存储列项信息
	public void saveColumn() {
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH
					+ INFORMATION_NAME), true);
			fw.write(getInfoColumn());
			fw.write(getMajorQuestionColumn());
			fw.write(getSubQuestionColumn());
			fw.write(getPersonInfoColumn());
			fw.write(System.getProperty("line.separator"));
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 存储调查基本信息
	public void saveInformation(String[] list) {
		String infoContent = "";
		for (int i = 0; i < list.length; i++) {
			infoContent += list[i] + ",";
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH
					+ INFORMATION_NAME), true);
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
				infoContent += list[i] + ",";
			else
				infoContent += list[i];
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH
					+ INFORMATION_NAME), true);
			fw.write(infoContent);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
