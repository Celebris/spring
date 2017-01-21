<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<jsp:include page="menu.jsp"/>
 		<form:form action="editclanek.do" method="post" commandName="showclanekedit">
						<span class="nazev">Název:</span> <form:input path="nazev"/>
						<form:errors path="nazev" cssStyle="color:red"></form:errors> <br /><br />				
						<span class="nazev">Text:</span><br /><form:textarea path="text" rows="40" cols="50"/>
						<form:errors path="text" cssStyle="color:red"></form:errors>  <br /><br />	
						<form:hidden path="id"/>
				<span class="nazev">Kategorie:</span> <select name="kategorieid">
						<c:forEach var="idk" items="${kategorielist}">
						<c:if test="${kategorieid==idk.id}">
		<option value="${idk.id}" selected>${idk.nazev}</option>	
		</c:if>		
		<c:if test="${kategorieid!=idk.id}">
		<option value="${idk.id}">${idk.nazev}</option>	
		</c:if>	
</c:forEach>
</select>			
<input type="submit" name="" value="Změnit článek">						
</form:form>
<jsp:include page="menudown.jsp"/>
