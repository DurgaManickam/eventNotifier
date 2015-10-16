package com.example.eventnotifier;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	

	private static final int DATABASE_VERSION = 1;
	public static final String KEY_ETITLE = "Etitle";
	public static final String KEY_EDESC = "Edesc";
	public static final String KEY_EDATE = "Edate";
	public static final String KEY_ETime = "ETime";

	public static final String KEY_DAY = "Day";
	public static final String KEY_MONTH = "Month";
	public static final String KEY_YEAR = "Year";

	public static final String TAG = "EVENTDB";
	public static final String DATABASE_NAME1 = "Eventd";
	public static final String TABLE_NAME = "EventInfo";
	public static final String TABLE_SQL = "Create Table " + TABLE_NAME
			+ "(etitle TEXT, " + "edesc TEXT," + "edate TEXT," + "etime TEXT);";

	public static int tot = 0;

	// INSERTION

	private final Context context;

	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	// DB HELPER CLASS
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME1, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_SQL);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS PESITMSEEVENT");
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}
	
	public Cursor view() throws SQLException{
		db = DBHelper.getReadableDatabase();
		Cursor r;
		r = db.rawQuery("Select * from EventInfo",null);
		
		return r;
		
	}
	public void deleteevent(String edate) throws SQLException{
		db=DBHelper.getReadableDatabase();
		Cursor cr=db.rawQuery("SELECT * FROM EventInfo WHERE Edate='"+edate.getBytes()+"'", null);
        if(cr.moveToFirst())
        {
        // Deleting record if found
            db.execSQL("DELETE FROM EventInfo WHERE Edate='"+edate.getBytes()+"'");
         // showMessage("Success", "Record Deleted");
        }
	}
	
	public void updateevent(String etitle,String edes,String edate,String etime)
    {
    	db = DBHelper.getWritableDatabase();
    	ContentValues cv=new ContentValues();
    	cv.put(KEY_ETITLE, etitle);
    	Log.i("Update", "Updating the Record");
    	String[] args = new String[]{etitle, edes, edate,etime};
    	db.update(TABLE_NAME, cv, "etitle=? AND edesc=? AND edate=? AND etime=?", args);
    }
	
	// ---insert into EVENT database---
	public long insertEvent(String etit, String etdesc, String edate,String etime) {
		long x;
		db = DBHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("etitle", etit);
		values.put("edesc", etdesc);
		values.put("edate", edate);
		values.put("etime", etime);
		tot += 1;
		x = db.insert("EventInfo", null, values);
		return x;
	}

	public Cursor getRecordByDate(String edate) throws SQLException {
		db = DBHelper.getReadableDatabase();
		Cursor mCursor = db.query(true, TABLE_NAME, new String[] { KEY_ETITLE,
				KEY_EDESC, KEY_EDATE, KEY_ETime }, KEY_EDATE + "=" + "\""
				+ edate + "\"", null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public int getRecord() throws SQLException {
		db = DBHelper.getReadableDatabase();

		int total = (int) DatabaseUtils.queryNumEntries(db, "eventinfo");
		String str = String.valueOf(total);
		return total;
	}
}
