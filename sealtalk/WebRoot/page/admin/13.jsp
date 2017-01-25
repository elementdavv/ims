<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<jsp:include page="0.jsp" flush="true" />
<script src="js/13.js"></script>
</head>
<body>
<div id='container'>

<div class="admheader">
	<ul class="admheadermenu">
		<li class="active">基本设置</li>
		<li><a href="21.jsp">高级设置</a></li>
	</ul>
</div>
<div class="menupanel12">
	<div id="jb" class="sidebar12">
		<div class="menu"><a href="11.jsp">组织结构</a></div>
		<div class="menu"><a href="12.jsp">组织信息</a></div>
		<div class="menu menuactive">群组管理</div>
	</div>
</div>
<div class="infopanel12">
	<div class="info">
		<div class="infotitle">
			<div class="title">群组管理</div>
		</div>
		<div style="width:100%;padding-left:30px;">
			<table class="t112">
				<thead>
					<tr">
						<th width="20%">群号</th>
						<th width="20%">群名称</th>
						<th width="15%">创建者</th>
						<th width="20%">创建日期</th>
						<th width="25%">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>4361784</td>
						<td>天坊test</td>
						<td>张宝宝</td>
						<td>2016-10-19</td>
						<td>
							<button>修改创建者</button>
							<button>解散群</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
				
		<p>&nbsp;</p>
	</div>
</div>

</div>
</body>
</html>
