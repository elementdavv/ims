var tid;
var rep = 0;
var reps = 40;
$(document).ready(function() {
	
	$('body').on('dblclick', '.errimp', function() {
		$(this).removeClass('errimp');
		$(this).prop('title', '');
		var text = $(this).text();
		$(this).empty();
		$(this).append('<input class="editimp" value="" />');
		$(this).find('input').focus().val(text);
		
		$('.editimp').blur(function() {
			leave(this);
		});
		$('.editimp').keyup(function(e) {
			if (e.keyCode == 13) {
				leave(this);
			}
		});
	});
	$('.result').click(function() {
		var img = $(this).find('img')[0];
		if ($(img).prop('src').indexOf('open') > 0) {
			$(img).prop('src','images/close.png');
			$(this).parent().next().hide();
		}
		else {
			$('#content').find('.result').find('img').prop('src', 'images/close.png');
			$('#content').find('.resultlist').hide();
			$(img).prop('src', 'images/open.png');
			$(this).parent().next().show();
		}
	});
	$('#impfile').on('change',function(){
		if (this.files.length > 0) {
			var t = this.files[0].type;
			if ( t == 'application/vnd.ms-excel' 
				|| t == 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {

				$('#impform').prop('action', path + 'admimp').submit();
				rep = 0;
				tid = setInterval('onimp()', 250);
			}
			else {
				bootbox.alert({
					'title': '提示',
					'message': '请选择XLS、XLSX格式文件.',
				});
			}
		}
	});
});
function showlist(result) {
	$('#content').find('.result').find('img').prop('src', 'images/close.png');
	$('#content').find('.resultlist').hide();
	
	var tb = 'impl' + result;
	var resultlist = $('#' + tb).parent().parent().parent();
	$(resultlist).prev().find('img').prop('src', 'images/open.png');
	$(resultlist).show();
}
function leave(a) {
	var text = $(a).val();
	var td = $(a).parent();
	$(td).empty();
	$(td).append(text);
	onedit($(td).parent());
}
function onedit(tr) {
	
}
function onimp() {
	if (rep++ < reps) {
		var d = $(window.frames["imptarget"].document);
		if (d.children(0)[0].innerText != '') {
			$('#imp1').hide();
			$('#imp2').show();
			clearInterval(tid);
		}
	}
	else {
		clearInterval(tid);
	}
}
function closeimp() {
	$('#imp').modal('hide');
	$('#imp1').show();
	$('#imp2').hide();
	$('#imp3').hide();
}