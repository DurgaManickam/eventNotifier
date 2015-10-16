package com.example.eventnotifier;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class AlarmClass  extends Activity{
	
		 DBAdapter dbAdapter;
		 DatePicker dp;
		 TimePicker tp;
		 Button buttonSetAlarm;
		 TextView info;
		 
		 final static int request = 1;

		 @Override
		 protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.alarm_activity);
		  info = (TextView)findViewById(R.id.info);
		  dp = (DatePicker)findViewById(R.id.pickerdate);
		  tp = (TimePicker)findViewById(R.id.pickertime);
		  
		  Calendar now = Calendar.getInstance();

		  dp.init(
		    now.get(Calendar.YEAR), 
		    now.get(Calendar.MONTH), 
		    now.get(Calendar.DAY_OF_MONTH), 
		    null);
		  
		  tp.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
		  tp.setCurrentMinute(now.get(Calendar.MINUTE));
		  
		  buttonSetAlarm = (Button)findViewById(R.id.setalarm);
		  buttonSetAlarm.setOnClickListener(new OnClickListener(){

		   @Override
		   public void onClick(View arg0) {
		    Calendar current = Calendar.getInstance();
		    
		    Calendar cal = Calendar.getInstance();
		    cal.set(dp.getYear(), 
		      dp.getMonth(), 
		      dp.getDayOfMonth(), 
		      tp.getCurrentHour(), 
		      tp.getCurrentMinute(), 
		      00);
		    
		    if(cal.compareTo(current) <= 0){
		     //The set Date/Time already passed
		        Toast.makeText(getApplicationContext(), 
		          "Invalid Date/Time", 
		          Toast.LENGTH_LONG).show();
		    }else{
		     setAlarm(cal);
		    }
		    
		   }});
		 }
		 
		 private void setAlarm(Calendar targetCal){

		  info.setText("***Event Created**"
		    + "Event is at " + targetCal.getTime() + "\n"
		    + "***\n");
		  
		  Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
		  PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), request, intent, 0);
		  AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		  alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);   
		 }

		}




