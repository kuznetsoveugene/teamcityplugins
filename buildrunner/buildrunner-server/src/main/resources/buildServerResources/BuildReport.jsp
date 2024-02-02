<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="text" type="java.lang.String"--%>
<%--@elvariable id="color" type="java.lang.String"--%>

<html>
<head>
    <style>
        .green-text {
            color: green;
        }
       .red-text {
           color: red;
           font-weight: bold;
       }
    </style>
</head>
<body>

<b>This is the report</b>
<p class="${color}-text">
	<c:out value="${text}" default="Build Has Passed"/><br/>
	Color: ${color}
</p>

</body>
</html>