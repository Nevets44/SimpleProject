package com.revature.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.bean.Reimbursement;

public interface ReimbursementDAOTemplate {
	public String fetchReceipt(Connection conn, int reimb_id, int user_id) throws SQLException;
	public List<Reimbursement> getReimbursements(Connection conn, int user_id, int requester_id) throws SQLException;
	public List<String> getReimbursementStatuses(Connection conn) throws SQLException;
	public List<String> getReimbursementTypes(Connection conn) throws SQLException;
	public boolean submitReimbursement(Connection conn, int user_id, double amount, String description, byte[] receipt, String type) throws FileNotFoundException, SQLException;
	public boolean updateReimbursementStatus(Connection conn, int reimb_id, String status, int user_id) throws SQLException;
	public List<Reimbursement> viewReimbursementsByEmployee(Connection conn, int user_id, int requester_id) throws SQLException;
	public List<Reimbursement> viewReimbursementsByStatus(Connection conn, int user_id, String status, int requester_id) throws SQLException;
	public List<Reimbursement> viewReimbursementsByType(Connection conn, int user_id, String type, int requester_id) throws SQLException;
}
