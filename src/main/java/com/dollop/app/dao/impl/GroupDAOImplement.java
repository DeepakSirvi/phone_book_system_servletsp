package com.dollop.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dollop.app.bean.Contact;
import com.dollop.app.bean.GroupDivision;
import com.dollop.app.dao.GroupDAOInterface;
import com.dollop.app.util.DbConnection;

public class GroupDAOImplement implements GroupDAOInterface{
	
	private final static String ALLGROUPBYUID="select * from  phonebook.Group where uId=? order by gFav desc,gName asc";
	private final static String FAVGROUP="update phonebook.group set gFav=? where gId=?";
	private final static String CHECKGROUPBYGROUP_SQL="select gId from phonebook.group where gName=? and uId=?";
	private final static String ADD_SQL="INSERT INTO phonebook.Group (gName,gFav,uId,gId) values(?,?,?,?)";
	private final static String UPDATE_SQL="Update phonebook.Group set gName=? where gId=? and uId=?";
	private final static String GROUP_SQL="select * from  phonebook.Group where gId=?";
	private final static String GROUPCONTACT="select * from Contact where conId in(select conId from favforeign where uId=? and gId=?)";
	private final static String ADDTOGROUP="insert Into favforeign values(?,?,?)";
	private final static String REMOVEFROMGROUP="delete from favforeign where conId=?";
	private final static String DELETEALLCONTACT="delete from favforeign where gId=?";
	private final static String DELETE_SQL="delete from phonebook.Group where gId=?";
	private final static String DELETEBYUID_SQL="delete from phonebook.Group where uId=?";
	private final static String DELETEALLUSERFAV="delete from favforeign where uId=?";

	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private Random random=new Random();

	public List<GroupDivision> groupByuId(Integer uid) {
		con=DbConnection.getConnection();
		List<GroupDivision> glist = new ArrayList<GroupDivision>();
		try {
			pstmt=con.prepareStatement(ALLGROUPBYUID);
			pstmt.setInt(1, uid);
			ResultSet rs= pstmt.executeQuery();
			
			   while(rs.next())
			   {
				 GroupDivision group = new GroupDivision();
				group.setgId(rs.getInt(1));
				group.setgName(rs.getString(2));
				group.setgFav(rs.getInt(3));
				group.setuId(rs.getInt(4));
				glist.add(group);
			   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return glist;
	}

	public Boolean modifyFav(Integer gId,Integer isFav)
	{
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(FAVGROUP);
			pstmt.setInt(1, isFav);
			pstmt.setInt(2, gId);
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
	
	public Integer checkGroupByGroup(String gName,Integer uId)
	{
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(CHECKGROUPBYGROUP_SQL);
			pstmt.setString(1,gName);
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

	public Boolean saveGroup(GroupDivision group) {
		con=DbConnection.getConnection();
		try {
			if(group.getgId()!=null)
			{

				pstmt=con.prepareStatement(UPDATE_SQL);
				pstmt.setInt(2, group.getgId());
				pstmt.setInt(3, group.getuId());
				
			
			}
			else
			{
				pstmt=con.prepareStatement(ADD_SQL);
				pstmt.setInt(2, group.getgFav());
				pstmt.setInt(3, group.getuId());
				pstmt.setInt(4, random.nextInt());
			}
			pstmt.setString(1, group.getgName());
		
			
						
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
	
    public GroupDivision groupById(Integer id) {
		con=DbConnection.getConnection();
		GroupDivision group = new GroupDivision();
		try {
			pstmt=con.prepareStatement(GROUP_SQL);
			pstmt.setInt(1, id);
			ResultSet rs= pstmt.executeQuery();
			
			rs.next();
				group.setgId(rs.getInt(1));
				group.setgName(rs.getString(2));
				group.setgFav(rs.getInt(3));
				group.setuId(rs.getInt(4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return group;
	}

	public List<Contact> viewAllContact(Integer gid,Integer uid) {
		List<Contact> list = new ArrayList<Contact>();
		con = DbConnection.getConnection();
		
		try {
			pstmt=con.prepareStatement(GROUPCONTACT);
			pstmt.setInt(1, uid);
			pstmt.setInt(2, gid);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				Contact contact = new Contact();
				contact.setConId(rs.getInt(1));
				contact.setConName(rs.getString(2));
				contact.setConEmail(rs.getString(3));
				contact.setConMobile(rs.getString(4));
				contact.setConAddress(rs.getString(5));
			    contact.setConIsFav(rs.getInt(6));
				list.add(contact);
				//System.out.println(contact);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Boolean addContactToGroup(Integer uId, Integer gId, Integer conId) {
		con=DbConnection.getConnection();
		try {
				pstmt=con.prepareStatement(ADDTOGROUP);
				pstmt.setInt(1, uId);
				pstmt.setInt(2, gId);
				pstmt.setInt(3, conId);						
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

	public Boolean removeFromGroup(Integer conId) {
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(REMOVEFROMGROUP);
			pstmt.setInt(1, conId);
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

	public Boolean removeAllContact(Integer gId) {
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(DELETEALLCONTACT);
			pstmt.setInt(1, gId);
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
	
	public Boolean deleteGroup(Integer id) {
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(DELETE_SQL);
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
	
	public Boolean deleteGroupByUid(Integer id) {
		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(DELETEBYUID_SQL);
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
	
	public Boolean removeGroupFromFavForeign(Integer uId)
	{

		con=DbConnection.getConnection();
		try {
			pstmt=con.prepareStatement(DELETEALLUSERFAV);
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
