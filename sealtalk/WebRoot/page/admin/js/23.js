$(document).ready(function() {
	
	callajax('pos!getList', '', cb_23);
	
	$('#save23').click(function() {
		var name = $('#posname').val();
		if (name == '') return;
		
		callajax('pos!save', {name: name}, cb_23_save)
	});
});
function cb_23(data) {
	$('#positionlist').empty();
	var i = data.length;
	while (i--) {
		$('#positionlist').append('<div class="pos" id="p' + data[i].id + '" >' + data[i].name + '&nbsp;&nbsp;<a href="#" onclick="del(' + data[i].id + ')">x</a></div>');
	}
}
function del(id) {
	$('#p' + id).remove();
	callajax('pos!del', {id: id}, cb_23_del);
}
function cb_23_del(data) {
}
function cb_23_save(data) {
	if (data.id == 0) {
		bootbox.alert({
			'title': '提示',
			'message': '职务已存在.'
		});
	}
	$('#positionlist').append('<div class="pos" id="p' + data.id + '" >' + data.name + '&nbsp;&nbsp;<a href="#" onclick="del(' + data.id + ')">x</a></div>');
}