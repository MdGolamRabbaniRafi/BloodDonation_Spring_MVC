<%@ page contentType="text/html" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<html>
<head></head>
<body>
<h3>Registration Page</h3>

<form:form method="post" action="DeleteStudent" modelAttribute="user">
<p>Id: ${user.id}</p>
<p>Name: ${user.fullname}</p>
<p>Email: ${user.email}</p>
<p>Gender: ${user.gender}</p>
<p>DOB: ${user.dob}</p>
<p> Quata:</p>

<c:forEach var="quata" items="${user.quataValues}">
    <p>${quata.name}</p>
</c:forEach>

<p>Country: ${user.country}</p>

<input type="submit" value="Delete" />

</form:form>

</body>
</html>