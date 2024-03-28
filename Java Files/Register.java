import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public class Register extends ActionSupport implements SessionAware {
	
	private String name, staffnum, password, repeatPassword, role;
	private Map<String, Object> session;
	
	public Register(){
		
	}
	
	public String RegisterUser() {
		String result = "";
		Connection connection = null;
		boolean register = true;
		boolean found = false;
		boolean link = false;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement getUser = connection.createStatement();
			//check if role is empty
			if(role == null){
				register = false;
				result = "EMPTY";
			}else if(role.equals("1")) {
				role = "Admin";
				setRole("Admin");
			}else if(role.equals("2")) {
				role = "User";
				setRole("User");
			}else {
				register = false;
				result = "EMPTY";
			}
			
			//check if name or staffnum is empty
			if ((name.equalsIgnoreCase(""))||(staffnum.equalsIgnoreCase(""))||(role.equalsIgnoreCase(""))) {
				register = false;
				result = "EMPTY";
			//check if staffnum is 5 numbers long 
			}else if(staffnum.matches("[0-9]{5}")==false){
					result = "STAFFNUMFORMAT";
			}else {
				//check if password or repeat password is empty
				if ((password.equalsIgnoreCase(""))||(repeatPassword.equalsIgnoreCase(""))) {
						register = false;
						result = "EMPTY";
				}else {
					//check if password and repeat password match 
						if (password.equals(repeatPassword)) {
							//check staffnum exists in user table
							ResultSet user = getUser.executeQuery("SELECT staffnum FROM Users");
							while(user.next()) {
								if((user.getString(1).equalsIgnoreCase(staffnum))) {
									register = false;
									result = "STAFFNUMTAKEN";
								}
							}
							//check if staffnum exists in employee table and if name matches
							user = getUser.executeQuery("SELECT staffnum, name FROM Employees");
							while(user.next()) {
								if((user.getString(1).equalsIgnoreCase(staffnum))) {
									found = true;
									if(user.getString(2).equalsIgnoreCase(name)==true){
										link = true;
									}
								}
							}
							if(found==false){
								register = false;
								result = "STAFFNOTEXIST";
								//staffnum does not exist in employees table 
							}else if(link==false){
								register = false;
								result = "NOMATCHNAME";
								//name doesnt match staffnum in employee table
							}
						}else {
							register = false;
							result = "NOMATCH";
						}
						if((register == true)&&(found == true)&&(link == true)) {
							//pass in the values as parameters
							PreparedStatement createUser = connection.prepareStatement("INSERT into Users " + "(staffnum,name,password,role)" + " VALUES (?, ?, ?, ?)");
							createUser.setString(1, staffnum);
							createUser.setString(2, name);
							createUser.setString(3, password);
							createUser.setString(4, role);
							createUser.executeUpdate();
							createUser.close();
							result = "SUCCESS";
						}
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public Map getSession() {
		return session;
	}

	@Override
	public void setSession(Map sessionFromStruts) {
		session = sessionFromStruts;
	}
}
