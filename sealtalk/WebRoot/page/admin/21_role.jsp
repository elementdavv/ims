<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<script src="js/21_role.js" language="javascript"></script>
</head>
<body>
<div class="modal-body">
	<div class='h5px'></div>
	<div>
		<div class='dialogtitle'>
			<div id='title11move' class='toleft'>添加身份</div>
			<div class='toright'>×</div>
		</div>
	</div>
	<div class='h40px'></div>
	<div>
		<div class='dialogtitle'><div class='toleft'>身份名称：</div>
			<input type='text' id='21_rolename' class='toleft' style='width: 270px; margin-left: 20px' />
			<div class='toleft' style='margin-left: 117px'>身份模板：</div>
			<select id="21_roletemplate" class='toleft' style='width: 270px;margin-left: 20px'>
				<option>性别</option>
			</select>
		</div>
	</div>
	<div class='h40px'></div>
	<div>
		<div class='dialogtitle'>权限配置：
			<div id="21_list" class='toright' style='overflow-y: auto;border: 1px solid #ccc; width: 748px; height: 400px;'>
				<div class="line211">后台管理</div>
				<div class="line21_a">
					<div class="line2111"><input type="checkbox" />人事管理</div>
					<div class="line2112">
						<div class="priv toleft"><input type="checkbox" />查看</div>
						<div class="priv toleft"><input type="checkbox" />添加</div>
					</div>
				</div>
				<div class="line21_b">
					<div class="line2111"><input type="checkbox" />部门管理</div>
					<div class="line2112">
						<div class="priv toleft"><input type="checkbox" />查看</div>
						<div class="priv toleft"><input type="checkbox" />添加</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style='height: 420px;'></div>
	<div>
		<div class='dialogtitle toleft' >
			<input type="checkbox" class='toleft' id='21rolecontinue'>继续添加下一个身份
			<button class='toright leftspace15' onclick="$('#role').modal('hide');">取消</button>
			<button class='toright' style='margin-left: 528px' id='save21role'>确定</button>
		</div>
	</div>	
</div>
</body>
</html>