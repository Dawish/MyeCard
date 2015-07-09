package com.gem.fs.ecard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gem.card.R;


public class MySpinnerAdapter extends BaseAdapter {

	private Context mContext;
	private String[] mData; 
	
	public MySpinnerAdapter(Context mContext,String[] mData) 
	{
		this.mContext = mContext;
		this.mData = mData;
	}
	
	@Override
	public int getCount() {
		
		return mData.length;   
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

	/*
	 * getView����ҪҪ��д�ķ��������ص���һ��view����
	 * 
	 * position������ѡ��� item�ĵڼ�����0��ʼ
	 * convertView����item�ϵĲ���layout�������
	 * ViewGroup parent����������adapter���Ǹ���������װһ��viewGroup����ʢ��item��һ�����ʹ��
	 */
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//���ഴ��һ������ӳ����
		
		System.out.println("==============>>"+position);
		
		LayoutInflater mInflater = LayoutInflater.from(mContext);
		
		//���ò���ӳ�����õ�item�Ĳ����ļ�
		
		ViewHolder holder;
		
		if(convertView == null)   //���item�Ĳ����ļ�Ϊ�����Ǿ�Ϊ�䴴��һ��
		{
			convertView = mInflater.inflate(R.layout.myspinner_item, null);
			
			holder = new ViewHolder();
			convertView.setTag(holder);
		}
		else  //���item�Ĳ����ļ���Ϊ�գ�����ȡtag��ǩ
		{
			
			holder = (ViewHolder) convertView.getTag();
			
		}
		
		
		holder.tv = (TextView) convertView.findViewById(R.id.tv);
		holder.tv.setText(mData[position]);
		
		
		
		return convertView;
	}

	class ViewHolder  
	{
		
		private TextView tv;
		
	}
	
}
