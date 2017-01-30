$(document).ready(function() {
	
	$('#newpassword').keyup(function(e) {
		
		var np = $('#newpassword').val();
		var grade = passwordGrade(np);
		clear();
		if (grade < 11) {
			$('#grade1').addClass('weak');
			$('#grade2').addClass('grade0');
			$('#grade3').addClass('grade0');
		}
		else if (grade < 21) {
			$('#grade1').addClass('good');
			$('#grade2').addClass('good');
			$('#grade3').addClass('grade0');
		}
		else if (grade < 41) {
			$('#grade1').addClass('strong');
			$('#grade2').addClass('strong');
			$('#grade3').addClass('strong');
		}
		else {
			$('#grade1').addClass('grade0');
			$('#grade2').addClass('grade0');
			$('#grade3').addClass('grade0');
		}
	});
	$('#save111reset').click(function() {
		callajax('branch!reset', {memberid: curmember, newpassword: $('#newpassword').val(), cb_111_reset});
	});
})
function cb_111_reset(date) {
	$('#reset').modal('hide');
	bootbox.alert({'title':'提示', 'message':'密码重置成功.'});
}
function clear() {
	$('#grade1').removeClass('grade0');
	$('#grade1').removeClass('weak');
	$('#grade1').removeClass('good');
	$('#grade1').removeClass('strong');
	$('#grade2').removeClass('grade0');
	$('#grade2').removeClass('weak');
	$('#grade2').removeClass('good');
	$('#grade2').removeClass('strong');
	$('#grade3').removeClass('grade0');
	$('#grade3').removeClass('weak');
	$('#grade3').removeClass('good');
	$('#grade3').removeClass('strong');
}