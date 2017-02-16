package com.revature.dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.revature.bean.Reimbursement;

import oracle.jdbc.OracleTypes;

public class ReimbursementDAO implements ReimbursementDAOTemplate {
	
	@Override
	public String fetchReceipt(Connection conn, int reimb_id, int user_id) throws SQLException {
		Blob receipt;
		
		CallableStatement stmt = conn.prepareCall("{call fetch_receipt(?, ?, ?)}");
		
		stmt.setInt(1, reimb_id);
		stmt.setInt(2, user_id);
		stmt.registerOutParameter(3, OracleTypes.CURSOR);
		
		stmt.executeUpdate();
		
		ResultSet rs = (ResultSet) stmt.getObject(3);
		
		if(rs.next()) {
			receipt = rs.getBlob(1);
			
			if(receipt != null)
				return new String(Base64.getEncoder().encode(receipt.getBytes(1, (int) receipt.length())));
		}
		
		return null;
	}
	
	// Start Getters
	@Override
	public List<Reimbursement> getReimbursements(Connection conn, int userid, int requester_id) throws SQLException {
		Blob image;
		String imageString = "";
		String submittedDate = "";
		String resolvedDate = "";
		Timestamp check; // Temporary timestamp to check if the value is null
		
		CallableStatement stmt = conn.prepareCall("{call get_reimbs(?, ?, ?)}");
		stmt.setInt(1, userid);
		stmt.setInt(2, requester_id);
		stmt.registerOutParameter(3, OracleTypes.CURSOR);
		
		stmt.executeUpdate();
		
		ResultSet rs = (ResultSet) stmt.getObject(3);;
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		while(rs.next()) {
			image = rs.getBlob(4);

			if(image != null)
				imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
			
			/* Legacy, in case want to save to a file
			try {
				if(image != null) {
					imageFile = makeImage(image);
					imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			submittedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(5));
			
			check = rs.getTimestamp(6);
			
			if(check != null)
				resolvedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(6));
			
			Reimbursement rb = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), imageString,
					submittedDate, resolvedDate, rs.getString(8), rs.getString(10), rs.getString(11), rs.getString(12));
			reimbursements.add(rb);
		}
		
		return reimbursements;
	}
	
	@Override
	public List<String> getReimbursementStatuses(Connection conn) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT rs_status FROM ers_reimbursement_status"); // Add an id check on this JUST in case
		ResultSet rs = stmt.executeQuery();
		
		ArrayList<String> statuses = new ArrayList<String>();
		
		while(rs.next())
			statuses.add(rs.getString("rs_status"));
		
		return statuses;
	}
	
	@Override
	public List<String> getReimbursementTypes(Connection conn) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("SELECT rt_type FROM ers_reimbursement_type");
		ResultSet rs = stmt.executeQuery();
		
		ArrayList<String> types = new ArrayList<String>();
		
		while(rs.next())
			types.add(rs.getString("rt_type"));
		
		return types;
	}
	
	public File makeImage(Blob image) throws SQLException, IOException {
		byte barr[] = image.getBytes(1,(int)image.length());//1 means first image  
        
		FileOutputStream fout = new FileOutputStream("jerry-close.jpg");  
		fout.write(barr);  
		              
		fout.close(); 
		
		return null;
	}
	
	// End Getters
	
	// Start Methods & Transactions
	@Override
	public boolean submitReimbursement(Connection conn, int user_id, double amount, String description, byte[] receipt, String type) throws FileNotFoundException, SQLException {
		// Try to convert the file to a stream
		/*InputStream is = new FileInputStream(image);
		 * (in the statement) 4, is, (int) image.length() */
		
		//InputStream is = new FileInputStream(new String(Base64.getEncoder().encode(receipt)));
		
		CallableStatement stmt = conn.prepareCall("{call submit_reimb(?, ?, ?, ?, ?, ?)}");
					
		stmt.setInt(1, user_id);
		stmt.setDouble(2, amount);
		stmt.setString(3, description);
		stmt.setBinaryStream(4, new ByteArrayInputStream(receipt), receipt.length);
		stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
		stmt.setString(6, type);
					
		int rowsAffected = stmt.executeUpdate();
					
		// If reimbursement was successfully submitted, return true
		if (rowsAffected == 1)
			return true;
		
		// Else, return false
		return false;
	}
	
	@Override
	public boolean updateReimbursementStatus(Connection conn, int reimb_id, String status, int user_id)
			throws SQLException {
		
		CallableStatement stmt = conn.prepareCall("{call update_reimb_status(?, ?, ?)}");
		
		stmt.setInt(1, reimb_id);
		stmt.setString(2, status);
		stmt.setInt(3, user_id);
		
		int rowsAffected = stmt.executeUpdate();
		
		// If status update was successful, return true
		if(rowsAffected == 1) 
			return true;
		
		// Else, return false
		return false;
	}
	
	@Override
	public List<Reimbursement> viewReimbursementsByEmployee(Connection conn, int user_id, int requester_id) throws SQLException {
		Blob image;
		String imageString = "";
		String submittedDate = "";
		String resolvedDate = "";
		Timestamp check; // Temporary timestamp to check if the value is null
		
		CallableStatement stmt = conn.prepareCall("{call view_reimbs_by_employee(?, ?, ?)}");
		
		stmt.setInt(1, user_id);
		stmt.setInt(2, requester_id);
		stmt.registerOutParameter(3, OracleTypes.CURSOR);
		
		stmt.executeUpdate();
		
		ResultSet rs = (ResultSet) stmt.getObject(3);
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		// CONVERTING TO A FILE IS NOT RIGHT!!!! (Leave it for the moment)
		while(rs.next()) {
			image = rs.getBlob(4);
			
			if(image != null)
				imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
			
			/*
			try {
				if(image != null) {
					imageFile = makeImage(image);
					imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			submittedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(5)); // We should NEVER have to check this since it's not null
			
			check = rs.getTimestamp(6);
			
			if(check != null)
				resolvedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(6));
			
			Reimbursement rb = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), imageString,
					submittedDate, resolvedDate, rs.getString(8), rs.getString(10), rs.getString(11), rs.getString(12));
			reimbursements.add(rb);
		}
		
		return reimbursements;
	}
	
	@Override
	public List<Reimbursement> viewReimbursementsByStatus(Connection conn, int user_id, String status, int requester_id) throws SQLException {
		Blob image;
		String imageString = "";
		String submittedDate = "";
		String resolvedDate = "";
		Timestamp check; // Temporary timestamp to check if the value is null
		
		CallableStatement stmt = conn.prepareCall("{call view_reimbs_by_status(?, ?, ?, ?)}");
		
		stmt.setInt(1, user_id);
		stmt.setString(2, status);
		stmt.setInt(3, requester_id);
		stmt.registerOutParameter(4, OracleTypes.CURSOR);
		
		stmt.executeUpdate();
		
		ResultSet rs = (ResultSet) stmt.getObject(4);
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		// CONVERTING TO A FILE IS NOT RIGHT!!!! (Leave it for the moment)
		while(rs.next()) {
			image = rs.getBlob(4);
			
			if(image != null)
				imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
			
			/*
			try {
				if(image != null) {
					imageFile = makeImage(image);
					imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			submittedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(5)); // We should NEVER have to check this since it's not null
			
			check = rs.getTimestamp(6);
			
			if(check != null)
				resolvedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(6));
			
			Reimbursement rb = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), imageString,
					submittedDate, resolvedDate, rs.getString(7), rs.getString(9), rs.getString(10), rs.getString(11));
			reimbursements.add(rb);
		}
		
		return reimbursements;
	}

	@Override
	public List<Reimbursement> viewReimbursementsByType(Connection conn, int user_id, String type, int requester_id) throws SQLException {
		Blob image;
		String imageString = "";
		String submittedDate = "";
		String resolvedDate = "";
		Timestamp check; // Temporary timestamp to check if the value is null
		
		CallableStatement stmt = conn.prepareCall("{call view_reimbs_by_type(?, ?, ?, ?)}");
		
		stmt.setInt(1, user_id);
		stmt.setString(2, type);
		stmt.setInt(3, requester_id);
		stmt.registerOutParameter(4, OracleTypes.CURSOR);
		
		stmt.executeUpdate();
		
		ResultSet rs = (ResultSet) stmt.getObject(4);
		
		ArrayList<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		
		// CONVERTING TO A FILE IS NOT RIGHT!!!! (Leave it for the moment)
		while(rs.next()) {
			image = rs.getBlob(4);

			if(image != null)
				imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
			
			/*
			try {
				if(image != null) {
					imageFile = makeImage(image);
					imageString = new String(Base64.getEncoder().encode(image.getBytes(1, (int) image.length())));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			submittedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(5)); // We should NEVER have to check this since it's not null
			
			check = rs.getTimestamp(6);
			
			if(check != null)
				resolvedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(rs.getTimestamp(6));
			
			Reimbursement rb = new Reimbursement(rs.getInt(1), rs.getInt(2), rs.getString(3), imageString,
					submittedDate, resolvedDate, rs.getString(7), rs.getString(9), rs.getString(10), rs.getString(11));
			reimbursements.add(rb);
		}
		
		return reimbursements;
	}
	
	// End Methods & Transactions
}
