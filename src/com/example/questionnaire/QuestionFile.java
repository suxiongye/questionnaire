package com.example.questionnaire;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;

import android.os.Environment;

public class QuestionFile implements Serializable {
	/**
	 * 支持序列化用于Intent传输
	 */
	private static final long serialVersionUID = 1L;

	private String INFORMATION_NAME;
	private String SDPATH = Environment.getExternalStorageDirectory().getPath();
	private String INFORMATION_PATH = SDPATH + "//questions//informations//";

	public QuestionFile() {
		INFORMATION_NAME = Long.toString(System.currentTimeMillis()) + ".txt";
		// 存储问卷
		// 判断是否插入SD卡
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED) == false) {
		}
		if ((new File(INFORMATION_PATH + INFORMATION_NAME).exists()) == false) {
			// 若不存在则生成目录
			File dir = new File(INFORMATION_PATH);
			if (!dir.exists()) {
				dir.mkdir();
			}
		}
	}

	//存储调查信息
	public void saveInformation(String[] list) {

		String infoTitle = "调查人姓名,调查日期,调查时间,调查区域,线路号,上车站点"
				+ ",上车站台类型,车辆类型,乘客性别";
		String infoContent = "";
		for (int i = 0; i < list.length; i++) {
			infoContent += list[i] + ",";
		}
		// 写入文件
		try {
			FileWriter fw = new FileWriter(new File(INFORMATION_PATH
					+ INFORMATION_NAME), true);
			fw.write(infoTitle);
			fw.write(System.getProperty("line.separator"));
			fw.write(infoContent);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
