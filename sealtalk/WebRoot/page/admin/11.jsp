<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<jsp:include page="0.jsp" flush="true" />
<script src="js/11.js"></script>
</head>
<body>
<div id='container'>

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
			<button class="toleft" id='zzgly'>超级管理员</button>

            <div class='btn-group dropdown toright'>
            	<a class="btn  btn-sm dropdown-toggle" data-toggle="dropdown" href="#">
            	添加
            	</a>
            	<ul id="admadd" class="dropdown-menu pull-right" style="padding: 5px; min-width: 200%">
            		<li class="admadd addbranch">添加部门</li>
            		<li class="admadd addmember">添加人员</li>
            		<li class="admadd addbatch" style="padding-bottom: 5px">批量导入</li>
            	</ul>
            </div>
			
		</div>
		<div class="organline" >
			<input type="text" placeholder="搜索人员" />
		</div>
		<div class="organline ztree" id="tree11"></div>
	</div>
</div>

<jsp:include page="110.jsp" flush="true" />
<jsp:include page="111.jsp" flush="true" />
<jsp:include page="112.jsp" flush="true" />

<div id="branch" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
		</div>	
	</div>
</div>

<div id="member" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
		</div>
	</div>
</div>

<div id="position" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog" style="width: 300px">
		<div class="modal-content">
		</div>
	</div>
</div>

<div id="move" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
<button onclick="$('#branch').modal('hide');">close</button>
			</div>
		</div>
	</div>
</div>

<div id="reset" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog" style="width: 400px;">
		<div class="modal-content" style="height: 200px;">
		</div>
	</div>
</div>

<div id="logo" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
<button onclick="$('#branch').modal('hide');">close</button>
			</div>
		</div>
	</div>
</div>

<div id="imp1" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
<button onclick="$('#branch').modal('hide');">close</button>
			</div>
		</div>
	</div>
</div>

<div id="imp2" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
<button onclick="$('#branch').modal('hide');">close</button>
			</div>
		</div>
	</div>
</div>

<div id="imp3" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="titleid" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
<button onclick="$('#branch').modal('hide');">close</button>
			</div>
		</div>
	</div>
</div>

</div>
</body>
</html>
