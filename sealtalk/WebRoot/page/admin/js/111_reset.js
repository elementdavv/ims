$(document).ready(function() {
	
	$('#newpassword').keyup(function(e) {
		
		var np = $('#newpassword').val();
		var grade = passwordGrade(np);
		if (grade < 11) {
			$('#grade1').removeClass('good');
			$('#grade2').removeClass('good');
			$('#grade3').removeClass('good');
		}
		else if (grade < 21) {
			$('#grade1').addClass('good');
			$('#grade2').removeClass('good');
			$('#grade3').removeClass('good');
		}
		else if (grade < 41) {
			$('#grade1').addClass('good');
			$('#grade2').addClass('good');
			$('#grade3').removeClass('good');
		}
		else {
			$('#grade1').addClass('good');
			$('#grade2').addClass('good');
			$('#grade3').addClass('good');
		}
	});
	$('#save111reset').click(function() {
		callajax('branch!reset', {memberid: curmember, newpassword: $('#newpassword').val(), cb_111_reset});
	});
})
function cb_111_reset(date) {
	$('#reset').modal('hide');
	alert('密码重置成功.');
}