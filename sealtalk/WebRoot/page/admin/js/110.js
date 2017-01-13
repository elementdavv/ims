function cb_110(data) {
	loadbranch(data);
}

function loadbranch(data) {
	$('#branchname').val(data.name);
	$('#branchmanagerid').val(data.managerId);
	$('#branchmanager').val(data.manager);
	$('#branchaddress').val(data.address);
	$('#branchtelephone').val(data.telephone);
	$('#branchwebsite').val(data.website);
	$('#branchfax').val(data.fax);
	$('#branchintro').val(data.intro);
}
