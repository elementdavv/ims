<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<script src="js/111_112.js"></script>
<div class="infopanel11" id="111">
	<div class="info">
		<div class="infotitle">
			<div class="title" id='membertitle'>成员信息</div>
			<div class="infotab">
				<div class="infotabi tabactive">基本信息</div>
				<div class="infotabi"><a href="#" onclick="showpage('112')">所属分支</a></div>
			</div>
		</div>
		<form id='memberform'>
		<div class="line111">
			<div class="column1">
				成员帐号：
				<input type="text" id="memberaccount" name="memberaccount">
			</div>
			<div class="column2">
				真实姓名：
				<input type="text" id="memberfullname" name="memberfullname">
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				手机：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" id="membermobile" name="membermobile">
			</div>
			<div class="column2">
				性别：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="membersex" name="membersex">
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				出生日期：
				<input type="text" id="memberbirthday" name="memberbirthday">
			</div>
			<div class="column2">
				职务：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="memberposition" name="memberposition">
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				工作电话：
				<input type="text" id="membertelephone" name="membertelephone">
			</div>
			<div class="column2">
				E-mail：&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" id="memberemail" name="memberemail">
			</div>
		</div>
		<div class="line111">
			<div class="column1">
				身份权限：
				<select id="memberrole" name="memberrole">
					<option value="1">男</option>
					<option value="2">女</option>
				</select>
			</div>
		</div>
		<div class="line111" style="height: 240px">成员简介：
			<textarea id="memberintro" name="memberintro"></textarea>
		</div>
		</form>
		<div class="line111">
			<button class="toleft">保存</button>
			<button class="toright">重置密码</button>
		</div>
		<p>&nbsp;</p>
	</div>
</div>