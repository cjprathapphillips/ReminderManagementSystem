<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />

<div style="background-color: lightgreen;">${message}</div>
<table class="table table-hover">
	<thead><tr><th>ID</th><th>Edit</th><th>Delete</th><th>Name</th><th>Type</th><th>Frequency</th><th>Unit</th><th>Expires On</th><th>Renew</th><th>Email Reminder</th></tr></thead>
	<tbody>
		<c:forEach items="${reminderList}" var="reminder">
            <c:if test="${reminder.urgentCountMonth<=0}">
                <tr style="background-color: red;color:white">
            </c:if>
            <c:if test="${reminder.urgentCountMonth>=0 and reminder.urgentCountMonth<1}">
                <tr style="background-color: green;color:white">
            </c:if>
            <c:if test="${reminder.urgentCountMonth>=1 and reminder.urgentCountMonth<2}">
                <tr style="background-color: aqua;color:black">
            </c:if>
            <c:if test="${reminder.urgentCountMonth>=2}">
                <tr>
            </c:if>
			<td>${reminder.id}</td>
			<td><a href="/reminder/editView?id=${reminder.id}" ><img src="${pageContext.request.contextPath}/images/edit.png" width="20"  height="20"/></a></td>
            <td><a href="/reminder/delete?id=${reminder.id}" ><img src="${pageContext.request.contextPath}/images/delete.png" width="20"  height="20" /></a></td>
			<td>${reminder.name}</td>
			<td>${reminder.reminderType}</td>
			<td>${reminder.frequency}</td>
			<td>${reminder.unit}</td>
			<td>${reminder.renewDate}</td>
			<td><a href="/reminder/renew?id=${reminder.id}" ><img src="${pageContext.request.contextPath}/images/renew.png" width="20"  height="20"/></a></td>
			<td><a href="/reminder/sendReminderMail?id=${reminder.id}" ><img src="${pageContext.request.contextPath}/images/email.png" width="20"  height="20"/>${reminder.user.username}</a></td>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="footer.jsp" />
