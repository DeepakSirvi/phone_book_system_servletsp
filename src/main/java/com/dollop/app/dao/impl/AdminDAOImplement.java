package com.dollop.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dollop.app.bean.Admin;
import com.dollop.app.bean.User;
import com.dollop.app.dao.AdminDAOInerface;
import com.dollop.app.util.DbConnection;

public class AdminDAOImplement implements AdminDAOInerface {

	private final static String TOTAL_USER="select count(uId) from user where uId!=?";
    private final static String USER_SQL="select uId,uName,uEmail,uMobile,uAddress,uRole,uImage from user where uId!=?";
	private Connection con = null;
	private PreparedStatement pstmt = null;
	

	public List<User> viewAllUser(Integer uId) {
		List<User> list = new ArrayList<User>();
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(USER_SQL);
			pstmt.setInt(1, uId);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next())
			{
				User user = new User();
				user.setuId(rs.getInt(1));
				user.setuName(rs.getString(2));
				user.setuEmail(rs.getString(3));
				user.setuMobile(rs.getString(4));
				user.setuAddress(rs.getString(5));
				user.setuRole(rs.getString(6));
				user.setuImage(rs.getString(7));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;
	}
	
	public Integer countUser(Integer uId) {
		Integer count=null;
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(TOTAL_USER);
			pstmt.setInt(1, uId);
			ResultSet rs= pstmt.executeQuery();
			if(rs.next())
			{ 
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
