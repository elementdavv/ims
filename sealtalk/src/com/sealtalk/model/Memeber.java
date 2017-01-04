package com.sealtalk.model;

/**
 * 成员模型
 * @since jdk1.7
 * @author hao_dy
 * */
public class Memeber {
	private int id;
	private int groupmax;			//可建群数量
	private int groupUse;			//已建群数量
	
	private char sex;
	
	private String account;			//账号
	private String password;		//密码
	private String fullName;		//全名称
	private String pinyin;			//姓名全拼
	private String workno;			//工号
	private String birthday;		//生日
	private String logo;			//头像图标 
	private String email;			//email
	private String mobile;			//电话号
	private String telephone;		//手机号
	private String gaddress;		//住址
	private String intro;			//I don't know
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupmax() {
		return groupmax;
	}
	public void setGroupmax(int groupmax) {
		this.groupmax = groupmax;
	}
	public int getGroupUse() {
		return groupUse;
	}
	public void setGroupUse(int groupUse) {
		this.groupUse = groupUse;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getWorkno() {
		return workno;
	}
	public void setWorkno(String workno) {
		this.workno = workno;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getGaddress() {
		return gaddress;
	}
	public void setGaddress(String gaddress) {
		this.gaddress = gaddress;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
}
