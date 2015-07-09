package com.gem.fs.ecard.adapter;

import java.util.ArrayList;


import com.gem.card.R;
import com.gem.fs.ecard.bean.ContactBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EditMyCardListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<ContactBean> mList;
	
	public EditMyCardListAdapter(Context context, ArrayList<ContactBean> mList) {
		super();
		this.context = context;
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHandler handler;
		
		if(convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			
			convertView = inflater.inflate(com.gem.card.R.layout.contact_item, null);
			handler = new ViewHandler();
			convertView.setTag(handler);
			 
			
		}
		else
		{
			handler = (ViewHandler) convertView.getTag();
			
		}
		
		handler.item_contactImg = (ImageView) convertView.findViewById(R.id.item_contactImg);
		handler.item_name = (TextView) convertView.findViewById(R.id.item_name);
		handler.item_number = (TextView) convertView.findViewById(R.id.item_number);
		
		handler.item_contactImg.setBackgroundResource(R.drawable.img_list_edit);
		handler.item_name.setText(mList.get(position).getCardName());
		handler.item_number.setText(mList.get(position).getName()+":"+mList.get(position).getCellNumber());
		
		return convertView;
	}
	
	public class ViewHandler
	{
		private ImageView item_contactImg;
		private TextView item_name;
		private TextView item_number;
		
		
	}

}
