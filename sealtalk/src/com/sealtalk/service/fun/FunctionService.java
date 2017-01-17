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
	 */
	public String setNotRecieveMsg(String status);

	/**
	 * 获取消息免打扰状态功能
	 * @param name
	 * @return
	 */
	public String getNotRecieveMsg();

}
