var curpage = '';
var curbranch = 0;
var curmember = 0;
var movnode = null;
var searchnodes11 = null;
var downid = 0;
$(document).ready(function(){
	
	var h = document.body.clientHeight > 830 ? document.body.clientHeight : '830';
	$('.sidebar11').css('height', h + 'px');
	$('#tree11').css('height', (h - 100) + 'px');
	$('#tree11').css('overflow', 'auto');
	$('.sidebar11').css('width', document.body.clientWidth * 0.13);
	
	
	showpage('110');
	$('#member').on('shown.bs.modal', function(e) {
		callajax("branch!getOrganOnlyTree", "", cb_11_member_branch);
		callajax("branch!getSex", "", cb_11_member_sex);
		callajax("branch!getPosition", "", cb_11_member_position);
		callajax("branch!getRole", "", cb_11_member_role);
	});

	$('#branch').on('shown.bs.modal', function(e) {
		callajax("branch!getOrganOnlyTree", "", cb_11_branch_branch);
		callajax("branch!getOrganTree", "", cb_11_branch_member);
	});

	$('#move').on('shown.bs.modal', function(e) {
		callajax("branch!getOrganOnlyTree", "", cb_11_move);
		$('#title11move').text('移动 ' + movnode.name.substr(55) +' 到');
		var o = $.fn.zTree.getZTreeObj('tree11move');
		o.selectNode(o.getNodeByParam('id', movnode.pid));
	});
	
	$('#zzgly').click(function(){
		if (curpage != '110' && curmember == '10001') return;
		if (curpage != '111') showpage('111');
		curmember = '10001';

		//权限
		if (has('rsglck')) {
			callajax("branch!getMemberById", {'id': curmember}, cb_111_112);
		}
		$('#shuoming').show();
	});
	$('.addbranch').click(function(){
		
		//权限
		if (has('bmgltj')) {
			$('#branch').modal({
				backdrop: false,
				remote: '11_branch.jsp'
			});
		}
		else {
			bootbox.alert({title:'提示', message:'您没有权限添加部门.', callback: function() {
				$('#container').css('width', document.body.clientWidth + 'px');	
			}});
		}
	});
	$('.addmember').click(function(){
		
		//权限
		if (has('rsgltj')) {
			$('#member').modal({
				backdrop: false,
				remote: '11_member.jsp'
			});
		}
		else {
			bootbox.alert({title:'提示', message:'您没有权限添加人员.', callback: function() {
				$('#container').css('width', document.body.clientWidth + 'px');	
			}});
		}
	});
	$('.addbatch').click(function(){
		
		//权限
		if (has('rsgltj')) {
			$('#imp').modal({
				backdrop: false,
				remote: '11_imp.jsp'
			});
		}
		else {
			bootbox.alert({title:'提示', message:'您没有权限批量导入成员.', callback: function() {
				$('#container').css('width', document.body.clientWidth + 'px');	
			}});
		}
	});
	
	$('.downmov').click(function() {
		mov(downid);
	});
	
	$('.downdel').click(function() {
		del(downid);
	});
	
	callajax("branch!getOrganTree", "", cb_11_tree);
	callajax("branch!getRole", "", cb_111_role);
	callajax("branch!getSex", "", cb_111_sex);
	callajax("branch!getPosition", "", cb_111_position);

	$('#tree11 li').hover(function(){
		var t = $.fn.zTree.getZTreeObj('tree11');
		var ns = t.getNodesByParam('tId', $(this).prop('id'), null);
		$('.movdel').remove();
		var movdel = '<div class="movdel toright">'
			+ '<div class="move toleft"><img src="images/movbutton.png"></div>'
		+ '<div class="delete toleft"><img src="images/delete-2.png"></div></div>';
		$(this).find('a').first().after(movdel);
		$('.move').click(function(){
			mov($($(this)[0]).parent().parent().prop('id'));
		});
		$('.delete').click(function(){
			del($($(this)[0]).parent().parent().prop('id'));
		});
		return false;
	},function(){
	});
	
	$('#search11').keyup(function(e) {
		if (e.keyCode == 13) {
			searchnodes11 = dosearch('search11', 'tree11', searchnodes11);
			handletree11open();
		}
	});
	$('.btnadmin').hover(function(){
		$(this).addClass('menuhover');
	},function(){
		$(this).removeClass('menuhover');
	});
});
function cb_11_member_branch(data) {
	$.fn.zTree.init($('#tree11member'), setting11_member_branch, data);
}
function cb_11_member_sex(data) {
	$('#11membersex').empty();
	var i = data.length;
	while(i--) {
		$('#11membersex').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function cb_11_member_position(data) {
	$('#11memberpositionid').empty();
	var i = data.length;
	while(i--) {
		$('#11memberpositionid').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function cb_11_member_role(data) {
	$('#11memberroleid').empty();
	var i = data.length;
	while(i--) {
		if (data[i].id != 1) {
			$('#11memberroleid').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
		}
	}
	$("#11memberroleid option[value='1']").attr('disabled', 'disabled');
}
function cb_11_branch_branch(data) {
	$.fn.zTree.init($('#tree11branchbranch'), setting11_branch_branch, data);
}
function cb_11_branch_member(data) {
	stripicon(data);
	$.fn.zTree.init($('#tree11branchmanager'), setting11_branch_member, data);
}
function cb_11_move(data) {

	$.fn.zTree.init($('#tree11move'), setting11_move, data);
	$.fn.zTree.getZTreeObj('tree11move').expandAll(true);
}
function cb_111_112(data) {
	loadmember(data);
}
function cb_11_tree(data) {

	$.fn.zTree.init($('#tree11'), setting11, data);
	$.fn.zTree.init($('#tree110'), setting110, stripicon(data));
//	$.fn.zTree.init($('#tree11branchmanager'), setting110, stripicon(data));
	var t = $.fn.zTree.getZTreeObj('tree11');
	var ns = t.getNodesByParam('id', 1, null);
	t.expandNode(ns[0], true);
	$('#tree11 a').each(function(i, a) {
		if (a.title.length > 55) {
			a.title = a.title.substr(55);
		}
	});
//	$('#tree11 li').find('a:first').after('<div class="toright" style="height:30px"><img src="images/down.png" style="width:14px;margin:10px 5px 0 0" /></div>');
}
function stripicon(data) {
	var i = data.length;
	while (i--) {
		data[i].name = data[i].name.substr(55);
	}
	return data;
}
function cb_111_role(data) {

	$('#memberroleid').empty();
	var i = data.length;
	while(i--) {
		$('#memberroleid').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
	$("#memberroleid option[value='1']").attr('disabled', 'disabled');
}
function cb_111_sex(data) {

	$('#membersex').empty();
	var i = data.length;
	while(i--) {
		$('#membersex').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function cb_111_position(data) {

	$('#memberpositionid').empty();
	var i = data.length;
	while(i--) {
		$('#memberpositionid').append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
	}
}
function loadmember(data) {
	
	$('#memberid').val(curmember);
	$('#memberaccount').val(data.account);
	$('#memberfullname').val(data.fullname);
	$('#membermobile').val(data.mobile);
	$('#memberworkno').val(data.workno);
	$('#membersex').val(data.sex);
	$('#memberbirthday').val(showdate(data.birthday));
	$('#memberpositionid').val(data.positionId);
	$('#memberbranchid').val(data.branchId);
	$('#branchmemberid').val(data.branchMemberId);
	$('#membertelephone').val(data.telephone);
	$('#memberemail').val(data.email);
	$('#memberroleid').val(data.roleId);
	$('#memberintro').val(data.intro);

	$('#membertitle').empty();
	$('#membertitle2').empty();
	if (data.roleId == '1') {
		$('#membertitle').append('超级管理员信息');
		$('#membertitle2').append('超级管理员信息');
		$('#memberroleid').attr('disabled', 'disabled');
	}
	else {
		$('#membertitle').append('员工信息');
		$('#membertitle2').append('员工信息');
		$('#memberroleid').removeAttr('disabled');
	}
	
	var w = ((document.body.clientWidth * 0.61 * 0.92 - 68 * 2) / 2 - $('#membertitle').css('width').replace('px',''));
	$('.infotab').css('padding-left',  w + 'px');

	loadbranchmember(data.branchmember);
}
function loadbranchmember(data) {
	$('#branchmember').empty();
	var i = data.length;
	while(i--) {
		$('#branchmember').append(branchmembertemplate[data[i].ismaster]
				.replace(/branchmemberid/g, data[i].branchmemberid)
				.replace('branch', data[i].branchname)
				.replace('position', data[i].positionname));
	}
	$('#branchmember tr').hover(function(){
		if ($(this).find('td input').length == 0) {
			var tdfirst = $(this).find('td:first');
			var bmid = $(tdfirst).attr('bmid');
			$(tdfirst).append("<button class='makemain'>设为主要</button>");
			$('.makemain').click(function() {
				setmaster(bmid);
			});
		}
		$(this).addClass('menuhover');
	},function(){
		$(this).removeClass('menuhover');
		$('.makemain').remove();
	});
}
var setting11 = {
	view: {
		showLine: false,
		showIcon: false,
		nameIsHTML: true,
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: null
		}
	},
	edit: {
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false,
		drag: {
			isCopy: false,
			isMove: true,
			prev: false,
			next: false,
			inner: true,
		},
	},
	callback: {
		// 不会执行
		onExpand: function(event, treeId, treeNode) {
			handletree11open();
		},
		onClick: function(event, treeId, treeNode, clickFlag) {
			if (!treeNode.open) {
				$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
				handletree11open();
			}
			else
				$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, false);

			if (treeNode.flag == 0) return;
			if (treeNode.flag == 1 && curpage == '110' && treeNode.id == curbranch) return;
			if (treeNode.flag == 2 && curpage != '110' && treeNode.id == curmember) return;

			if (treeNode.flag == 1) {
				curbranch = treeNode.id;
				
				//权限
				if (has('bmglck')) {
					if (curpage != '110') showpage('110');
					callajax("branch!getBranchById", {'id': curbranch}, cb_110);
				}
			}

			if (treeNode.flag == 2) {
				curmember = treeNode.id;

				//权限
				if (has('rsglck')) {
					if (curpage == '110') showpage('111');
					if (curmember == '10001') 
						$('#shuoming').show();
					else
						$('#shuoming').hide();
					callajax("branch!getMemberById", {'id': curmember}, cb_111_112);
				}
			}
		},
		onRightClick: function(event, treeId, treeNode) {
			if (treeNode.flag == 0) return;
			downid = treeNode.tId;
			$('#downb').click();
			$('#downbdiv').css('top', event.clientY);
			$('#downbdiv').css('left', event.clientX);
		},
		beforeDrag: function(treeId, treeNodes) {
			
			if (treeNodes[0].flag == 0)	return false;
			else if (treeNodes[0].flag == 1) {
				if (!has('bmglyd')) return false;
			}
			else if (treeNodes[0].flag == 2) {
				if (!has('rsglyd')) return false;
			}
		},
		beforeDragOpen: function(treeId, treeNode) {
			
			$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
			handletree11open();
			return false;
		},
		beforeDrop: function(treeId, treeNodes, targetNode, moveType, isCopy) {
			
			if (targetNode.flag == 2) return false;
			
			var data = {id: treeNodes[0].id, pid: treeNodes[0].pid, toid: targetNode.id};
			callajax('branch!mov', data, function(data) {
				callajax("branch!getOrganTree", "", function(data) {
					$.fn.zTree.init($('#tree110'), setting110, stripicon(data));
				});
				if (treeNodes[0].id > 10000) {
					callajax("branch!getMemberById", {'id': data.id}, cb_111_112);
				}
			});
		},
	}
};
var setting110 = {
	view: {
		showLine: false,
		showIcon: false,
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: null
		}
	},
	callback: {
		onClick: function(event, treeId, treeNode, clickFlag) {
			if (treeNode.flag == 2) {
				$('#branchmanager').val(treeNode.name);
				$('#branchmanagerid').val(treeNode.id);
				$('#tree110wrap').hide();
			}
			else {
				if (!treeNode.open)
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
				else
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, false);
			}
		}
	}
};
var setting11_member_branch = {
	view: {
		showLine: false,
		showIcon: false,
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
			if (treeNode.flag == 1) {
				$('#11memberbranch').val(treeNode.name);
				$('#11memberbranchid').val(treeNode.id);
				$('#tree11memberwrap').hide();
			}
			else {
				if (!treeNode.open)
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
				else
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, false);
			}
		}
	}
}
var setting11_branch_branch = {
	view: {
		showLine: false,
		showIcon: false,
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
			if (treeNode.flag == 0) {
				$('#11branchbranch').val(treeNode.name);
				$('#11branchbranchid').val(0);
				$('#tree11branchbranchwrap').hide();
			}
			else if (treeNode.flag == 1) {
				$('#11branchbranch').val(treeNode.name);
				$('#11branchbranchid').val(treeNode.id);
				$('#tree11branchbranchwrap').hide();
			}
			else {
				if (!treeNode.open)
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
				else
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, false);
			}
		}
	}
}
var setting11_branch_member = {
	view: {
		showLine: false,
		showIcon: false,
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pid",
			rootPId: null
		}
	},
	callback: {
		onClick: function(event, treeId, treeNode, clickFlag) {
			if (treeNode.flag == 2) {
				$('#11branchmanager').val(treeNode.name);
				$('#11branchmanagerid').val(treeNode.id);
				$('#tree11branchmanagerwrap').hide();
			}
			else {
				if (!treeNode.open)
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
				else
					$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, false);
			}
		}
	}
};
var setting11_move = {
	view: {
		showLine: false,
		showIcon: false,
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
	}
}
function handletree11open() {
	$('#tree11 a').each(function(i, a) {
		if (a.title.length > 55) {
			a.title = a.title.substr(55);
		}
	});
	$('#tree11 li').hover(function(){
		var t = $.fn.zTree.getZTreeObj('tree11');
		var ns = t.getNodesByParam('tId', $(this).prop('id'), null);
		$('.movdel').remove();
		var movdel = '<div class="movdel toright">'
			+ '<div class="move toleft"><img src="images/movbutton.png"></div>'
		+ '<div class="delete toleft"><img src="images/delete-2.png"></div></div>';
		$(this).find('a').first().after(movdel);
		$('.move').click(function(){
			mov($($(this)[0]).parent().parent().prop('id'));
		});
		$('.delete').click(function(){
			del($($(this)[0]).parent().parent().prop('id'));
		});
		return false;
	},function(){
	});
}
function mov(tId) {
	$('.movdel').remove();
	var t = $.fn.zTree.getZTreeObj('tree11');
	var ns = t.getNodesByParam('tId', tId, null);

	if (ns[0].flag == 0) {
		bootbox.alert({title:'提示', message:'不能移动组织.', callback: function() {
			$('#container').css('width', document.body.clientWidth + 'px');	
		}});
		return;
	}
	
	//权限
	else if (ns[0].flag == 1) {
		if (!has('bmglyd')) {
			bootbox.alert({title:'提示', message:'您没有权限移动部门.', callback: function() {
				$('#container').css('width', document.body.clientWidth + 'px');	
			}});
			return;
		}
	}
	else if (ns[0].flag == 2){
		if (!has('rsglyd')) {
			bootbox.alert({title:'提示', message:'您没有权限移动人员.', callback: function() {
				$('#container').css('width', document.body.clientWidth + 'px');	
			}});
			return;
		}
	}
	
	movnode = ns[0];
	
	$('#move').modal({
		backdrop: false,
		remote: '11_move.jsp'
	});
	
}
function del(tId) {
	$('.movdel').remove();
	var t = $.fn.zTree.getZTreeObj('tree11');
	var ns = t.getNodesByParam('tId', tId, null);

	if (ns[0].flag == 0) {
		bootbox.alert({title:'提示', message:'不能删除组织.', callback: function() {
			$('#container').css('width', document.body.clientWidth + 'px');	
		}});
		return;
	}
	
	if (ns[0].id == '10001') {
		bootbox.alert({title:'提示', message:'不能删除组织管理员.', callback: function() {
			$('#container').css('width', document.body.clientWidth + 'px');	
		}});
		return;
	}
	
	//权限
	else if (ns[0].flag == 1) {
		if (!has('bmglsc')) {
			bootbox.alert({title:'提示', message:'您没有权限删除部门.', callback: function() {
				$('#container').css('width', document.body.clientWidth + 'px');	
			}});
			return;
		}
	}
	else if (ns[0].flag == 2){
		if (!has('rsglsc')) {
			bootbox.alert({title:'提示', message:'您没有权限删除人员.', callback: function() {
				$('#container').css('width', document.body.clientWidth + 'px');	
			}});
			return;
		}
	}
	
	bootbox.confirm({
		title:'提示',
		message:'确定删除 ' + ns[0].name.substr(55) + ' ?',
		callback: function(result) {
			alert(result);
			if (result == false) return;
			if (hasChildBranch(ns[0].id)) {
				bootbox.dialog({
					title: '提示',
					message: '是否同时删除下属部门 ？',
					buttons: {
						'NO': function() {
							this.modal('hide');
							callajax('branch!del', {id: ns[0].id, r: 0}, cb_del_member);
						},
						'YES': function() {
							this.modal('hide');
							callajax('branch!del', {id: ns[0].id, r: 1}, cb_del_member);
						}
					},
					callback: function() {
						$('#container').css('width', document.body.clientWidth + 'px');	
					}
				});
			}
			else {
				callajax('branch!del', {id: ns[0].id, r: 0}, cb_del_member);
			}
			$('#container').css('width', document.body.clientWidth + 'px');	
		},
	});
}
function hasChildBranch(id) {
	var t = $.fn.zTree.getZTreeObj('tree11');
	var ns = t.getNodesByParam('pid', id, null);
	if (ns == null) return false;
	var i = ns.length;
	while (i--) {
		var pns = t.getNodesByParam('pid', ns[i].id, null);
		if (pns != null) return true;
	}
	return false;
}
function cb_del_member(data) {
	callajax("branch!getOrganTree", "", cb_11_tree);
}
function showpage(cp) {
	curpage = cp;
	$('#110').hide();
	$('#111').hide();
	$('#112').hide();
	$('#' + cp).show();
}