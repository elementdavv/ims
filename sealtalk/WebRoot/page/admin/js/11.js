var curpage = '';
var curbranch = 0;
var curmember = 0;
var movnode = null;
var searchnodes11 = null;
$(document).ready(function(){

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
		$('#title11move').text('移动 ' + movnode.name +' 到');
		var o = $.fn.zTree.getZTreeObj('tree11move');
		o.selectNode(o.getNodeByParam('id', movnode.pid));
	});
	
	$('#zzgly').click(function(){
		if (curpage != '110' && curmember == '10001') return;
		if (curpage != '111') showpage('111');
		curmember = '10001';
		callajax("branch!getMemberById", {'id': curmember}, cb_111_112);
	});
	$('.addbranch').click(function(){
		$('#branch').modal({
			backdrop: false,
			remote: '11_branch.jsp'
		});
	});
	$('.addmember').click(function(){
		$('#member').modal({
			backdrop: false,
			remote: '11_member.jsp'
		});
	});
	
	callajax("branch!getOrganTree", "", cb_11_tree);
	callajax("branch!getRole", "", cb_111_role);
	callajax("branch!getSex", "", cb_111_sex);
	callajax("branch!getPosition", "", cb_111_position);

	$('#tree11 li').hover(function(){
		var t = $.fn.zTree.getZTreeObj('tree11');
		var ns = t.getNodesByParam('tId', $(this).prop('id'), null);
		$('.movdel').remove();
		$(this).find('a').first().after('<div class="movdel toright"><div class="move toleft">移</div>&nbsp;<div class="delete toleft">删</div></div>');
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
		}
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
	$.fn.zTree.init($('#tree110'), setting110, data);
	$.fn.zTree.init($('#tree11branchmanager'), setting110, data);
	var t = $.fn.zTree.getZTreeObj('tree11');
	var ns = t.getNodesByParam('id', 1, null);
	t.expandNode(ns[0], true);
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
}
var setting11 = {
	view: {
		showLine: false,
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
	callback: {
		onExpand: function(event, treeId, treeNode) {
			$('#tree11 li').hover(function(){
				var t = $.fn.zTree.getZTreeObj('tree11');
				var ns = t.getNodesByParam('tId', $(this).prop('id'), null);
				$('.movdel').remove();
				$(this).find('a').first().after('<div class="movdel toright"><div class="move toleft">移</div>&nbsp;<div class="delete toleft">删</div></div>');
				$('.move').click(function(){
					mov($($(this)[0]).parent().parent().prop('id'));
				});
				$('.delete').click(function(){
					del($($(this)[0]).parent().parent().prop('id'));
				});
				return false;
			},function(){
			});
		},
		onClick: function(event, treeId, treeNode, clickFlag) {
			treeNode.name = '<span colr="red">' + treeNode.name + '</span>';
			if (!treeNode.open) {
				$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
				$('#tree11 li').hover(function(){
					var t = $.fn.zTree.getZTreeObj('tree11');
					var ns = t.getNodesByParam('tId', $(this).prop('id'), null);
					$('.movdel').remove();
					$(this).find('a').first().after('<div class="movdel toright"><div class="move toleft">移</div>&nbsp;<div class="delete toleft">删</div></div>');
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
			else
				$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, false);

			if (treeNode.flag == 0) return;
			if (treeNode.flag == 1 && curpage == '110' && treeNode.id == curbranch) return;
			if (treeNode.flag == 2 && curpage != '110' && treeNode.id == curmember) return;

			if (treeNode.flag == 1 && curpage != '110') showpage('110');
			if (treeNode.flag == 2 && curpage == '110') showpage('111');
			
			if (treeNode.flag == 1) {
				curbranch = treeNode.id;
				callajax("branch!getBranchById", {'id': curbranch}, cb_110);
			}

			if (treeNode.flag == 2) {
				curmember = treeNode.id;
				callajax("branch!getMemberById", {'id': curmember}, cb_111_112);
			}
		}
	}
};
var setting110 = {
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
function mov(tId) {
	var t = $.fn.zTree.getZTreeObj('tree11');
	var ns = t.getNodesByParam('tId', tId, null);
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
	if (ns[0].id < 101) {
		bootbox.alert({title:'提示', message:'不能删除组织.'});
		return;
	}
	bootbox.confirm({
		title:'提示',
		message:'确定删除 ' + ns[0].name + ' ?',
		callback: function(result) {
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
					}
				});
			}
			else {
				callajax('branch!del', {id: ns[0].id, r: 0}, cb_del_member);
			}
		}
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
function dosearch(search, tree, nodes) {
	var i;
	if (nodes != null) {
		i = nodes.length;
		while (i--) {
			$('#' + nodes[i].tId + '_a').removeAttr('style');
		}
	}

	var text = $('#' + search).val();
	if (text == '') return;
	
	var t = $.fn.zTree.getZTreeObj(tree);
	t.expandAll(true);
	nodes = t.getNodesByParamFuzzy('name', text);
	i = nodes.length;
	while (i--) {
		$('#' + nodes[i].tId + '_a').attr('style', 'color: red');
		t.expandNode(nodes[i].getParentNode(), true);
	}
	
	return nodes;
}