package com.revature.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.bean.Reimbursement;
import com.revature.bean.User;

public interface EmployeeDAOTemplate {
	public String[] getAccountDetails(Connection conn, int userid) throws SQLException;
	public List<User> getAllEmployees(Connection conn, int userid) throws SQLException;
	public User login(Connection conn, String username, String password) throws SQLException;
	public boolean updateAccount(Connection conn, int userid, String username, String password, String fname, String lname, String email) throws SQLException;
}
