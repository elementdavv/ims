package com.sealtalk.dao.member;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TMember;

public interface MemberDao extends IBaseDao<TMember, Integer> {
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
	public boolean updateUserPwdForAccount(String account, String md5Pwd);
	
	/**
	 * 更新用户密码
	 * @param userName
	 * @param md5Pwd
	 * @return
	 */
	public boolean updateUserPwdForPhone(String account, String md5Pwd);

	/**
	 *	获取单用户 
	 * @param id
	 * @return
	 */
	public Object[] getOneOfMember(int id);

	/**
	 * in 查询多个用户按账号
	 * @param mulMemberStr
	 * @return
	 */
	public List<TMember> getMultipleMemberForAccounts(String[] mulMemberStr);

	/**
	 * 查询多个用户按id
	 * @param accounts
	 * @return
	 */
	public List<TMember> getMultipleMemberForIds(Integer[] accounts);

	/**
	 * 更新用户token
	 * @param userId
	 * @param token
	 * @return
	 */
	public int updateUserTokenForId(String userId, String token);

	/**
	 * 查询单用户按id
	 * @param valueOf
	 * @return
	 */
	public TMember getMemberForId(int valueOf);

	/**
	 * 按账号查询id
	 * @param account
	 * @return
	 */
	public int getMemberIdForAccount(String account);

	/**
	 * 按 账号或名称或全拼查找用户
	 * @param account
	 * @return
	 */
	public List searchUser(String account);

	/**
	 * 验证旧密码
	 * @param account
	 * @param oldPwd
	 * @return
	 */
	public boolean valideOldPwd(String account, String oldPwd);

	/**
	 * 更新用户头像
	 * @param userId
	 * @param picName
	 * @return
	 */
	public int updateUserLogo(int userId, String picName);

	/**
	 * 查看头像是否在使用中
	 * @param userIdInt 
	 * @param userId
	 * @return
	 */
	public boolean isUsedPic(int userIdInt, String userId);

	/**
	 * 更新个人设置
	 * @param account
	 * @param fullname
	 * @param sex
	 * @param email
	 * @param phone
	 * @param sign
	 * @return
	 */
	public int updateMemeberInfo(String account, String fullname, String sex,
			String email, String phone, String sign);
	
	public List getMemberPosition(Integer memberId);
	
	public List getMemberRole(Integer memberId);
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
	public TMember getOneMember(String account);

} 
