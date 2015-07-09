package com.gem.fs.ecard.contactlist;

import java.util.List;

import com.gem.card.R;



import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends Activity{
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.sms);
	        Intent intent = getIntent();
	        Button butsend=(Button) findViewById(R.id.butsend);
	        final EditText myEditText=(EditText)findViewById(R.id.mobile);
	        myEditText.setText(intent.getStringExtra("CellNumber"));
	        final EditText EditText2=(EditText)findViewById(R.id.content);
	        butsend.setOnClickListener(new OnClickListener() {
			
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String mobile=myEditText.getText().toString();
					 String content=EditText2.getText().toString();
					SmsManager sms=SmsManager.getDefault();
					PendingIntent sentintent =PendingIntent.getBroadcast(SmsActivity.this,
							0, new Intent(), 0);
					try {
						if(content.length()>70)
						{
							List<String> msgs=sms.divideMessage(content);
							for(String msg:msgs)
							{
								sms.sendTextMessage(mobile, null, msg, sentintent, null); 
							}
						}
						else
						{
						
							sms.sendTextMessage(mobile, null, content, sentintent, null);
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					Toast.makeText(SmsActivity.this, "短信发送成功", 1000).show();
				}
			});
			
		}

}
