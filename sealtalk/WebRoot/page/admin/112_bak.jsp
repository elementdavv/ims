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
				<div class="infotabi"><a href="111.jsp">基本信息</a></div>
				<div class="infotabi tabactive">所属分支</div>
			</div>
		</div>
		<div class="line112">
			<button class="toright" >+</button>
		</div>
		<div style="width:100%;padding-left:30px;">
			<table class="t112">
				<thead>
					<tr>
						<th width="20%"></th>
						<th width="40%">分支</th>
						<th width="20%">职务</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>主要职能</td>
						<td>上海天坊信息科技公司</td>
						<td>程序员</td>
						<td><button>编辑</button><button>删除</button></td>
					</tr>
					<tr>
						<td><button class="btnadmin">设为主要</button></td>
						<td>上海一特科技网络有限公司</td>
						<td>高级程序员</td>
						<td><button>编辑</button><button>删除</button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>
