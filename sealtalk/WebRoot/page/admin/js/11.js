var curpage = '';
var curbranch = 0;
var curmember = 0;
$(document).ready(function(){
	showpage('110');
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
	
	callajax("branch!getOrganTree", "", cb_11);
	callajax("branch!getRole", "", cb_111_role);
	callajax("branch!getSex", "", cb_111_sex);
	callajax("branch!getPosition", "", cb_111_position);
});

function cb_11(data) {

	$.fn.zTree.init($('#tree11'), setting11, data);
	$.fn.zTree.init($('#tree110'), setting110, data);
	var t = $.fn.zTree.getZTreeObj('tree11');
	var ns = t.getNodesByParam('id', 1, null);
	t.expandNode(ns[0], true);
}

var setting11 = {
	view: {
		showLine: false,
		nameIsHTML: true,
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
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
			if (!treeNode.open)
				$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode, true);
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

function addHoverDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#diyBtn_"+treeNode.id).length>0) return;
	var editStr = "<span id='diyBtn_space_" +treeNode.id+ "' > </span>"
		+ "<button type='button' class='diyBtn1' id='diyBtn_" + treeNode.id
		+ "' title='"+treeNode.name+"' onfocus='this.blur();'></button>";
	aObj.append(editStr);
	var btn = $("#diyBtn_"+treeNode.id);
	if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
};
function removeHoverDom(treeId, treeNode) {
	$("#diyBtn_"+treeNode.id).unbind().remove();
	$("#diyBtn_space_" +treeNode.id).unbind().remove();
};

function showpage(cp) {
	curpage = cp;
	$('#110').hide();
	$('#111').hide();
	$('#112').hide();
	$('#' + cp).show();
}