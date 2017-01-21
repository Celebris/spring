<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="menu.jsp"/>
<form:form action="novyuzivatel.do" method="post" commandName="showregistrace">
<center><h1>Registrace</h1></center><c:if test="${error=='1'}"><div class="chyba">Uživatel zaregistrován.</div></c:if>
<table><tr><td>
						<span class="nazev">Nick</span> </td><td><form:input path="nick"/></td><td> 
						<form:errors path="nick" cssStyle="color:red"></form:errors></td></tr><td>
						<span class="nazev">Heslo</span> </td><td><form:password path="heslo"/></td><td> 
						<form:errors path="heslo" cssStyle="color:red"></form:errors></td></tr><td>
						<span class="nazev">Kontrola hesla</span> </td><td><form:password path="heslo2"/></td><td> 
						<form:errors path="heslo2" cssStyle="color:red"></form:errors></td></tr><td>				
						<span class="nazev">Email</span> </td><td><form:input path="email"/></td><td> 
						<form:errors path="email" cssStyle="color:red"></form:errors></td></tr></table>			
<center><input type="submit" name="" value="Zaregistrovat se">	</center>
</form:form>
<jsp:include page="menudown.jsp"/>
