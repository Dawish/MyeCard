package com.gem.fs.sqliteopenhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 此类是SQLiteOpenHelper类，就是用于创建数据库
 * 数据库的名字是：contacts.db
 * @author 但行希
 *
 */
public class ContactsSQLiteOpenHelper extends SQLiteOpenHelper
{
	
	//private static final String TAG = "PersonSQLiteOpenHelper";

	/**
	 * 此构造方法创建的数据库是存储用户自己的 名片
	 * 构造方法，指定上下文，指定穿创建数据库文件的名字，游标为空，版本1（最低为1）
	 * @param context
	 */
	
	public ContactsSQLiteOpenHelper(Context context) 
	{
		super(context, "contacts.db", null, 1);
		
	}
	
	

	/**
	 * 创建两个表
	 * 第一个表是用于存储用户自己的名片
	 * 第二张表是用于存储其他联系人的名片（就是交换得到的名片）
	 * 两张表的数据项和格式都是一样的，只是名字不一样
	 */
	
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		//第一个表是用于存储用户自己的名片
		db.execSQL("create table mycard(id integer primary key autoincrement," +
				"cardName vachar(12),name vachar(12),cellNumber vachar(12)," +
				"phoneNumber varchar(12),email varchar(12),qq varchar(12)," +
				"address varchar(12),company varchar(12),remark varchar(20))");
		
		//第二张表是用于存储其他联系人的名片（就是交换得到的名片）
		db.execSQL("create table mycontact(id integer primary key autoincrement," +
				"cardName vachar(12),name vachar(12),cellNumber vachar(12)," +
				"phoneNumber varchar(12),email varchar(12),qq varchar(12)," +
				"address varchar(12),company varchar(12),remark varchar(20))");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
//		Log.i(TAG, "数据库的版本更新了哦--------------------");
//		db.execSQL("alter table person add account varchar(20)");
//		Log.i(TAG, "数据库修改成-------------------");
		
	}

	
	
	
}
