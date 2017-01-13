package com.sealtalk.action.group;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Tips;
import com.sealtalk.service.group.GroupService;

/**
 * 成员action
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/07
 */
public class GroupAction extends BaseAction {

	private static final long serialVersionUID = 5512359170256277370L;
	private static final Logger logger = Logger.getLogger(GroupAction.class);
	
	/**
	 * 创建群组
	 * @return
	 * @throws ServletException
	 */
	public String createGroup() throws ServletException {
		String result = null;
		
		if (groupService != null) {
			result = groupService.createGroup(userid, groupids, groupname);
		} else {
			JSONObject jo = new JSONObject();
			jo.put("code", 500);
			jo.put("text", Tips.UNKNOWERR.getText());
			result = jo.toString();
		}
		
		return result;
	}
	
	/**
	 * 加入群组
	 * @return
	 * @throws ServletException
	 */
	public String joinGroup() throws ServletException {
		return null;
	}
	
	/**
	 * 离开群组
	 * @return
	 * @throws ServletException
	 */
	public String leftGroup() throws ServletException {
		return null;
	}
	
	/**
	 * 解散群组
	 * @return
	 * @throws ServletException
	 */
	public String disslovedGroup() throws ServletException {
		return null;
	}
	
	/**
	 * 刷新群组
	 * @return
	 * @throws ServletException
	 */
	public String refreshGroup() throws ServletException {
		return null;
	}
	
	/**
	 * 查询群成员
	 * @return
	 * @throws SevletException
	 */
	public String listGroupMemebers () throws ServletException {
		return null;
	}
	
	private GroupService groupService;
	
	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	private String userid;
	private String groupids;
	private String groupname;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getGroupids() {
		return groupids;
	}

	public void setGroupids(String groupids) {
		this.groupids = groupids;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}
