package com.gem.fs.ecard.setup;

import com.gem.card.R;
import com.gem.fs.ecard.ado.MyCardAdo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 添加用户名片的界面
 * @author 但行希
 *
 */

public class AddMyCardActivity extends Activity
{

	private EditText et_cardName,et_name,et_cellNumber,et_phoneNumber,et_email,et_qq,et_address,et_company,et_remark;
	public String cardName,name,cellNumber,phoneNumber,email,qq,address,company,remark;
	private MyCardAdo contactDao;
	private int add_finished = 6;  //反作用于父activity码为6
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addmycard);
		
		et_cardName = (EditText) findViewById(R.id.cardName);
		et_name = (EditText) findViewById(R.id.name);
		et_cellNumber = (EditText) findViewById(R.id.cellNumber);
		et_phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		et_email = (EditText) findViewById(R.id.email);
		et_qq = (EditText) findViewById(R.id.qq);
		et_address = (EditText) findViewById(R.id.address);
		et_company = (EditText) findViewById(R.id.company);
		et_remark = (EditText) findViewById(R.id.remark);
		
		
		contactDao = new MyCardAdo(AddMyCardActivity.this);
		
		
	}
	
	/**
	 * 返回到我的名片列表
	 * @param view
	 */
	public void returnMyCardListActivity(View view)
	{
		
		Intent intent = new Intent(AddMyCardActivity.this,MyCardListActivity.class);
		startActivity(intent);
		
	}
	
	/**
	 * 把新增的名片提交到数据库
	 * @param view
	 */
	public void addMyCard(View view)
	{
		cardName = et_cardName.getText().toString();
		name = et_name.getText().toString();
		cellNumber = et_cellNumber.getText().toString();
		phoneNumber = et_phoneNumber.getText().toString();
		email = et_email.getText().toString();
		qq= et_qq.getText().toString();
		address = et_address.getText().toString();
		company = et_company.getText().toString();
		remark = et_remark.getText().toString();
		
		
		contactDao.add(cardName, name, cellNumber, phoneNumber, email, qq, address, company, remark);
		
		Toast.makeText(AddMyCardActivity.this, "添加成功！", 0).show();
		//将名片提交到数据库成功后也要跳转到我的名片列表
		this.returnMyCardListActivity(view);
		
	}
	
}
