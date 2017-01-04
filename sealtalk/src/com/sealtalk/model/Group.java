package com.sealtalk.model;

/**
 * 群组模型
 * @since jdk1.7
 * @author hao_dy 
 * */
public class Group {
	private int id;
	private int createtorId;			//创建者 default 0
	private int volume;					//可容纳人数 default 0
	private int volumeuse;				//已有人数 default 0
	private int space;					//共享空间 default 0
	private int spaceuse;				//已用共享空间 default 0
	private int annexlong;				//聊天附件保留天数 default 0
	private int listorder;
	
	private String code;				//群组代码
	private String name;				//群组名称
	private String createDate;
	private String notice;				//群公告 default '0'
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCreatetorId() {
		return createtorId;
	}
	public void setCreatetorId(int createtorId) {
		this.createtorId = createtorId;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getVolumeuse() {
		return volumeuse;
	}
	public void setVolumeuse(int volumeuse) {
		this.volumeuse = volumeuse;
	}
	public int getSpace() {
		return space;
	}
	public void setSpace(int space) {
		this.space = space;
	}
	public int getSpaceuse() {
		return spaceuse;
	}
	public void setSpaceuse(int spaceuse) {
		this.spaceuse = spaceuse;
	}
	public int getAnnexlong() {
		return annexlong;
	}
	public void setAnnexlong(int annexlong) {
		this.annexlong = annexlong;
	}
	public int getListorder() {
		return listorder;
	}
	public void setListorder(int listorder) {
		this.listorder = listorder;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
}
