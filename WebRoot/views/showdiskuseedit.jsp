<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="taglib_includes.jsp" %>
<jsp:include page="menu.jsp"/>
<center><form:form action="diskuseedit.do" method="post" commandName="showdiskuseedit">
<span class="nazev">Text příspěvku:</span><br />
<form:textarea path="text" rows="5" cols="40"/>
<form:errors path="text" cssStyle="color:red"></form:errors><form:hidden path="id"/> <br /><br />
<input type="submit" name="" value="Změnit příspěvek">						
</form:form></center> 
<jsp:include page="menudown.jsp"/>
