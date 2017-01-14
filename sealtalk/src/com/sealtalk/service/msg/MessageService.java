package com.sealtalk.service.msg;

/**
 * 融云消息发送
 * @author hao_dy
 * @date 2017/01/11
 * @since jdk1.7
 */
public interface MessageService {

	/**
	 * 发送系统消息 
	 * @param fromid
	 * @param targetids
	 * @param msg
	 * @param extraMsg
	 * @return
	 */
	public String sendSysMsg(String fromid, String targetids, String msg, String extraMsg);

}
