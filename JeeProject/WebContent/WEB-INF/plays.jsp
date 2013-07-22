

	<table >
		<c:forEach items="${applicationScope.plays}" var="play">
			
			<tr>
				<td>
	<a name="${play.name}"></a>
<div class ="play">
				
					<table>
						<tr>
							<td><h3><c:if test="${not empty play.imageURI}">
											<img alt="${play.name}" src="${play.imageURI}">
										</c:if><c:out value="${play.name}" /></h3>
										<h4><c:out value="${play.genre}" /> &nbsp; <a
								href="JavaScript:showAuthorInfo('${play.author}')">${play.author}</a> </h4>
								<c:if
									test="${not empty play.description  }">
									
									<div class="description">
									<a onClick="change(this)" href="#${play.name}">Description</a><div class="hidable">
										
										<p>
										<c:out value="${play.description}" />
										</p>
										</div>
									</div>


								</c:if></td>
						</tr>
						<tr>
							<td>
							<c:forEach items="${play.dates}" var="entry">
									<a
										href="JavaScript:sendFormPlay('playInfoId','view_tickets',${entry.id})">
										<fmt:formatDate value="${entry.date}" pattern="dd.MM"/> </a>
   		&nbsp;
   </c:forEach></td>
						</tr>
					</table>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
	<form id="playInfoId" action="plays">
		<input type=hidden name="action" value="no" /> <input type="hidden"
			name="dateId" value="no" />
	</form>
	

