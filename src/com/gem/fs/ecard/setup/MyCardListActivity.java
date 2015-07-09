package com.gem.fs.ecard.setup;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.gem.card.R;
import com.gem.fs.ecard.adapter.EditMyCardListAdapter;
import com.gem.fs.ecard.ado.MyCardAdo;
import com.gem.fs.ecard.bean.ContactBean;
/**
 * 显示用户所有名片的界面
 * @author 但行希
 *
 */
public class MyCardListActivity extends Activity
{
	private ListView editContactList;
	private MyCardAdo contactDao;
	private  ArrayList<ContactBean> list;
	private int myRequestCode = 1; //跳向子activity的请求码为1
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mycardlist);
		
		editContactList = (ListView) findViewById(R.id.contactList);
		contactDao = new MyCardAdo(MyCardListActivity.this);
		list = new ArrayList<ContactBean>();
		//界面一展现，就查找所有的联系人，显示在listView中
		
		
		
		

		showMyCardList();  //显示自己所有的名片
		
		
		
		
	}
	
	private void showMyCardList()
	{
		list = contactDao.findAll();  //查找所有数据库中的联系人
		if(list.size()<0)  //如果数据库没有数据，那么就告诉添加数据
		{
			
			Toast.makeText(MyCardListActivity.this, "还没有联系人，快点添加吧！", 0).show();
			
		}
		else  //如果数据库有数据那么就把数据显示在listview中
		{
			EditMyCardListAdapter cardListAdapter = new EditMyCardListAdapter(MyCardListActivity.this, list);
			
			editContactList.setAdapter(cardListAdapter);
			
			/**
			 *为自己的名片列表List绑定监听器，点击的时候跳转到编辑当前的名片，传递一个对象过去 
			 */
			editContactList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					
					ContactBean myCard = list.get(position); //根据位置得到一个对象
					
					Intent intent = new Intent(MyCardListActivity.this,EditMyCardActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("myCard", myCard);
					
					intent.putExtras(bundle);
					
					startActivity(intent);
							
					
				}
			});
		}
		
		
	}
	
	
	/**
	 * 返回到设置界面
	 * @param view
	 */
	public void returnSetupActivity(View view)
	{
		
		Intent intent = new Intent(MyCardListActivity.this,SetupActivity.class);
		
		startActivity(intent);
		
	}
	
	/**
	 * 调到添加名片的界面
	 * @param view
	 */
	public void toAddMyCardActivity(View view)
	{
		
		Intent intent = new Intent(MyCardListActivity.this,AddMyCardActivity.class);
		
		startActivity(intent);
		
		
	}
	
	
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		
		list = contactDao.findAll();  //查找所有数据库中的联系人
		

		showMyCardList();  //显示自己所有的名片
		
		
		System.out.println("onResume-------------->"+list.size());
		
	}
	
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//		super.onStart();
//		list = contactDao.findAll();  //查找所有数据库中的联系人
//		System.out.println("onStart-------------->"+list.size());
//
//		showMyCardList();  //显示自己所有的名片
//		
//	}
	
}
