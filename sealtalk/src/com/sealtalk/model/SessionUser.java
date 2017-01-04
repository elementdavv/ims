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
	private String userName; 
	
	/** 
	* @Fields accountName : TODO(名字) 
	*/ 
	private String personName;

	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}