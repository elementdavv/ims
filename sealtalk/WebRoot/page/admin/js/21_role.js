$(document).ready(function() {
	$('#21_roletemplate').change(function() {
		callajax('priv!getPrivByRole', {roleid: $(this).val().substr(2)}, cb_21_role_fresh)
	});
	$('#save21role').click(function() {
		var rolename = $('#21_rolename').val();
		var inps = $('#21_list').find('input');
		var i = inps.length;
		var data = '';
		while(i--) {
			if (inps[i].checked == true) {
				if (data != '') data += ',';
				data += inps[i].id.substr(2);
			}
		}
		callajax('priv!saveRole', {rolename: rolename, privs: data}, cb_21_role_save);
	});
});
function cb_21_role_fresh(data) {
	$('#21_list').empty();
	var i = data.length;
	while (i--) {
		if (data[i].parentid == 0) {
			$('#21_list').append('<div class="line211">' + data[i].privname + '</div>');
			var j = data.length;
			var x = 0;
			while (j--) {
				if (data[j].parentid == data[i].privid) {
					if (x++ % 2 == 0)
						$('#21_list').append('<div class="line21_a"></div>');
					else
						$('#21_list').append('<div class="line21_b"></div>');
					var a = $('#21_list').children().last();
					$(a).append('<div class="line2111"><input type="checkbox" class="privgroup" id="pr' + data[j].privid + '" /> ' + data[j].privname + '</div>');
					$(a).append('<div class="line2112"></div>');
					var b = $(a).children().last();
					var k = data.length;
					while (k--) {
						if (data[k].parentid == data[j].privid) {
							if (data[k].roleid > 0) {
								$(b).append('<div class="priv toleft"><input type="checkbox" id="pr' + data[k].privid + '" checked /> ' + data[k].privname + '</div>');
							}
							else {
								$(b).append('<div class="priv toleft"><input type="checkbox" id="pr' + data[k].privid + '" /> ' + data[k].privname + '</div>');
							}
						}
					}
				}
			}
		}
	}	
};
function cb_21_role_save(data) {
	if ($('#21rolecontinue').prop('checked') == false) {
		$('#role').modal('hide');
	}
	$('#21_rolename').val('');
}