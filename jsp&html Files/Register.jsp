<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
	<h1>Register</h1>
	<p>Please fill in this form to create an account...</p>
	<hr>
	<s:form action = "register">
	<s:textfield name="name" label="Enter your Name" />
	<s:textfield name="staffnum" label="Enter your Staff Number" />
	<s:textfield name="password" label="Enter your Password" />
	<s:textfield name="repeatPassword" label="Repeat Password" />
	<s:radio label="Role" name="role" list="#{'1':'Admin','2':'User'}" value="2" />
	<s:submit />
	</s:form>
	<hr>
	<br>
	<p>Already have an account(Admin or User)? <a href="index.jsp"  name = "linkSignIn" >Sign in</a>.</p>
	</body>
</html>