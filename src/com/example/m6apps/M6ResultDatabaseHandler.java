package com.example.m6apps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class M6ResultDatabaseHandler extends SQLiteOpenHelper {
    static final int TOTAL_NUM_OF_BALL  = 49; 
	static final String firstBallCol = "first";
	static final String secondBallCol = "second";
	static final String thirdBallCol = "third";
	static final String forthBallCol = "forth";
	static final String fifthBallCol = "fifth";
	static final String sixBallCol = "sixth";
	static final String specialBallCol = "special";

	static final String m6TableName = "m6table";
 
	static final String createTableSQLStr = "CREATE TABLE m6table( first INTEGER, second INTEGER, "
			+ "third INTEGER, forth INTEGER , fifth INTEGER , sixth INTEGER,"
			+ "special INTEGER ) ";
    static String[] columnIndexArray = new String[] {firstBallCol,secondBallCol,thirdBallCol,forthBallCol,fifthBallCol,sixBallCol,specialBallCol} ;
	public M6ResultDatabaseHandler(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, 1 );
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(createTableSQLStr);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
 
	}

	public void addM6Result(M6Result m6r) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		int[] myDrawResultArr = m6r.getBallResult();

		cv.put(firstBallCol, myDrawResultArr[0]);
		cv.put(secondBallCol, myDrawResultArr[1]);
		cv.put(thirdBallCol, myDrawResultArr[2]);
		cv.put(forthBallCol, myDrawResultArr[3]);
		cv.put(fifthBallCol, myDrawResultArr[4]);
		cv.put(sixBallCol, myDrawResultArr[5]);
		cv.put(specialBallCol, myDrawResultArr[6]);
		db.insert(m6TableName, null, cv);
		db.close();
	}

	public void removeAllM6Records() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DELETE FROM m6table");
		db.close();
	}

	public int[] getBallFrequencyArr() {
		boolean bHaveResult = false;
		int[] freqCounter = new int[TOTAL_NUM_OF_BALL];
		for ( int i = 0 ; i < TOTAL_NUM_OF_BALL ; i++ ){
		    freqCounter[i] = 0;
		}
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from " + m6TableName , null); 
		cursor.moveToFirst();
		if (cursor.moveToFirst()) {
	        do {
	        	int tmp;
	        	bHaveResult = true;
	        	for ( int i = 0 ; i < columnIndexArray.length ; i++ ){
	        		tmp = cursor.getInt(cursor.getColumnIndex(columnIndexArray[i]));
	        		if ( tmp >= 1 && tmp <= TOTAL_NUM_OF_BALL ){
		        		freqCounter[tmp-1] += 1;
	        		}
	        	}
	        } while (cursor.moveToNext());
	    }
		db.close();
	    if ( bHaveResult == false ){
		  return null;
	    } 
	    return freqCounter;
	}
}

