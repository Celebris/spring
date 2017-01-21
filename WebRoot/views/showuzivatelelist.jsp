<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp"%>
<jsp:include page="menu.jsp"/>
<center><h1>Uživatelé</h1></center>
<table class="xtable">
			<c:if test="${! empty uzivatelex}">
				<c:forEach var="u" items="${uzivatelex}">	
				<tr><td class="xtd"><a href="uzivatel.do?uid=${u.id}">${u.nick}</a></td></tr>		
				</c:forEach>
			</c:if>
			</table>
<jsp:include page="menudown.jsp"/>