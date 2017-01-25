<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<script src="js/11_branch.js" language="javascript"></script>
</head>
<body>
<div class="modal-body">
	<div class='h5px'></div>
	<div>
		<div class='dialogtitle'>
			<div class='toleft'>添加部门</div>
			<div class='toright'>×</div>
		</div>
	</div>
	<div class='h40px'></div>
	<div>
		<div class='dialogtitle'>上级部门：
			<img src="images/下拉.png" id='xiala12' class='treeedit1 toright rightspace60'>
			<input type='text' id='11branchbranch' name='11branchbranch' class='treeedit2 w200px toright' readonly>
			<input type="hidden" id="11branchbranchid" name="11branchbranchid">
			<div class='treewrap2' id='tree11branchbranchwrap'>
				<div id='tree11branchbranch' class='ztree'></div>
			</div>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>部门名称：	
			<input type='text' id='11branchname' name='11branchname' class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>部门领导：
			<div id='11branchaddmember' class='toright'>+添加人员</div>
			<img src="images/下拉.png" id='xiala13' class='treeedit1 toright'>
			<input type='text' id='11branchmanager' name='11branchmanager' class='treeedit2 w200px toright' readonly>
			<input type="hidden" id="11branchmanagerid" name="11branchmanagerid">
			<div class='treewrap2' id='tree11branchmanagerwrap'>
				<div id='tree11branchmanager' class='ztree'></div>
			</div>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>部门地址：	
			<input type='text' id='11branchaddress' name='11branchaddress' class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>部门电话：
			<input type='text' id="11branchtelephone" name="11branchtelephone"  class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>部门网址：
			<input type='text' id="11branchwebsite" name="11branchwebsite"  class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>部门简介：
			<textarea id="11branchintro" name="11branchintro" class='toright w280px h100px'></textarea>
		</div>
	</div>
	<div class='h110px'></div>
	<div>
		<div class='dialogtitle'>
			<input type="checkbox" class='toleft' id='11branchcontinue'>继续添加下一个部门
			<button class='toright leftspace15' onclick="$('#branch').modal('hide');">取消</button>
			<button class='toright leftspace15' id='save11branch'>确定</button>
		</div>
	</div>	
	<div class='h10px'></div>
</div>
</body>
</html>