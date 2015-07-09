package com.gem.fs.ecard.index;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.gem.card.R;
import com.gem.fs.ecard.main.MainActivity;

public class IndexPage extends Activity {
	private RelativeLayout rl; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		rl=(RelativeLayout)findViewById(R.id.rl);
		startIndexAvtivity();
		rl.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				IndexPage.this.finish();
			}
			
		});
	}
	private void startIndexAvtivity() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(IndexPage.this, MainActivity.class);
				IndexPage.this.startActivity(intent);
				IndexPage.this.finish();
			}
		},2000);// 设置执行时间1秒
	}

}
