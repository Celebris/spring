<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%-- <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:include page="menu.jsp"/>--%>
<%@include file="menu.jsp" %> 
<center> 
<c:if test="${error=='1'}"><div class="chyba">Špatné uživatelské jméno nebo heslo.</div></c:if> 
<c:choose>
            <c:when test="${not empty loginx}">
                Jste přihlášen jako <b>${loginx}</b>. <a href="logout.do">Logout</a>
            </c:when>
            <c:otherwise>
                <form:form action="login.do" method="post" commandName="showlogin">
						<b>Nick</b><form:input path="nick"/>
						<form:errors path="nick" cssStyle="color:red"></form:errors><br />
						<b>Heslo</b><form:password path="heslo"/>
						<form:errors path="heslo" cssStyle="color:red"></form:errors><br />
	<input type="submit" name="" value="Login">	
		</form:form>
            </c:otherwise>
        </c:choose>
   </center>
<jsp:include page="menudown.jsp"/>