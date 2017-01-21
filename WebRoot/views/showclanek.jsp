<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<jsp:include page="menu.jsp"/>
<h1>${clanektext.nazev}</h1>
<span class="nazev">Kategorie:</span> ${clanektext.kategorie.nazev}<br />
<span class="nazev">Autor:</span> <a href="uzivatel.do?uid=${clanektext.autor.id}">${clanektext.autor.nick}</a><br />
<span class="nazev">Datum:</span> <fmt:formatDate value="${clanektext.datum}" type="both" pattern="yyyy-dd-MM HH:mm" /> 
<br /><br /> 
${fn:replace(clanektext.text, newLineChar, "<br />")}<br /><br />
<c:if test="${not empty loginx}">
<c:if test="${hodnotit=='1'}">
	<form:form action="clanek.do" method="post" commandName="showclanek">
	<form:radiobutton path="znamka" value="1"/>1
	<form:radiobutton path="znamka" value="2"/>2
	<form:radiobutton path="znamka" value="3"/>3
	<form:radiobutton path="znamka" value="4"/>4
	<form:radiobutton path="znamka" value="5"/>5
<input type="hidden" name="clanekid" value="${clid}">
<input type="submit" name="" value="Hodnotit">		
</form:form></c:if>
</c:if><br /><span class="nazev">Hodnocení:</span> ${znamkax} <span class="nazev">Počet hodnocení:</span> ${pocet}<br />
<c:if test="${adminlog=='1'}">
<span class="nazev">Admin:</span> <a href="editclanek.do?clanekid=${clid}">Editovat</a> 
<a href="smazclanek.do?clanekid=${clid}">Smazat</a></c:if>
<center><a href="diskuse.do?diskuseid=${clid}">Diskuse</a><br /><br /><div class="nadpis">Podobné články</div><br />
<table><c:if test="${! empty podobneclanky}">
<c:forEach var="c" items="${podobneclanky}">
<tr><td class="xtd">  <a href="clanek.do?clanekid=${c.id}"><b>${c.nazev}</b></a> <br/></td></tr>
</c:forEach>
</c:if></table></center>
<jsp:include page="menudown.jsp"/>