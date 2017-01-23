<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<script src="js/11_member.js" language="javascript"></script>
</head>
<body>
<div class="modal-body">
	<div class='h5px'></div>
	<div>
		<div class='dialogtitle'>
			<div class='toleft'>添加人员</div>
			<div class='toright'>×</div>
		</div>
	</div>
	<div class='h40px'></div>
	<div>
		<div class='dialogtitle'>成员帐号：	
			<input type='text' id='11memberaccount' name='11memberaccount' class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>手机号：	
			<input type='text' id='11membermobile' name='11membermobile' class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>姓名：
			<input type='text' id='11membername' name='11membername' class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle' id='cyss'>成员所属：
			<img src="images/下拉.png" id='xiala11' class='treeedit1 toright rightspace60'>
			<input type='text' id="11memberbranch" name="11memberbranch" class='treeedit1 w200px toright' readonly>
			<input type="hidden" id="11memberbranchid" name="11memberbranchid">
			<div class='treewrap1' id='tree11memberwrap'>
				<div id='tree11member' class='ztree'></div>
			</div>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>性别：
			<select id="11membersex" name="11membersex" class='w220px toright rightspace60'>
				<option>性别</option>
			</select>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>职务：
			<select id="11memberpositionid" name="11memberpositionid"  class='w220px toright rightspace60'>
				<option>性别</option>
			</select>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>E-mail：
			<input type='text' id="11memberemail" name="11memberemail"  class='w220px toright rightspace60'>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>身份权限：
			<select id="11memberroleid" name="11memberroleid" class='w220px toright rightspace60'>
				<option>性别</option>
			</select>
		</div>
	</div>
	<div class='linespace'></div>
	<div>
		<div class='dialogtitle'>成员简介：
			<textarea id="11memberintro" name="11memberintro" class='toright w280px h100px'></textarea>
		</div>
	</div>
	<div class='h110px'></div>
	<div>
		<div class='dialogtitle'>
			<input type="checkbox" class='toleft' id='11membercontinue'>继续添加下一个成员
			<button class='toright leftspace15' onclick="$('#member').modal('hide');">取消</button>
			<button class='toright leftspace15' id='save11member'>确定</button>
		</div>
	</div>	
	<div class='h10px'></div>
</div>
</body>
</html>