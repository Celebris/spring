<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp"%>
<jsp:include page="menu.jsp"/>
<h1>${uz.nick}</h1>
<span class="nazev">Email:</span> ${uz.email} <br />
<span class="nazev">Typ uživatele:</span><c:if test="${uz.admin==0}">Běžný uživatel</c:if>
<c:if test="${uz.admin==1}">Admin</c:if>
<center>
<c:if test="${uz.admin==0}">
<a href="uzadmin.do?uzid=${uz.id}">Dát administrátorské oprávnění</a><br /></c:if>
<c:if test="${uz.admin==1}"><a href="uzneadmin.do?uzid=${uz.id}">Odebrat administrátoské oprávnění</a><br /></c:if></center>
<jsp:include page="menudown.jsp"/>