package com.sealtalk.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
//import org.hibernate.Criteria;
//import org.springframework.util.StringUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.sealtalk.model.SessionUser;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware
{
	private static final long serialVersionUID = 1L;
	//protected static Logger logger = UimpLogger.getLogger();
	
	public BaseAction(){}
	public InputStream inputStream;
	public HttpServletRequest request;
	public HttpServletResponse response;
	
	public void setServletResponse(HttpServletResponse response)
	{
		this.response = response;
	}
	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}
	public InputStream getInputStream()
	{
		return inputStream;
	}
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;
	}
	
	
	/** 返回客户端JSON格式数据 */
	public void returnToClient(String jsonString)
	{
		try {
			response.addHeader("pragma", "NO-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addDateHeader("Expries", 0);
			setInputStream(new ByteArrayInputStream(jsonString.getBytes("utf-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// AJAX输出，返回null
	public String ajax(String content, String type)
	{
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap)
	{
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	
	
	/** 返回客户端XML格式数据 */
	public void returnXMLToClient(String xml)
	{
		try {
			response.addHeader("pragma", "NO-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.addDateHeader("Expries", 0);
			response.setContentType("text/xml;charset=utf-8");
			
			setInputStream(new ByteArrayInputStream(xml.getBytes("utf-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/** 设置当前会话的用户包装类 */
	protected void setSessionUser(SessionUser su)
	{
		if (request == null) {
			WebContext ctx = WebContextFactory.get();
			HttpSession session = ctx.getSession(false);
			session.setAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONUSER, su);
		} else {
			request.getSession().setAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONUSER, su);
		}
	}

	/** 获取当前会话的用户包装类 */
	protected SessionUser getSessionUser()
	{
		if (request == null) {
			WebContext ctx = WebContextFactory.get();
			HttpSession session = ctx.getSession(false);
			return (SessionUser) session.getAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONUSER);
		} else {
			return (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONUSER);
		}
	}
	
	
	protected void setSessionAttribute(String key, Object o)
	{
	/*	if (request == null)
		{
			WebContext ctx = WebContextFactory.get();
			HttpSession session = ctx.getSession(false);
			session.setAttribute(key, o);
		} else
		{
			request.getSession().setAttribute(key, o);
		}*/
	}
	
	
/*	protected Object getSessionAttribute(String key)
	{
		if (request == null)
		{
			WebContext ctx = WebContextFactory.get();
			HttpSession session = ctx.getSession(false);
			return session.getAttribute(key);
		} else
		{
			return request.getSession().getAttribute(key);
		}
	}*/
	
/*	
	protected String getApplicaitonQueryFilter()
	{
		
		String condition = "";
		SessionUser su = getSessionUser();
		if (su == null || su.isSuperAdmin())
			return condition;
		
		List<String> applicaitonP = su.getApplicationIds();
		if (applicaitonP == null || applicaitonP.isEmpty())
			return condition;
		return "('" + StringUtils.collectionToDelimitedString(applicaitonP, "','") + "')";
	}
	*/
	
	/*protected String getOrganizationQueryFilter()
	{
		
		String condition = "";
		SessionUser su = getSessionUser();
		if (su == null || su.isSuperAdmin())
			return condition;
		
		List<String> organizationP = su.getOrganizationIds();
		if (organizationP == null || organizationP.isEmpty())
			return condition;
		return "('" + StringUtils.collectionToDelimitedString(organizationP, "','") + "')";
	}*/
	
	
	
	/** 获得当前登录管理员的accountID */
	/*protected String obtainLoginAccountId()
	{
		SessionUser userInfo = getSessionUser();
		return userInfo == null ? null : userInfo.getAccountId();
	}*/
	
	
	/*protected String obtainLoginAccount()
	{
		SessionUser userInfo = getSessionUser();
		return userInfo == null ? null : userInfo.getAccountName();
	}*/
	
	
	
	/** 构�?�grid查询条件one */
	/*protected String queryConditionHandler(List<String> where, List<String> order, String relationship)
	{
		//return QueryConditionHandler.queryConditionHandler(where, order, relationship);
	}*/
	
	
	
	/** 构�?�grid查询条件 */
/*	protected String buildWhereHql(List<String> where)
	{
		//return QueryConditionHandler.buildWhereHql(where);
	}*/
	
	
	
	/** 构�?�grid排序条件 */
	/*protected String buildOrderHql(List<String> orderBy)
	{
		//return QueryConditionHandler.buildOrderHql(orderBy);
	}
	*/
	
	
	/** 构�?�grid查询条件 */
	/*protected void queryConditionHandler(List<String> where, List<String> order, Criteria criteria)
	{
		//QueryConditionHandler.queryConditionHandler(where, order, criteria);
	}*/
	
}