package com.rbarnes.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DessertDB extends SQLiteOpenHelper implements BaseColumns{

	public static final String DB_TEST = "dessert.db";
	public static final String TABLE_NAME = "desserts";
	public static final String CATEGORY = "category";
	public static final String TITLE = "title";
	public static final String ADDRESS = "address";
	public static final String CITY = "city";
	public static final String STATE = "state";
	public static final String PHONE = "phone";

	public DessertDB(Context context){
		super(context, DB_TEST, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORY + " TEXT, " + TITLE + " TEXT, " + ADDRESS + " TEXT, " + CITY + " TEXT, " + STATE + " TEXT," + PHONE + " TEXT );");

		ContentValues values = new ContentValues();

		values.put(CATEGORY, "Cookies");
		values.put(TITLE, "");
		values.put(ADDRESS, "");
		values.put(CITY, "");
		values.put(STATE, "");
		values.put(PHONE, "");
		db.insert(TABLE_NAME, CATEGORY, values);

		values.put(CATEGORY, "Pies");
		values.put(TITLE, "");
		values.put(ADDRESS, "");
		values.put(CITY, "");
		values.put(STATE, "");
		values.put(PHONE, "");
		db.insert(TABLE_NAME, CATEGORY, values);

		values.put(CATEGORY, "Cakes");
		values.put(TITLE, "");
		values.put(ADDRESS, "");
		values.put(CITY, "");
		values.put(STATE, "");
		values.put(PHONE, "");
		db.insert(TABLE_NAME, CATEGORY, values);

		values.put(CATEGORY, "Candy");
		values.put(TITLE, "");
		values.put(ADDRESS, "");
		values.put(CITY, "");
		values.put(STATE, "");
		values.put(PHONE, "");
		db.insert(TABLE_NAME, CATEGORY, values);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}


}
