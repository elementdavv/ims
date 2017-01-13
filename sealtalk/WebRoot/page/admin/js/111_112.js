var branchmembertemplate=[
	"<tr>" +
		"<td><button onclick='makemaster(branchmemberid)'>设为主要</button></td>" +
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

function cb_111_role(data) {

	$('#memberrole').empty();
	var i = data.length;
	while(i--) {
		$('#memberrole').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function cb_111_sex(data) {

	$('#membersex').empty();
	var i = data.length;
	while(i--) {
		$('#membersex').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function cb_111_position(data) {

	$('#memberposition').empty();
	var i = data.length;
	while(i--) {
		$('#memberposition').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}

function cb_111_112(data) {
	loadmember(data);
}
function loadmember(data) {
	
	$('#memberaccount').val(data.account);
	$('#memberfullname').val(data.fullname);
	$('#membermobile').val(data.mobile);
	$('#membersex').val(data.sex);
	$('#memberbirthday').val(showdate(data.birthday));
	$('#memberposition').val(data.positionId);
	$('#membertelephone').val(data.telephone);
	$('#memberemail').val(data.email);
	$('#memberrole').val(data.roleId);
	$('#memberintro').val(data.intro);

	$('#membertitle').empty();
	if (data.roleId == '1') {
		$('#membertitle').append('超级管理员信息');
		$('#memberrole').attr('disabled', 'disabled');
	}
	else {
		$('#membertitle').append('员工信息');
		$('#memberrole').removeAttr('disabled');
		$("#memberrole option[value='1']").attr('disabled', 'disabled');
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