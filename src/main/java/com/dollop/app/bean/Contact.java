package com.dollop.app.bean;

public class Contact {
	private Integer conId;
	private String conName;
	private String conEmail;
	private String conMobile;
	private String conAddress;
	private Integer conIsFav;
	private Integer uId;
	public Contact(Integer conId, String conName, String conEmail, String conMobile, String conAddress,
			Integer conIsFav, Integer uId) {
		super();
		this.conId = conId;
		this.conName = conName;
		this.conEmail = conEmail;
		this.conMobile = conMobile;
		this.conAddress = conAddress;
		this.conIsFav = conIsFav;
		
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Integer getuId() {
		return uId;
	}
	public void setuId(Integer uId) {
		this.uId = uId;
	}
		public Integer getConId() {
		return conId;
	}
	public void setConId(Integer conId) {
		this.conId = conId;
	}
	public String getConName() {
		return conName;
	}
	public void setConName(String conName) {
		this.conName = conName;
	}
	public String getConEmail() {
		return conEmail;
	}
	public void setConEmail(String conEmail) {
		this.conEmail = conEmail;
	}
	public String getConMobile() {
		return conMobile;
	}
	public void setConMobile(String conMobile) {
		this.conMobile = conMobile;
	}
	public String getConAddress() {
		return conAddress;
	}
	public void setConAddress(String conAddress) {
		this.conAddress = conAddress;
	}
	public Integer getConIsFav() {
		return conIsFav;
	}
	public void setConIsFav(Integer conIsFav) {
		this.conIsFav = conIsFav;
	}
	@Override
	public String toString() {
		return "Contact [conId=" + conId + ", conName=" + conName + ", conEmail=" + conEmail + ", conMobile="
				+ conMobile + ", conAddress=" + conAddress + ", conIsFav=" + conIsFav + ", uId=" + uId + "]";
	}
	
}
