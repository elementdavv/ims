Ext.onReady( function() {
	Ext.QuickTips.init();
	var head = new Ext.Toolbar({
		region: 'north',
		height:70,
		items:[
			'<h2> xx驾校管理系统 </h2>',
			'->',{
				text: '注销',
				iconCls: 'icon-login',
				handler: function() {
					Ext.Ajax.request({
						method: 'POST',
						url: 'system!logOut.action',
						success: function(response) {
							var contextPath = document.location.pathname;
							var index =contextPath.substr(1).indexOf("/");
							contextPath = contextPath.substr(0,index+1);
							delete index;
							window.location.href=contextPath + "/page/system/login.jsp"
						},
						failure: function() {},
						params: ''
					});
				}
			},{
			text: '修改密码',
			iconCls: 'icon-password',
			handler: function() {
				newWin();
			}
		}]
	});
	
	var bottom = new Ext.Toolbar({
		region: 'south',
		height: 10,
		items:[{
			   text: ''
		}]
	});
	
	var tree = new Ext.tree.TreePanel({
		region : 'west',
		margins : '5 0 5 5',
		split : true,
		width : 210,
		animate: true,
		autoScroll: true,   
		autoHeight: true,
		store: new Ext.data.TreeStore({
			proxy:{
				type: 'ajax',
				url: 'menus!buildMenus.action'
			},
			root: {
				text: '功能菜单',
				id: '0',
				expanded: true
			}
		})
	});
	
	var addTabPanal = function(view, record, item) {
		var n;
		if(record.get("leaf") == false) {return;}
		var str = record.get("id");
		var strArray = str.split("-");
		n = tabPanel.getComponent(strArray[0]);
		if(n) {
			tabPanel.setActiveTab(n);
			return;
		}
		var contextPath = document.location.pathname;
		var index =contextPath.substr(1).indexOf("/");
		contextPath = contextPath.substr(0,index+1);
		delete index;
		n = tabPanel.add( {
			id : strArray[0],
			title : record.get("text"),
			height:document.body.clientHeight-140,
			autoLoad : {
				url: contextPath+'/page/'+strArray[1],
				scripts: true
			},
			closable : 'true'
		});
		tabPanel.setActiveTab(n);
	};
	tree.on("itemclick",addTabPanal);
	var tabPanel = new Ext.TabPanel( {
		region : 'center',
		enableTabScroll : true,
		deferredRender : false,
		activeTab : 0,
		items : [ {
			title : '主页面板',
			autoLoad : 'welcome.jsp'
		} ]
	});

	var viewport = new Ext.Viewport( {
		layout : 'border',
		items : [ head, tree, tabPanel, bottom ]
	});
});