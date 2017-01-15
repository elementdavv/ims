var branch112position = 0;
$(document).ready(function(){

	//下拉相关
	treeplace($('#tree112position'), $('#tree112positionwrap'));
	
	$('#save112position').click(function(){
		var data = {branchmemberid: branchmemberid,
				memberid: curmember, 
				branchid: branch112position, 
				positionid: $('#select112position').val()};
		callajax('branch!savePosition', data, cb_112_position_save);
	});

	callajax("branch!getOrganOnlyTree", "", cb_112_position_tree);
	callajax("branch!getPosition", "", cb_112_position_select);
})
function cb_112_position_tree(data) {

	$.fn.zTree.init($('#tree112position'), setting112_position, data);
	$.fn.zTree.getZTreeObj('tree112position').expandAll(true);
}
function cb_112_position_select(data) {
	
	$('#select112position').empty();
	var i = data.length;
	while(i--) {
		$('#select112position').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
var setting112_position = {
	view: {
		showLine: false,
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: null
		}
	},
	async: {
		enable: false
	},
	callback: {
		onClick: function(event, treeId, treeNode, clickFlag) {
			branch112position = treeNode.id;
		}
	}
}
function cb_112_position_save(data) {

	if (data.branchmemberid == '0') {
		alert('职位已存在.');
	}
	else {
		$('#position').modal('hide');
		update_112_position();
	}
}