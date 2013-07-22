

	

		<c:if test="${not empty errorMessage}">
			<c:out value="${errorMessage}" />
			<hr />
		</c:if>
<table> <tr>
<td> <div class = "header"><h1>Theatre</h1></div> </td>
<td>
<div class="login">
<form method="post" action="login" name="loginForm" id="loginFormId">
		<input type=hidden name="action" value="no"/>
		<script src="script.js"></script>
		<c:choose>
			<c:when test="${not empty user}">
				<table>
					<tr>
						<td>User: <c:out value="${user.name}" />
						</td>
						<td><a href="JavaScript:sendForm('loginFormId','Logout')">Logout</a>
						</td>
					</tr>
					<tr>
						<td>Role: <c:out value="${user.role}" />
						</td>
					</tr>


				</table>
			</c:when>
			<c:otherwise>
				<table>
					<tr>
						<td>Login:</td>
						<td><input name="login" type="text"></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input name="password" type="password"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="button" value="Login"
							onclick="JavaScript:sendForm('loginFormId','login')"> <a
							href="signup.jsp">Sign in</a></td>
					</tr>
				</table>

			</c:otherwise>
		</c:choose>
	</form>
	</div>
	</td>
	</tr>
	</table>
	
