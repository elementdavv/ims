<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %> 
<div class="infopanel11" id="112">
	<div class="info">
		<div class="infotitle">
			<div class="title" id='membertitle2'>成员信息</div>
			<div class="infotab">
				<div class="infotabi"><a href="#" onclick="showpage('111')">基本信息</a></div>
				<div class="infotabi tabactive">所属分支</div>
			</div>
		</div>
		<div class="line112">
			<button class="toright addposition">+</button>
		</div>
		<div style="width:100%;padding-left:30px;">
			<table class="t112">
				<thead>
					<tr>
						<th width="20%"></th>
						<th width="40%">分支</th>
						<th width="20%">职务</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody id='branchmember'>
					<tr>
						<td>主要职能</td>
						<td>上海天坊信息科技公司</td>
						<td>程序员</td>
						<td><button>编辑</button><button>删除</button></td>
					</tr>
					<tr>
						<td><button>设为主要</button></td>
						<td>上海一特科技网络有限公司</td>
						<td>高级程序员</td>
						<td><button>编辑</button><button>删除</button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
