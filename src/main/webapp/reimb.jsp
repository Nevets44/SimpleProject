<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- fmt = formatting date/time/money etc -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- functions = useful for Strings -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="com.revature.bean.User"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="resources/css/styles.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<title>ERS - Reimbursements</title>
</head>
<body>
	<%@include file="nav-bar.html"%>

	<!-- New Reimbursement Modal -->
	<div id="newReimb" class="modal">
	  <div class="modal-content">
	    <span class="close">&times;</span>
	    <h3>Reimbursement Form</h3>
	    <form class="reimbForm" method="POST" action="reimb" enctype="multipart/form-data">
	    	<div class="reimb-attr col=md-12">
				<select class="reimb-types form-control col-md-2" name="type">
					<c:forEach items="${types}" var="type">
						<option>${type}</option>
					</c:forEach>
				</select>
	    	</div>
	    	<span class="form-block col-md-12">Amount: $<input name="amount" type="number"/></span>
	    	<span class="form-block col-md-12">Description: </span>
	    	<textarea class="col-md-12" name="desc"></textarea >
	    	<span class="form-block col-md-12">(Optional) Upload Receipt: <input name="receipt" type="file"/></span>
	    	<span class="form-block"><input type="submit" value="Submit Form" /></span>
	    </form>
	  </div>
	</div>
	
	<!-- Receipt Image Modal -->
	<div id="viewReceipt" class="modal">
	  <div class="modal-content">
	    <span class="close">&times;</span>
	    <img id="receipt" src="data:image/jpeg;base64,${receipt}"/>
	  </div>
	</div>

	<div class="table-top col-md-12">
		<div class="clear-div col-md-12"><input class="search-box col-md-2" type="text" placeholder="Search..." /></div>
		<%if (((User) session.getAttribute("user")).isManager()) {%>
			<select id="employees" class="reimb-emps form-control col-md-3" onchange="reimbsByEmployee()">
				<option>--All--</option>
				<c:forEach items="${emps}" var="emp">
					<option value="${emp.userid}">${emp.fname} ${emp.lname}</option>
				</c:forEach>
			</select> 
		<%}%>
		<div class="reimb-attr clear-div col-md-3">
			<select id="status" class="reimb-statuses form-control col-md-3" onchange="reimbsByStatus()">
				<option>--Status--</option>
				<c:forEach items="${statuses}" var="status">
					<option>${status}</option>
				</c:forEach>
			</select> 
			<select id="type" class="reimb-types form-control col-md-3" onchange="reimbsByType()">
				<option>--Type--</option>
				<c:forEach items="${types}" var="type">
					<option>${type}</option>
				</c:forEach>
			</select>
		</div>
		<%if (!((User) session.getAttribute("user")).isManager()) {%>
			<button id="addReimb" class="mod-table">Add Reimbursement</button>
		<%} else {%>
			<button id="approveReimb" class="mod-table" onclick="updateReimbursements()">Approve/Deny Reimbursements</button>
		<%}%>
	</div>
	<div class="table-box col-md-12">
		<table class="reimb-table col-md-12">
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
	<script type="text/javascript" src="resources/js/ers_script.js"></script>	
	<script type="text/javascript" src="resources/js/reimb_script.js"></script>
</body>
</html>