<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/jstl/core"%>
<%@ taglib prefix="fmt" uri="/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href='styles/style.css' type=text/css rel=stylesheet>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Theatre</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<hr />
		<div class="menu">
		<a href="JavaScript:sendFormPlay('playInfoId','go_main','')">Main</a>
	</div>
	<div class="viewPlay">
		<table>

			<tr>
				<td><h3>
						<c:if test="${not empty currentPlay.imageURI}">
							<img alt="${currentPlay.name}" src="${currentPlay.imageURI}">
						</c:if>
						<c:out value="${currentPlay.name}" />
					</h3>
					<h4>
						<c:out value="${currentPlay.genre}" />
						&nbsp; <a
							href="JavaScript:showAuthorInfo('${currentPlay.author}')">${currentPlay.author}</a>
					</h4> <c:if test="${not empty currentPlay.description  }">

						<div class="description">
							<a onClick="change(this)" href="#">Description</a><div class="hidable">

								<p>
									<c:out value="${currentPlay.description}" />
								</p>
							</div>
						</div>


					</c:if></td>
			</tr>

			<tr>
				<td><c:forEach items="${currentPlay.dates}" var="entry">
						<c:choose>
							<c:when test="${entry.id eq dateId}">
								<fmt:formatDate value="${entry.date}" pattern="dd.MM"/>
							</c:when>
							<c:otherwise>
								<a
									href="JavaScript:sendFormPlay('playInfoId','view_tickets',${entry.id})"><fmt:formatDate value="${entry.date}" pattern="dd.MM"/></a>
							</c:otherwise>
						</c:choose>
   		&nbsp;
   </c:forEach></td>
			</tr>
		</table>
	</div>
	<form id="playInfoId" action="plays">
		<input type=hidden name="action" value="no" /> <input type="hidden"
			name="dateId" value="no" />
	</form>
	<br/>
		<form action="plays" id="ticketsFormId">
			<table border="0.5" cellpadding="5">
				<tr>
					<th></th>
					<th>Login</th>
					<th>Date</th>
					<th>Category</th>
					<th>Ordered</th>
					<th>Sum</th>
					<th>Quantity</th>
					<th>Price</th>
				</tr>
				<c:forEach items="${userOrders}" var="order">
					<tr>
						<td><input type="checkbox" name="orderIds" value="${order.orderId}"/></td>
						<td><c:out value="${order.user.name}" /></td>
						<td><c:out value="${order.date}" /></td>
						<td><c:out value="${order.order.ticket.name}" /></td>
						<td><c:out value="${order.order.quantity}" /></td>
						<td><c:out value="${order.order.sum}" /></td>
						<td> <input type="text" name="${order.orderId}"  onchange="changeSumOrder (this, ${order.order.ticket.price})" id="${order.orderId}" value="${order.order.quantity}" /></td>
						 <td><label name = "label"  for="${order.orderId}" >${order.order.sum}</label></td>
					</tr>
				</c:forEach>
				<tr>
				</tr>
			</table>
			<c:if test="${not empty user}">
				<input type="button" onclick="JavaScript:sendForm('ticketsFormId','pay')"
					value="Pay Order" />
				<input type="button" onclick="JavaScript:sendForm('ticketsFormId','cancel_order')"
					value="Cancel order" />
				<a href="JavaScript:sendForm('ticketsFormId','report')"> Report</a>	
				<input type=hidden name="action" value="no" />
			</c:if>
		</form> 
		<%@ include file="footer.html"%>
</body>
</html>