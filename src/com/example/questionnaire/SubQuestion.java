package com.example.questionnaire;

import java.io.Serializable;

public class SubQuestion implements Serializable {
	private static final long serialVersionUID = 1L;
		public String question;
		public String label;
		public String ID;
		public String belong;
		public String belongContent;
		public int selectedAnswer = 0;
}
