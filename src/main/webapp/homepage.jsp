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
<link type="text/css" rel="stylesheet"
	href="bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="resources/css/styles.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERS - Homepage</title>
</head>
<body>
	<%@include file="nav-bar.html"%>

	<div class="home-top col-md-12">
		<h1>Welcome, <%=((User) session.getAttribute("user")).getFname()%> <%=((User) session.getAttribute("user")).getLname()%></h1>
	</div>

	<%if (((User) session.getAttribute("user")).isManager()) {%>
		<div class="home-bottom col-md-12">
			<a href="reimb">Check Submitted Reimbursements</a>
		</div>
	<%} else {%>
		<div class="home-bottom col-md-12">
			<a href="reimb">Check Your Reimbursements</a>
		</div>
	<%}%>
</body>
</html>