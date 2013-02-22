/*
 * project	Java2Week3
 * 
 * package	com.rbarnes.other
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Feb 21, 2013
 */
package com.rbarnes.java2week3;

import com.rbarnes.other.DessertDB;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class DessertContentProvider extends ContentProvider{

	static final String AUTHORITY = "content://com.rbarnes.other.DessertContentProvider";
	  public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	  private SQLiteDatabase db;
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int retVal = db.delete(DessertDB.TABLE_NAME, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);

		return retVal;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues inValues) {
		ContentValues values = new ContentValues(inValues);
		long rowId = db.insert(DessertDB.TABLE_NAME, DessertDB.CATEGORY, values);

		if(rowId > 0){
		Uri url = ContentUris.withAppendedId(CONTENT_URI, rowId);
		getContext().getContentResolver().notifyChange(url, null);

		return uri;
		}else{
		throw new SQLException("Failed to insert row into " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		db = new DessertDB(getContext()).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String orderBy;

		if(TextUtils.isEmpty(sortOrder)){
		orderBy = DessertDB.CATEGORY;
		}else{
		orderBy = sortOrder;
		}

		Cursor c = db.query(DessertDB.TABLE_NAME, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int retVal = db.update(DessertDB.TABLE_NAME, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);

		return retVal;
	}

}
