<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IM</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/component/message/skin/qq/ymPrompt.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/component/message/ymPrompt.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/component/extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/component/extjs/ext-all.js" /></script>
<!--script type="text/javascript" src="<%=request.getContextPath() %>/component/extjs/bootstrap.js" /></script-->
<script type="text/javascript" src="<%=request.getContextPath() %>/component/extjs/locale/ext-lang-zh_CN.js" /></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/page/usermanager/js/user.js" /></script>

</head>
<body>
	<c:if test="${SessionUser != null}">
		Hello <b>${SessionUser.userName }</b> welcome to Im!
	</c:if>
	<c:if test="${SessionUser == null}">
		<%
			response.sendRedirect(request.getContextPath()+"/system!login.action");
		%>
	</c:if>
</body>
</html>
