<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<div class="col2" id='211' style='display:none'>
	<div class="infotitle">
		<div class="infotab" style='font-size: 16px;'>
			<div class="infotabi" style='color:rgb(153,153,153);text-align:center;width:100px;cursor:pointer' onclick='showpage("210")'>人员管理</div>
			<div class="infotabi tabactive" style='color:rgb(40,192,210);text-align:center;width:100px;padding-bottom:11px'>所有权限</div>
		</div>
		<div class="infotabr" style='padding-top: 10px'>
			<button id="editmember" class='addedit' style='width:100px' onclick='if(currole==1){bootbox.alert({"title":"提示","message":"不能修改组织管理员权限."});return;}showpage("212")'>修改权限</button>
		</div>
	</div>
	<div id='list211'>
		<div class="line211">后台管理</div>
		<div class="line211a">
			<div class="line2111">人事管理</div>
			<div class="line2112">
				<div class="priv toleft">查看</div>
				<div class="priv toleft">添加</div>
			</div>
		</div>
		<div class="line211b">
			<div class="line2111">部门管理</div>
			<div class="line2112">
				<div class="priv toleft">查看</div>
				<div class="priv toleft">添加</div>
			</div>
		</div>
	</div>
</div>