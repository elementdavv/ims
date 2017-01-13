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
					<button>+添加权限</button>
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
						<div class="infotabi tabactive">人员管理</div>
						<div class="infotabi"><a href="211.jsp">所有权限</a></div>
					</div>
					<div class="infotabr">
						<button id="editmember">新增/修改人员</button>
					</div>
				</div>
				<div style="width:100%;padding-left:30px;">
					<table class="t112">
						<thead>
							<tr>
								<th width="30%">名称</th>
								<th width="30%">部门</th>
								<th width="30%">职位</th>
								<th width="10%"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>A君</td>
								<td>UI部</td>
								<td>UI设计师</td>
								<td><button>删除</button></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<p>&nbsp;</p>
	</div>
</div>
</body>
</html>
