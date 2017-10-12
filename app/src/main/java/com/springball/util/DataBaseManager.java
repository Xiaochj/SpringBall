package com.springball.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {

	private DataBaseHelper dbHelper;
	private SQLiteDatabase sqliteDb;

	public DataBaseManager(Context context){
		dbHelper = DataBaseHelper.getInstance(context);
		sqliteDb = dbHelper.getWritableDatabase();
		//		sqliteDb.delete(DataBaseHelper.TABLE_NAME, null, null);
	}

	/**
	 * 插入一条数据
	 * @param values
	 */
	public void insertData(ContentValues values){
		sqliteDb.insert(DataBaseHelper.TABLE_NAME, null, values);	}

	/**
	 * 查找数据
	 * @param columns
	 * @param selection
	 * @param selectionArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 */
	public Cursor queryDatas(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
		Cursor c = sqliteDb.query(DataBaseHelper.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}

	/**
	 * 根据time删除一条数据
	 * @param time
	 */
	public void deleteData(String time){
		sqliteDb.delete(DataBaseHelper.TABLE_NAME, "time=?", new String[]{time});
	}

	/**
	 * 关闭数据库
	 */
	public void closeDb(){
		sqliteDb.close();
	}
}
