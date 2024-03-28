<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Total Tax for the Year</title>
    </head>
    <body>
	<h1>Total Tax for the Year</h1>
	<h3>Logged in as <s:property value="#session.currentStaffNumber" />, <s:property value="#session.currentUser" /></h3>
	<hr>
	<s:form action = "taxYear">
	<s:textfield name="year" label="Enter a Year" />
	<s:submit />
	</s:form>
	<hr>
	<br>
	<p>Return to <a href="AdminHomePage.jsp" >Admin Home Page</a>.</p>
	</body>
</html>