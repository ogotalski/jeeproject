<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href='styles//style.css' type=text/css rel=stylesheet>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Registration</title>
</head>
<body>
	<%@ include file="WEB-INF/header.jsp" %>
	<hr />

	<h1>Registration</h1>
	<form method="get" name="signUp" action="login">
		<label id="errorLabel"></label>
		
		<input type="hidden" name="action" value="no" />
		
		<table>
			<tr>
				<td><label for="login" >Login</label></td>
				<td><input type="text" name="login" id="login" /></td>
			</tr>
			<tr>
				<td><label for="password" >Password</label></td>
				<td><input type="password" name="password" id="password" /></td>
			</tr>
			<tr>
				<td><label for="password2" >Confirm password</label></td>
				<td><input type="password" name="password2" id="password2" /></td>
			</tr>
			<tr>
				<td><label for="email" >E-mail</label></td>
				<td><input type="text" name="email" id="email" /></td>
			</tr>
			<tr>
				<td><label for="phone" >Phone number</label></td>
				<td><input type="text" name="phone" id="phone" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="button" value="Sign up"
					onclick="sendFormSignUP('registration')" /></td>
			</tr>
		</table>
	</form>
<script src="WEB-INF/script.js"></script>

	<%@ include file="WEB-INF/footer.html"%>
</body>
</html>