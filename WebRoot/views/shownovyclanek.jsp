<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="menu.jsp"/>
 		<form:form action="novyclanek.do" method="post" commandName="shownovyclanek">

						<span class="nazev">Název</span> <form:input path="nazev"/> <form:errors path="nazev" cssStyle="color:red"></form:errors><br />
						<span class="nazev">Text</span> <form:errors path="text" cssStyle="color:red"></form:errors><br /><form:textarea path="text" rows="20" cols="50" /><br />
						<p><span class="nazev">Kategorie</span> <select name="kategorieid">
						<c:forEach var="idk" items="${kategorielist}">
		<option value="${idk.id}">${idk.nazev}</option>			
				</c:forEach>
</select></p>					
	<center><input type="submit" name="" value="Přidat článek"></center>
		</form:form>
<jsp:include page="menudown.jsp"/>