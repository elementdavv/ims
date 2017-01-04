<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Im</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/page/system/css/login.css" />
</head>
<body onkeydown="if(event.keyCode == 13) {login();}" onload="document.getElementById('loginForm').um_name.focus();">
<div class="top"></div>
<div class="middle">
   <form name="loginForm" id="loginForm" method="post" action="<%=request.getContextPath() %>/system!afterLogin.action"  AutoComplete="off">
   <div style="height:20px;"></div>
   <div style="height:30px;color:#2378b0;"><h2>IM</h2></div>
    <div id="lib_Tab1" class="tabs">
      <div class="lib_Contentbox">
         <table class="login-table">
            <tr>
              <th scope="row"><font color="white">账号：</font></th>
              <td><input id="um_name" name="um_name" type="text" class="l-input"  maxlength="30" /></td>
            </tr>
            <tr>
              <th scope="row"><font color="white">密码：</font></th>
              <td><input id="um_pwd" name="um_pwd" type="password" class="l-input" maxlength="30" /></td>
            </tr>
         </table>
         <table>
            <tr>
              <td width="175"><li id="warning"></li></td>
              <td><button type="button" onclick="login();" class="l-btn">登 录</button>
            </tr>
        </table>
     
	    <c:if test="${loginErrorMsg != null}">
		   <div id="warning"  class="warning"><c:out value="${loginErrorMsg}"></c:out></div>
        </c:if>
      </div>
    </div>
  </form>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/page/system/js/login.js"></script>
</html>
