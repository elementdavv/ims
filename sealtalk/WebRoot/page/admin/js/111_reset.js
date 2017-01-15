$(document).ready(function() {
	
	$('#save111reset').click(function() {
		callajax('branch!reset', {memberid: curmember, newpassword: $('#newpassword').val(), cb_111_reset});
	});
})
function cb_111_reset(date) {
	alert('密码重置成功.');
}