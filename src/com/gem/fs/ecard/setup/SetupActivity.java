package com.gem.fs.ecard.setup;

import java.util.ArrayList;

import com.gem.card.R;
import com.gem.fs.ecard.adapter.MySpinnerAdapter;
import com.gem.fs.ecard.ado.MyCardAdo;
import com.gem.fs.ecard.ado.MyContactAdo;
import com.gem.fs.ecard.bean.ContactBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * 设置界面
 * @author 但行希
 *
 */
public class SetupActivity extends Activity {

	private Spinner spinner;
	
	private MyCardAdo contactDao;
	private ArrayList<ContactBean> myCardList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setup);
		
		//spinner = (Spinner) findViewById(R.id.sp);
		
		contactDao = new MyCardAdo(SetupActivity.this);
		MyContactAdo contactDao = new MyContactAdo(SetupActivity.this);
	    ArrayList<ContactBean> list = contactDao.findAll();
	    System.out.println("-------------------------->"+list.size());
		//myCardList = new ArrayList<ContactBean>();
		
		//myCardList = contactDao.findAll( );
		
		
		
//		int size = myCardList.size();
//		if(size>0)
//		{
//
//			String[] mData = new String[size];
//			
//			
//			
//			for(int i=0;i<size-1;i++)
//			{
//				mData[i] = myCardList.get(i).getCardName()+":"+myCardList.get(i).getCellNumber();
//				
//			}
//				
//			
//			
//			MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(SetupActivity.this, mData);
//			
//			
//			spinner.setAdapter(spinnerAdapter);
//			
//		}
//		else
//		{
//			
//			
//			
//		}
	}

	/**
	 * 管理自己的名片
	 * @param view
	 */
	
	public void managerCard(View view)
	{
		
		Intent intent = new Intent(SetupActivity.this,MyCardListActivity.class);
		
		startActivity(intent);
		
		
	}
	
	/**
	 * 修改账户资料
	 * @param view
	 */
	public void updateAccount(View view)
	{
		
		Intent intent = new Intent(SetupActivity.this,ModifyUserInfoActivity.class);
		
		startActivity(intent);
		
	}
	
	
	/**
	 * 关于软件
	 * @param view
	 */
	public void aboutDeveloper(View view)
	{
		
		Intent intent = new Intent(SetupActivity.this,AboutsoftwareActivity.class);
		
		startActivity(intent);
		
	}
	
	
	

}
