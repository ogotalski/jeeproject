<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="org.apache.naming.java.javaURLContextFactory"%>
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
								<c:set var="date" value="${entry.date}" />
								<fmt:formatDate value="${entry.date}" pattern="dd.MM"/>
							</c:when>
							<c:otherwise>
								<a
									href="JavaScript:sendFormPlay('playInfoId','view_tickets',${entry.id})"><fmt:formatDate value="${entry.date}" pattern="dd.MM"/> </a>
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
	<br />
	<form action="plays" id="ticketsFormId">
		<div class="tickets">
			<table>
				<tr>
					<th>Category</th>
					<th>Ticket price</th>
					<th>Total tickets</th>
					<th>Available</th>
					<c:if test="${not empty user}">
						<th>Paid</th>
						<th>Ordered</th>
						<th>Quantity</th>
						<th>Price</th>
					</c:if>
				</tr>
				<c:set var="sumPaid" value="0" />
				<c:set var="sumOrdered" value="0" />
				<c:forEach items="${ticketsSummaries}" var="ticket">
					<tr>
						<td><c:out value="${ticket.ticket.name}" /></td>
						<td><c:out value="${ticket.ticket.price}" /></td>
						<td><c:out value="${ticket.ticket.count}" /></td>
						<td><c:out value="${ticket.avalible}" /></td>
						<c:if test="${not empty user}">
							<td><c:out value="${ticket.paid}" /> <c:set var="sumPaid"
									value="${sumPaid+ticket.paid*ticket.ticket.price}" /></td>
							<td><c:out value="${ticket.ordered}" /> <c:set
									var="sumOrdered"
									value="${sumOrdered+ticket.ordered*ticket.ticket.price}" /></td>
							<td><input type="text"  onchange="changeSum(this, ${ticket.ticket.price})" name="${ticket.ticket.name}" id ="${ticket.ticket.name}" /></td>
						    <td><label name = "label"  for="${ticket.ticket.name}" >0</label></td>
						</c:if>
					</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<c:if test="${not empty user}">
						<td>Sum</td>
						<td><c:out value="${sumPaid}" /></td>
						<td><c:out value="${sumOrdered}" /></td>
						<td></td>
						<td><label id="sumLabel">0</label> 
							</td>

					</c:if>
				</tr>
			</table>
            <c:set var="nowTime" value="<%=  new java.util.Date() %>"  />
            <c:if test="${not empty user}">
			    <c:if test="${nowTime.time - date.time < 86400000}">
				<input type="button"
					onclick="JavaScript:sendForm('ticketsFormId','order')"
					value="Order" />
				</c:if>	
				<input type="button"
					onclick="JavaScript:sendForm('ticketsFormId','cancel_order')"
					value="Cancel order" />
				<input type=hidden name="action" value="no" />
			</c:if>
		</div>
	</form>
	<%@ include file="footer.html"%>
</body>
</html>