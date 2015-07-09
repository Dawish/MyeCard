package com.gem.fs.ecard.bean;

import java.io.Serializable;

public class ContactBean implements Serializable
{
	
	private String cardName;    //名片名称
	private String name;     	//联系人姓名
	private String cellNumber;  //手机号码
	private String phoneNumber;  //座机号码
	private String email;        //邮箱
	private String qq;           //QQ号码
	private String address;      //地址
	private String company;      //公司名称
	private String remark;       //备注
	
	public ContactBean() {   //无参数构造函数
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 有参构造函数
	 * @param cardName
	 * @param name
	 * @param cellNumber
	 * @param phoneNumber
	 * @param email
	 * @param qq
	 * @param address
	 * @param company
	 * @param remark
	 */
	
	public ContactBean(String cardName, String name, String cellNumber,
			String phoneNumber, String email, String qq, String address,
			String company, String remark) {
		super();
		this.cardName = cardName;
		this.name = name;
		this.cellNumber = cellNumber;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.qq = qq;
		this.address = address;
		this.company = company;
		this.remark = remark;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	
	
	
	
	
	
}
