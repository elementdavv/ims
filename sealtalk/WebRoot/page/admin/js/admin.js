$(document).ready(function(){
	
	// 下拉相关
	$('#container').click(function(){
		if ($('.treewrap').is(':visible')) {
			$('.treewrap').hide();
		}
		return true;
	});
	$('.treeedit').click(function(){
		var tw = $(this).parent().children('.treewrap');
		if ($(tw).is(':visible')) {
			$(tw).hide();
		}
		else {
			$(tw).show();
		}
		return false;
	});
	$('.treewrap').click(function(){
		return false;
	});
})
// 下拉相关
function treeplace(oedit, otree) {
	$(otree).css({
		'left': $(oedit).offset().left, 
		'top': $(oedit).offset().top + 20, 
		'width': $(oedit).width() + 4
	});
}

//ajax
var path = '';
function callajax(url, data, cb){
	$.ajax({
		type: "POST",
		url: path + url,
		data: data,
		datatype: 'json',
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
function formtojson(form) {
	
	var astring = '{';
	var fa = $(form).serializeArray();
	var i = fa.length;
	while (i--) {
		var a = fa[i];
		if (astring != '{') astring += ',';
		astring += '"' + a.name + '":"' + a.value + '"';
	}
	astring += '}';
	return $.parseJSON(astring);
}

function passwordGrade(pwd) {
    var score = 0;
    var regexArr = ['[0-9]', '[a-z]', '[A-Z]', '[\\W_]'];
    var repeatCount = 0;
    var prevChar = '';

    //check length
    var len = pwd.length;
    score += len > 18 ? 18 : len;

    //check type
    for (var i = 0, num = regexArr.length; i < num; i++) { if (eval('/' + regexArr[i] + '/').test(pwd)) score += 4; }

    //bonus point
    for (var i = 0, num = regexArr.length; i < num; i++) {
        if (pwd.match(eval('/' + regexArr[i] + '/g')) && pwd.match(eval('/' + regexArr[i] + '/g')).length >= 2) score += 2;
        if (pwd.match(eval('/' + regexArr[i] + '/g')) && pwd.match(eval('/' + regexArr[i] + '/g')).length >= 5) score += 2;
    }

    //deduction
    for (var i = 0, num = pwd.length; i < num; i++) {
        if (pwd.charAt(i) == prevChar) repeatCount++;
        else prevChar = pwd.charAt(i);
    }
    score -= repeatCount * 1;

    return score;

}