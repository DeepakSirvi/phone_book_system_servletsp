package com.dollop.app.dao.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.dollop.app.bean.User;
import com.dollop.app.dao.ContactDAOInterface;
import com.dollop.app.dao.GroupDAOInterface;
import com.dollop.app.dao.UserDAOInterface;
import com.dollop.app.util.DbConnection;

public class UserDAOImplement implements UserDAOInterface {
	private final static String ADD_SQL="INSERT INTO User (uName,uEmail,uMobile,uAddress,uRole,uPassword,uId) values(?,?,?,?,?,?,?)";
	private final static String UPDATE_SQL="Update user set uName=?,uEmail=?,uMobile=?,uAddress=?,uRole=? where uId=?";
	private final static String USER_LOGIN="select uId,uRole from user where uEmail=? and uPassword=?";
	private final static String USER_SQL="select uId,uName,uEmail,uMobile,uAddress,uRole,uImage from user where uId=?";
	private final static String DELETEUSER="delete from user where uId=?";
	
	
	private final static String IMG_SQL="update user set uImage=? where uId=?";
	private final static String UPDATE_PASSWORD="update user set uPassword=? where uId=? and uPassword=?";
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs=null;
	private Random random=new Random();
	
	

	public Boolean saveUser(User user) {
		con=DbConnection.getConnection();
		try {
			if(user.getuId()!=null)
			{

				pstmt=con.prepareStatement(UPDATE_SQL);
				pstmt.setInt(6, user.getuId());
			
			}
			else
			{
				pstmt=con.prepareStatement(ADD_SQL);
				pstmt.setString(6, user.getuPassword());
				pstmt.setInt(7, random.nextInt());
		
			}
			pstmt.setString(1, user.getuName());
			pstmt.setString(2, user.getuEmail());
			pstmt.setString(3, user.getuMobile());
			pstmt.setString(4, user.getuAddress());
			pstmt.setString(5, user.getuRole());
		    
			
			if(pstmt.executeUpdate()>0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	public User userLogin(String uemail, String upassword) {
		con=DbConnection.getConnection();
		User user=new User();
		try {
			pstmt=con.prepareStatement(USER_LOGIN);
			pstmt.setString(1, uemail);
			pstmt.setString(2, upassword);
			System.out.println(pstmt);
			rs=pstmt.executeQuery();
			if(rs.next())
			{
				user.setuId(rs.getInt("uId"));
				user.setuRole(rs.getString("uRole"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		}
	
	public User userById(Integer id) {

		con=DbConnection.getConnection();
		User user=new User();
		try {
			pstmt=con.prepareStatement(USER_SQL);
			pstmt.setInt(1, id);
			 rs= pstmt.executeQuery();
			    if(rs.next())
			    {
				user.setuId(rs.getInt(1));
				user.setuName(rs.getString(2));
				user.setuEmail(rs.getString(3));
				user.setuMobile(rs.getString(4));
				user.setuAddress(rs.getString(5));
				user.setuRole(rs.getString(6));
				user.setuImage(rs.getString(7));
			    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	public Boolean deleteUser(Integer id) {
		GroupDAOInterface gdo=new GroupDAOImplement();
		ContactDAOInterface cdo=new ContactDAOImplement();
		gdo.removeGroupFromFavForeign(id);
		gdo.deleteGroupByUid(id);
		cdo.deleteContactByUserId(id);
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(DELETEUSER);
			pstmt.setInt(1, id);
			if(pstmt.executeUpdate()>0)
			return true;
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	
	}

	
	@Override
		public Boolean insertImg(String uImg,Integer uId) {
		con = DbConnection.getConnection();
		try
		{
			pstmt=con.prepareStatement(IMG_SQL);
			InputStream in = new FileInputStream(uImg);
			pstmt.setBinaryStream(1,in);
			pstmt.setInt(2, uId);
			if(pstmt.executeUpdate()>0)
			{
				return true;
			}
			else
			{
				return false;
			}	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}	 
	}
	
	public Boolean updatePassword(String oldpwd,String newpwd,Integer uId)
	{
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(UPDATE_PASSWORD);
			pstmt.setString(1,newpwd);
			pstmt.setString(3, oldpwd);
			pstmt.setInt(2, uId);
			if(pstmt.executeUpdate()>0)
				return true;
				else
					return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	}

}
