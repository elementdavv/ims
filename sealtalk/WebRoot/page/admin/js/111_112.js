var branchmembertemplate=[
	"<tr>" +
		"<td><button onclick='setmaster(branchmemberid)'>设为主要</button></td>" +
		"<td>branch</td>" +
		"<td>position</td>" +
		"<td>" +
		"<button onclick='editbranchmember(branchmemberid)'>编辑</button>" +
		"<button onclick='delbranchmember(branchmemberid)'>删除</button>" +
		"</td></tr>",
	"<tr>" +
		"<td>主要职能</td>" +
		"<td>branch</td>" +
		"<td>position</td>" +
		"<td>" +
		"<button onclick='editbranchmember(branchmemberid)'>编辑</button>" +
		"<button onclick='delbranchmember(branchmemberid)'>删除</button>" +
		"</td></tr>"];
var branchmemberid;
$(document).ready(function(){
	
	$('#position').on('shown.bs.modal', function(e) {
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

function cb_111_role(data) {

	$('#memberroleid').empty();
	var i = data.length;
	while(i--) {
		$('#memberroleid').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
	$("#memberroleid option[value='1']").attr('disabled', 'disabled');
}
function cb_111_sex(data) {

	$('#membersex').empty();
	var i = data.length;
	while(i--) {
		$('#membersex').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function cb_111_position(data) {

	$('#memberpositionid').empty();
	var i = data.length;
	while(i--) {
		$('#memberpositionid').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}

function cb_111_112(data) {
	loadmember(data);
}
function loadmember(data) {
	
	$('#memberid').val(curmember);
	$('#memberaccount').val(data.account);
	$('#memberfullname').val(data.fullname);
	$('#membermobile').val(data.mobile);
	$('#membersex').val(data.sex);
	$('#memberbirthday').val(showdate(data.birthday));
	$('#memberpositionid').val(data.positionId);
	$('#memberbranchid').val(data.branchId);
	$('#branchmemberid').val(data.branchMemberId);
	$('#membertelephone').val(data.telephone);
	$('#memberemail').val(data.email);
	$('#memberroleid').val(data.roleId);
	$('#memberintro').val(data.intro);

	$('#membertitle').empty();
	$('#membertitle2').empty();
	if (data.roleId == '1') {
		$('#membertitle').append('超级管理员信息');
		$('#membertitle2').append('超级管理员信息');
		$('#memberrole').attr('disabled', 'disabled');
	}
	else {
		$('#membertitle').append('员工信息');
		$('#membertitle2').append('员工信息');
		$('#memberrole').removeAttr('disabled');
	}
	
	loadbranchmember(data.branchmember);
}
function loadbranchmember(data) {
	$('#branchmember').empty();
	var i = data.length;
	while(i--) {
		$('#branchmember').append(branchmembertemplate[data[i].ismaster]
				.replace(/branchmemberid/g, data[i].branchmemberid)
				.replace('branch', data[i].branchname)
				.replace('position', data[i].positionname));
	}
}
function editbranchmember(bmid) {
	branchmemberid = bmid;
	$('#position').modal({
		backdrop: false,
		remote: '112_position.jsp'
	});
}
function delbranchmember(bmid) {
	if (confirm('确定删除该职位？')) {
		callajax("branch!delBranchMember", {branchmemberid: bmid}, cb_112_position_del);
	}
}
function setmaster(bmid) {
	callajax("branch!setMaster", {branchmemberid: bmid}, cb_112_position_master);
}
function cb_112_position_fresh(data) {
	$('#select112position').val(data.positionid);
	var o = $.fn.zTree.getZTreeObj('tree112position');
	o.selectNode(o.getNodeByParam('id', data.branchid));
	branch112position = data.branchid;
}
function cb_112_position_del(data) {
	update_112_position();
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
	alert('保存成功.');
}