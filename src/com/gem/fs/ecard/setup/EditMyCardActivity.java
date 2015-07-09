package com.gem.fs.ecard.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.gem.card.R;
import com.gem.fs.ecard.ado.MyCardAdo;
import com.gem.fs.ecard.bean.ContactBean;
/**
 * 编辑用户名片的界面
 * @author 但行希
 *
 */
public class EditMyCardActivity extends Activity
{
	
	private EditText e_cardName,e_name,e_cellNumber,e_phoneNumber,e_email,e_qq,e_address,e_company,e_remark;
	private MyCardAdo contactDao;
	public String cardName,name,cellNumber,phoneNumber,email,qq,address,company,remark;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editmycard);
		
		contactDao = new MyCardAdo(EditMyCardActivity.this);
		
		Intent intent = getIntent();
		
		Bundle bundle = intent.getExtras();
		
		ContactBean myCard = (ContactBean) bundle.getSerializable("myCard");
		
		e_cardName = (EditText) findViewById(R.id.e_cardName);
		e_name = (EditText) findViewById(R.id.e_name);
		e_cellNumber = (EditText) findViewById(R.id.e_cellNumber);
		e_phoneNumber = (EditText) findViewById(R.id.e_phoneNumber);
		e_email = (EditText) findViewById(R.id.e_email);
		e_qq = (EditText) findViewById(R.id.e_qq);
		e_address = (EditText) findViewById(R.id.e_address);
		e_company = (EditText) findViewById(R.id.e_company);
		e_remark = (EditText) findViewById(R.id.e_remark);
		
		//把传递过来的对象中的属性显示在空间上
		e_cardName.setText(myCard.getCardName());
		e_name.setText(myCard.getName());
		e_cellNumber.setText(myCard.getCellNumber());
		e_phoneNumber.setText(myCard.getPhoneNumber());
		e_email.setText(myCard.getEmail());
		e_qq.setText(myCard.getQq());
		e_address.setText(myCard.getAddress());
		e_company.setText(myCard.getCompany());
		e_remark.setText(myCard.getRemark());
		
		e_name.setFocusable(false);  //设置姓名文本框是不可编辑的
		

		
	}
	
	/**
	 * 返回到我的名片列表Activity
	 * @param view
	 */
	public void returnMyCardListActivity(View view)
	{
		
		Intent intent = new Intent(EditMyCardActivity.this,MyCardListActivity.class);
		startActivity(intent);
	}
	
	/**
	 * 提交修改后的自己的名片
	 * @param view
	 */
	public void commitMyCard(View view)
	{
		cardName = e_cardName.getText().toString();
		name = e_name.getText().toString();
		cellNumber = e_cellNumber.getText().toString();
		phoneNumber = e_phoneNumber.getText().toString();
		email = e_email.getText().toString();
		qq= e_qq.getText().toString();
		address = e_address.getText().toString();
		company = e_company.getText().toString();
		remark = e_remark.getText().toString();
		
		contactDao.update(cardName, name,cellNumber, phoneNumber, email, qq, address, company, remark);
		Toast.makeText(EditMyCardActivity.this,"修改成功", 0).show();
		
		//提交修改好的名片后返回到我的名片列表
		this.returnMyCardListActivity(view);
	}
	
	/**
	 * 删除自己的名片
	 * @param view
	 */
	public void deleteMyCard(View view)
	{
		contactDao.delete(e_name.getText().toString());
		System.out.println("删除了哦-------------------------------》");
		Toast.makeText(EditMyCardActivity.this,"删除成功", 0).show();
		
		//删除自己的名片后也要返回我的名片列表
		Intent intent = new Intent(EditMyCardActivity.this,MyCardListActivity.class);
		startActivity(intent);
		
	}

}
