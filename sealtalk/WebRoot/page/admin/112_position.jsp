<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<script src="js/112_position.js" language="javascript"></script>
</head>
<body>
<div class="modal-body">
	<div>
		<div id='title112position'>添加用户职位信息</div>
		<div class='toright'>×</div>
	</div>
	<div class='line112position'>
		<div>
			<input type='text' id='search112position'>
		</div>
	</div>
	<div class='line112_position'>
		<div>
			<div class='treeopenwrap' id='tree112positionwrap' style='height: 400px'>
				<div id='tree112position' class='ztree'></div>
			</div>
		</div>
	</div>
	<div class='line112_position'>
		<div>职务：
			<select id="select112position">
				<option>部门</option>
			</select>
		</div>
	</div>
	<div class='line112_position'>
		<div>
			<button id='save112position'>确定</button>
			<button onclick="$('#position').modal('hide');">取消</button>
		</div>
	</div>	
</div>
</body>
</html>