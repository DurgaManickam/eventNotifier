package com.example.eventnotifier;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.text.InputType;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.preference.EditTextPreference;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressWarnings("unused")
public class MainActivity extends Activity implements OnClickListener {

	EditText eti, ede, multxt, fromDateEtxt, toDateEtxt;
	EditText txtfromDate, txttoDate, txtfromTime, txttoTime;
	int mYear, mMonth, mDay, mHour, mMinute;
	TextView txtviewd, txtviewt, info;
	Button eSave, eView, b1, eupd, bnotify;
	DBAdapter dbAdapter;
	Cursor ca;
	Calendar c;
	Date dt;
	final Context context = this;
	DatePicker pickerdate;
	TimePicker pickertime;

	public static String wdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbAdapter = new DBAdapter(getApplicationContext());

		eti = (EditText) findViewById(R.id.etitletext);
		ede = (EditText) findViewById(R.id.edesctext);
		txtfromDate = (EditText) findViewById(R.id.efromdate);
		txttoDate = (EditText) findViewById(R.id.etodate);
		txtfromTime = (EditText) findViewById(R.id.efromtime);
		txttoTime = (EditText) findViewById(R.id.etotime);

		c = Calendar.getInstance();

		dt = new Date();
		txtviewd = (TextView) findViewById(R.id.textView1);
		txtviewt = (TextView) findViewById(R.id.textView2);
		SimpleDateFormat formatDate = new SimpleDateFormat("dd-MMMM-yyyy",
				Locale.getDefault());
		SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm aa",
				Locale.getDefault());

		String fd = formatDate.format(dt);
		String ft = formatTime.format(dt);
		txtviewd.setText(fd);
		txtviewt.setText(ft);
		txtfromDate.setText(fd);
		txttoDate.setText(fd);
		txtfromTime.setText(ft);
		txttoTime.setText(ft);

		wdate = txtviewd.toString();

		txtfromDate.setOnClickListener(this);
		txttoDate.setOnClickListener(this);
		txtfromTime.setOnClickListener(this);
		txttoTime.setOnClickListener(this);

		eSave = (Button) findViewById(R.id.Savebtn);
		eSave.setOnClickListener(this);

		eView = (Button) findViewById(R.id.Viewbtn);
		eView.setOnClickListener(this);

		b1 = (Button) findViewById(R.id.diabtn);
		b1.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId() == R.id.diabtn) {
			Intent i = new Intent(MainActivity.this, AlarmClass.class);
			startActivity(i);

		}

		if (arg0.getId() == R.id.Savebtn) {

			dbAdapter = new DBAdapter(this);
			String etit = eti.getText().toString();
			String etdec = ede.getText().toString();
			String edate = txtviewd.getText().toString();
			String etime = txtviewt.getText().toString();
			dbAdapter.open();
			dbAdapter.insertEvent(etit, etdec, edate, etime);
			ca = dbAdapter.getRecordByDate(edate);
			Toast.makeText(
					getApplicationContext(),
					"Event Created"
							+ dbAdapter.insertEvent(etit, etdec, edate, etime),
					Toast.LENGTH_LONG).show();
			dbAdapter.close();

		}
		if (arg0.getId() == R.id.efromdate) {

			// Process to get Current Date
			dbAdapter = new DBAdapter(this);
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox
							txtfromDate.setText(dayOfMonth + "-"
									+ (monthOfYear + 1) + "-" + year);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}
		if (arg0.getId() == R.id.etodate) {

			// Process to get Current Date
			dbAdapter = new DBAdapter(this);
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox
							txttoDate.setText(dayOfMonth + "-"
									+ (monthOfYear + 1) + "-" + year);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}
		if (arg0.getId() == R.id.efromdate) {

			dbAdapter = new DBAdapter(this);
			// Process to get Current Time
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// Display Selected time in textbox
							txtfromTime.setText(hourOfDay + ":" + minute);
						}
					}, mHour, mMinute, false);
			tpd.show();
		}
		if (arg0.getId() == R.id.etotime) {

			// Process to get Current Time
			dbAdapter = new DBAdapter(this);
			final Calendar c = Calendar.getInstance();
			mHour = c.get(Calendar.HOUR_OF_DAY);
			mMinute = c.get(Calendar.MINUTE);

			// Launch Time Picker Dialog
			TimePickerDialog tpd = new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// Display Selected time in textbox
							txttoTime.setText(hourOfDay + ":" + minute);
						}
					}, mHour, mMinute, false);
			tpd.show();
		}
		if (arg0.getId() == R.id.Viewbtn) {

			ca = dbAdapter.view();
			ca.moveToFirst();
			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.custom);
			dialog.setTitle("Event Details");
			final TextView tt1;
			tt1 = (TextView) findViewById(R.id.textView5);

			tt1.setText(ca.getString(0).toString());

			// set the custom dialog components - text, image and button
			final TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setText(ca.getString(0).toString());
			final TextView text1 = (TextView) dialog.findViewById(R.id.text1);
			text1.setText(ca.getString(1).toString());
			ImageView image = (ImageView) dialog.findViewById(R.id.image);
			image.setImageResource(R.drawable.ic_launcher);

			Button bn = (Button) dialog.findViewById(R.id.buttonnext);
			bn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (ca.moveToNext()) {
						Log.d("op", ca.getString(0));
						text.setText(ca.getString(0).toString());
						text1.setText(ca.getString(1).toString());
					} else {
						dialog.dismiss();
					}

				}
			});

			Button dialogButton = (Button) dialog
					.findViewById(R.id.dialogButtonOK);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			dialog.show();

			dbAdapter.close();
		}
	}

}
