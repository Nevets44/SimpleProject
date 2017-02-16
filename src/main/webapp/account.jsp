<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="com.revature.bean.User"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- fmt = formatting date/time/money etc -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- functions = useful for Strings -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link type="text/css" rel="stylesheet"
		href="bootstrap/css/bootstrap.css" />
	<link type="text/css" rel="stylesheet" href="resources/css/styles.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<title>ERS - Account Information</title>
</head>
<body class="account">
	<%@include file="nav-bar.html"%>

	<!-- Receipt Image Modal -->
	<div id="viewReceipt" class="modal">
	  <div class="modal-content">
	    <span class="close">&times;</span>
	    <img id="receipt" src="data:image/jpeg;base64,${receipt}"/>
	  </div>
	</div>

	<div class="clear-div col-md-12"><button id="showForm" type="button" onclick="showForm()" value="Change Account Information">Change Account Information</button/></div>
	<div class="account-display col-md-5">
		<div class="col-md-12">
			<h2><%=((User) session.getAttribute("user")).getFname()%> <%=((User) session.getAttribute("user")).getLname()%></h2>
			<h4>User Id: <%=((User) session.getAttribute("user")).getUserid()%></h4>
		</div>
		<div class="col-md-4">
			<div class="side-stack">
				<span>Username: <%=((User) session.getAttribute("user")).getUsername()%></span>
				<span>Email: <%=((User) session.getAttribute("user")).getEmail()%></span>
			</div>
		</div>
	</div>
	<div class="account-form col-sm-5">
		<h3>Account Update Form</h3>
		<form class="col-sm-12" method="POST" action="account">
			<span class="form-block col-sm-12">Username: <input name="username" type="text" /></span> 
			<span class="form-block col-sm-12">Password: <input name="password" type="password" /></span>
			<span class="form-block col-sm-12">Confirm Password: <input name="confirm" type="password" /></span> 
			<span class="form-block col-sm-12">First Name: <input name="fname" type="text" /></span> 
			<span class="form-block col-sm-12">Last Name: <input name="lname" type="text" /></span> 
			<span class="form-block col-sm-12">Email: <input name="email" type="text" /></span> <br /> 
			<input type="submit" value="Save Changes" />
		</form>
	</div>

	<%if (!((User) session.getAttribute("user")).isManager()) {%>
		<div class="table-box col-md-12">
			<h3>Reimbursements</h3>
			<table class="col-md-12">
				<tr>
					<th class="col-md-1">ID</th>
					<th class="col-md-2">Author</th>
					<th class="col-md-3">Description</th>
					<th class="col-md-1">Submitted On</th>
					<th class="col-md-1">Type</th>
					<th class="col-md-1">Status</th>
					<th class="col-md-1">Resolved On</th>
					<th class="col-md-2">Resolved By</th>
					<th class="col-md-1">View Receipt</th>
				</tr>
				<c:forEach items="${reimbs}" var="reimb">
					<tr>
						<td class="col-md-1">${reimb.r_id}</td>
						<td class="col-md-2">${reimb.author}</td>
						<td class="col-md-3">${reimb.description}</td>
						<td class="col-md-1">${reimb.submittedDate}</td>
						<td class="col-md-1">${reimb.rt_type}</td>
						<td class="col-md-1">${reimb.status}</td>
						<td class="col-md-1">${reimb.resolvedDate}</td>
						<td class="col-md-2">${reimb.resolver}</td>
						<td class="col-md-1"><button class="receipt-${reimb.r_id}" onclick="launchReceiptModal(${reimb.r_id})">View Receipt</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	<%}%>
		
	<script type="text/javascript" src="resources/js/ers_script.js"></script>
	<script type="text/javascript" src="resources/js/reimb_script.js"></script>
</body>
</html>