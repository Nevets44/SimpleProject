package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO used specifically for helping with test cases
 * @author Steven
 *
 */

public class TestDAO {

	public boolean checkUpdate(Connection conn, int userid, String username, String password, String fname, String lname, String email) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT u_username, u_password, u_firstname, u_lastname, u_email FROM ers_users WHERE u_id = ?");
		stmt.setInt(1, userid);
		
		boolean passed = true;
		
		ResultSet rs = stmt.executeQuery();
		
		while(rs.next()) {
			if(!username.equals(rs.getString(1)))
				passed = false;
			if(!password.equals(rs.getString(2)))
				passed = false;
			if(!fname.equals(rs.getString(3)))
				passed = false;
			if(!lname.equals(rs.getString(4)))
				passed = false;
			if(!email.equals(rs.getString(5)))
				passed = false;
		}
		
		return passed;
	}
	
	public boolean deleteReimbursement(Connection conn, int emp_id, double amount, String description, String status, int r_type) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM ers_reimbursements WHERE u_author_id = ?, r_amount = ?, r_description = ?, rt_type = ?");
		
		// Assuming all rows with the provided parameters are test inserts so don't care how many we delete
		if(stmt.executeUpdate() > 0)
			return true;
		
		return false;
	}
	
}
