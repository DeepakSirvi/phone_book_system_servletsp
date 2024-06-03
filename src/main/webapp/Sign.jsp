<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.dollop.app.bean.User"  %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin</title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="./assets/vendors/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="./assets/vendors/css/vendor.bundle.base.css">
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <!-- endinject -->
    <!-- Layout styles -->
    <link rel="stylesheet" href="./assets/css/style.css">
    <!-- End layout styles -->
    <link rel="shortcut icon" href="./assets/images/favicon.ico" />
  </head>
  <style>
    .auth .auth-form-light {
    background: #ffffff;
    border-radius: 10px;
}

.auth form .auth-form-btn {
    height: 50px;
    width: 100%;
    line-height: 1.5;
}
.btn-gradient-primary {
    background: -webkit-gradient(linear, left top, right top, from(#da8cff), to(#9a55ff));
    background: linear-gradient(to right, #6073e3, #6073e3);
    border: 0;
    -webkit-transition: opacity 0.3s ease;
    transition: opacity 0.3s ease;
}
.auth .brand-logo {
    margin-bottom: 2rem;
    display: flex;
    justify-content: center;
}
  </style>
  <body>
  <%User user=(User)request.getAttribute("user"); %>
    <div class="container-scroller">
      <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth">
          <div class="row flex-grow">
            <div class="col-lg-4 mx-auto">
              <div class="auth-form-light text-left p-5">
                <div class="brand-logo">
                  <img src="./assets/images/logo2.png" style="width:50%;height:50%;">
                </div>
               
                <form class="pt-3" action="./PhoneBookController" method="Post">
                 <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputName1" placeholder="Enter User Name" name="uname" required="required">
                  <span id="nameError" class="error"></span>
                  </div>
                  <div class="form-group">
                    <input type="email" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="Enter Email Id" name="uemail" required="required">
                  </div>
                   <div class="form-group">
                    <input type="number" class="form-control form-control-lg" id="exampleInputMobile1" placeholder="Enter Mobile Number" name="umobile" required="required">
                       <span id="mobileError" class="mobile"></span>                  
                  </div>
                   <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputAddress1" placeholder="Enter Address" name="uaddress" required="required">
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="Enter Password" name="upassword" required="required">
                  </div>
                  <div class="form-group">
                    <input type="hidden" value="SIGNUPUSER" class="form-control form-control-lg" id="examplInputAction1" name="Action">
                  </div>
                    <% if(request.getAttribute("message")!=null) {
                  %>
                  <p class="message" style="color:red"><%=request.getAttribute("message")%></p>
                  <%
                  } 
                  %>
                                    <div class="form-group">
                    <input type="hidden" value="User" class="form-control form-control-lg" id="examplInputRole1" name="urole">
                  </div>
                  
                  <div class="mt-3">
                  </div>
                  <div>
                      <a href="index.jsp">LoginUp</a>
                  </div>
                    <input type="submit" class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn" onclick="return validation()">
                  
                 
                </form>
              </div>
            </div>
          </div>
        </div>
        <!-- content-wrapper ends -->
      </div>
      <!-- page-body-wrapper ends -->
    </div>
    <script src="./assets/js/off-canvas.js"></script>
    <script src="./assets/js/hoverable-collapse.js"></script>
    <script src="./assets/js/misc.js"></script>
    <!-- endinject -->
  </body>
  <script type="text/javascript">
  function validation()
	{
	  var mobile = document.getElementById("exampleInputMobile1");
		var mobileError = document.getElementById("mobileError");
		
		var name = document.getElementById("exampleInputName1");
		var nameError = document.getElementById("nameError");

		var temp=true;
		if(!validateMobile(mobile.value))	
		{
			mobileError.innerText="Invalid mobile number";
			temp=false;
		}
		else
		{
			mobileError.innerText="";
		}
		if(name.value.length<3)
		{
			nameError.innerText="Atleast 3 char required ";
			temp=false;
		}
		else
		{
			nameError.innerText=""
		}
	}
	    function validateMobile(mobileNumber)
	    {
	    	var regm = /^(1\s?)?(\(\d{3}\)|\d{3})[\s\-]?\d{3}[\s\-]?\d{4}$/;
	    	var regmob = new RegExp(regm);
	    	if(regmob.test(mobileNumber))
	    	{
	    		return true;
	    	}
	    	else
	    	{
	    		return false;
	    	}
	    }
	
  </script>
</html>