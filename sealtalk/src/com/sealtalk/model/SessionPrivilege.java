package com.sealtalk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/** 
* @ClassName: SessionPrivilege 
* @Description: TODO(权限session) 
* @author hdy
*  
*/
public class SessionPrivilege {
	
	private static final ArrayList<String> privileges = new ArrayList<String>();
	private Map<String, String> map;
	/*
	private String htgl;			//后台管理
//	private String yyapppcd;		//应用APP/PC端
	private String cjxz;			//层级限制
	private String rsgl;			//人事管理
	private String bmgl;			//部门管理
	private String zzxxgl;			//组织信息管理
	private String qzgl;			//群组管理
	private String qxgl;			//权限管理
	private String grsz;			//个人设置
	private String stsz;			//聊天设置
	private String qz;				//群组
	private String djj;				//对讲机
	private String qt;				//其他
	private String dpjhzsjbmkf;		//对平级或者上级部门开放
	private String rsglck;			//查看
	private String rsgltj;			//添加
	private String rsgljcxx;		//基础信息
	private String rsglxgmm;		//修改密码
	private String rsglyd;			//移动
	private String rsglsc;			//删除
	private String bmglck;			//查看
	private String bmgltj;			//添加
	private String bmglxg;			//修改
	private String bmglyd;			//移动	
	private String bmglsc;			//删除
	private String zzxxglck;		//查看
	private String zzxxglxg;		//修改
	private String qzglck;			//查看
	private String qzgljs;			//解散
	private String qzglxg;			//修改
	*/
	public SessionPrivilege(){
		addList();
	};
	
	public void setPrivilige(ArrayList<JSONObject> ja) {
		int len = ja.size();
		Map<String, String> map = new HashMap<String, String>();
		
		for(int i = 0; i < len; i++) {
			JSONObject jo = ja.get(i);
			if (privileges.contains(jo.getString("priurl"))) {
				map.put(jo.getString("priurl"), "1");
			}
		}
		
		this.map = map;
	}
	
	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	private void addList() {
		privileges.add("htgl");
		privileges.add("yyapppcd");
		privileges.add("cjxz");
		privileges.add("rsgl");
		privileges.add("bmgl");
		privileges.add("zzxxgl");
		privileges.add("qzgl");
		privileges.add("qxgl");
		privileges.add("grsz");
		privileges.add("stsz");
		privileges.add("qz");
		privileges.add("djj");
		privileges.add("qt");
		privileges.add("dpjhzsjbmkf");
		privileges.add("rsglck");
		privileges.add("rsgltj");
		privileges.add("rsgljcxx");
		privileges.add("rsglxgmm");
		privileges.add("rsglyd");
		privileges.add("rsglsc");
		privileges.add("bmglck");
		privileges.add("bmgltj");
		privileges.add("bmglxg");
		privileges.add("bmglyd");
		privileges.add("bmglsc");
		privileges.add("zzxxglck");
		privileges.add("zzxxglxg");
		privileges.add("qzglck");
		privileges.add("qzgljs");
		privileges.add("qzglxg");
	}
}