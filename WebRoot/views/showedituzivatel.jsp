<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<jsp:include page="menu.jsp"/>
<center><c:if test="${error=='1'}"><div class="chyba">Hesla nesouhlasí.</div></c:if>
<c:if test="${error=='2'}"><div class="chyba">Heslo změněno.</div></c:if>
<form:form action="uzivateledit.do" method="post" commandName="showuzivateledit">
<span class="nazev">Heslo:</span> <input type="password" name="hesloa"><br />
<span class="nazev">Kontrola:</span> <input type="password" name="heslob"><br />
<input type="submit" name="" value="Změnit heslo">						
</form:form></center>
<jsp:include page="menudown.jsp"/>