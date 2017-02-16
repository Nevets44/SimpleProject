<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="bootstrap/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="resources/css/styles.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERS - Login</title>
</head>
<body class="col-md-12">
	<div class="login">
		<form method="POST" action="login">
			<div class="col-md-12">Username <input class="col-md-12" name="username" type="text" /></div>
			<div class="col-md-12">Password <input class="col-md-12" name="password" type="password" /></div>
			<input type="submit" value="Login"/>
		</form>
	</div>
</body>
</html>