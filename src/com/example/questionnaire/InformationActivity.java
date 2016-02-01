package com.example.questionnaire;

import java.util.ArrayList;

import com.example.questionnaire.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class InformationActivity extends Activity {
	//声明GUI控件
	private static Button infoBtn; 
	private static EditText nameEditText;
	private static EditText dateEditText;
	private static EditText timeEditText;
	private static EditText zoneEditText;
	private static EditText lineEditText;
	private static EditText stationEditText;
	private static RadioGroup busTypeRadioGroup;
	private static RadioButton[] busTypeRadios;
	private static RadioGroup sexTypeRadioGroup;
	private static RadioButton[] sexTypeRadios;
	
	//存储信息
	private static String[] infoList;
	private static QuestionFile questionFile;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_information);
	        
	        //GUI初始化
	        nameEditText = (EditText)findViewById(R.id.nameEditText);
	        dateEditText = (EditText)findViewById(R.id.dateEditText);
	        timeEditText = (EditText)findViewById(R.id.timeEditText);
	        zoneEditText = (EditText)findViewById(R.id.zoneEditText);
	        lineEditText = (EditText)findViewById(R.id.lineEditText);
	        stationEditText = (EditText)findViewById(id.stationNameEditText);
	        busTypeRadioGroup = (RadioGroup)findViewById(R.id.busTypesRadioGroup);
	        busTypeRadios = new RadioButton[4];
	        busTypeRadios[0] = (RadioButton)findViewById(R.id.busType1Radio);
	        busTypeRadios[1] = (RadioButton)findViewById(R.id.busType2Radio);
	        busTypeRadios[2] = (RadioButton)findViewById(R.id.busType3Radio);
	        busTypeRadios[3] = (RadioButton)findViewById(R.id.busType4Radio);
	        sexTypeRadioGroup = (RadioGroup)findViewById(R.id.sexRadioGroup);
	        sexTypeRadios = new RadioButton[2];
	        sexTypeRadios[0] = (RadioButton)findViewById(R.id.sexManRadio);
	        sexTypeRadios[1] = (RadioButton)findViewById(R.id.sexWomanRadio);
	        infoList = new String[8];
	        
	        //进入下一个调查界面
	        infoBtn = (Button)findViewById(R.id.informationBtn);
	        infoBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//如果个人信息完善则可以开始调查
					if(checkIfComplete() == true){
						questionFile = new QuestionFile();
						questionFile.saveInformation(infoList);
						Intent intent = new Intent(InformationActivity.this, MajorQuestionActivity.class);
						startActivity(intent);
					}
				}
			});
	  }
	  
	  //存储并查看信息是否齐全
	  private static boolean checkIfComplete(){
		  boolean completed = true;
		  if(nameEditText.getText() != null && nameEditText.getText().toString().trim().equals("")!=true){
			  infoList[0] = nameEditText.getText().toString();
		  }
		  else{
			  return false;
		  }
		  if(dateEditText.getText() != null && dateEditText.getText().toString().trim().equals("")!=true){
			  infoList[1] = dateEditText.getText().toString();
		  }
		  else{
			  return false;
		  }
		  if(timeEditText.getText() != null && timeEditText.getText().toString().trim().equals("")!=true){
			  infoList[2] = timeEditText.getText().toString();
		  }
		  else{
			  return false;
		  }
		  if(zoneEditText.getText() != null && zoneEditText.getText().toString().trim().equals("")!=true){
			  infoList[3] = zoneEditText.getText().toString();
		  }
		  else{
			  return false;
		  }
		  if(lineEditText.getText() != null && lineEditText.getText().toString().trim().equals("")!=true){
			  infoList[4] = lineEditText.getText().toString();
		  }
		  else{
			  return false;
		  }
		  if(stationEditText.getText() != null && stationEditText.getText().toString().trim().equals("")!=true){
			  infoList[5] = stationEditText.getText().toString();
		  }
		  else{
			  return false;
		  }
		  if(busTypeRadios[0].isChecked() == false && busTypeRadios[1].isChecked() == false &&
				  busTypeRadios[2].isChecked() == false &&busTypeRadios[3].isChecked() == false)
		  {
			  return false;
		  }
		  else{
			  for(int i = 0; i < 4; i++){
				  if(busTypeRadios[i].isChecked() == true){
					  infoList[6] =Integer.toString( i+1);
					  break;
				  }
			  }
		  }
		  if(sexTypeRadios[0].isChecked() == false && sexTypeRadios[1].isChecked() == false )
		  {
			  return false;
		  }
		  else{
			  for(int i = 0; i < 2; i++){
				  if(sexTypeRadios[i].isChecked() == true){
					  infoList[7] =Integer.toString( i+1);
					  break;
				  }
			  }
		  }
		  return completed;
	  }
}
