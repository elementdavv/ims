<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<script src="js/111_reset.js" language="javascript"></script>
</head>
<body>
<div class="modal-body">
	<div>
		<div>重置成员密码</div>
		<div class='toright'>×</div>
	</div>
	<div class='line111reset'>
		<div>成员新密码：
			<input type='text' id='newpassword'>
		</div>
	</div>
	<div class='line111reset'>
		<div style="float: left">新密码强度：</div>
		<div id='grade1' class='grade good'></div>
		<div id='grade3' class='grade soso'></div>
		<div id='grade2' class='grade weak'></div>
	</div>
	<div class='line112_position'>
		<div>
			<button class="toright" id='save111reset'>确定</button>
			<button class="toright" onclick="$('#reset').modal('hide');">取消</button>
		</div>
	</div>	
</div>
</body>
</html>