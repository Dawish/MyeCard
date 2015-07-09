package com.gem.fs.ecard.setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.gem.card.R;
/**
 * 关于软件的界面
 * @author 陈念念
 *
 */
public class AboutsoftwareActivity extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aboutsoftware);
		
		
		
		
		
	}
	
	public void returnActivitySetup(View view)
	{
		
		Intent intent = new Intent(AboutsoftwareActivity.this,SetupActivity.class);
		startActivity(intent);
		
	}

}
