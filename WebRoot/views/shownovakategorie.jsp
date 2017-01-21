<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="menu.jsp"/>
<center><h1>Nová kategorie</h1></center>
<form:form action="novakategorie.do" method="post" commandName="shownovakategorie">
<span class="nazev">Název kategorie:</span> 
<form:input path="nazev"/><form:errors path="nazev" cssStyle="color:red"></form:errors><br />
<input type="submit" name="" value="Přidat kategorii">
</form:form>
<jsp:include page="menudown.jsp"/>
