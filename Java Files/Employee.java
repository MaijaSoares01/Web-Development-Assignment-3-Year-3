import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class  Employee  extends  ActionSupport implements SessionAware  {
	private Map<String, Object> session;
	private String name, staffnum, repeatstaffnum, ppsNum,year;
	
	private double salary;

	public Employee(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStaffnum() {
		return staffnum;
	}

	public void setStaffnum(String staffnum) {
		this.staffnum = staffnum;
	}

	public String getRepeatstaffnum() {
		return repeatstaffnum;
	}

	public void setRepeatstaffnum(String repeatstaffnum) {
		this.repeatstaffnum = repeatstaffnum;
	}
	
	public String getPpsNum() {
		return ppsNum;
	}

	public void setPpsNum(String ppsNum) {
		this.ppsNum = ppsNum;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String addEmployeePage() {
		String result = "";
			result = "SUCCESS";
		return result;
	}
	
	public String calculateTaxPage() {
		String result = "";
			result = "SUCCESS";
		return result;
	}
	
	public String removeEmpPage() {
		String result = "";
			result = "SUCCESS";
		return result;
	}
	
	public String yearPage() {
		String result = "";
			result = "SUCCESS";
		return result;
	}
	
	public void  addEmployee()  throws  Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = (String) request.getSession().getAttribute("currentUser");
			String curstaffnum = (String) request.getSession().getAttribute("currentStaffNumber");
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Add Employee Page</title></head>");
			out.println("<body><h1>Add Employee</h1><hr><h3>Logged in as " + curstaffnum + ", " + username + "</h3><hr>");
			String name = getName();
			String staffnum = getStaffnum();
			String ppsNum = getPpsNum();
			boolean error = false;
			String result = "";
			//check if ppsnum, name and staffnum is empty
			if((staffnum.equals(null))||(ppsNum.equals(null)||(name.equals(null)||(name.equalsIgnoreCase(""))||(staffnum.equalsIgnoreCase(""))||(ppsNum.equalsIgnoreCase(""))))){
				error = true;
				result = "EMPTY";
				out.println("<h3>~ERROR PAGE~</h3><hr><h2>Issue occurred while Adding an Employee!</h2><h3>Possible reasons:</h3><ul><li>Empty Name</li><li>Empty Staff Number</li><li>Empty PPS Number</li></ul></br><b>Sorry for the inconvenience!</b>");
				out.println("<hr><p>Return to <a href=\"AddEmployee.jsp\" >Add Employee Page</a>.</p>");
				out.println("</body></html>");	
			}else{
			//check if ppsnum is 7 numbers long and 1 uppercase letter or 2
			if((ppsNum.matches("[0-9]{7}[A-Z]{1,2}")==false)) {
				error = true;
				result = "PPSFORMAT";
				out.println("<h3>~ERROR PAGE~</h3><hr>The PPS Number is not 7 digits and 1 or 2 upper case letters. eg. 1234567AB</br><b>Sorry for the inconvenience!</b>");
				out.println("<hr><p>Return to <a href=\"AddEmployee.jsp\" >Add Employee Page</a>.</p>");
				out.println("</body></html>");
			//check if staffnum is 5 numbers long
			}else if((staffnum.matches("[0-9]{5}")==false)){
				error = true;
				result = "STAFFNUMFORMAT";
				out.println("<h3>~ERROR PAGE~</h3><hr>The Staff Number is not 5 digits long. eg. 12345</br><b>Sorry for the inconvenience!</b>");
				out.println("<hr><p>Return to <a href=\"AddEmployee.jsp\" >Add Employee Page</a>.</p>");
				out.println("</body></html>");
			}else {
						//check if staffnum and ppsnum exist in employee table
						ResultSet user = getUser.executeQuery("SELECT staffnum,ppsNum FROM Employees");
						while(user.next()) {
							if((user.getString(1).equalsIgnoreCase(staffnum))) {
								error = true;
								result = "STAFFNUMADDED";
								result = "STAFFNUMFORMAT";
								out.println("<h3>~ERROR PAGE~</h3><hr>The staff number you inputted already exists in the Employee Database...</br><b>Sorry for the inconvenience!</b>");
								out.println("<hr><p>Return to <a href=\"AddEmployee.jsp\" >Add Employee Page</a>.</p>");
								out.println("</body></html>");
							}
							if((user.getString(2).equalsIgnoreCase(ppsNum))) {
								error = true;
								result = "PPSNUMADDED";
								out.println("<h3>~ERROR PAGE~</h3><hr>The pps number you inputted already exists in the Employee Database...</br><b>Sorry for the inconvenience!</b>");
								out.println("<hr><p>Return to <a href=\"AddEmployee.jsp\" >Add Employee Page</a>.</p>");
								out.println("</body></html>");
							}
						}
				}
			}
			if(error == false){
				//pass in the values as parameters
				PreparedStatement createUser = connection.prepareStatement("INSERT into Employees " + "(staffnum,name,ppsNum)" + " VALUES (?, ?, ?)");
				createUser.setString(1, staffnum);
				createUser.setString(2, name);
				createUser.setString(3, ppsNum);
				createUser.executeUpdate();
				createUser.close();
				result="SUCCESS";
				out.println("<h2>You Successfully added the Employee!</h2>");
				out.println("<hr><p>Return to <a href=\"AddEmployee.jsp\" >Add Employee Page</a>.</p><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p>");
				out.println("</body></html>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
     }
	
	public void  CalculateTax()  throws  Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = (String) request.getSession().getAttribute("currentUser");
			String curstaffnum = (String) request.getSession().getAttribute("currentStaffNumber");
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Calculate Tax Report Page</title></head>");
			out.println("<body><h1>Calculate Tax Report for Employee</h1><hr><h3>Logged in as " + curstaffnum + ", " + username + "</h3><hr>");	
			String staffnum = getStaffnum();
			String year = getYear();
			String name = "";
			Double salary = getSalary();
			boolean found = false;
			boolean error = false;
			String result = "";
			//if year equals 2022,2021,2020,2019
			if((year.equalsIgnoreCase("2022"))||(year.equalsIgnoreCase("2021"))||(year.equalsIgnoreCase("2020"))||(year.equalsIgnoreCase("2019"))) {
				//if staffnum is 5 numbers long
				if((staffnum.matches("[0-9]{5}")==false)||(staffnum.equalsIgnoreCase(null))){
					error = true;
					out.println("<h3>~ERROR PAGE~</h3>The Staff Number you inputted is not 5 numbers long...</br><b>Sorry for the inconvenience!</b>");
					out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
					out.println("</body></html>");
				}else {
					//if staffnum exists in Employee table
					ResultSet employees = getUser.executeQuery("SELECT staffnum,name FROM Employees");
					while(employees.next()) {
							if (employees.getString(1).equalsIgnoreCase(staffnum)) {
								found = true;
								setName(employees.getString(2));
							}
					}
					name = getName();
					if(found==false){
						error = true;
						out.println("<h3>~ERROR PAGE~</h3>The staff number you inputted does not exist in the Employee Database</br><b>Sorry for the inconvenience!</b>");
						out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
						out.println("</body></html>");					
					}else {
						//if year for that employee already exists in Employee Tax report
						ResultSet employeetaxrepo = getUser.executeQuery("SELECT staffnum,year FROM EmployeeTaxReports");
						while(employeetaxrepo.next()) {
								if (employeetaxrepo.getString(1).equalsIgnoreCase(staffnum)) {
									if(employeetaxrepo.getString(2).equalsIgnoreCase(year)){
										error = true;
										out.println("<h3>~ERROR PAGE~</h3>The tax report for that employee for that year already exists</br><b>Sorry for the inconvenience!</b>");
										out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
										out.println("</body></html>");
									}
								}
						}
						if(error==false) {
							//if year is 2022 salary must be greater than or equal to 36800 else 35300
							if(year.equalsIgnoreCase("2022")){
								if(salary>=36800){
									double tax = (((36800/100)*20)+(((salary - 36800)/100)*40));
									double netSalary = salary - tax;
									PreparedStatement createUser = connection.prepareStatement("INSERT into EmployeeTaxReports " + "(staffnum,name,year,grossSalary,tax,netSalary)" + " VALUES (?, ?, ?, ?, ?, ?)");
									createUser.setString(1, staffnum);
									createUser.setString(2, name);
									createUser.setString(3, year);
									createUser.setDouble(4, salary);
									createUser.setDouble(5, tax);
									createUser.setDouble(6, netSalary);
									createUser.executeUpdate();
									createUser.close();
									out.println("<h3>CALCULATED TAX REPORT:</h3>Annual Tax report for staff member " + name + " for " + year + "</br>Gross Salary " + salary + ", Tax Due " + tax + ",and Net Salary " + netSalary);
									out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
									out.println("</body></html>");
								}else {
									error = true;
									out.println("<h3>~ERROR PAGE~</h3>Inputted Salary is insufficient! It needs to be greater than or equal to 36800</br><b>Sorry for the inconvenience!</b>");
									out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
									out.println("</body></html>");
								}
							}else {
								if(salary>=35300){
									double tax = (((35300/100)*20)+(((salary - 35300)/100)*40));
									double netSalary = salary - tax;
									PreparedStatement createUser = connection.prepareStatement("INSERT into EmployeeTaxReports " + "(staffnum,name,year,grossSalary,tax,netSalary)" + " VALUES (?, ?, ?, ?, ?, ?)");
									createUser.setString(1, staffnum);
									createUser.setString(2, name);
									createUser.setString(3, year);
									createUser.setDouble(4, salary);
									createUser.setDouble(5, tax);
									createUser.setDouble(6, netSalary);
									createUser.executeUpdate();
									createUser.close();
									out.println("<h3>CALCULATED TAX REPORT:</h3>Annual Tax report for staff member " + name + " for " + year + "</br>Gross Salary " + salary + ", Tax Due " + tax + ",and Net Salary " + netSalary);
									out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
									out.println("</body></html>");
								}else {
									error = true;
									out.println("<h3>~ERROR PAGE~</h3>Inputted Salary is insufficient! It needs to be greater than or equal to 35300</br><b>Sorry for the inconvenience!</b>");
									out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
									out.println("</body></html>");
								}
							}
					}
					}
				}
			}else {
				error = true;
				out.println("<h3>~ERROR PAGE~</h3>Inputted invalid year: 2022, 2021, 2020, or 2019</br><b>Sorry for the inconvenience!</b>");
				out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='CalculateTaxReport.jsp'>Calculate Tax Report Page</a>.</p>");
				out.println("</body></html>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void  removeEmployee()  throws  Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = (String) request.getSession().getAttribute("currentUser");
			String curstaffnum = (String) request.getSession().getAttribute("currentStaffNumber");
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Remove Employee Page</title></head>");
			out.println("<body><h1>Remove Employee</h1><hr><h3>Logged in as " + curstaffnum + ", " + username + "</h3><hr>");
			String staffnum = getStaffnum();
			String repeatstaffnum = getRepeatstaffnum();
			boolean found = false;
			boolean error = false;
			String result = "";
			//staffnum or repeatstaffnum is empty
			if((staffnum.equalsIgnoreCase(""))||(repeatstaffnum.equalsIgnoreCase(""))) {
				error = true;
				out.println("<h3>~ERROR PAGE~</h3>The staff number or repeat staff number is empty...</br><b>Sorry for the inconvenience!</b>");
				out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='RemoveEmployee.jsp'>Remove Employee Page</a>.</p>");
				out.println("</body></html>");
			}else {
				//check is staffnum or repeatstaffnum is 5 numbers long
				if((staffnum.matches("[0-9]{5}")==false)||(repeatstaffnum.matches("[0-9]{5}")==false)){
					error = true;
					out.println("<h3>~ERROR PAGE~</h3>The Staff Number and or Repeat Staff Number you inputted is not 5 numbers long...</br><b>Sorry for the inconvenience!</b>");
					out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='RemoveEmployee.jsp'>Remove Employee Page</a>.</p>");
					out.println("</body></html>");
				}else {
					//staffnum matches repeatstaffnum
					if(staffnum.equalsIgnoreCase(repeatstaffnum)){
						//check is staffnum is in employee table
						ResultSet employees = getUser.executeQuery("SELECT staffnum FROM Employees");
						while(employees.next()) {
								if (employees.getString(1).equalsIgnoreCase(staffnum)) {
									found = true;
								}
						}
						if(found==true){
							if(staffnum.equals(curstaffnum)) {
								error = true;
								out.println("<h3>~ERROR PAGE~</h3>The Staff Number you inputted is your own, please ask another Admin to delete your records</br><b>Sorry for the inconvenience!</b>");
								out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='RemoveEmployee.jsp'>Remove Employee Page</a>.</p>");
								out.println("</body></html>");
							}else {
								//delete employee from employee,user,employeetaxrates
								getUser.executeUpdate("DELETE FROM Users WHERE staffnum="+staffnum);
								getUser.executeUpdate("DELETE FROM Employees WHERE staffnum="+staffnum);
								getUser.executeUpdate("DELETE FROM EmployeeTaxReports WHERE staffnum='" + staffnum + "'");
								out.println("<h3>DELETED EMPLOYEE SUCCESSFULLY: " + staffnum + "</h3></br><b>Thank you</b>");
								out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='RemoveEmployee.jsp'>Remove Employee Page</a>.</p>");
								out.println("</body></html>");
							}
						}else{
							error = true;
							out.println("<h3>~ERROR PAGE~</h3>The Staff Number you inputted does not exist in the Employee table</br><b>Sorry for the inconvenience!</b>");
							out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='RemoveEmployee.jsp'>Remove Employee Page</a>.</p>");
							out.println("</body></html>");
						}
					}else {
						error = true;
						out.println("<h3>~ERROR PAGE~</h3>Your Staff Number and Repeat Staff Number does not Match</br><b>Sorry for the inconvenience!</b>");
						out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='RemoveEmployee.jsp'>Remove Employee Page</a>.</p>");
						out.println("</body></html>");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void  allTax()  throws  Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = (String) request.getSession().getAttribute("currentUser");
			String curstaffnum = (String) request.getSession().getAttribute("currentStaffNumber");
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Calculate Tax Report Page</title></head>");
			out.println("<body><h1>Calculate Tax Report for Employee</h1><hr><h3>Logged in as " + curstaffnum + ", " + username + "</h3><hr><table border=\\\"1\\\">");
			out.println( "<caption><b>All Employee Tax Reports</b></caption>" );
			out.println( "<thead><tr><th>Staff Number</th><th>Name</th><th>Year</th><th>Gross Salary</th><th>Tax Due</th><th>Net Salary</th></tr></thead>" );
			// Begin table body
			out.println("<tbody>" );
			ResultSet employeetaxrepo = getUser.executeQuery("SELECT * FROM EmployeeTaxReports");
			while(employeetaxrepo.next()) {
				out.println( "<tr><td>" + employeetaxrepo.getString(1) + "</td><td>" + employeetaxrepo.getString(2) + "</td><td>" + employeetaxrepo.getString(3) + "</td><td>" + employeetaxrepo.getString(4) + "</td><td>" + employeetaxrepo.getString(5) + "</td><td>" + employeetaxrepo.getString(6) + "</td></tr>" );
			}
			out.println( "</tbody></table>" );
			out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a></p>");
			out.println("</body></html>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void  taxYear()  throws  Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = (String) request.getSession().getAttribute("currentUser");
			String curstaffnum = (String) request.getSession().getAttribute("currentStaffNumber");
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			double tax = 0;
			out.println("<html><head><title>Calculate Tax Report Page</title></head>");
			out.println("<body><h1>Calculate Tax Report for Employee</h1><hr><h3>Logged in as " + curstaffnum + ", " + username + "</h3><hr>");
			if((year.equals("2022"))||(year.equals("2021"))||(year.equals("2020"))||(year.equals("2019"))) {
				ResultSet employeetaxrepo = getUser.executeQuery("SELECT * FROM EmployeeTaxReports WHERE year = '" + year + "'");
				while(employeetaxrepo.next()) {
					String strTax = employeetaxrepo.getString(5);
					double strtax = Double.parseDouble(strTax); 
					tax = tax + strtax;
				}
				out.println("<h3>Annual Tax Due for Year: " + year + "</h3>" + tax + "</br><b>");
				out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='TaxYear.jsp'>Year of Tax Page</a>.</p>");
				out.println("</body></html>");
			}else if((year.equals(""))||(year.equals(null))){
				out.println("<h3>~ERROR PAGE~</h3>Year is empty please input a year eg.(2022,2021,2020,2019)</br><b>Sorry for the inconvenience!</b>");
				out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='TaxYear.jsp'>Year of Tax Page</a>.</p>");
				out.println("</body></html>");
			}else {
				out.println("<h3>~ERROR PAGE~</h3>Inputted invalid year: 2022, 2021, 2020, or 2019</br><b>Sorry for the inconvenience!</b>");
				out.println("<hr><p>Return to <a href=\"AdminHomePage.jsp\" >Admin Home Page</a>.</p><p>Return to <a href='TaxYear.jsp'>Year of Tax Page</a>.</p>");
				out.println("</body></html>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void  AnnualTax()  throws  Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String username = (String) request.getSession().getAttribute("currentUser");
			String curstaffnum = (String) request.getSession().getAttribute("currentStaffNumber");
			Statement getUser = connection.createStatement();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Annual Employee Tax Reports Page</title></head>");
			out.println("<body><h1>Annual Employee Tax Reports</h1><hr><h3>Logged in as " + curstaffnum + ", " + username + "</h3><hr><table border=\\\"1\\\">");
			out.println( "<caption><b>Annual Tax reports for staff member "+ username +"</b></caption>" );
			out.println( "<thead><tr><th>Year</th><th>Gross Salary</th><th>Tax Due</th><th>Net Salary</th></tr></thead>" );
			// Begin table body
			out.println("<tbody>" );
			ResultSet employeetaxrepo = getUser.executeQuery("SELECT year,grossSalary,tax,netSalary FROM EmployeeTaxReports WHERE staffnum ='" + curstaffnum + "'");
			while(employeetaxrepo.next()) {
				out.println( "<tr><td>" + employeetaxrepo.getString(1) + "</td><td>" + employeetaxrepo.getString(2) + "</td><td>" + employeetaxrepo.getString(3) + "</td><td>" + employeetaxrepo.getString(4) + "</td></tr>" );
			}
			out.println( "</tbody></table>" );
			out.println("<hr><p>Return to <a href=\"UserHomePage.jsp\" >User Home Page</a></p>");
			out.println("</body></html>");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
public void setSession(Map sessionFromStruts) {
	session = sessionFromStruts;
}
public Map getSession() {
	return session;
}

}