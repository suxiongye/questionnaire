package com.example.questionnaire;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.os.Environment;

public class QuestionFile implements Serializable {
	public void saveInformation(String[] list){
		// 存储问卷
				// 判断是否插入SD卡
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED) == false) {
				}

				String SDPATH = Environment.getExternalStorageDirectory().getPath();
				String INFORMATION_PATH = SDPATH + "//questions//informations//";
				String INFORMATION_NAME = Long.toString(System.currentTimeMillis())
						+ ".txt";
				if ((new File(INFORMATION_PATH + INFORMATION_NAME).exists()) == false) {
					// 若不存在则生成目录
					File dir = new File(INFORMATION_PATH);
					if (!dir.exists()) {
						dir.mkdir();
					}
				}
				byte[] buffer = null;
				String information = "调查人姓名，调查日期，调查时间，调查区域，线路号，上车站点" +
						"，上车站台类型，车辆类型，乘客性别 ";
				for(int i = 0; i < list.length; i++){
					information+=list[i]+",";
				}
				buffer = information.getBytes();
				// 写入文件
				try {
					OutputStream os = new FileOutputStream(INFORMATION_PATH
							+ INFORMATION_NAME);
					os.write(buffer);
					os.flush();
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
}
