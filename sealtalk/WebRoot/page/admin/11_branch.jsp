<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<script src="js/11_branch.js"></script>
</head>
<body>
<div class="modal-body">
	<div>
		<div>添加部门</div>
		<div>×</div>
	</div>
	<div>
		<div>上级部门：
			<select>
				<option>部门</option>
			</select>
		</div>
	</div>
	<div>
		<div>部门名称：
			<input type='text'>
		</div>
	</div>
	<div>
		<div>部门领导：
			<select>
				<option>领导</option>
			</select>
			<a href='#' class='addmember'>+添加人员</a>
		</div>
	</div>
	<div>
		<div>部门地址：
			<input type='text'>
		</div>
	</div>
	<div>
		<div>部门电话：
			<input type='text'>
		</div>
	</div>
	<div>
		<div>部门网址：
			<input type='text'>
		</div>
	</div>
	<div>
		<div>部门简介：
			<textarea></textarea>
		</div>
	</div>
	<div>
		<div><input type="checkbox">继续添加下一个分支</div>
		<div>
			<button>确定</button>
			<button onclick="$('#branch').modal('hide');">取消</button>
		</div>
	</div>	
</div>
</body>
</html>