package com.sealtalk.dao.group.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.sealtalk.common.BaseDao;
import com.sealtalk.dao.group.GroupDao;
import com.sealtalk.model.TGroup;
import com.sealtalk.model.TGroupMember;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TimeGenerator;

/**
 * @功能  成员数据管理层
 * @author hao_dy
 * @date 2017/01/04
 * @since jdk1.7
 */
public class GroupDaoImpl extends BaseDao<TGroup, Long> implements GroupDao {

	@Override
	public String createGroup(int userId, String groupname) {
		try {
			TGroup tg = new TGroup();
			
			int volume = StringUtils.getInstance().strToInt(PropertiesUtils.getStringByKey("group.volume"));
			int volumeUse = StringUtils.getInstance().strToInt(PropertiesUtils.getStringByKey("group.volumeuse"));
			int space = StringUtils.getInstance().strToInt(PropertiesUtils.getStringByKey("group.space"));
			int spaceUse = StringUtils.getInstance().strToInt(PropertiesUtils.getStringByKey("group.spaceuse"));
			int annexLong = StringUtils.getInstance().strToInt(PropertiesUtils.getStringByKey("group.annexlong"));
			
			volume = volume == -1 ? 0 : volume;
			volumeUse = volumeUse == -1 ? 0 : volumeUse;
			space = space == -1 ? 0 : space;
			spaceUse = spaceUse == -1 ? 0 : spaceUse;
			annexLong = annexLong == -1 ? 0 : annexLong;
			
			String code = "G" + userId + "_" + TimeGenerator.getInstance().getUnixTime();
			
			tg.setCreatorId(userId);
			tg.setCode(code);
			tg.setName(groupname);
			tg.setCreatedate(TimeGenerator.getInstance().formatNow("yyyyMMdd"));
			tg.setVolume(volume);
			tg.setVolumeuse(volumeUse);
			tg.setSpace(space);
			tg.setSpaceuse(spaceUse);
			tg.setAnnexlong(annexLong);
			tg.setNotice("");
			
			save(tg);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return null;
	}

	@Override
	public int countGroup() {
		int count = 0;
		
		try {
			count = count(" from TGroup");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public TGroup getGroupForIdAndCode(String userid, String code) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("id", userid));
			ctr.add(Restrictions.eq("code", code));
			
			List list = ctr.list();
			
			if (list.size() > 0) {
				return (TGroup) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void removeGroup(TGroup tg) {
		try {
			delete(tg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
