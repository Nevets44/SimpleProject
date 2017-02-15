package com.revature.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class Reimbursement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4847027179460972772L;
	
	private final int r_id;
	private final double r_amount;
	private final String description;
	private final String receipt; // 64-bit string made from BLOB's byte array
	private final String submittedDate;
	private final String resolvedDate;
	private final String author;
	private final String resolver;
	private final String rt_type;
	private final String status;

	public Reimbursement(int r_id, double r_amount, String description, String receipt, String submittedDate,
			String resolvedDate, String author, String resolver, String rt_type, String status) {
		super();
		this.r_id = r_id;
		this.r_amount = r_amount;
		this.description = description;
		this.receipt = receipt;
		this.submittedDate = submittedDate;
		this.resolvedDate = resolvedDate;
		this.author = author;
		this.resolver = resolver;
		this.rt_type = rt_type;
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getR_id() {
		return r_id;
	}

	public double getR_amount() {
		return r_amount;
	}

	public String getDescription() {
		return description;
	}

	public String getReceipt() {
		return receipt;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public String getResolvedDate() {
		return resolvedDate;
	}

	public String getAuthor() {
		return author;
	}

	public String getResolver() {
		return resolver;
	}

	public String getRt_type() {
		return rt_type;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "{\"id\":" + r_id + ", \"amount\":" + r_amount + ", \"author\":" + "\"" + author + "\""
				+ ", \"description\":" + "\"" + description + "\"" + ", \"receipt\":" + ((receipt != null) ? true : false) + 
				", \"submittedDate\":" + "\"" + submittedDate + "\"" + 
				", \"resolvedDate\":" + "\"" + resolvedDate + "\"" + ", \"type\":" + "\"" + rt_type +  "\"" + 
				", \"author\":" + "\"" + author + "\"" + ", \"resolver\":" + "\"" + resolver + "\"" + ", \"status\":" + "\"" + status + "\"" + "}";
		/*
		 * return "{\"r_id\"=" + r_id + ", \"r_amount\"=" + r_amount + ", \"author\"=" + "\"" + author + "\""
				+ ", \"rt_type\"=" + "\"" + rt_type +  "\"" + ", \"status\"=" + "\"" + status + "\"" + "}";
		 */
	}
	
	/*@Override
	public String toString() {
		return "Reimbursement [r_id=" + r_id + ", r_amount=" + r_amount + ", description=" + description + ", receipt="
				+ receipt + ", submittedDate=" + submittedDate + ", resolvedDate=" + resolvedDate + ", author=" + author
				+ ", resolver=" + resolver + ", rt_type=" + rt_type + ", status=" + status + "]";
	}*/

	
}
