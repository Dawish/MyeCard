package com.gem.fs.ecard.contactlist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.gem.card.R;
import com.gem.fs.ecard.ado.MyContactAdo;
import com.gem.fs.ecard.bean.ContactBean;
import com.gem.fs.ecard.utils.AddContactToSystem;

public class EcardDetailsActivity extends Activity {

	
    private ContactBean contact = new ContactBean();
    private TextView title_name,last_name,detail_name,company_name,company_name2,phone_number,email_address,qq_number,homepage_address,detail_address;
    
    private MyContactAdo myContactDao = new MyContactAdo(EcardDetailsActivity.this);
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ecard_details);
		
		Intent intent = getIntent();
		
		Bundle bundle = intent.getExtras();
		
		contact = (ContactBean) bundle.getSerializable("contact");
		
	    initView();
	    
	    
		
	}

	public void initView()
	{
		title_name = (TextView) findViewById(R.id.title_name);
		last_name = (TextView) findViewById(R.id.last_name);
		detail_name = (TextView) findViewById(R.id.detail_name);
		company_name = (TextView) findViewById(R.id.company_name);
		phone_number = (TextView) findViewById(R.id.phone_number);
		email_address = (TextView) findViewById(R.id.email_address);
		qq_number = (TextView) findViewById(R.id.qq_number);
		company_name2 = (TextView) findViewById(R.id.company_name2);
		//homepage_address = (TextView) findViewById(R.id.homepage_address);
		detail_address = (TextView) findViewById(R.id.detail_address);; 
		
		title_name.setText(contact.getName());
		last_name.setText(contact.getName().subSequence(0, 1));
		detail_name.setText(contact.getName());
		company_name.setText(contact.getCompany());
		phone_number.setText(contact.getCellNumber());
		email_address.setText(contact.getEmail());
		qq_number.setText(contact.getQq());
		company_name2.setText(contact.getCompany());
		detail_address.setText(contact.getAddress());
		
		
		
	}
	
	public void more(View view)
	{
		AlertDialog.Builder ad=new AlertDialog.Builder(EcardDetailsActivity.this);
		ad.setItems(new String[]{"删除联系人","添加到系统联系人"},new itemOnClickListener());
		
		ad.show();
		
	}
	
	public void send_message(View view)
	{
		
		AlertDialog.Builder ad=new AlertDialog.Builder(EcardDetailsActivity.this);
		ad.setItems(new String[]{"拨打电话","发送短信"},new itemListonClick());
		ad.show();
		
	}
	
	class itemListonClick implements android.content.DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface arg0, int which) {
			switch(which){
			case 0:
				Intent intent1 =new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+contact.getCellNumber()));
				
				startActivity(intent1);
				break;
			case 1:
				Intent intent2 =new Intent();
				intent2.putExtra("CellNumber", contact.getCellNumber());
				intent2.setClass(EcardDetailsActivity.this, SmsActivity.class);
				startActivity(intent2);
				
				break;
			}
			
		}

	}
	
	class itemOnClickListener implements android.content.DialogInterface.OnClickListener
	{

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			switch (arg1) {
			case 0:
				myContactDao.delete(contact.getName());
				Toast.makeText(EcardDetailsActivity.this, "删除成功", 0).show();
				break;
			case 1:
				AddContactToSystem addContactToSystem = new AddContactToSystem(EcardDetailsActivity.this);
				addContactToSystem.saveContact(contact.getName(), contact.getCellNumber());
				Toast.makeText(EcardDetailsActivity.this, "添加成功", 0).show();
				break;
			default:
				break;
			}
			
		}
		
		
		
		
	}
	
}
