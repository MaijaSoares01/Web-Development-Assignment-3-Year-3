import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport implements SessionAware {
	
	private String staffnum, password;

	private Map<String, Object> session;
	
	public Login(){
		
	}
	
	public String login() {
		String result = "";
		Connection connection = null;
		boolean login = false;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Payroll?serverTimezone=UTC","root","root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			Statement getUser = connection.createStatement();
			ResultSet rs = getUser.executeQuery("SELECT * FROM Users");
			while(rs.next()) {
				if((rs.getString(1).equalsIgnoreCase(staffnum))&&(rs.getString(3).equalsIgnoreCase(password))) {
					login = true;	
					session.put("currentUser", rs.getString(2));
					session.put("currentStaffNumber", rs.getString(1));
					if(rs.getString(4).equalsIgnoreCase("Admin")) {
						result = "ADMINSUCCESS";
					}else {
						result = "USERSUCCESS";
					}
				}
			}
			if(login == false) {
				result = "FAILURE";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String logOut() {
		String result = "";
		session.clear();
		if (session.isEmpty()==true) {
			result = "SUCCESS";
		}else {
			result = "FAILURE";
		}
		return result;
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
	
	public Map getSession() {
		return session;
	}

	@Override
	public void setSession(Map sessionFromStruts) {
		session = sessionFromStruts;
	}
}