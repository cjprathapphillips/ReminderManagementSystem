<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp" />

<div style="background-color: lightgreen;">${message}</div>
<table class="table table-hover">
	<thead><tr><th>ID</th><th>Edit</th><th>Delete</th><th>Name</th><th>Type</th><th>Frequency</th><th>Unit</th><th>Expires On</th><th>Email Reminder</th><th>SMS Reminder</th></tr></thead>
	<tbody>
		<c:forEach items="${reminderList}" var="reminder">
            <c:if test="${reminder.urgentCountMonth<=0 and reminder.urgentCountDays<=0 }">
                    <tr style="background-color: #F65737;color:white">
            </c:if>
            <c:if test="${reminder.urgentCountMonth>=0 and reminder.urgentCountMonth<1 and reminder.urgentCountDays>0}">
                <tr style="background-color: #39C809;color:white">
            </c:if>
            <c:if test="${reminder.urgentCountMonth>=1 and reminder.urgentCountMonth<2}">
                <tr style="background-color: #91A4FA;color:white">
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
			<td>${reminder.renewDateString}</td>
			<td><a href="/reminder/sendReminderMail?id=${reminder.id}" ><img src="${pageContext.request.contextPath}/images/email.png" width="30"  height="30"/>${reminder.user.username}</a></td>
			<td><a href="/reminder/sendReminderMail?id=${reminder.id}" ><img src="${pageContext.request.contextPath}/images/phoneIcon.png" width="30"  height="30"/>${reminder.user.phoneNumber}</a></td>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="footer.jsp" />
