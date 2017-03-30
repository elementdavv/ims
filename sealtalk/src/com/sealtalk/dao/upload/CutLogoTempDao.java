package com.sealtalk.dao.upload;

import java.util.List;

import com.sealtalk.common.IBaseDao;
import com.sealtalk.model.TCutLogoTemp;
import com.sealtalk.model.TMember;

public interface CutLogoTempDao extends IBaseDao<TCutLogoTemp, Long> {

	/**
	 * 保存临时裁剪图 片
	 * @param clte
	 */
	public void saveTempPic(TCutLogoTemp clte);

	/**
	 * 根据id获取临时头像
	 * @param userid
	 * @return
	 */
	public List<TCutLogoTemp> getTempLogoForId(int userid);

	/**
	 * 根据用户id和图片名称查找图片
	 * @param userIdInt
	 * @param picName
	 * @return
	 */
	public TCutLogoTemp getTempLogoForIdAndPicName(int userIdInt, String picName);

	/**
	 * 删除指定头像从头像库
	 * @param userIdInt
	 * @param picName
	 * @return
	 */
	public int delUserLogos(int userIdInt, String picName);

	/**
	 * 拉取头像库
	 * @param userIdInt
	 * @return
	 */
	public List<TCutLogoTemp> getUserLogos(int userIdInt);

	/**
	 * 删除成员头像
	 * @param userids
	 * @return
	 */
	public int deletePicsByIds(String userids);
	
	
} 
