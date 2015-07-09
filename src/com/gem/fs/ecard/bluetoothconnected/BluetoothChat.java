package com.gem.fs.ecard.bluetoothconnected;

import java.util.ArrayList;

import com.gem.card.R;
import com.gem.fs.ecard.ado.MyCardAdo;
import com.gem.fs.ecard.ado.MyContactAdo;
import com.gem.fs.ecard.bean.ContactBean;
import com.gem.fs.ecard.main.ExchangeActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


@SuppressLint("HandlerLeak")
public class BluetoothChat extends Activity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private TextView contectDeviceName;
    private ListView mConversationView;
    private Button mSendButton;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothChatService mChatService = null;
    //private Information infor=new Information("limin","1234567889");
    private GsonTools tool=new GsonTools();

    private ContactBean sendCard = new ContactBean();
    
    private MyContactAdo myContactDao = new MyContactAdo(BluetoothChat.this);
    private Spinner spinner_myCard;
    private MyCardAdo myCardDao;
    private String card_message;
    private ArrayList<ContactBean> myCardList;
    private ArrayAdapter<String> contactAdapter;
    private ContactBean myCard = new ContactBean();
    private ArrayList<String> Str = new ArrayList<String>();
  
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        //得到父activity传递过来的名片对象
        
        Intent intent = getIntent();
        
        Bundle bundle = intent.getExtras();  //得到bundle对象
        
        //sendCard = (ContactBean) bundle.getSerializable("myCard");
        
        // Set up the window layout
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bluetoothc_chat);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.bluetooth_custom_title);
        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        contectDeviceName = (TextView) findViewById(R.id.connected_device_name);
        spinner_myCard = (Spinner) findViewById(R.id.spinner_myCard);
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
           // finish();
            //return;
        }
        
        myCardDao = new MyCardAdo(BluetoothChat.this);
		myCardList = new ArrayList<ContactBean>();
		myCardList = (ArrayList<ContactBean>) myCardDao.findAll();  //从数据库得到用户所有的名片
		for (int i = 0; i < myCardList.size(); i++) {

			card_message = myCardList.get(i).getCardName() + "("
					+ myCardList.get(i).getCellNumber() + ")";
			Str.add(card_message);
		}
		
		contactAdapter = new ArrayAdapter<String>(this,
				R.layout.exchange_spinner, Str);
		
		spinner_myCard.setAdapter(contactAdapter);
		
		//为spinner_Card设置监听器
		
		spinner_myCard.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) 
			{  //根据position位置来从数据库中得到的对象集合中取出一个对象
				sendCard = myCardList.get(position);   //这就是当前用户选中的名片对象
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				
			}
		});
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
        	mBluetoothAdapter.enable();
        	
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");       
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.bluetooth_message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the send button with a listener that for click events
       /* mSendButton = (Button) findViewById(R.id.button_send);
        mSendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget
                //TextView view = (TextView) findViewById(R.id.edit_text_out);
                String message = tool.writeInforToGson(sendCard);  //把对象转换为字符串
                sendMessage(message); //发送字符串
            }
        });
        
        */

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    public void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.发送消息的方法
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }

    
    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                	contectDeviceName.setText("当前链接：");
                	contectDeviceName.append(mConnectedDeviceName); //显示当前连接设备的名字
                    mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                	contectDeviceName.setText("链接中...");
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                	contectDeviceName.setText("无连接...");
                    break; 
                }
                break;
            case MESSAGE_WRITE:  //界面上显示的我的消息
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                mConversationArrayAdapter.add("Me:  " + sendCard.getName()+"\n"+sendCard.getCellNumber());
                break;
            case MESSAGE_READ:  //读消息
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1); //读到的消息也是一个字符串
                ContactBean readCard=tool.readInforFormGson(readMessage); //把得到的字符串转为为一个对象
                myContactDao.addObject(readCard);
                Toast.makeText(BluetoothChat.this, "交换成功", 0).show();
                mConversationArrayAdapter.add( readCard.getName()+"\n"+readCard.getCellNumber()); //取出数据显示在界面上面
                break;
            case MESSAGE_DEVICE_NAME:
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);  //得到链接设备的名字
                Toast.makeText(getApplicationContext(), "Connected to "  //告诉用户链接的是那个设备
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
            } else {
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scan:
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        case R.id.discoverable:
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        
        return false;
    }
    
    /**
     * 打开可见性
     * @param view
     */
//    public void discoverable(View view)
//    {
//    	ensureDiscoverable();
//    } 
    
    /**
     * 链接其他设备
     * @param view
     */
    public void connect(View view)
    {
    	
    	Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    } 
    
    /**
     * 交换名片
     */
    public void exchange(View view)
    {
    	 String myCardMessage = tool.writeInforToGson(sendCard);  //把对象转换为字符串
         sendMessage(myCardMessage); //发送字符串
         
    	
    }

}