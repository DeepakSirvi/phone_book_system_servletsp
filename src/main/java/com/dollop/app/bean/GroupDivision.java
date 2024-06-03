package com.dollop.app.bean;

public class GroupDivision {
	private Integer gId;
	private Integer uId;
	private String gName;
	private Integer gFav;
	public GroupDivision() {
		super();
		// TODO Auto-generated constructor stub
	}
   
	public GroupDivision(Integer gId, Integer uId, String gName, Integer gFav) {
		super();
		this.gId = gId;
		this.uId = uId;
		this.gName = gName;
		this.gFav = gFav;
	}
	public Integer getgId() {
		return gId;
	}
	public void setgId(Integer gId) {
		this.gId = gId;
	}
	
	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getgName() {
		return gName;
	}
	public void setgName(String gName) {
		this.gName = gName;
	}
	public Integer getgFav() {
		return gFav;
	}
	public void setgFav(Integer gFav) {
		this.gFav = gFav;
	}
	@Override
	public String toString() {
		return "GroupDivision [gId=" + gId + ", uId=" + uId + ", gName=" + gName + ", gFav=" + gFav + "]";
	}
	

}
