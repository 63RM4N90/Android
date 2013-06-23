package com.example.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FlightsDbAdapter {

	public static final String KEY_ROWID = "rowid";
	public static final String KEY_PRICE = "customer";
	public static final String KEY_FROM = "name";
	public static final String KEY_TO = "city";
	public static final String KEY_DEPDATE = "state";
	public static final String KEY_RETDATE = "zipCode";
	public static final String KEY_SEARCH = "searchData";

	private static final String LIMIT = "5"; // Limita la cantidad de resultados
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	private static final String DATABASE_FROM = "CustomerData";
	private static final String FTS_VIRTUAL_TABLE = "CustomerInfo";
	private static final int DATABASE_VERSION = 1;

	// Create a FTS3 Virtual Table for fast searches
	private static final String DATABASE_CREATE = "CREATE VIRTUAL TABLE "
			+ FTS_VIRTUAL_TABLE + " USING fts3(" + KEY_PRICE + "," + KEY_FROM
			+ "," + KEY_TO + "," + KEY_DEPDATE + "," + KEY_RETDATE + ","
			+ KEY_SEARCH + "," + " UNIQUE (" + KEY_PRICE + "));";

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			
			super(context, DATABASE_FROM, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
			onCreate(db);
		}
	}

	public FlightsDbAdapter(Context ctx) {
		
		this.mCtx = ctx;
	}

	public FlightsDbAdapter open() throws SQLException {
		
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		
		return this;
	}

	public void close() {
		
		if (mDbHelper != null) {
		
			mDbHelper.close();
		}
	}

	public long createCities(String city) {

		ContentValues initialValues = new ContentValues();
		String searchValue = city;

		initialValues.put(KEY_TO, city);
		initialValues.put(KEY_SEARCH, searchValue);

		return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);
	}

	public long createFlights(String customer, String name, String city,
			String state, String zipCode) {

		ContentValues initialValues = new ContentValues();
		
		String searchValue = customer + " " + name + " " + city + " " + state + " " + zipCode;
		
		initialValues.put(KEY_PRICE, customer);
		initialValues.put(KEY_FROM, name);
		initialValues.put(KEY_TO, city);
		initialValues.put(KEY_DEPDATE, state);
		initialValues.put(KEY_RETDATE, zipCode);
		initialValues.put(KEY_SEARCH, searchValue);
		
		Log.d("oetrsua", searchValue);

		return mDb.insert(FTS_VIRTUAL_TABLE, null, initialValues);
	}

	public Cursor searchFlights(String inputText) throws SQLException {
		
		String query = "SELECT docid as _id," + KEY_PRICE + "," + KEY_FROM
				+ "," + KEY_TO + "," + KEY_DEPDATE + "," + KEY_RETDATE
				+ " FROM " + FTS_VIRTUAL_TABLE + " WHERE " + KEY_SEARCH
				+ " MATCH '" + inputText + "'" + " ORDER BY " + KEY_SEARCH
				+ " LIMIT " + LIMIT + ";";
		
		Cursor mCursor = mDb.rawQuery(query, null);

		if (mCursor != null) {
		
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public boolean deleteAllFlights() {

		int doneDelete = 0;
		
		doneDelete = mDb.delete(FTS_VIRTUAL_TABLE, null, null);

		return doneDelete > 0;
	}
}
