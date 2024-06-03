package com.dollop.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.dollop.app.bean.Contact;

public interface ContactDAOInterface {
	
	/* To insert and update the Contact*/
	public Boolean saveContact(Contact contact);

	/* To delete contact by its id */
	public Boolean deleteContact(Integer id);
	
	/* To get contact by Id */
	public Contact contactById(Integer id);

	/* To view all contact belong to specific user id */
	public List<Contact> viewAllContact(Integer uid);
	
	public Boolean modifyFav(Integer conId,Integer isFav);
	public Boolean deleteContactByUserId(Integer uId);
	public Integer checkContactByContact(String contact,Integer uId);

}
