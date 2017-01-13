package com.sealtalk.dao.group.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
	public int createGroup(int userId, String code, String groupname) {
		int id = -1;
		
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
			tg.setListorder(0);
			save(tg);
			
			id = tg.getId();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return id;
		
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
	public TGroup getGroupForIdAndCode(int userid, String code) {
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

	@Override
	public TGroup getGroupForId(int groupId) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("id", groupId));
			
			List list = ctr.list();
			
			if (list.size() > 0) {
				return (TGroup) list.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TGroup> getGroupList(int userId) {
		try {
			
			Criteria ctr = getCriteria();
			ctr.add(Restrictions.eq("id", userId));
			
			List<TGroup> list = ctr.list();
			
			if (list.size() > 0) {
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


}
