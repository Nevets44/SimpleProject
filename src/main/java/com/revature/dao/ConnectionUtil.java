package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "emp_admin";
		String password = "p4ssw0rd";
		
		return DriverManager.getConnection(url, username, password);
	}
	
	
}