<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<jsp:include page="menu.jsp"/>
<center>
<c:if test="${! empty loginx}">
  		<form:form action="diskuse.do" method="post" commandName="showdiskuse">
						<form:textarea path="text" rows="5" cols="40"/>
						<input type="hidden" name="diskuseid" value="${diskuseid}"/><br/><br />						
	<input type="submit" name="" value="Vložit příspěvek">		
		</form:form>
		</c:if> <br /><br />
   <c:if test="${! empty prispevky}">
				<c:forEach var="d" items="${prispevky}">
				<table class="dtable"><tr><td class="dtd">
							<a href="uzivatel.do?uid=${d.autor.id}"><b>${d.autor.nick}</b></a></td><td class="dtd">${d.datum} <c:if test="${adminlog=='1'}"><a href="diskuseedit.do?diskuseid=${d.id}">(Editovat)</a> 
							<a href="smazdiskuse.do?did=${d.id}">X</a></c:if></td></tr><tr>
							<td class="dtd">${fn:replace(d.text, newLineChar, "<br />")}</td>
							</tr></table><br />
				</c:forEach>
			</c:if></center>
<jsp:include page="menudown.jsp"/>
