package com.gem.fs.ecard.ado;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gem.fs.ecard.bean.ContactBean;
import com.gem.fs.sqliteopenhelper.ContactsSQLiteOpenHelper;

/**
 * 此类用来操作存储用户自己名片的那一张表
 * 做了增删改查四个操作
 * @author 但行希
 *
 */

public class MyCardAdo 
{
	//private static final String TAG ="PersonDao";

	private ContactsSQLiteOpenHelper helper; 
	
	public MyCardAdo(Context context) {
		
	  helper = new ContactsSQLiteOpenHelper(context);  //在构造方法中得到数据库助手
	
	}
    
	/**
	 * 添加一条数据
	 *
	 */
	
	public void add(String cardName,String name,String cellNumber,String phoneNumber,String email,String qq,String address,String company,String remark)
	{
		
		  
		  SQLiteDatabase db = helper.getWritableDatabase();  //得到数据库
		  
		//  db.execSQL("insert into person (name,number) values(?,?);", new Object[]{name,number});
		  ContentValues values = new ContentValues();
		  values.put("cardName", cardName);
		  values.put("name", name);
		  values.put("cellNumber", cellNumber);
		  values.put("phoneNumber", phoneNumber);
		  values.put("email", email);
		  values.put("qq", qq);
		  values.put("address", address);
		  values.put("company", company);
		  values.put("remark", remark);
		  
		  
		 
		  db.insert("mycard", null, values);
		  
		  db.close();
		
	}
	
	/**
	 * 查找单个的联系人，返回的是一个字符串
	 * @param name  要查找用户的姓名
	 * @return
	 */
	
	public String find(String name)
	{
		
		 SQLiteDatabase db = helper.getWritableDatabase();  //得到数据库
		 
		// Cursor cursor = db.rawQuery("select *from person where name=?;", new String[]{name});  //得到查询返回的结果集
		 
		 Cursor cursor = db.query("mycard", null,"name=?" , new String[]{name}, null, null, null);
		 
		 if(cursor.moveToNext())
		 {
			
			 String name1 = cursor.getString(cursor.getColumnIndex("name"));
			 
			 String number1 = cursor.getString(cursor.getColumnIndex("number"));
			
			 return name1+":"+number1;
			 
		 }
		 
		 else
		 {
			 
			 return "该联系人不存在！";
			 
		 }
		 
//		 boolean result = cursor.moveToNext();  //游标移向查找的数据，存在返回true，否则反之。
//		 
//		 cursor.close();
//		 db.close();
//		 return result;
	        
		 
	}
	
	/**
	 * 更新数据库中数据的属性，忽略联系人的name，
	 * @param cardName
	 * @param cellNumber
	 * @param phoneNumber
	 * @param email
	 * @param qq
	 * @param address
	 * @param company
	 * @param remark
	 * @return
	 */
	
	public int update(String cardName,String name,String cellNumber,String phoneNumber,String email,String qq,String address,String company,String remark)
	{
		SQLiteDatabase db = helper.getWritableDatabase();  //得到数据库
		//db.execSQL("update person set number=? where name=?;", new Object[]{number,name});
	    ContentValues values = new ContentValues();
	    
		  values.put("cardName", cardName);
		  values.put("cellNumber", cellNumber);
		  values.put("phoneNumber", phoneNumber);
		  values.put("email", email);
		  values.put("qq", qq);
		  values.put("address", address);
		  values.put("company", company);
		  values.put("remark", remark);
		  
		  int numberAffected = db.update("mycard", values, "name=?", new String[]{name}); //得到修改后影响的行数
		 
		  db.close();
		
		  return numberAffected;
		
	}
	
	/**
	 * 删除一条数据
	 * @param name 要删除人的名字
	 */
	
	public void delete(String name)
	{
		SQLiteDatabase db = helper.getWritableDatabase();  //得到数据库
	//	db.execSQL("delete from person where name=?;", new Object[]{name});
		db.delete("mycard", "name=?", new String[]{name});
		db.close();
	}
	
	/**
	 * 查询所有数据，返回一个List集合
	 * @return
	 */
	
	@SuppressLint("NewApi")
	public ArrayList<ContactBean> findAll()
	{
		ArrayList<ContactBean> list = new ArrayList<ContactBean>();
		ContactBean contact;
		SQLiteDatabase db = helper.getWritableDatabase();  //得到数据库
		//Cursor cursor = db.rawQuery("select *from person", null);
		Cursor cursor = db.query("mycard", new String[]{"id","cardName","name","cellNumber","phoneNumber","email","qq","address","company","remark"}, null, null, null, null, null, null);
		while(cursor.moveToNext())
		{
			//int id = cursor.getInt(cursor.getColumnIndex("id"));
			String cardName = cursor.getString(cursor.getColumnIndex("cardName"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String cellNumber = cursor.getString(cursor.getColumnIndex("cellNumber"));
			String phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"));
			String email = cursor.getString(cursor.getColumnIndex("email"));
			String qq = cursor.getString(cursor.getColumnIndex("qq"));
			String address = cursor.getString(cursor.getColumnIndex("address"));
			String company = cursor.getString(cursor.getColumnIndex("company"));
			String remark = cursor.getString(cursor.getColumnIndex("remark"));
			
			contact = new ContactBean(cardName,name,cellNumber,phoneNumber,email,qq,address,company,remark);
			
			list.add(contact);
		}
		
		cursor.close();
		db.close();
		return list;
		
	}
	
//	public void Test()
//	{
//	
//		SQLiteDatabase db = helper.getWritableDatabase();
//		this.add("dandan", "110", 6000);
//		this.add("danxi", "119", 1000);
//		db.beginTransaction(); //事务开始
//		
//		try 
//		 {
//		   db.execSQL("update person set account=account-1000 where name=?", new Object[]{"dandan"} );
//		   db.execSQL("update person set account=account+1000 where name=?", new Object[]{"danxi"} );
//		   
//		   db.setTransactionSuccessful();  //标识数据库事务执行成功
//		 }
//		 catch(Exception e)
//		 {
//			 e.printStackTrace();
//			 
//		 }
//		 finally 
//		 {
//		     db.endTransaction();  //事务结束
//		     db.close(); //关闭数据库
//		 }
//
//		Log.i(TAG, "数据库事务执行成功--------------");
//		
//		
//	}
//	
	
	
}
