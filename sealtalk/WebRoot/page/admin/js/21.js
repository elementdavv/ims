var curpage = '';
var currole = 0;
$(document).ready(function(){

	showpage('210');
	
	callajax('priv!getRoleList', '', cb_21_fresh);
	
	$('body').on('click', '#list21 li', function() {
		$(this).parent().find('li').removeClass('active');
		$(this).addClass('active');
		if (curpage == '212') {
			showpage('210');
		}
		currole = this.id.substr(1);
		load210();
		load211();
		load212();
	});
	$('body').on('click', '.privgroup', function() {
		$(this).parent().parent().find('input').prop('checked', $(this).prop('checked'));
	});
});
function load210() {
	callajax('priv!getMemberByRole', {roleid: currole}, cb_210_fresh)
}
function cb_210_fresh(data) {
	$('#list210').empty();
	var i = data.length;
	while(i--) {
		$('#list210').append('<tr></tr>');
		$('#list210 tr:last-child')
			.append('<td>' + data[i].membername + '</td>')
			.append('<td>' + data[i].branchname + '</td>')
			.append('<td>' + data[i].positionname + '</td>')
			.append('<td><button onclick="del210(' + data[i].memberroleid + ')">删除</button></td>');
	}
}
function load211() {
	callajax('priv!getPrivByRole', {roleid: currole}, cb_211_fresh)
}
function cb_211_fresh(data) {
	$('#list211').empty();
	var i = data.length;
	while (i--) {
		if (data[i].parentid == 0) {
			$('#list211').append('<div class="line211">' + data[i].privname + '</div>');
			var j = data.length;
			var x = 0;
			while (j--) {
				if (data[j].parentid == data[i].privid) {
					if (x++ % 2 == 0)
						$('#list211').append('<div class="line211a"></div>');
					else
						$('#list211').append('<div class="line211b"></div>');
					var a = $('#list211').children().last();
					$(a).append('<div class="line2111">' + data[j].privname + '</div>');
					$(a).append('<div class="line2112"></div>');
					var b = $(a).children().last();
					var k = data.length;
					while (k--) {
						if (data[k].parentid == data[j].privid) {
							if (data[k].roleid == currole) {
								$(b).append('<div class="priv toleft">' + data[k].privname + '</div>');
							}
						}
					}
				}
			}
		}
	}
}
function load212() {
	callajax('priv!getPrivByRole', {roleid: currole}, cb_212_fresh)
}
function cb_212_fresh(data) {
	$('#list212').empty();
	var i = data.length;
	while (i--) {
		if (data[i].parentid == 0) {
			$('#list212').append('<div class="line211">' + data[i].privname + '</div>');
			var j = data.length;
			var x = 0;
			while (j--) {
				if (data[j].parentid == data[i].privid) {
					if (x++ % 2 == 0)
						$('#list212').append('<div class="line211a"></div>');
					else
						$('#list212').append('<div class="line211b"></div>');
					var a = $('#list212').children().last();
					$(a).append('<div class="line2111"><input type="checkbox" class="privgroup" id="p' + data[j].privid + '" /> ' + data[j].privname + '</div>');
					$(a).append('<div class="line2112"></div>');
					var b = $(a).children().last();
					var k = data.length;
					while (k--) {
						if (data[k].parentid == data[j].privid) {
							if (data[k].roleid == currole) {
								$(b).append('<div class="priv toleft"><input type="checkbox" id="p' + data[k].privid + '" checked /> ' + data[k].privname + '</div>');
							}
							else {
								$(b).append('<div class="priv toleft"><input type="checkbox" id="p' + data[k].privid + '" /> ' + data[k].privname + '</div>');
							}
						}
					}
				}
			}
		}
	}
}
function cb_21_fresh(data) {
	$('#list21').empty();
	var i = data.length;
	while (i--) {
		if (currole == 0) currole = data[i].id;
		$('#list21').append('<li id="r' + data[i].id + '">' + data[i].name + '</li>');
	}
	$('#list21').find('li:first-child').addClass('active');
	load210();
	load211();
	load212();
}
function del210(id) {
	bootbox.confirm({
		title: '提示', 
		message:'确定删除么 ？',
		callback: function(result) {
			if (result) {
				callajax('priv!delMemberRole', {id: id}, cb_210_del)
			}
		}
	});
}
function cb_210_del(data) {
	load210();
}
function save212() {
	var inps = $('#212').find('input');
	var i = inps.length;
	var data = '';
	while(i--) {
		if (inps[i].checked == true) {
			if (data != '') data += ',';
			data += inps[i].id.substr(1);
		}
	}
	callajax('priv!saveRole', {roleid: currole, privs: data}, cb_212_save);
}
function cb_212_save(data) {
	load211();
	load212();
	showpage('211');
}
function delrole() {
	if (currole == 1) {
		bootbox.alert({title:'提示', message:'组织管理员身份不能删除.'});
		return;
	}
	bootbox.confirm({
		title: '提示', 
		message:'确定删除么 ？',
		callback: function(result) {
			if (result) {
				callajax('priv!delRole', {roleid: currole}, cb_21_del);
			}
		}
	});
}
function cb_21_del(data) {
	var $a = $('#r' + currole);
	var $b = $a.prev();
	$b.addClass('active');
	$a.remove();
	currole = $b[0].id.substr(1);
	load210();
	load211();
	load212();
}
function showpage(cp) {
	curpage = cp;
	$('#210').hide();
	$('#211').hide();
	$('#212').hide();
	$('#' + cp).show();
}