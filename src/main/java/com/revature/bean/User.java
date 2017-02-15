package com.revature.bean;

public class User {
	private final int userid;
	private final String username;
	private final String fname;
	private final String lname;
	private final String email;
	private final boolean isManager;
	
	public User(int userid, String username, String fname, String lname, String email, boolean isManager) {
		super();
		this.userid = userid;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.isManager = isManager;
	}

	public int getUserid() {
		return userid;
	}

	public String getUsername() {
		return username;
	}

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getEmail() {
		return email;
	}

	public boolean isManager() {
		return isManager;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", fname=" + fname + ", lname=" + lname
				+ ", email=" + email + ", isManager=" + isManager + "]";
	}
}
