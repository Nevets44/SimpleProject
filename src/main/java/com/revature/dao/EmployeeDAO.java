package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.bean.User;

import oracle.jdbc.OracleTypes;

public class EmployeeDAO implements EmployeeDAOTemplate {

	@Override
	public String[] getAccountDetails(Connection conn, int userid) throws SQLException {
		String[] userParams = new String[5]; 
		
		PreparedStatement stmt = conn.prepareStatement("SELECT u_id, u_username, u_firstname, u_lastname, "
				+ "u_email FROM ers_users WHERE u_id = ?");
		
		stmt.setInt(1, userid);
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			userParams[0] = rs.getInt("u_id") + "";
			userParams[1] = rs.getString("u_username");
			userParams[2] = rs.getString("u_firstname");
			userParams[3] = rs.getString("u_lastname");
			userParams[4] = rs.getString("u_email");
		}
		
		return userParams;
	}
	
	@Override
	public List<User> getAllEmployees(Connection conn, int userid) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		
		CallableStatement stmt = conn.prepareCall("{call get_all_employees(?, ?)}");
		
		stmt.setInt(1, userid);
		stmt.registerOutParameter(2, OracleTypes.CURSOR);
		
		stmt.executeUpdate();
		
		ResultSet rs = (ResultSet) stmt.getObject(2);;
		
		while(rs.next())
			users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), (rs.getInt(6) == 1 ? true : false)));
		
		return users;
	}
	
	@Override
	public User login(Connection conn, String username, String password) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{call login(?, ?, ?)}");
		
		stmt.setString(1, username);
		stmt.setString(2, password);
		stmt.registerOutParameter(3, OracleTypes.CURSOR);
		
		stmt.executeUpdate();
		
		ResultSet rs = (ResultSet) stmt.getObject(3);;
		
		if(rs.next())
			return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), (rs.getInt(6) == 1 ? true : false));
		
		return null;
	}

	@Override
	public boolean updateAccount(Connection conn, int userid, String username, String password, String fname, String lname, String email) throws SQLException {
		CallableStatement stmt = conn.prepareCall("{call update_emp_account(?, ?, ?, ?, ?, ?)}");
		
		stmt.setInt(1, userid);
		stmt.setString(2, username);
		stmt.setString(3, password);
		stmt.setString(4, fname);
		stmt.setString(5, lname);
		stmt.setString(6, email);
		
		int rowsAffected = stmt.executeUpdate();
		
		// If account was successfully updated, return true
		if (rowsAffected == 1)
			return true;
		
		// Else, return false
		return false;
	}

}
