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
 * 此类使来操作存储其他联系人的表
 * 此表只做添加、查找和删除操作
 * @author 但行希
 *
 */
public class MyContactAdo {

	private ContactsSQLiteOpenHelper helper; 
	
	public MyContactAdo(Context context) {
		super();
		helper = new ContactsSQLiteOpenHelper(context);
	}
	
	/**
	 * 添加一联系人
	 * @param cardName
	 * @param name
	 * @param cellNumber
	 * @param phoneNumber
	 * @param email
	 * @param qq
	 * @param address
	 * @param company
	 * @param remark
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
		  
		  
		 
		  db.insert("mycontact", null, values);
		  
		  db.close();
		
	}

	/**
	 * 把传进来的一个对象加入到数据库
	 * @param myCard
	 */
	
	public void addObject(ContactBean myCard)
	{
		
		this.add(myCard.getCardName(), myCard.getName(), myCard.getCellNumber(),
				myCard.getPhoneNumber(), myCard.getEmail(), myCard.getQq(), 
				myCard.getAddress(), myCard.getCompany(), myCard.getRemark());
		
		
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
		Cursor cursor = db.query("mycontact", new String[]{"id","cardName","name","cellNumber","phoneNumber","email","qq","address","company","remark"}, null, null, null, null, null, null);
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
	
	/**
	 * 删除一条数据
	 * @param name 要删除人的名字
	 */
	
	public void delete(String name)
	{
		SQLiteDatabase db = helper.getWritableDatabase();  //得到数据库
	//	db.execSQL("delete from person where name=?;", new Object[]{name});
		db.delete("mycontact", "name=?", new String[]{name});
		db.close();
	}
	
	}
	
	
	
