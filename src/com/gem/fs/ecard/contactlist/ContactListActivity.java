package com.gem.fs.ecard.contactlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gem.card.R;
import com.gem.fs.ecard.adapter.SortAdapter;
import com.gem.fs.ecard.ado.MyContactAdo;
import com.gem.fs.ecard.bean.ContactBean;
import com.gem.fs.ecard.contactlist.SideBar.OnTouchingLetterChangedListener;

/**
 * 联系人列表activity
 * @author 李润、但行希
 */
public class ContactListActivity extends Activity {
	private Context				   context	= ContactListActivity.this;
	private ListView			   sortListView;
	private SideBar				   sideBar;
	private TextView			   dialog;
	private SortAdapter			   adapter;
    private ArrayList<ContactBean> myContactList;   //存放从数据库中得到的数据集合
    private MyContactAdo           myContactAdo;
    private String[]               mName , mImg;  //存放名字和图片的数组
    private ContactBean contact = new ContactBean();
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser		characterParser;
	private List<PhoneModel>	SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator	pinyinComparator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contactlist);
		
		myContactList = new ArrayList<ContactBean>();
		myContactAdo = new MyContactAdo(ContactListActivity.this);
		
		//添加测试数据
//		myContactAdo.add("工作", "雷军", "123435445", "7766888", "574554@qq.com", "56676543", "北京", "小米科技", "");
		myContactAdo.add("", "但行希", "123435445", "", "", "", "", "", "");
//		myContactAdo.add("", "谢磊", "123435445", "", "", "", "", "", "");
//		myContactAdo.add("", "李润", "123435445", "", "", "", "", "", "");
//		myContactAdo.add("", "成晓知", "123435445", "", "", "", "", "", "");
//		myContactAdo.add("", "马云", "123435445", "", "", "", "", "", "");
//		myContactAdo.add("", "陈姗", "123435445", "", "", "", "", "", "");
//		myContactAdo.add("", "任正非", "123435445", "", "", "", "", "", "");
		
		myContactList = myContactAdo.findAll();  //从数据库得到全部的数据
		initData();  //初始化数据
		initViews();  //初始化界面
	}

	/**
	 * 初始化得到数据，建立对象加入到list中，并返回一个list对象
	 * @param date 姓名字符串数组
	 * @param imgData 头像ID数组
	 * @return
	 */
	private List<PhoneModel> filledData(String[] date, String[] imgData) {
		List<PhoneModel> mPhoneList = new ArrayList<PhoneModel>();

		for (int i = 0; i < date.length-1; i++) {
			PhoneModel phoneModel = new PhoneModel();
			phoneModel.setImgSrc(imgData[i]);
			phoneModel.setName(date[i]);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")) {
				phoneModel.setSortLetters(sortString.toUpperCase());
			} else {
				phoneModel.setSortLetters("#");
			}

			mPhoneList.add(phoneModel);  //通过验证就加入到ArrayList
			//System.out.println("==============>"+phoneModel.getName());
			//System.out.println("--------------------------------->"+mPhoneList.size());
		}
		
		//System.out.println("--------------------------------->"+mPhoneList.size());
		return mPhoneList;

	}

	/**
	 * 显示我们要看到的
	 */
	
	private void initViews() {
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);   //通过id得到listview控件
		//为listview控件中的 每一个小项绑定监听器
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				//Toast.makeText(context, ((PhoneModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
			    contact = myContactList.get(position);  //得到用户点击的对象
			    System.out.println(contact.getName()+"---------------->");
			    Intent intent = new Intent(ContactListActivity.this,EcardDetailsActivity.class);
			    
			    Bundle bundle = new Bundle();
			    
			    bundle.putSerializable("contact", contact);
			    
			    intent.putExtras(bundle);
			    
			    startActivity(intent);
			    
			}
		});

		//通过filledData方法传入两个字符串数组，得到的是一个arraylist集合，在设置listview适配器的时候这个集合作为一个参数传入
		SourceDateList = filledData(mName, mImg);

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(context, SourceDateList);   //新建一个适配器，传的参数是上下文和
		sortListView.setAdapter(adapter);
		
		
		
		
	}
	
	private void initData()
	{
		
		int size = myContactList.size();  //得到集合的大小，方便遍历集合
		mName = new String[size];  //为名字数组分配内存
		mImg = new String[size];   //为图片数组分配内存
		for (int i = 0; i < size-1; i++) 
		{
		
			mName[i] = myContactList.get(i).getName();
			
			mImg[i] = R.drawable.head_img+"";
		}
		
		
		
		
	} 
	
	//当界面再次出现的时候刷新界面上listview里面的内容
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myContactList = myContactAdo.findAll();  //从数据库得到全部的数据
		initData();  //初始化数据
		initViews();  //初始化界面
	}
	
}
