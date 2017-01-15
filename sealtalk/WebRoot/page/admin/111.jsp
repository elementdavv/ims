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
		<form id="memberform" name="memberform">
		<input type="hidden" id="memberid" name="memberid">
		<input type="hidden" id="memberbranchid" name="memberbranchid">
		<input type="hidden" id="branchmemberid" name="branchmemberid">
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
				<select id="memberpositionid" name="memberpositionid">
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
				<select id="memberroleid" name="memberroleid">
				</select>
			</div>
		</div>
		<div class="line111" style="height: 240px">成员简介：
			<textarea id="memberintro" name="memberintro"></textarea>
		</div>
		</form>
		<div class="line111">
			<button id="membersave">保存</button>
			<button class="toright" id="reset111">重置密码</button>
		</div>
		<p>&nbsp;</p>
	</div>
</div>