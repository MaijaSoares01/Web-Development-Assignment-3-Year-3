<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
		<h1>LOG IN</h1>
		<p>Please fill in this form to log into your account(Admin or User)...</p>
		<hr>
        <s:form action="login" >
			<s:textfield name="staffnum" label="Enter your Staff Number" />
			<s:textfield name="password" label="Enter your Password" />
			<s:submit />
        </s:form>
	<hr>
	<br>
	<p>Do not have an account(Admin or User)? <a href="Register.jsp"  name = "linkRegister" >Register here</a>.</p>
	</body>
</html>