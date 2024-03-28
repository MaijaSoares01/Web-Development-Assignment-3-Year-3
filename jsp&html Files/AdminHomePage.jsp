<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ADMIN MENU</title>
    </head>
    <body>
	<h1>ADMIN MENU</h1>
	<h3>Logged in as <s:property value="#session.currentStaffNumber" />, <s:property value="#session.currentUser" /></h3>
	<hr>
	<s:form action="AddEmployee" >
		<s:hidden name="addemployee" value="addemployee" />
		<s:submit  value="Add Employee" />
    </s:form>
	<s:form action="CalculateTax" >
		<s:hidden name="calculatetax" value="calculatetax" />
		<s:submit  value="Calculate Tax for an Employee" />
    </s:form>
	<s:form action="ViewAllEmployeeTax" >
		<s:hidden name="viewallemployeetax" value="viewallemployeetax" />
		<s:submit  value="View All Employees Tax Reports" />
    </s:form>
	<s:form action="RemoveEmployee" >
		<s:hidden name="removeemployee" value="removeemployee" />
		<s:submit  value="Remove Employee" />
    </s:form>
	<s:form action="TotalTaxDueYear" >
		<s:hidden name="totaltaxdueyear" value="totaltaxdueyear" />
		<s:submit  value="View Total Tax Due for a Year" />
    </s:form>
	<s:form action="Logout" >
		<s:hidden name="logout" value="logout" />
		<s:submit  value="Log Out" />
    </s:form>
	<hr>
	</body>
</html>