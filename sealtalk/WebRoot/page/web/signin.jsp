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
                <div class="form-inline ng-valid-pattern ng-dirty ng-valid-parse ng-valid ng-valid-required" name="formSignin" novalidate="novalidate">
                    <!--<div class="title">-->
                        <!--<a class="cur signinBtn" href="javascript:void 0">登录</a>-->
                        <!--<a class="signupBtn" href="#/account/signup" ui-sref="account.signup">注册</a>-->
                        <!--<div class="triangle-up"></div>-->
                    <!--</div>-->
                    <div class="form-group firstNone">
                        <label for="username" class="username"></label>
                        <input type="text" placeholder="手机号" required="" class="form-control-my ng-valid-pattern ng-dirty ng-valid-parse ng-valid ng-valid-required ng-touched" name="accountNumber" ng-model="user.accountNumber" ng-pattern="/^1[3-9][0-9]{9,9}$/" id="username" my-focus="">
                        <!--<p class="error-block ng-hide" ng-show="(formSignin.accountNumber.$dirty||formSignin.submitted)&amp;&amp;formSignin.accountNumber.$invalid&amp;&amp;!formSignin.accountNumber.$focused">手机号格式错误</p>-->

                    </div>
                    <div class="form-group">
                        <label for="pwdIn" class="pwdIn"></label>
                        <input type="password" placeholder="密码" required="" ng-pattern="/^[0-9a-zA-Z!@#$%^&amp;*(){}:&quot;|>?\[\];,.\/\-=_+]{6,16}$/" class="form-control-my ng-untouched ng-valid-pattern ng-dirty ng-valid-parse ng-valid ng-valid-required" name="passWord" ng-model="user.passWord" id="pwdIn" my-focus="">
                        <!--<p class="error-block ng-hide" ng-show="(formSignin.passWord.$dirty||formSignin.submitted)&amp;&amp;formSignin.passWord.$invalid&amp;&amp;!formSignin.passWord.$focused">密码格式错误</p>-->
                        <!--<p class="error-block ng-hide" ng-show="!formSignin.passWord.$invalid&amp;&amp;userorpwdIsError&amp;&amp;!formSignin.passWord.$focused">手机号或密码错误</p>-->
                    </div>
                    <div class="bot clearfix">
                        <a class="pull-right" href="<%=request.getContextPath() %>/system!fogetPassword" ui-sref="account.forgotpassword">忘记密码？</a>
                    </div>
                    <div class="button-wrapper form-group">
                        <button class="sign-button submit" type="submit" onclick="signin()">登录</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>