package com.gem.fs.ecard.main;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.gem.card.R;
import com.gem.fs.ecard.bluetoothconnected.BluetoothChat;
import com.gem.fs.ecard.contactlist.ContactListActivity;
import com.gem.fs.ecard.setup.SetupActivity;
/**
 * 显示主界面
 * @author 谢磊
 *
 */
@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);  
		
		Resources resources = getResources();
        // 获取当前activity的标签,该方法的实现中已经执行了setContentView(com.android.internal.R.layout.tab_content);  
        TabHost tabHost = getTabHost();  
        // 每一个标签项  
        TabHost.TabSpec spec;   
  
        /** 
         * tabHost.newTabSpec("artist")创建一个标签项，其中artist为它的标签标识符，相当于jsp页面标签的name属性 
         * setIndicator("艺术标签",resources.getDrawable(R.drawable.ic_tab))设置标签显示文本以及标签上的图标（该图标并不是一个图片，而是一个xml文件哦） 
         * setContent(intent)为当前标签指定一个意图 
         * tabHost.addTab(spec); 将标签项添加到标签中 
         */  
        // 声明一个意图，该意图告诉我们，下一个跳转到的activity是ArtistActivity
        Intent intent1 = new Intent(this, ContactListActivity.class);  
        spec = tabHost.newTabSpec("IdCard").setIndicator("名片").setContent(intent1);  
        tabHost.addTab(spec); 
  
        Intent intent2 = new Intent(this, ExchangeActivity.class);  
        spec = tabHost.newTabSpec("Exchange").setIndicator("交换").setContent(intent2);  
        tabHost.addTab(spec); 
        
        Intent intent3 = new Intent(this, SetupActivity.class);  
        spec = tabHost.newTabSpec("Settings").setIndicator("设置").setContent(intent3);  
        tabHost.addTab(spec); 
        
        TabWidget tabWidget=this.getTabWidget();
 /*     View mView = tabWidget.getChildAt(1);//0是代表第一个Tab
        ImageView imageView = (ImageView)mView.findViewById(android.R.id.icon);//获取控件imageView
        imageView .setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher)); //改变我们需要的图标
  */
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
        	//获取控件TextView
            TextView tv=(TextView)tabWidget.getChildAt(i).findViewById(android.R.id.title);
            //获取控件imageView
            ImageView img = (ImageView)tabWidget.getChildAt(i).findViewById(android.R.id.icon);
            tv.setGravity(BIND_AUTO_CREATE);
            tv.setPadding(10, 10,10, 10);
            //设置字体的大小
            tv.setTextSize(18);
            //设置字体的颜色
            tv.setTextColor(Color.WHITE);
            img.setScaleType(ScaleType.FIT_XY);
        }
        
      //设置第一次打开时默认显示的标签，参数代表其添加到标签中的顺序，位置是从0开始的 
        tabHost.setCurrentTab(1);
	}
}
