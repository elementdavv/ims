package com.sealtalk.dao.member;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TextCode;

public interface TextCodeDao extends IBaseDao<TextCode, Long> {
	
	/**
	 * 获取短信验证码
	 * @param phone
	 * @return
	 */
	public TextCode getTextCode(String phone);

	/**
	 * 删除短信验证码记录
	 * @param string
	 */
	public int deleteTextCode(String string);

	/**
	 * 保存或更新短信验证码记录
	 * @param stc
	 */
	public void saveTextCode(TextCode stc);
	
} 
