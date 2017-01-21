<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<jsp:include page="menu.jsp"/>
<center><h1>Vyhledat články</h1></center>
 		<form:form action="vyhledat.do" method="post" commandName="showvyhledatclanky">
						<input type="text" name="hledanytext">		
						<input type="hidden" name="page" value="0">
						<input type="submit" name="" value="Vyhledat">						
</form:form><c:if test="${empty clankylist}">Žádné články nenalezeny.</c:if>
<table class="xtable"><c:if test="${! empty clankylist}">
<c:forEach var="s" items="${clankylist}">
<tr><td class="xtd"> <fmt:formatDate value="${s.datum}" type="both" pattern="yyyy-dd-MM HH:mm" /> </td><td class="xtd"><a href="clanek.do?clanekid=${s.id}">${s.nazev}</a></td></tr>
</c:forEach></c:if></table>
<c:if test="${stranaa>-1}">
			<a href="vyhledat.do?page=${stranaa}"><<</a></c:if> 
			<c:if test="${stranab>-1}"><a href="vyhledat.do?page=${stranab}">>></a></c:if>
<jsp:include page="menudown.jsp"/>