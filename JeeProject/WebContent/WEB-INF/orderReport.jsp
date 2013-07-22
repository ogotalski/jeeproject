<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <link href='styles/style.css' type=text/css rel=stylesheet>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Theatre</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="menu">
	<a href="JavaScript:sendFormPlay('playInfoId','go_main','')">Main</a>
	</div>
	<table>
		<tr>
			<td><c:out value="${currentPlay.name}" /></td>
		</tr>
		<tr>
			<td><c:out value="${currentPlay.genre}" /> &nbsp; <c:out
					value="${currentPlay.author}" /></td>
		</tr>
		<tr>
			<td><c:forEach items="${currentPlay.dates}" var="entry">
					<c:choose>
						<c:when test="${entry.id eq dateId}">
							<c:out value="${entry.date}" />
						</c:when>
						<c:otherwise>
							<a
								href="JavaScript:sendFormPlay('playInfoId','report',${entry.id})"><c:out
									value="${entry.date}" /> </a>
						</c:otherwise>
					</c:choose>
   		&nbsp;
   </c:forEach></td>
		</tr>
	</table>
	<form id="playInfoId" action="plays">
		<input type=hidden name="action" value="no" /> <input type="hidden"
			name="dateId" value="no" />
	</form>
	<br />
	<h3>Ticket summary:</h3>
	<table border="0.5" cellpadding="5">
		<tr>
			<th>Category</th>
			<th>Ticket price</th>
			<th>Total tickets</th>
			<th>Available</th>
			<th>Paid</th>
			<th>Ordered</th>
		</tr>
		<c:set var="sumPaid" value="0" />
		<c:set var="sumOrdered" value="0" />
		<c:forEach items="${ticketsSummaries}" var="ticket">
			<tr>
				<td><c:out value="${ticket.ticket.name}" /></td>
				<td><c:out value="${ticket.ticket.price}" /></td>
				<td><c:out value="${ticket.ticket.count}" /></td>
				<td><c:out value="${ticket.avalible}" /></td>
				<td><c:out value="${ticket.paid}" /> 
				<c:set var="sumPaid"
						value="${sumPaid+ticket.paid*ticket.ticket.price}" /></td>
				<td><c:out value="${ticket.ordered}" /> <c:set
						var="sumOrdered"
						value="${sumOrdered+ticket.ordered*ticket.ticket.price}" /></td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>Sum</td>
			<td><c:out value="${sumPaid}" /></td>
			<td><c:out value="${sumOrdered}" /></td>
			<td></td>
		</tr>
	</table>
	<h3>Order summary:</h3>
	<table border="0.5" cellpadding="5">
		<tr>
			<th>Login</th>
			<th>E-mail</th>
			<th>Phone</th>
			<th>Date</th>
			<th>Category</th>
			<th>Ordered</th>
			<th>Sum</th>
		</tr>
		<c:set var="sum" value="0"/>
		<c:forEach items="${userOrders}" var="order">
			<tr>
				<td><c:out value="${order.user.name}" /></td>
				<td><c:out value="${order.user.email}" /></td>
				<td><c:out value="${order.user.phone}" /></td>
				<td><c:out value="${order.date}" /></td>
				<td><c:out value="${order.order.ticket.name}" /></td>
				<td><c:out value="${order.order.quantity}" /></td>
				<td><c:out value="${order.order.sum}" /></td>
				<c:set var="sum"
						value="${sum+order.order.sum}" /></td>
			</tr>
		</c:forEach>
		<tr>
		
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td>Sum</td>
			<td><c:out value="${sum}"/> </td>
		</tr>
	</table>
	<%@ include file="footer.html"%>
</body>
</html>