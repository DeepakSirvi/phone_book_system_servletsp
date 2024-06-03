package com.dollop.app.dao;

import java.util.List;

import com.dollop.app.bean.Contact;
import com.dollop.app.bean.GroupDivision;
import com.dollop.app.bean.User;

public interface GroupDAOInterface {
	/* To insert and update the Group */
	public Boolean saveGroup(GroupDivision group);
	
	/* To delete group by its id */
	public Boolean deleteGroupByUid(Integer id);
	
	/* To get group by Id */ 
	public GroupDivision groupById(Integer id);
	public List<GroupDivision> groupByuId(Integer uid);
	public List<Contact> viewAllContact(Integer gid,Integer uid);
	public Boolean modifyFav(Integer conId,Integer isFav);
	public Boolean removeFromGroup(Integer conId);
	public Boolean removeAllContact(Integer gId);
	public Boolean removeGroupFromFavForeign(Integer uId);
	public Boolean deleteGroup(Integer id);
	public Boolean addContactToGroup(Integer uId,Integer gId,Integer conId);
	public Integer checkGroupByGroup(String gName,Integer uId);
}
