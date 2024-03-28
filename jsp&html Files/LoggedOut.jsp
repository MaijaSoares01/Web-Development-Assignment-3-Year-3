<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log Out Page</title>
    </head>
    <body>
	<h1>Logging Out</h1>
	<h3>BYE!!</h3>
	<hr>
	<p>Thanks for checking in</p>
	<h4>~Check if still logged in(issue)~</h4>
	<p>Name will be here: <s:property value="#session.currentUser" /><p>
	<p>Staff Number will be here: <s:property value="#session.currentStaffNumber" /><p>
	<p>if empty all good!<p>
	<hr>
	<p>Go to <a href="index.jsp"  name = "linkSignIn" >Sign in Page</a>.</p>
	<p>Go to <a href="Register.jsp"  name = "linkRegister" >Register Page</a>.</p>
	</body>
</html>