package com.dollop.app.dao;

import java.util.List;

import com.dollop.app.bean.Contact;
import com.dollop.app.bean.User;

public interface UserDAOInterface {
	/* To insert and update the user */
	public Boolean saveUser(User user);

	/* To delete user by its id */
	public Boolean deleteUser(Integer id);

	/* To get user by Id */
	public User userById(Integer id);
	
	/* For user Login */
	public User userLogin(String uemail,String upassword);
	public Boolean insertImg(String uImg,Integer uId);
	public Boolean updatePassword(String oldpwd,String newpwd,Integer uId);
	

}
