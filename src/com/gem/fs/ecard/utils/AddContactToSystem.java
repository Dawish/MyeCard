package com.gem.fs.ecard.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.RawContacts;

/**
 * 将联系人添加到安卓系统通讯录，只将名字和手机号码保存通讯录
 * @author Administrator
 *
 */
public class AddContactToSystem 
{

	private Context mContext;
	
	
	
	public AddContactToSystem(Context mContext) {
		super();
		this.mContext = mContext;
	}



	public boolean saveContact(String name,String cellNumber)
	{
		ContentValues values = new ContentValues();
		
		//首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
		//因为我们要从最后一个系统联系人的下一个id开始添加
		Uri rawContactUri = mContext.getContentResolver() //得到rawContactUri
				.insert(RawContacts.CONTENT_URI, values);
		
		long rawContactId = ContentUris.parseId(rawContactUri);  //得到rawContactId
		
		//向data表插入姓名数据
		
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);  //把id加入到集合
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); //内容类型
		values.put(StructuredName.GIVEN_NAME, name);  //加入姓名
		mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);
		
		//向data表插入电话号码数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);  //把id加入到集合
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE); //内容类型
		values.put(Phone.NUMBER, cellNumber);
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		mContext.getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
		return true;
	}
	
}
