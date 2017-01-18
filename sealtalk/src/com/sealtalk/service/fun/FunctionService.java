package com.sealtalk.service.fun;

/**
 * 辅助功能管理 
 * @since jdk1.7
 * @author hao_dy
 *
 */
public interface FunctionService {

	/**
	 * 设置消息免打扰功能
	 * @param name
	 * @param status
	 * @param userid 
	 * @param groupid 
	 */
	public String setNotRecieveMsg(String status, String groupid, String userid);

	/**
	 * 获取消息免打扰状态功能
	 * @param userid 
	 * @param groupid 
	 * @param name
	 * @return
	 */
	public String getNotRecieveMsg(String groupid, String userid);

	
	/**
	 * 设置系统提示音功能
	 * @param status
	 * @return
	 */
	public String setSysTipVoice(String status);

	/**
	 * 获取系统提示音状态 
	 * @return
	 */
	public String getSysTipVoice();

}