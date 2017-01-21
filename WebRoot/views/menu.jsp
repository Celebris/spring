<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Zpravodajský server</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="description" content="" />
<meta name="keywords" content="" />
<link rel="stylesheet" type="text/css" href="views/style.css">
<script type="text/javascript" src="js/script.js"></script>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script> 
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/datetimepicker.js"></script>
</head>
<body>

<div id="container" class="clearfix">
	<div id="menucont">
	</div>
	<div id="middlecont">
		<p>Zpravodajský server</p>
	</div>
	<div id="maincont" class="clearfix">
		<div id="mainleft">
		<h3>Zprávy</h3>
            <ul class="sidemenu">

            <li><a title="" href="seznamclanku.do?kategorie=0&page=0">Zprávy</a></li>
            </ul>
            <br /><br /><h3>Kategorie</h3>
             <ul class="sidemenu">
            <c:if test="${! empty kategoriemenu}">
				<c:forEach var="menuk" items="${kategoriemenu}">
								<li> <a href="seznamclanku.do?kategorie=${menuk.id}&page=0">${menuk.nazev}</a></li>
				</c:forEach>
			</c:if>
			</ul>
       <c:if test="${not empty loginx}"><br /><h3>Přihlášen</h3>
       <ul class="sidemenu">
                <li><a href="uzivateledit.do?par=0"><b>${loginx}</b></a></li>
                <li> <a href="logout.do">Logout</a></li>
                </ul>
            </c:if>
            <c:if test="${empty loginx}">
           <h3>Přihlásit se</h3><ul class="sidemenu">
                <li><a title="" href="novyuzivatel.do?par=0">Registrace</a></li>
                <li><a title="" href="login.do?par=0">Login</a></li>
                </ul>
            </c:if> 
<c:if test="${adminlog=='1'}">
                <br /><br /><br /><h3>Admin</h3><ul class="sidemenu">
                 <li><a href="uzivatele.do">Seznam uživatelů</a></li>
                 <li><a href="novyclanek.do">Nový článek</a></li>
				 <li><a href="novakategorie.do">Nová kategorie</a></li>
				 </ul>
            </c:if>      
           
		</div>
		<div id="mainright">