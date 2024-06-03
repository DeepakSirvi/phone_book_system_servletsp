package com.dollop.app.dao;

import java.util.List;

import com.dollop.app.bean.Admin;
import com.dollop.app.bean.User;

public interface AdminDAOInerface {

	/* To view all user */
	public List<User> viewAllUser(Integer uId);
	public Integer countUser(Integer uId);
}
