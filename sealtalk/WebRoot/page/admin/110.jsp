<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<script src="js/110.js"></script>
<div class="infopanel11" id="110">
	<div class="info">
		<div class="infotitle">
			<div class="title">部门信息</div>
		</div>
		<form id="branchform">
		<div class="line11">部门名称：
			<input type="text" id="branchname" name="branchname" >
		</div>
		<div class="line11">部门领导：
			<input type="text" id="branchmanager" name="branchmanager">
			<input type="hidden" id="branchmanagerid" name="branchmanagerid">
			<button id="branchaddmember">+添加人员</button>
		</div>
		<div class="line11">部门地址：
			<input type="text" id="branchaddress" name="branchaddress">
		</div>
		<div class="line11">部门电话：
			<input type="text" id="branchtelephone" name="branchtelephone">
		</div>
		<div class="line11">部门网址：
			<input type="text" id="branchwebsite" name="branchwebsite">
		</div>
		<div class="line11">部门传真：
			<input type="text" id="branchfax" name="branchfax">
		</div>
		<div class="line11" style="height: 240px">部门简介：
			<textarea id="intro" name="branchintro"></textarea>
		</div>
		<div class="line11">
			<button id="branchsave">保存</button>
		</div>
		</form>
		<p>&nbsp;</p>
	</div>
</div>