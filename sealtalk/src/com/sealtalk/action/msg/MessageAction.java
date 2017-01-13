package com.sealtalk.action.msg;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Tips;
import com.sealtalk.service.msg.MessageService;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;

/**
 * 消息管理
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/12
 */
public class MessageAction extends BaseAction {

	private static final long serialVersionUID = -1948853366651740073L;
	private static final Logger logger = Logger.getLogger(MessageAction.class);
	
	/**
	 * 发送系统消息
	 * @return
	 * @throws ServletException
	 */
	public String sendSysMsg() throws ServletException {
		JSONObject jo = new JSONObject();
		String result = null;
		
		if (StringUtils.getInstance().isBlank(fromid) ||
				StringUtils.getInstance().isBlank(targetids)) {
			
			jo.put("code", 0);
			jo.put("text", Tips.NOSENDPERSON.getName());
			result = jo.toString();
			
		} else {
			result = msgService.sendSysMsg(fromid, targetids, msg, extraMsg);
		}
		
		return result;
	}
	
	private String fromid;
	private String targetids;
	private String msg;
	private String extraMsg;
	
	private MessageService msgService;

	public String getFromid() {
		return fromid;
	}

	public void setFromid(String fromid) {
		this.fromid = fromid;
	}

	public String getTargetids() {
		return targetids;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getExtraMsg() {
		return extraMsg;
	}

	public void setExtraMsg(String extraMsg) {
		this.extraMsg = extraMsg;
	}

	public void setTargetids(String targetids) {
		this.targetids = targetids;
	}

	public MessageService getMsgService() {
		return msgService;
	}

	public void setMsgService(MessageService msgService) {
		this.msgService = msgService;
	}
	
	

}
