<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${title}</title>
<spring:url value="http://code.jquery.com/jquery-2.1.4.min.js" var="jquery" />
<spring:url value="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js" var="jqueryUI" />
<spring:url value="/resources/css/style.css" var="css" />
<spring:url value="/resources/js/script.js" var="script" />
<script type="text/javascript" src="${jquery}"></script>
<script type="text/javascript" src="${jqueryUI}"></script>
<script type="text/javascript" src="${script}"></script>
<link rel="stylesheet" href="${css}">
</head>
<body>

	<div class="title">
		<span class="tag tag-regular">${title}</span>
	</div>
	<div class="delimiter"></div>
	<div class="container">
		<form:form id="person_info" modelAttribute="personInfoForm" method="POST">
			<table>
				<tr>
					<td><span class="red-text">*</span>First Name:</td>
					<td><form:input class="input" type="text" path="firstName" maxlength="45" /></td>
					<td class="hidden"><span class="error"> <form:errors path="firstName" />
					</span></td>
				</tr>
				<tr>
					<td><span class="red-text">*</span>Last Name:</td>
					<td><form:input class="input" type="text" path="lastName" maxlength="45" /></td>
					<td class="hidden"><span class="error"> <form:errors path="lastName" />
					</span></td>
				</tr>
				<tr>
					<td><span class="red-text">*</span>Birth Date(yyyy-MM-dd):</td>
					<td><form:input class="input" type="date" path="birthDate" maxlength="10" /></td>
					<td class="hidden"><span class="error"> <form:errors path="birthDate" />
					</span></td>
				</tr>
				<tr>
					<td><span class="red-text">*</span>Age:</td>
					<td><form:input class="input" type="number" path="age" maxlength="3" /></td>
					<td class="hidden"><span class="error"> <form:errors path="age" />
					</span></td>
				</tr>
				<tr>
					<td><span class="red-text">*</span>Gender</td>
					<td><form:radiobutton path="gender" value="m" label="Male" /> <form:radiobutton path="gender" value="f" label="Female" /></td>
					<td class="hidden"><span class="error"> <form:errors path="gender" />
					</span></td>
				</tr>
				<tr>
					<td><span class="red-text">*</span></td>
					<td><span class="red-text">- inputs that must be filled or selected.</span></td>
				</tr>
			</table>

			<br>

			<c:if test="${mode eq 'add'}">
				<input id="add_btn" class="btn valid" type="submit" value="Add" formaction="add">
			</c:if>

			<c:if test="${mode eq 'edit'}">
				<input id="save_btn" class="btn valid" type="submit" value="Save" formaction="save">
			</c:if>
			<input id="cancel_btn" class="btn" type="submit" value="Cancel" formaction="cancel">
		</form:form>
	</div>

</body>
</html>