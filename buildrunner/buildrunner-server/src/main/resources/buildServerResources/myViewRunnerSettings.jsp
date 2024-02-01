<%@ page import="com.eugene.teamcity.buildrunner.MyConstants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<c:set var="messageId" value="<%=MyConstants.MESSAGE_KEY%>" />

<div class="parameter">
    Message: <props:displayValue name="${messageId}" emptyValue=""/>
</div>