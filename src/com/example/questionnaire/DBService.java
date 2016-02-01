package com.example.questionnaire;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBService {
	private SQLiteDatabase db;

	// 打开数据库
	public DBService() {
		db = SQLiteDatabase
				.openDatabase(
						"/data/data/com.example.questionnaire/databases/questionnaire.db",
						null, SQLiteDatabase.OPEN_READWRITE);
	}

	// 从数据库中获取第一部分问题
	public List<MajorQuestion> getMajorQuestions() {
		List<MajorQuestion> list = new ArrayList<MajorQuestion>();
		Cursor cursor = db.rawQuery("select * from majorquestion", null);
		// 获取问题数目并逐个添加到question数组
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			int count = cursor.getCount();
			for (int i = 0; i < count; i++) {
				cursor.moveToPosition(i);
				// 与数据库一一对应
				MajorQuestion majorQuestion = new MajorQuestion();
				majorQuestion.question = cursor.getString(cursor
						.getColumnIndex("question"));
				majorQuestion.label = cursor.getString(cursor
						.getColumnIndex("label"));
				majorQuestion.ID = cursor
						.getString(cursor.getColumnIndex("id"));

				list.add(majorQuestion);
			}
		}
		return list;
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
				subQuestion.question = cursor.getString(cursor
						.getColumnIndex("question"));
				subQuestion.label = cursor.getString(cursor
						.getColumnIndex("label"));
				subQuestion.ID = cursor.getString(cursor.getColumnIndex("id"));

				list.add(subQuestion);
			}
		}
		return list;
	}

	// 根据ID获取第二部分题目数组
	public List<SubQuestion> getSubQuestions(List<String> worstList) {
		List<SubQuestion> list = new ArrayList<SubQuestion>();
		for (int i = 0; i < worstList.size(); i++) {
			Cursor cursor = db.rawQuery(
					"select * from subquestion where belong = ? ",
					new String[] { worstList.get(i) });
			// 获取问题数目并逐个添加到question数组
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				int count = cursor.getCount();
				for (int j = 0; j < count; j++) {
					cursor.moveToPosition(j);
					// 与数据库一一对应
					SubQuestion subQuestion = new SubQuestion();
					subQuestion.question = cursor.getString(cursor
							.getColumnIndex("question"));
					subQuestion.label = cursor.getString(cursor
							.getColumnIndex("label"));
					subQuestion.ID = cursor.getString(cursor
							.getColumnIndex("id"));
					list.add(subQuestion);
				}
			}
		}
		return list;
	}
}
