<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<%@ include file="0.jsp" %>
<script src="js/21.js"></script>
</head>
<body>
<div id='container'>

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
					<button onclick='delrole()'>删除</button>
				</div>
				<div class="line21" style="width: 70%">
					<ul id='list21'>
						<li>组织管理员</li>
						<li class="active">普通成员</li>
					</ul>
				</div>
			</div>
<jsp:include page="210.jsp" flush="true" />
<jsp:include page="211.jsp" flush="true" />
<jsp:include page="212.jsp" flush="true" />
		</div>
		<p>&nbsp;</p>
	</div>
</div>

<div id="role" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog" style='width: 420px'>
		<div class="modal-content">
		</div>	
	</div>
</div>

<div id="member" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog" style='width: 420px'>
		<div class="modal-content">
		</div>
	</div>
</div>

</div>
</body>
</html>