<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp"%>
<jsp:include page="menu.jsp"/>
<center><h1>Zprávy</h1></center>
<center><a href="vyhledat.do?page=0">Vyhledat články</a></center>
<table class="xtable">
<c:if test="${! empty cl}">
<c:forEach var="s" items="${cl}">
<tr><td class="xtd" valign="middle"> <fmt:formatDate value="${s.datum}" type="both" pattern="yyyy-dd-MM HH:mm" /> 
</td><td class="xtd"><a href="clanek.do?clanekid=${s.id}"><b>${s.nazev}</b></a></td></tr>
</c:forEach></c:if>
</table><center><c:if test="${stranaa>-1}">
<a href="seznamclanku.do?page=${stranaa}&kategorie=${kategorie}"><<</a></c:if>
<c:if test="${stranab>-1}"><a href="seznamclanku.do?page=${stranab}&kategorie=${kategorie}">>></a></c:if></center>
<jsp:include page="menudown.jsp"/>