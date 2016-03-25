package com.example.questionnaire;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * 该类为与数据库交互类，负责存储与获取题目
 * 
 * @author su
 * 
 */

public class DBService {
	private SQLiteDatabase db;
	private static List<MajorQuestion> list_major = null;
	private String DB_PATH = Environment.getDataDirectory()
			+ "/data/com.example.questionnaire/databases/questionnaire.db";

	// 打开数据库
	public DBService() {

		db = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
	}

	// 从数据库中获取第一部分问题
	public List<MajorQuestion> getMajorQuestions() {
		list_major = new ArrayList<MajorQuestion>();
		Cursor cursor = db.rawQuery("select * from majorquestion", null);
		// 获取问题数目并逐个添加到question数组
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int count = cursor.getCount();
			for (int i = 0; i < count; i++) {
				cursor.moveToPosition(i);
				// 与数据库一一对应
				MajorQuestion majorQuestion = new MajorQuestion();
				majorQuestion.question = cursor.getString(cursor.getColumnIndex("question"));
				majorQuestion.label = cursor.getString(cursor.getColumnIndex("label"));
				majorQuestion.ID = cursor.getString(cursor.getColumnIndex("id"));
				list_major.add(majorQuestion);
			}
		}
		return list_major;
	}

	// 从数据库中获取第二部分问题
	public List<SubQuestion> getSubQuestions() {
		List<SubQuestion> list = new ArrayList<SubQuestion>();
		Cursor cursor = db.rawQuery("select * from subquestion", null);
		// 获取问题数目并逐个添加到question数组
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int count = cursor.getCount();
			for (int i = 0; i < count; i++) {
				cursor.moveToPosition(i);
				// 与数据库一一对应
				SubQuestion subQuestion = new SubQuestion();
				subQuestion.question = cursor.getString(cursor.getColumnIndex("question"));
				subQuestion.label = cursor.getString(cursor.getColumnIndex("label"));
				subQuestion.ID = cursor.getString(cursor.getColumnIndex("id"));
				subQuestion.belong = cursor.getString(cursor.getColumnIndex("belong"));
				subQuestion.belongContent = cursor.getString(cursor.getColumnIndex("belongcontent"));
				list.add(subQuestion);
			}
		}
		return list;
	}

	// 根据ID的String数组获取第二部分题目数组
	public List<SubQuestion> getSubQuestions(List<String> worstList) {
		List<SubQuestion> list = new ArrayList<SubQuestion>();
		for (int i = 0; i < worstList.size(); i++) {
			Cursor cursor = db.rawQuery("select * from subquestion where belong = ? ",
					new String[] { worstList.get(i) });
			// 获取问题数目并逐个添加到question数组
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				int count = cursor.getCount();
				for (int j = 0; j < count; j++) {
					cursor.moveToPosition(j);
					// 与数据库一一对应
					SubQuestion subQuestion = new SubQuestion();
					subQuestion.question = cursor.getString(cursor.getColumnIndex("question"));
					subQuestion.label = cursor.getString(cursor.getColumnIndex("label"));
					subQuestion.ID = cursor.getString(cursor.getColumnIndex("id"));
					subQuestion.belong = cursor.getString(cursor.getColumnIndex("belong"));
					subQuestion.belongContent = cursor.getString(cursor.getColumnIndex("belongcontent"));
					list.add(subQuestion);
				}
			}
		}
		return list;
	}

	// 根据ID的String获取第二部分题目数组
	public List<SubQuestion> getSubQuestions(String worstList) {
		List<SubQuestion> list = new ArrayList<SubQuestion>();

		Cursor cursor = db.rawQuery("select * from subquestion where belong = ? ", new String[] { worstList });
		// 获取问题数目并逐个添加到question数组
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int count = cursor.getCount();
			for (int j = 0; j < count; j++) {
				cursor.moveToPosition(j);
				// 与数据库一一对应
				SubQuestion subQuestion = new SubQuestion();
				subQuestion.question = cursor.getString(cursor.getColumnIndex("question"));
				subQuestion.label = cursor.getString(cursor.getColumnIndex("label"));
				subQuestion.ID = cursor.getString(cursor.getColumnIndex("id"));
				subQuestion.belong = cursor.getString(cursor.getColumnIndex("belong"));
				subQuestion.belongContent = cursor.getString(cursor.getColumnIndex("belongcontent"));
				list.add(subQuestion);
			}
		}

		return list;
	}
	
	//获取第一部分题目数组最后一个ID
	public String getLastMajorQuestionID(){
		return list_major.get(list_major.size()-1).ID;
	}
}
