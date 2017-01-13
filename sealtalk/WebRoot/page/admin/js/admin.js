//ajax
var path = '';
function callajax(url, data, cb){
	$.ajax({
		type: "POST",
		url: path + url,
		data: data,
		async: false,
		success: function(msg){
			var ret = $.parseJSON(msg);
			cb(ret);
//			if (ret.status == 'ok') {
//				cb(ret.data);
//			}
//			else if (ret.status == 'bad'){
//				alert(ret.message);
//			}
//			else {
//				alert(msg);
//			}
		},
		error: function(msg){
			alert(msg.status + ', ' + msg.statusText);
		}
	});
}

function showdate(data) {
	return data.substr(0,4) + '-' + data.substr(4,2) + '-' + data.substr(6,2);
}