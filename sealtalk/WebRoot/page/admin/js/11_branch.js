$(document).ready(function(){
	$('.addmember').click(function(){
		$('#member').modal({
			backdrop: false,
			remote: '11_member.jsp'
		});
	});
});