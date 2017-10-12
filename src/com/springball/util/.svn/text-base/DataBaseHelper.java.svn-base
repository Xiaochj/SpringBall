package com.springball.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static DataBaseHelper instance = null;
	public static final String DATABASE_NAME = "springball.db";
	public static final String TABLE_NAME = "springball";
	private static final int DATABASE_VERSION = 1;
	//创建一个数据表springball，以name为主键，并且name保证唯一性
	private static final String CREATE_TABLE = "create table "
			+ TABLE_NAME
			+ "(name TEXT PRIMARY KEY ON CONFLICT REPLACE,score INTEGER,time LONG);";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DataBaseHelper getInstance(Context context) {
		synchronized (DataBaseHelper.class) {
			if (instance == null) {
				instance = new DataBaseHelper(context);
			}
		}
		return instance;
	}

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 建立一个数据表
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * 删除数据库
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DATABASE_NAME);
	}
}
