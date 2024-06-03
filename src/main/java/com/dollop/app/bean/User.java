package com.dollop.app.bean;

public class User {
	private Integer uId;
	private String uName;
	private String uEmail;
	private String uPassword;
	private String uMobile;
	private String uAddress;
	private String uRole;
	private String uImage;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	public User(Integer uId, String uName, String uEmail, String uPassword, String uMobile, String uAddress,
			String uRole, String uImage) {
		super();
		this.uId = uId;
		this.uName = uName;
		this.uEmail = uEmail;
		this.uPassword = uPassword;
		this.uMobile = uMobile;
		this.uAddress = uAddress;
		this.uRole = uRole;
		this.uImage = uImage;
	}



	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuEmail() {
		return uEmail;
	}

	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getuPassword() {
		return uPassword;
	}

	public void setuPassword(String uPassword) {
		this.uPassword = uPassword;
	}

	public String getuMobile() {
		return uMobile;
	}

	public void setuMobile(String uMobile) {
		this.uMobile = uMobile;
	}

	public String getuAddress() {
		return uAddress;
	}

	public void setuAddress(String uAddress) {
		this.uAddress = uAddress;
	}

	public String getuRole() {
		return uRole;
	}

	public void setuRole(String uRole) {
		this.uRole = uRole;
	}
	

	public String getuImage() {
		return uImage;
	}



	public void setuImage(String uImage) {
		this.uImage = uImage;
	}



	@Override
	public String toString() {
		return "User [uId=" + uId + ", uName=" + uName + ", uEmail=" + uEmail + ", uPassword=" + uPassword
				+ ", uMobile=" + uMobile + ", uAddress=" + uAddress + ", uRole=" + uRole + ", uImage=" + uImage + "]";
	}

}
