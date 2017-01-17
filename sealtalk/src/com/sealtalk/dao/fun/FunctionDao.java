package com.sealtalk.dao.fun;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TFunction;

/**
 * 辅助功能  
 * @author hao_dy
 * @since jdk1.7
 * @date 2017/01/09
 */
public interface FunctionDao extends IBaseDao<TFunction, Long> {

	/**
	 * 设置消息免打扰功能
	 * @param tf
	 */
	public void setNotRecieveMsg(TFunction tf);

	/**
	 * 获取消息免打扰功能 
	 * @param name
	 * @return
	 */
	public TFunction getNotRecieveMsg(String name);

	
} 
