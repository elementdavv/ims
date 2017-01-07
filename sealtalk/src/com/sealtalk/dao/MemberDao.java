package com.sealtalk.dao;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TMember;

public interface MemberDao extends IBaseDao<TMember, Long> {
	/**
	 * 登陆验证
	 * @param name
	 * @param password
	 * @return
	 */
	public TMember searchSigleUser(String name, String password);

	/**
	 * 更新用户密码
	 * @param userName
	 * @param md5Pwd
	 * @return
	 */
	public boolean updateUserPwd(String account, String md5Pwd);

	/**
	 *	获取单用户 
	 * @param account
	 * @return
	 */
	public TMember getOneOfMember(String account);

} 
