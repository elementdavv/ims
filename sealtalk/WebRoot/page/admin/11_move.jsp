<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<script src="js/11_move.js" language="javascript"></script>
</head>
<body>
<div class="modal-body">
	<div class='h5px'></div>
	<div>
		<div class='dialogtitle'>
			<div id='title11move' class='toleft'>移动到</div>
			<div class='toright'>×</div>
		</div>
	</div>
	<div class='h40px'></div>
	<div>
		<div class='dialogtitle'>
			<input type='text' id='search11move' class='w100pc'>
		</div>
	</div>
	<div>
		<div class='dialogtitle'>
			<div class='treeopenwrap h400px' id='tree11movewrap' style='margin-top: -1px'>
				<div id='tree11move' class='ztree'></div>
			</div>
		</div>
	</div>
	<div class='h30px'></div>
	<div>
		<div class='dialogtitle'>
			<button onclick="$('#move').modal('hide');" class='leftspace15 toright'>取消</button>
			<button id='save11move' class='leftspace15 toright'>确定</button>
		</div>
	</div>	
</div>
</body>
</html>