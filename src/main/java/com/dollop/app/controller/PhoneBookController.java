package com.dollop.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.dollop.app.bean.Contact;
import com.dollop.app.bean.GroupDivision;
import com.dollop.app.bean.User;
import com.dollop.app.dao.AdminDAOInerface;
import com.dollop.app.dao.ContactDAOInterface;
import com.dollop.app.dao.GroupDAOInterface;
import com.dollop.app.dao.UserDAOInterface;
import com.dollop.app.dao.impl.AdminDAOImplement;
import com.dollop.app.dao.impl.ContactDAOImplement;
import com.dollop.app.dao.impl.GroupDAOImplement;
import com.dollop.app.dao.impl.UserDAOImplement;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class PhoneBookController
 */
public class PhoneBookController extends HttpServlet {
	private AdminDAOInerface ado = new AdminDAOImplement();
	private UserDAOInterface udo = new UserDAOImplement();
	private GroupDAOInterface gdo = new GroupDAOImplement();
	private ContactDAOInterface cdo = new ContactDAOImplement();
	private RequestDispatcher rd = null;
	private User user = null;
	private GroupDivision group = null;
	private Contact contact = null;
	private List<Contact> conlist = null;
	private List<GroupDivision> glist = null;
	private HttpSession session = null;
	

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public PhoneBookController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("Action");
		session = request.getSession();
		if(session.getAttribute("uId")!=null||action.equals("SIGNUPUSER")||action.equals("USERLOGIN"))
		{
			switch (action) {
			
		case "SIGNUPUSER":
			user = new User();
			user.setuName(request.getParameter("uname"));
			user.setuEmail(request.getParameter("uemail"));
			user.setuMobile(request.getParameter("umobile"));
			user.setuAddress(request.getParameter("uaddress"));
			user.setuRole(request.getParameter("urole"));
			user.setuPassword(request.getParameter("upassword"));
			if(udo.saveUser(user))
			login(request, response);
			else
			{
				request.setAttribute("message", "Email is Already exist..!");
				request.setAttribute("user", user);
				rd = request.getRequestDispatcher("./Sign.jsp");
				rd.forward(request, response);
				
			}
			break;
		case "DASHBOARD":
			userToDesktop(request, response);
		break;

		case "USERLOGIN":
			user = udo.userLogin(request.getParameter("uemail"), request.getParameter("upassword"));
			if (user.getuId()!= null) {
				if (user.getuRole().equals("User")) {
					session.setAttribute("uId", user.getuId());
					userToDesktop(request, response);
				} else {
					session.setAttribute("uId", user.getuId());
					userToDesktop(request, response);
				}
			} else {
				request.setAttribute("message","Invalid User Id or Password");
				login(request, response);
			}
			break;
		
		case "LOGOUT":
			session.invalidate();
			login(request, response);
			break;
			
		case "UPDATEPROFILE":
			
			user.setuId((Integer) session.getAttribute("uId"));
			user.setuName(request.getParameter("uname"));
			user.setuEmail(request.getParameter("uemail"));
			user.setuMobile(request.getParameter("umobile"));
			user.setuAddress(request.getParameter("uaddress"));
			if(udo.saveUser(user))
			userToDesktop(request, response);
			else
			{
				request.setAttribute("message", "This emailis already exist");
				profile(request,response);
				
			}
			break;

		case "GETUSER":
			profile(request, response);
			break;

		case "TOCREATECONTACT":
			toCreateContact(request, response);
			break;

		case "ADDCONTACT":
			contact = new Contact();
			contact.setConName(request.getParameter("conname"));
			contact.setConEmail(request.getParameter("conemail"));
			contact.setConAddress(request.getParameter("conaddress"));
			contact.setConMobile(request.getParameter("conmobile"));
			contact.setConIsFav(Integer.parseInt(request.getParameter("conisfav")));
			contact.setuId((Integer) session.getAttribute("uId"));
			if(cdo.checkContactByContact(request.getParameter("conmobile"),(Integer) session.getAttribute("uId")).equals(-1))
			{
				cdo.saveContact(contact);
				viewContact(request, response);
			}
			else
			{
				request.setAttribute("message", "Contact already exist");
				request.setAttribute("contact", contact);
				user = udo.userById((Integer) session.getAttribute("uId"));
				request.setAttribute("user", user);
				glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
				request.setAttribute("glist", glist);
				Integer count = ado.countUser((Integer) session.getAttribute("uId"));
				request.setAttribute("count", count);
				rd = request.getRequestDispatcher("./pages/ui-features/fordublicatecontact.jsp");
				rd.forward(request, response);
			}
			
			break;

		case "TOVIEWCONTACT":
			viewContact(request, response);
			break;

		case "DELETECONTACT":
			cdo.deleteContact(Integer.parseInt(request.getParameter("conid")));
			viewContact(request, response);
			break;
		
		case "TOEDITCONTACT":
			toEditContact(request, response);
			break;
		

		case "UPDATECONTACT":
			contact.setConId(Integer.parseInt(request.getParameter("conid")));
			contact.setConName(request.getParameter("conname"));
			contact.setConEmail(request.getParameter("conemail"));
			contact.setConMobile(request.getParameter("conmobile"));
			contact.setConAddress(request.getParameter("conaddress"));
			Integer cId=cdo.checkContactByContact(request.getParameter("conmobile"),(Integer) session.getAttribute("uId"));
		    if(cId.equals(-1) || cId.equals(Integer.parseInt(request.getParameter("conid"))))
			{
				cdo.saveContact(contact);
				viewContact(request, response);
			}
			else
			{
				request.setAttribute("message", "Contact already exist");
				toEditContact(request, response);
			}
			break;

		case "FAVOURITE":
			Integer isFav = Integer.parseInt(request.getParameter("isfav"));
			if (isFav == 0)
				isFav = 1;
			else
				isFav = 0;
			cdo.modifyFav(Integer.parseInt(request.getParameter("conid")), isFav);
			viewContact(request, response);
			break;
			
		
		case "GROUPFAVOURITE":
			Integer gFav = Integer.parseInt(request.getParameter("gfav"));
			if (gFav == 0)
				gFav = 1;
			else
				gFav = 0;
			gdo.modifyFav(Integer.parseInt(request.getParameter("gid")), gFav);
			viewGroup(request, response);
			break;

		case "UPDATEPASSWORD":
			String oldpwd = request.getParameter("password");
			String newpwd = request.getParameter("newpassword");
			String conpwd = request.getParameter("confirmPassword");
			if (newpwd.equals(conpwd)) {
				if (udo.updatePassword(oldpwd, newpwd, (Integer) session.getAttribute("uId"))) {
					session.invalidate();
					login(request, response);
				} else {
					request.setAttribute("message", "Current Password not match new password");
					userToDesktop(request, response);
				}
			}

			else {
				request.setAttribute("message", "Confirm password is not match");
				userToDesktop(request, response);
			}
			break;

		case "DELETEACCOUNT":
			if (udo.deleteUser((Integer) session.getAttribute("uId"))) {
				session.invalidate();
				login(request, response);
			} else {
				request.setAttribute("message"," Unable delete account");
				userToDesktop(request, response);
			}
			break;
	
		case "CREATEGROUP":
			toCreateGroup(request, response);
			break;
		case "EDITGROUP":
			toCreateGroup(request, response);
			break;

		case "SAVEGROUP":
			group = new GroupDivision();
			if(!request.getParameter("gid").equals(""))
			{
			group.setgId(Integer.valueOf(request.getParameter("gid")));
			}
			group.setgName(request.getParameter("groupname"));
			group.setuId((Integer) session.getAttribute("uId"));
			group.setgFav(0);
			Integer gId=gdo.checkGroupByGroup(group.getgName(),group.getuId());
			if(gId.equals(-1))
			{
				
				gdo.saveGroup(group);
				userToDesktop(request, response);
			}
			else if(!request.getParameter("gid").equals(""))
			{
		    if(gId.equals(Integer.parseInt(request.getParameter("gid"))))
			{
				userToDesktop(request, response);
			}
			}
			else
			{
				request.setAttribute("group", group);
				request.setAttribute("message", "Group already exist");
				user = udo.userById((Integer) session.getAttribute("uId"));
				request.setAttribute("user", user);
				glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
				request.setAttribute("glist", glist);
				Integer count = ado.countUser((Integer) session.getAttribute("uId"));
				request.setAttribute("count", count);
				rd = request.getRequestDispatcher("./pages/ui-features/CreateGroup.jsp");
				rd.forward(request, response);
			}
			break;

		case "GETGROUP":
			viewGroup(request, response);
			break;
			

		case "CONADDGROUP":
			contactViewToAddedGroup(request, response);
			break;

		case "ADDTOGROUP":
			String subject[] = request.getParameterValues("contact");
			Integer conId=null;
				if (subject != null) {
					for (int i = 0; i < subject.length; i++)
					{
					conId = Integer.parseInt(subject[i]);
				   gdo.addContactToGroup((Integer)session.getAttribute("uId"),Integer.parseInt(request.getParameter("gid")),conId);
					}
					request.setAttribute("NOTIFICATION", "Contact Deleted Successfully!");
					viewGroup(request, response);
				break;
		     }
					else 
					{
				request.setAttribute("NOTIFICATION", "Something went wrong!!!");
				viewGroup(request, response);
				break;
			}	
		case "REMOVEFROMGROUP":
			gdo.removeFromGroup(Integer.parseInt(request.getParameter("conid")));
			viewGroup(request, response);
			break;
			
		case "REMOVEGROUP":
			gdo.removeAllContact(Integer.parseInt(request.getParameter("gid")));
			gdo.deleteGroup(Integer.parseInt(request.getParameter("gid")));
			userToDesktop(request, response);
			break;		
	
			
		case "VIEWUSER":
			viewUser(request, response);
			break;

		case "ADMINVIEWCONTACT":
			adminViewToContactOfUser(request, response);
			break;

		case "ADMINDELETEUSER":
			if (udo.deleteUser(Integer.parseInt(request.getParameter("uidbyadmin")))) {
				viewUser(request, response);
			} else {
				request.setAttribute("message"," Unable delete account");
				viewUser(request, response);
			}
			
			break;
		case "TOEDITCONTACTADMIN":
			toEditContactAdmin(request, response);
			break;	
		  case "UPDATECONTACTADMIN":
			  contact.setConId(Integer.parseInt(request.getParameter("conid")));
			  contact.setConName(request.getParameter("conname"));
			  contact.setConEmail(request.getParameter("conemail"));
			  contact.setConMobile(request.getParameter("conmobile"));
			  contact.setConAddress(request.getParameter("conaddress"));
			  Integer cId1=cdo.checkContactByContact(request.getParameter("conmobile"),Integer.parseInt(request.getParameter("uidbyadmin")));
			    if(cId1.equals(-1) || cId1.equals(Integer.parseInt(request.getParameter("conid"))))
				{
					cdo.saveContact(contact);
					adminViewToContactOfUser(request, response);
				}
				else
				{
					request.setAttribute("message", "Contact already exist");
					toEditContactAdmin(request, response);
				}
			  break;	
		  case "FAVOURITEADMIN":
				Integer isFav1 = Integer.parseInt(request.getParameter("isfav"));
				if (isFav1 == 0)
					isFav1 = 1;
				else
					isFav1 = 0;
				cdo.modifyFav(Integer.parseInt(request.getParameter("conid")), isFav1);
				adminViewToContactOfUser(request, response);
				break;
			case "DELETECONTACTADMIN":
				cdo.deleteContact(Integer.parseInt(request.getParameter("conid")));
				adminViewToContactOfUser(request, response);
				break;	
				
		case "TOADMINEDITUSER":
			toAdminEditUser(request, response);
			break;
		
	
		case "ADMINUSEREDIT":
			user.setuId(Integer.parseInt(request.getParameter("uidbyadmin")));
			user.setuName(request.getParameter("uname"));
			user.setuEmail(request.getParameter("uemail"));
			user.setuMobile(request.getParameter("umobile"));
			user.setuAddress(request.getParameter("uaddress"));
			user.setuRole(request.getParameter("urole"));
			if(udo.saveUser(user))
			viewUser(request, response);
			else
			{
				request.setAttribute("message", "This email is already exist");
				toAdminEditUser(request, response);

			}
			break;
		
		
		default:
			System.out.println("Something went wrong");
			break;
		   }
			
		}
		else
		{
			rd = request.getRequestDispatcher("./index.jsp");
			rd.forward(request, response);
		}
		}


	private void userToDesktop(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		user = udo.userById((Integer) session.getAttribute("uId"));
		request.setAttribute("user", user);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		List<GroupDivision> glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		rd = request.getRequestDispatcher("./UserDesktop.jsp");
		rd.forward(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("./index.jsp");
	}

	private void toCreateContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		user = udo.userById((Integer) session.getAttribute("uId"));
		request.setAttribute("user", user);
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/createapp.jsp");
		rd.forward(request, response);
	}

	private void profile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/login/profile.jsp");
		rd.forward(request, response);

	}

	private void viewContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		conlist = cdo.viewAllContact((Integer) session.getAttribute("uId"));
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		request.setAttribute("conlist", conlist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/viewapp.jsp");
		rd.forward(request, response);
	}

	private void toCreateGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getParameter("gid")!=null)
		{
		GroupDivision group = gdo.groupById(Integer.parseInt(request.getParameter("gid")));
		request.setAttribute("group", group);
		}
		user = udo.userById((Integer) session.getAttribute("uId"));
		request.setAttribute("user", user);
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/CreateGroup.jsp");
		rd.forward(request, response);
	}
	
	private void viewGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		conlist = gdo.viewAllContact(Integer.parseInt(request.getParameter("gid")),
		(Integer) session.getAttribute("uId"));
		group = gdo.groupById(Integer.parseInt(request.getParameter("gid")));
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		request.setAttribute("group", group);
		request.setAttribute("conlist", conlist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/viewgroup.jsp");
		rd.forward(request, response);
	}
	
	private void contactViewToAddedGroup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		conlist = cdo.viewAllContact((Integer) session.getAttribute("uId"));
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("gid",request.getAttribute("gid"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		request.setAttribute("conlist", conlist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/conviewaddgroup.jsp");
		rd.forward(request, response);
	}

	private void viewUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<User> ulist = (ArrayList<User>) ado.viewAllUser((Integer) session.getAttribute("uId"));
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		request.setAttribute("ulist", ulist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/viewUser.jsp");
		rd.forward(request, response);
	}
	private void adminViewToContactOfUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		conlist = cdo.viewAllContact(Integer.parseInt(request.getParameter("uidbyadmin")));
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		request.setAttribute("conlist", conlist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/adminviewcontact.jsp");
		rd.forward(request, response);
	}
	private void toEditContact(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contact = cdo.contactById(Integer.parseInt(request.getParameter("conid")));
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		request.setAttribute("contact", contact);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/editContact.jsp");
		rd.forward(request, response);

	}
	
	private void toEditContactAdmin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contact = cdo.contactById(Integer.parseInt(request.getParameter("conid")));
		user = new User();
		user = udo.userById((Integer) session.getAttribute("uId"));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		request.setAttribute("user", user);
		request.setAttribute("contact", contact);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		rd = request.getRequestDispatcher("./pages/ui-features/admineditcontact.jsp");
		rd.forward(request, response);

	}


	

	private void toAdminEditUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("user1", udo.userById(Integer.parseInt(request.getParameter("uidbyadmin"))));
		glist = (List<GroupDivision>) gdo.groupByuId((Integer) session.getAttribute("uId"));
		request.setAttribute("glist", glist);
		Integer count = ado.countUser((Integer) session.getAttribute("uId"));
		request.setAttribute("count", count);
		user = udo.userById((Integer) session.getAttribute("uId"));
		request.setAttribute("user", user);
		rd = request.getRequestDispatcher("./pages/login/adminuseredit.jsp");
		rd.forward(request, response);
	}

	
	
	




	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
