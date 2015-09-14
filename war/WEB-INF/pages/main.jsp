<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List of Person Information</title>
<spring:url value="/resources/js/script.js" var="script" />
<spring:url value="http://code.jquery.com/jquery-2.1.4.min.js" var="jquery" />
<spring:url value="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js" var="jqueryUI" />
<spring:url value="/resources/css/style.css" var="css" />
<script type="text/javascript" src="${jquery}"></script>
<script type="text/javascript" src="${jqueryUI}"></script>
<script type="text/javascript" src="${script}"></script>
<link rel="stylesheet" href="${css}">
</head>
<body>
	<div class="title">
		<span class="tag tag-regular">List of Person Information</span>
	</div>
	<div class="delimiter">
	</div>
	<div class="container">
		<form:form id="person_info_list" modelAttribute="checkBoxes" method="POST">
			<table>
				<thead>
					<tr>
						<th><input type="checkbox" name="select" id="select_all" onclick="selectAll" /></th>
						<th><h3>First Name</h3></th>
						<th><h3>Last Name</h3></th>
						<th><h3>Birth Date</h3></th>
						<th><h3>Age</h3></th>
						<th><h3>Gender</h3></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${personInfoList}" var="personInfo">
						<tr>
							<td><form:checkbox path="checkedIds" class="check" value="${personInfo.getId()}" /></td>
							<td><c:out value="${personInfo.getFirstName()}" /></td>
							<td><c:out value="${personInfo.getLastName()}" /></td>
							<td><c:out value="${personInfo.getBirthDate()}" /></td>
							<td><c:out value="${personInfo.getAge()}" /></td>
							<!-- Output male or female depending on char m or f -->
							<td><c:out value="${personInfo.getGender() == 'm'.charAt(0) ? 'Male':'Female'}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<br>
			<input id="edit_btn" class="btn" type="submit" value="Edit" formaction="edit">
			<input id="delete_btn" class="btn" type="submit" value="Delete" formaction="delete">
			<input id="refresh_btn" class="btn" type="button" value="Refresh" />
		</form:form>
	</div>
	<!-- when controller indicates that there is multiply edit selection, it places variable errorMsg to model -->
	<!-- then popup message applies with error-->
	<c:if test="${not empty errorMsg}">
		<div id="popup_overlay" class="overlay">
			<div class="popup">
				<h3>Oops!</h3>
				<a id="close_btn" class="close" type="button">&times;</a>
				<div class="content">
					<p>${errorMsg}</p>
				</div>
			</div>
		</div>
	</c:if>
</body>
</html>