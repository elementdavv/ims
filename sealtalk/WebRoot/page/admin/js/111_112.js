var branchmembertemplate=[
	"<tr>" +
		"<td></td>" +
		"<td>branch</td>" +
		"<td>position</td>" +
		"<td>" +
		"<img src='images/编辑.png' style='margin-right: 15px;cursor:pointer' onclick='editbranchmember(branchmemberid)' />" +
		"<img src='images/删除.png' style='cursor:pointer' onclick='delbranchmember(branchmemberid)' />" +
		"</td></tr>",
	"<tr>" +
		"<td><input class='mainpos' value='主要职能' readonly /></td>" +
		"<td>branch</td>" +
		"<td>position</td>" +
		"<td>" +
		"<img src='images/编辑.png' style='margin-right: 15px;cursor:pointer' onclick='editbranchmember(branchmemberid)' />" +
		"<img src='images/删除.png' style='cursor:pointer' onclick='delbranchmember(branchmemberid)' />" +
		"</td></tr>"];
var branchmemberid;
var branch112position = 0;
$(document).ready(function(){
	
	$('#position').on('shown.bs.modal', function(e) {
		callajax("branch!getOrganOnlyTree", "", cb_112_position_tree);
		callajax("branch!getPosition", "", cb_112_position_select);
		if (branchmemberid > 0) {
			$('#title112position').text('编辑用户职位信息');
			callajax("branch!getBranchMemberById", {branchmemberid: branchmemberid}, cb_112_position_fresh);
		}
		else {
			$('#title112position').text('添加用户职位信息');
		}
	});

	$('#membersave').click(function(){
		var data = formtojson($('#memberform'));
		callajax('branch!saveMember', data, cb_111_1);
	});
	$('.addposition').click(function(){
		branchmemberid = 0;
		$('#position').modal({
			backdrop: false,
			remote: '112_position.jsp'
		});
	});
	$('#reset111').click(function(){
		$('#reset').modal({
			backdrop: false,
			remote: '111_reset.jsp'
		});
	});
})

function editbranchmember(bmid) {
	branchmemberid = bmid;
	$('#position').modal({
		backdrop: false,
		remote: '112_position.jsp'
	});
}
function delbranchmember(bmid) {
	bootbox.confirm({
		title:'提示',
		message:'确定删除该职位 ?',
		callback: function(result) {
			if (result == true) {
				callajax("branch!delBranchMember", {branchmemberid: bmid}, cb_112_position_del);
			}
		}
	});
}
function setmaster(bmid) {
	callajax("branch!setMaster", {branchmemberid: bmid}, cb_112_position_master);
}
function cb_112_position_tree(data) {

	$.fn.zTree.init($('#tree112position'), setting112_position, data);
	$.fn.zTree.getZTreeObj('tree112position').expandAll(true);
}
function cb_112_position_select(data) {
	
	$('#select112position').empty();
	var i = data.length;
	while(i--) {
		$('#select112position').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function cb_112_position_fresh(data) {
	$('#select112position').val(data.positionid);
	var o = $.fn.zTree.getZTreeObj('tree112position');
	o.selectNode(o.getNodeByParam('id', data.branchid));
	branch112position = data.branchid;
}
var setting112_position = {
	view: {
		showLine: false,
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: null
		}
	},
	async: {
		enable: false
	},
	callback: {
		onClick: function(event, treeId, treeNode, clickFlag) {
			branch112position = treeNode.id;
		}
	}
}
function cb_112_position_del(data) {
	if (data.branchmemberid == '-1') {
		
	}
	else if (data.branchmemberid > 0) {
		bootbox.alert({'title':'提示', 'message':'最后一个职位不能删除.'});
	}
	else {
		update_112_position();
	}
}
function cb_112_position_master(data) {
	update_112_position();
}
function update_112_position() {
	callajax("branch!getMemberBranchById", {'memberid': curmember}, cb_112_position_after);
}
function cb_112_position_after(data) {
	loadbranchmember(data);
}
function cb_111_1(data) {
	bootbox.alert({'title':'提示', 'message':'保存成功.'});
}