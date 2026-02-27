<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />

<div style="background-color: lightgreen;">${message}</div>
<center><a href="/user/editView?" ><img src="${pageContext.request.contextPath}/images/add.png" width="20"  height="20"/></a></center>
<table class="table table-hover">
	<thead><th>ID</th><th>Edit</th><th>Delete</th><th>Name</th><!--th>Password</th--><th>Role</th></thead>
	<tbody>
		<c:forEach items="${userList}" var="user">
            <tr>
			<td>${user.userId}</td>
			<td><a href="/user/editView?userId=${user.userId}" ><img src="${pageContext.request.contextPath}/images/edit.png" width="20"  height="20"/></a></td>
            <td><a href="/user/delete?userId=${user.userId}" ><img src="${pageContext.request.contextPath}/images/delete.png" width="20"  height="20" /></a></td>
			<td>${user.username}</td>
			<!--td>${user.password}</td-->
			<td>${user.authorities}</td>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="footer.jsp" />
