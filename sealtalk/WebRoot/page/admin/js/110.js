$(document).ready(function(){
	
	//下拉相关
	treeplace($('#branchmanager'), $('#tree110wrap'));
	
	$('#branchsave').click(function(){
		var data = formtojson($('#branchform'));
		callajax('branch!saveBranch', data, cb_110_1);
	});
})
function cb_110(data) {
	loadbranch(data);
}
function loadbranch(data) {
	$('#branchid').val(curbranch);
	$('#branchname').val(data.name);
	$('#branchmanagerid').val(data.managerId);
	$('#branchmanager').val(data.manager);
	$('#branchaddress').val(data.address);
	$('#branchtelephone').val(data.telephone);
	$('#branchwebsite').val(data.website);
	$('#branchfax').val(data.fax);
	$('#branchintro').val(data.intro);
}
function cb_110_1(data) {
	alert('保存成功.');
}