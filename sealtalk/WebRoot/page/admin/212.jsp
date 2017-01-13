<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<%@ include file="0.jsp" %>
<script src="js/21.js"></script>
</head>
<body>
<div class="admheader">
	<ul class="admheadermenu">
		<li><a href="11.jsp">基本设置</a></li>
		<li class="active">高级设置</li>
	</ul>
</div>
<div class="menupanel12">
	<div id="jb" class="sidebar12">
		<div class="menu menuactive">成员身份权限</div>
		<div class="menu"><a href="#">高级功能</a></div>
		<div class="menu"><a href="23.jsp">职务职位</a></div>
	</div>
</div>
<div class="infopanel21">
	<div class="info">
		<div class="infotitle">
			<div class="title">成员身份权限</div>
		</div>
		<div class="col21">
			<div class="col1">
				<div class="line21">
					<button>+添加身份</button>
					<button>删除</button>
				</div>
				<div class="line21" style="width: 70%">
					<ul>
						<li>组织管理员</li>
						<li class="active">普通成员</li>
					</ul>
				</div>
			</div>
			<div class="col2">
				<div class="infotitle">
					<div class="infotab">
						<div class="infotabi"><a href="21.jsp">人员管理</a></div>
						<div class="infotabi tabactive">所有权限</div>
					</div>
					<div class="infotabr">
						<a href="211.jsp">保存权限</a>
					</div>
				</div>
				<div>
					<div class="line211">后台管理</div>
					<div class="line211a">
						<div class="line2111"><input type="checkbox" />人事管理</div>
						<div class="line2112">
							<div class="priv toleft"><input type="checkbox" />查看</div>
							<div class="priv toleft"><input type="checkbox" />添加</div>
						</div>
					</div>
					<div class="line211b">
						<div class="line2111"><input type="checkbox" />部门管理</div>
						<div class="line2112">
							<div class="priv toleft"><input type="checkbox" />查看</div>
							<div class="priv toleft"><input type="checkbox" />添加</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
