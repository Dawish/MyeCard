package com.gem.fs.ecard.bluetoothconnected;

import com.gem.fs.ecard.bean.ContactBean;
import com.google.gson.Gson;



public class GsonTools {
	
	
	
	private static Gson gson;
    static public String writeInforToGson(ContactBean card){
    	
    	int i =1;
    	
    	if(i>0)
    	{
    		System.out.println("dsdsdsds");
    	}
    	
    	 gson= new Gson();
    	return  gson.toJson(card);
    	
    }
    public ContactBean readInforFormGson(String mgson){
    	 gson=new Gson();
    	 return gson.fromJson(mgson, ContactBean.class );
    }
    
}
