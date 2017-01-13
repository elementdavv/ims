<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
</head>
<body>
<div class="modal-body">
	<div>
		<div>添加人员</div>
		<div>×</div>
	</div>
	<div>
		<div>手机号：
			<input type='text'>
		</div>
	</div>
	<div>
		<div>姓名：
			<input type='text'>
		</div>
	</div>
	<div>
		<div>成员所属：
			<select>
				<option>领导</option>
			</select>
		</div>
	</div>
	<div>
		<div>性别：
			<select>
				<option>性别</option>
			</select>
		</div>
	</div>
	<div>
		<div>职务：
			<select>
				<option>性别</option>
			</select>
		</div>
	</div>
	<div>
		<div>E-mail：
			<input type='text'>
		</div>
	</div>
	<div>
		<div>身份权限：
			<select>
				<option>性别</option>
			</select>
		</div>
	</div>
	<div>
		<div>成员简介：
			<textarea></textarea>
		</div>
	</div>
	<div>
		<div><input type="checkbox">继续添加下一个成员</div>
		<div>
			<button>确定</button>
			<button onclick="$('#member').modal('hide');">取消</button>
		</div>
	</div>	
</div>
</body>
</html>