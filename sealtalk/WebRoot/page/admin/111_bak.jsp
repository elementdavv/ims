<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<%@ include file="0.jsp" %>
<script src="js/organ.js"></script>
</head>
<body>
<div class="admheader">
	<ul class="admheadermenu">
		<li class="active">基本设置</li>
		<li><a href="21.jsp">高级设置</a></li>
	</ul>
</div>
<div class="menupanel11">
	<div id="jb" class="sidebar11">
		<div class="menu menuactive">组织结构</div>
		<div class="menu"><a href="12.jsp">组织信息</a></div>
		<div class="menu"><a href="13.jsp">群组管理</a></div>
	</div>
	<div class="organ">
		<div class="organline">
			<button class="toleft btnadmin">超级管理员</button>
			<button class="toright btnadmin" >+</button>
		</div>
		<div class="organline" >
			<input type="text" placeholder="搜索人员" />
		</div>
		<div class="organline ztree" id="tree"></div>
	</div>
</div>
<div class="infopanel11">
	<div class="info">
		<div class="infotitle">
			<div class="title">成员信息</div>
			<div class="infotab">
				<div class="infotabi tabactive">基本信息</div>
				<div class="infotabi"><a href="112.jsp">所属分支</a></div>
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				成员帐号：
				<input type="text">
			</div>
			<div class="column2">
				真实姓名：
				<input type="text">
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				手机：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text">
			</div>
			<div class="column2">
				性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select>
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				出生日期：
				<input type="text">
			</div>
			<div class="column2">
				职务：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select>
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				工作电话：
				<input type="text">
			</div>
			<div class="column2">
				E-mail：&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text">
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				身份权限：
				<select>
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</div>
		</div>
		<div class="line111" style="height: 240px">成员简介：
			<textarea></textarea>
		</div>
		<div class="line111">
			<button class="toleft">保存</button>
			<button class="toright">重置密码</button>
		</div>
		<p>&nbsp;</p>
	</div>
</div>
</body>
</html>
