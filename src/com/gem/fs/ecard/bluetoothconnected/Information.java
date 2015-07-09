package com.gem.fs.ecard.bluetoothconnected;

import java.io.Serializable;

public class Information implements  Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String num;
	 public Information(String name,String num){
		 this.name=name;
		 this.num=num;
	 }
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name+"\n"+num+"\n";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
  
}
