package com.dollop.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dollop.app.bean.Contact;
import com.dollop.app.bean.Contact;
import com.dollop.app.dao.ContactDAOInterface;
import com.dollop.app.util.DbConnection;

public class ContactDAOImplement implements ContactDAOInterface {
	
	private final static String ADDCONTACT="INSERT INTO Contact (conName,conEmail,conMobile,conAddress,conIsFav,uId,conId) values(?,?,?,?,?,?,?)";
	private final static String UPDATECONTACT="Update Contact set conName=?,conEmail=?,conMobile=?,conAddress=? where conId=?";
	private final static String CHECKCONTACTBYCONTACT_SQL="select conId from Contact where conMobile=? and uId=?";
	private final static String ALLCONTACT_SQL="select * from contact where uId=? order by conIsFav desc,conName asc";
	private final static String DELETE_SQL="delete from Contact where conId=?";
	private final static String FAVCON="update contact set conIsFav=? where conId=?";
	private final static String CONTACT_SQL="select * from Contact where conId=?";
	private final static String DELETEALLCONTACTUID_SQL="delete from Contact where uId=?";


	private Connection con = null;
	private PreparedStatement pstmt = null;
	private Random random=new Random();
	public Boolean saveContact(Contact contact) {
		con=DbConnection.getConnection();
		//System.out.println(contact.getConId());
		try {
			if(contact.getConId()!=null)
			{

				pstmt=con.prepareStatement(UPDATECONTACT);
				pstmt.setInt(5, contact.getConId());
				
			
			
			}
			else
			{
				pstmt=con.prepareStatement(ADDCONTACT);
				pstmt.setInt(5, contact.getConIsFav());
				pstmt.setInt(6, contact.getuId());
				pstmt.setInt(7, random.nextInt());
				
			}
			pstmt.setString(1, contact.getConName());
			pstmt.setString(2, contact.getConEmail());
			pstmt.setString(3, contact.getConMobile());
			pstmt.setString(4, contact.getConAddress());
			
			
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
	
	public Integer checkContactByContact(String contact,Integer uId)
	{
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(CHECKCONTACTBYCONTACT_SQL);
			pstmt.setString(1,contact);
			pstmt.setInt(2, uId);
			ResultSet rs= pstmt.executeQuery();
			
			if(rs.next())
			{
			   return rs.getInt(1);
			}   
			else
			{
				return -1;
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}	
	}

	public List<Contact> viewAllContact(Integer uid) {

		List<Contact> list = new ArrayList<Contact>();
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(ALLCONTACT_SQL);
			pstmt.setInt(1, uid);
			ResultSet rs= pstmt.executeQuery();
			while(rs.next())
			{
				Contact contact = new Contact();
				contact.setConId(rs.getInt(1));
				contact.setConName(rs.getString(2));
				contact.setConEmail(rs.getString(3));
				contact.setConMobile(rs.getString(4));
				contact.setConAddress(rs.getString(5));
				contact.setConIsFav(rs.getInt(6));
				contact.setuId(rs.getInt(7));
		
				list.add(contact);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public Boolean deleteContact(Integer conid) {
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(DELETE_SQL);
			pstmt.setInt(1, conid);
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
	
	public Boolean modifyFav(Integer conId,Integer isFav)
	{
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(FAVCON);
			pstmt.setInt(1, isFav);
			pstmt.setInt(2, conId);
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

	public Contact contactById(Integer id) {
		con=DbConnection.getConnection();
	     Contact contact = new Contact();
		try {
			pstmt=con.prepareStatement(CONTACT_SQL);
			pstmt.setInt(1, id);
			ResultSet rs= pstmt.executeQuery();
			
			if(rs.next())
			{
				contact.setConId(rs.getInt(1));
				contact.setConName(rs.getString(2));
				contact.setConEmail(rs.getString(3));
				contact.setConMobile(rs.getString(4));
				contact.setConAddress(rs.getString(5));
			    contact.setConIsFav(rs.getInt(6));
			    contact.setuId(rs.getInt(7));
			}   
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contact;
	}

	public Boolean deleteContactByUserId(Integer uId)
	{
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(DELETEALLCONTACTUID_SQL);
			pstmt.setInt(1, uId);
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
