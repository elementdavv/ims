<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<!doctype html>
<html>
<head>
<%@ include file="0.jsp" %>
<script src="js/organ.js"></script>
</head>
<body>
<div class="admheader">
	<ul class="admheadermenu">
		<li class="active">基本设置</li>
		<li><a href="21.jsp">高级设置</a></li>
	</ul>
</div>
<div class="menupanel12">
	<div id="jb" class="sidebar12">
		<div class="menu"><a href="11.jsp">组织结构</a></div>
		<div class="menu menuactive">组织信息</div>
		<div class="menu"><a href="13.jsp">群组管理</a></div>
	</div>
</div>
<div class="infopanel12">
	<div class="info">
		<div class="infotitle">
			<div class="title">组织信息</div>
		</div>
		<div class="col12">
			<div class="col1">
				<div class="line12a">企业/机构名称</div>
				<div class="line12b">组织号码：
					<input type="text">
				</div>
				<div class="line12b">组织全称：
					<input type="text">
				</div>
				<div class="line12b">组织全称：
					<input type="text">
				</div>
				<div class="line12b">英文名称：
					<input type="text">
				</div>
				<div class="line12b">广告语：&nbsp;&nbsp;&nbsp;
					<input type="text">
				</div>
				<div class="line12a">联系方式</div>
				<div class="line12b">所在城市：
					<select class="sel1">
						<option value="id">上海</option>
					</select>
					<select class="sel1">
						<option value="id">上海</option>
					</select>
					<select class="sel1">
						<option value="id">上海</option>
					</select>
				</div>
				<div class="line12b">联系人：&nbsp;&nbsp;&nbsp;
					<input type="text">
				</div>
				<div class="line12b">办公地址：
					<input type="text" style="width:410px"><span> 填写后将显示在成员名片中</span>
				</div>
				<div class="line12b">电话：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text">
				</div>
				<div class="line12b">传真：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text">
				</div>
				<div class="line12b">E-mail：&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text">
				</div>
				<div class="line12b">邮政编码：
					<input type="text">
				</div>
				<div class="line12b">网站：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="text">
				</div>
				<div class="line12a">其他信息</div>
				<div class="line12b">组织性质：
					<select class="sel2">
						<option value="id">上海</option>
					</select>
				</div>
				<div class="line12b">主营行业：
					<select class="sel2">
						<option value="id">上海</option>
					</select>
					<select class="sel2">
						<option value="id">上海</option>
					</select>
				</div>
				<div class="line12b">注册资本：
					<input type="text"> 万
				</div>
				<div class="line12b">成员人数：
					<select class="sel2">
						<option value="id">上海</option>
					</select>
				</div>
				<div class="line12b">电脑台数：
					<select class="sel2">
						<option value="id">上海</option>
					</select>
				</div>
				<div class="line12b" style="height: 240px;">组织简介：
					<textarea></textarea>
				</div>
				<div class="line12b">
					<button class="save">保存</button>
				</div>
				
			</div>
			<div class="col2">
				<div class="line12c">信息完整度</div>
				<div style="padding: 10px 0 0 90px;">
					<img width=150px height=150px>
				</div>
				<div style="padding: 10px 0 0 135px"><a href="#" >编辑</a> | <a href="#" >删除</a></div>
			</div>
		</div>
		<p>&nbsp;</p>
	</div>
</div>

</body>
</html>
