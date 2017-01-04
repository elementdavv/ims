package com.sealtalk.model;

/** 
* @ClassName: SessionUser 
* @Description: TODO(代表已登录用户的包装类,表示此用户处于会话中。) 
* @author hdy
*  
*/
public class SessionUser
{
	/** 
	* @Fields accountId : TODO(账号ID) 
	*/ 
	private String account; 
	
	/** 
	* @Fields accountName : TODO(名字) 
	*/ 
	private String fullname;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	
	
}