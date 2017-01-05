package com.sealtalk.service;

import com.sealtalk.model.TMember;

/**
 * 用户逻辑处理接口
 * @author hao_dy
 * @since jdk1.7
 *
 */
public interface MemberService {

	/**
	 * 查找单用户
	 * @param name
	 * @param password
	 * @return
	 */
	public TMember searchSigleUser(String name, String password);

	/**
	 * 更新密码
	 * @param userName
	 * @param newPwd
	 * @return
	 */
	public boolean updateUserPwd(String account, String newPwd);

}
