<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JMX Values</title>
</head>
<body>
<c:forEach items="${attributes}" var="element">  

<table>
	<tr>
	<td>${element.getAttribute()}</td>
	<td>:</td>
	<td>${element.getValue()}</td>
	</tr>
</table>

</c:forEach>

<a href="../">HOME</a>
</body>
</html>