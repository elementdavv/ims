<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/signin.css"/>
	<script src="<%=request.getContextPath() %>/page/web/js/jquery-2.1.1.min.js"></script>
	<script src="<%=request.getContextPath() %>/page/web/js/signin.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/jquery-2.1.1.min.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/md5.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/signin.js"></script>
</head>
<body>

<div class="signin-bg">
    <div class="signin-logo">
        <div class="main clearfix">
            <a href=""><span>G</span>roup Meal</a>
            <a class="pull-left ng-binding">团餐IM系统</a>
        </div>
    </div>
    <div class="signin-form">
        <div class="rightBox ">
            <div class="sign-flow signinBox">
                <form onsubmit="addMD5()" method="post" action="system!afterLogin" class="form-inline" name="formSignin" novalidate="novalidate">
                    <div class="form-group firstNone">
                        <label for="username" class="username"></label>
                        <input type="text" name="account" placeholder="手机号" required="" class="form-control" name="accountNumber" ng-model="user.accountNumber" ng-pattern="/^1[3-9][0-9]{9,9}$/" id="username" my-focus="">
                    </div>
                    <div class="form-group">
                        <label for="pwdIn" class="pwdIn"></label>
                        <input type="password" name="userpwd" placeholder="密码" required="" ng-pattern="/^[0-9a-zA-Z!@#$%^&amp;*(){}:&quot;|>?\[\];,.\/\-=_+]{6,16}$/" class="form-control-my ng-untouched ng-valid-pattern ng-dirty ng-valid-parse ng-valid ng-valid-required" name="passWord" ng-model="user.passWord" id="pwdIn" my-focus="">
                        </div>
                    <div class="bot clearfix">
                        <a class="pull-right" href="<%=request.getContextPath() %>/system!fogetPassword" ui-sref="account.forgotpassword">忘记密码？</a>
                    </div>
                    <div class="button-wrapper form-group">
                        <button class="sign-button submit" type="submit">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>