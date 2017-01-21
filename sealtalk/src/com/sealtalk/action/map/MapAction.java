package com.sealtalk.action.map;

import javax.servlet.ServletException;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
import com.sealtalk.service.map.MapService;

@Secured
public class MapAction extends BaseAction {

	private static final long serialVersionUID = -1765255265459599929L;
	
	
	public String subLocation() throws ServletException {
		String result = null;
		
		try {
			result = mapService.subLocation(userid, latitude, longtitude);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		returnToClient(result);
		return "text";
	}
	
	/**
	 * 获取好友或群组成员位置 
	 * @return
	 * @throws Exception
	 */
	public String getLocation() throws ServletException {
		String result = null;
		
		try {
			result = mapService.getLocation(userid, targetid, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		returnToClient(result);
		return "text";
	}
	
	private MapService mapService;
	
	private String userid;
	private String targetid;
	private String type;
	private String latitude;
	private String longtitude;
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public MapService getMapService() {
		return mapService;
	}
	public void setMapService(MapService mapService) {
		this.mapService = mapService;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTargetid() {
		return targetid;
	}
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
