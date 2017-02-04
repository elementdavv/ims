package com.sealtalk.service.member;

import com.sealtalk.model.TMember;

/**
 * 用户逻辑处理接口
 * @author hao_dy
 * @since jdk1.7
 *
 */
public interface MemberService {

	/**
	 * 登陆验证
	 * @param name
	 * @param password
	 * @return
	 */
	public TMember searchSigleUser(String name, String password);

	/**
	 * 更新密码按账号
	 * @param userName
	 * @param newPwd
	 * @return
	 */
	public boolean updateUserPwdForAccount(String account, String newPwd);
	
	/**
	 * 更新密码按手机号
	 * @param userName
	 * @param newPwd
	 * @return
	 */
	public boolean updateUserPwdForPhone(String phone, String newPwd);

	/**
	 * 获取单个成员
	 * @param account
	 * @return
	 */
	public String getOneOfMember(String account);

	/**
	 * 更新token
	 * @param userId
	 * @param token
	 * @return
	 */
	public int updateUserTokenForId(String userId, String token);

	/**
	 * 搜索用户按账号或拼音
	 * @param account
	 * @return
	 */
	public String searchUser(String account);

	/**
	 * 验证旧密码
	 * @param account
	 * @param newPwd
	 * @return
	 */
	public boolean valideOldPwd(String account, String newPwd);

	/**
	 * 保存短信验证码
	 * @param code
	 * @param code2 
	 */
	public void saveTextCode(String phone, String code);

	/**
	 * 获取短信验证码
	 * @param phone
	 * @return
	 */
	public String getTextCode(String phone);

	/**
	 * 更新个人设置
	 * @param account
	 * @param fullname
	 * @param sex
	 * @param position
	 * @param branch
	 * @param email
	 * @param phone
	 * @param sign
	 * @return
	 */
	public String updateMemberInfo(String account, String fullname, String sex,
			String position, String branch, String email, String phone,
			String sign);

}
