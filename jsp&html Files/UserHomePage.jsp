<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>USER MENU</title>
    </head>
    <body>
	<h1>USER MENU</h1>
	<h3>Logged in as <s:property value="#session.currentStaffNumber" />, <s:property value="#session.currentUser" /></h3>
	<hr>
	<s:form action="EmployeeTaxReports" >
		<s:hidden name="employeetaxreports" value="employeetaxreports" />
		<s:submit  value="View Your Annual Reports" />
    </s:form>
	<s:form action="Logout" >
		<s:hidden name="logout" value="logout" />
		<s:submit  value="Log Out" />
    </s:form>
	<hr>
	</body>
</html>