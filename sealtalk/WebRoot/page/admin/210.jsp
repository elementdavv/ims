<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<div class="col2" id='210'>
	<div class="infotitle">
		<div class="infotab">
			<div class="infotabi tabactive">人员管理</div>
			<div class="infotabi"><a href='#' onclick='showpage("211")'>所有权限</a></div>
		</div>
		<div class="infotabr">
			<button id="editmember" style='width:100px'>新增/修改人员</button>
		</div>
	</div>
	<div style="width:100%;padding-left:30px;">
		<table class="t112">
			<thead>
				<tr>
					<th width="30%">名称</th>
					<th width="30%">部门</th>
					<th width="30%">职位</th>
					<th width="10%"></th>
				</tr>
			</thead>
			<tbody id='list210'>
				<tr>
					<td>A君</td>
					<td>UI部</td>
					<td>UI设计师</td>
					<td><button>删除</button></td>
				</tr>
			</tbody>
		</table>
		<div style='margin: 30px'>
			<div class='toright leftspace15' id='pagelast'>last</div>
			<div class='toright leftspace15' id='pagenext'>next</div>
			<div class='toright leftspace15' id='pagecurr'>1/10</div>
			<div class='toright leftspace15' id='pageprev'>prev</div>
			<div class='toright leftspace15' id='pagefirst'>first</div>
		</div>
	</div>
</div>