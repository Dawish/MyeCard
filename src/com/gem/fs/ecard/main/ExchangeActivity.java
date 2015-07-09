package com.gem.fs.ecard.main;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.gem.card.R;
import com.gem.fs.ecard.ado.MyCardAdo;
import com.gem.fs.ecard.bean.ContactBean;
import com.gem.fs.ecard.bluetoothconnected.BluetoothChat;
import com.gem.fs.ecard.setup.EditMyCardActivity;
import com.gem.fs.ecard.setup.MyCardListActivity;

public class ExchangeActivity extends Activity {

	private Spinner spinner_Card;
	private ArrayList<ContactBean> myCardList;
	private ArrayList<String> Str = new ArrayList<String>();
	private ArrayAdapter<String> contactAdapter;
	private MyCardAdo myCardDao;
	private ImageButton imgbtn_exchange;
	private String card_message;
	private ContactBean myCard = new ContactBean();  // 最为交换的用户名片传递到下一个界面去
	private BluetoothAdapter mBluetoothAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange);
		imgbtn_exchange = (ImageButton) findViewById(R.id.imgbtn_exchange);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		 if (mBluetoothAdapter.getScanMode() !=
		            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
		            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		            startActivity(discoverableIntent);
		        }
		imgbtn_exchange.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(ExchangeActivity.this,
						BluetoothChat.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("myCard", myCard);
				
				intent.putExtras(bundle);
				
				startActivity(intent);
			}
		});

		spinner_Card = (Spinner) findViewById(R.id.spinner_IdCard);   //在交换界面标题栏显示，
		myCardDao = new MyCardAdo(ExchangeActivity.this);
		myCardList = new ArrayList<ContactBean>();
		myCardList = (ArrayList<ContactBean>) myCardDao.findAll();  //从数据库得到用户所有的名片
		for (int i = 0; i < myCardList.size(); i++) {

			card_message = myCardList.get(i).getCardName() + "("
					+ myCardList.get(i).getCellNumber() + ")";
			Str.add(card_message);
		}
		
		contactAdapter = new ArrayAdapter<String>(this,
				R.layout.exchange_spinner, Str);
		
		spinner_Card.setAdapter(contactAdapter);
		
		//为spinner_Card设置监听器
		
		spinner_Card.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) 
			{  //根据position位置来从数据库中得到的对象集合中取出一个对象
				myCard = myCardList.get(position);   //这就是当前用户选中的名片对象
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				
			}
		});

	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		myCardList = (ArrayList<ContactBean>) myCardDao.findAll();
		if (myCardList.size() != spinner_Card.getCount()) 
		{
			contactAdapter.clear();
			for (int i = 0; i < myCardList.size(); i++) 
			{

				card_message = myCardList.get(i).getCardName() + "("
						+ myCardList.get(i).getCellNumber() + ")";
				Str.add(card_message);
			}
			contactAdapter = new ArrayAdapter<String>(this,
					R.layout.exchange_spinner, Str);
			spinner_Card.setAdapter(contactAdapter);
		} 
		else
		{
//			Toast.makeText(ExchangeActivity.this, "Spinner没有变化",
//					Toast.LENGTH_SHORT).show();
			
		}
	}
	
	
}