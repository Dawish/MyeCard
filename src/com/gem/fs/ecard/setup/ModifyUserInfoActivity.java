package com.gem.fs.ecard.setup;

import com.gem.card.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
/**
 * 修改用户资料
 * @author 陈姗
 *
 */
public class ModifyUserInfoActivity extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_modifyuserinfo);
		
		
	}

}
