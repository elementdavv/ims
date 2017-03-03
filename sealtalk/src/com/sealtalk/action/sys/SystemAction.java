package com.sealtalk.action.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Constants;
import com.sealtalk.common.Tips;
import com.sealtalk.model.SessionPrivilege;
import com.sealtalk.model.SessionUser;
import com.sealtalk.model.TMember;
import com.sealtalk.service.adm.PrivService;
import com.sealtalk.service.member.MemberService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.MathUtils;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TextHttpSender;
import com.sealtalk.utils.TimeGenerator;

/**
 * 系统相关
 * @since jdk1.7
 * @author hao_dy
 *
 */

public class SystemAction extends BaseAction {
	
	private static final long serialVersionUID = -3901445181785461508L;
	private static final String LOGIN_ERROR_MESSAGE = "loginErrorMsg";
	private static final Logger logger = Logger.getLogger(SystemAction.class);
	
	/**
	 * 跳转登陆页面(仅web使用)
	 * @return
	 * @throws Exception
	 */
	public String login() throws IOException, ServletException {
	
		if (getSessionUser() == null) {
			return "loginPage";
		} else {
			return "loginSuccess";
		}
	}
	
	/**
	 * web端登陆验证
	 * @return
	 * @throws Exception
	 */
	public String loginForWeb() throws IOException, ServletException {
		
		if (StringUtils.getInstance().isBlank(account)) {
			request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.NULLUSER.getName());
			return "loginPage";
		}
		
		TMember member = memberService.searchSigleUser(account, userpwd);
		
		if(member == null) {
			request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.ERRORUSERORPWD.getName());
			return "loginPage";
		}
		
		logger.debug("That logining account is " + account);
		
		String userId = "" + member.getId();
		String name = member.getFullname();
		String token = null;
		String tokenMaxAge = PropertiesUtils.getStringByKey("db.tokenMaxAge");
		
		long tokenMaxAgeLong = 0;
		long firstTokenDate = 0;
		if (member.getCreatetokendate() != null) {
			firstTokenDate = member.getCreatetokendate();
		}
		long now = TimeGenerator.getInstance().getUnixTime();
		
		if (tokenMaxAge != null && !"".equals(tokenMaxAge)) {
			tokenMaxAgeLong = Long.valueOf(tokenMaxAge);
		}
		
		if ((now - firstTokenDate) > tokenMaxAgeLong) {
			try {
				String domain = PropertiesUtils.getDomain();
				String uploadDir = PropertiesUtils.getUploadDir();
				String logo = member.getLogo();
				String url = domain + uploadDir + logo;
				
				token = RongCloudUtils.getInstance().getToken(userId, name, url);
				memberService.updateUserTokenForId(userId, token);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		} else {
			token = member.getToken();
		}
		
		logger.info(token);
		
		//设置用户session
		SessionUser su = new SessionUser();
		
		su.setId(member.getId());
		su.setAccount(member.getAccount());
		su.setFullname(member.getFullname());
		su.setToken(token);
		setSessionUser(su);
		
		//2.设置权限session
		List privList = privService.getRoleIdForId(member.getId());
		SessionPrivilege sp = new SessionPrivilege();
		
		if (privList != null) {
			Iterator it = privList.iterator();
			ArrayList<JSONObject> ja = new ArrayList<JSONObject>();
			while(it.hasNext()) {
				Object[] o = (Object[])it.next();
				JSONObject js = new JSONObject();
				js.put("privid", o[0]);
				js.put("priurl", o[1]);
				ja.add(js);
			}
		
			sp.setPrivilige(ja);
		}
		setSessionAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONPRIVILEGE, sp);
		
		return "loginSuccess";
	}
	
	/**
	 * app端登陆验证
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String afterLogin() throws IOException, ServletException {
		JSONObject result = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(account)) {
			result.put("code", 0);
			result.put("text", Tips.NULLUSER.getText());
			returnToClient(result.toString());
			return "text";
		}
		
		TMember member = memberService.searchSigleUser(account, userpwd);
		
		if(member == null) {
			result.put("code", 0);
			result.put("text", Tips.ERRORUSERORPWD.getText());
			returnToClient(result.toString());
			return "text";
		}
		
		logger.debug("That logining account is " + account);
		
		String userId = "" + member.getId();
		String name = member.getFullname();
		String token = null;
		String tokenMaxAge = PropertiesUtils.getStringByKey("db.tokenMaxAge");
		
		long tokenMaxAgeLong = 0;
		long firstTokenDate = 0;
		if (member.getCreatetokendate()!=null) {
			firstTokenDate = member.getCreatetokendate();
			System.out.println("afterLogin 180: " + firstTokenDate);
		}
		long now = TimeGenerator.getInstance().getUnixTime();
		
		if (tokenMaxAge != null && !"".equals(tokenMaxAge)) {
			tokenMaxAgeLong = Long.valueOf(tokenMaxAge);
		}
		
		if ((now - firstTokenDate) > tokenMaxAgeLong) {
			try {
				String domain = PropertiesUtils.getDomain();
				String uploadDir = PropertiesUtils.getUploadDir();
				String logo = member.getLogo();
				if(logo == null) logo = "PersonImg.png";
				String url = domain + uploadDir + logo;
				
				System.out.println("afterLogin 195url: " + url);
				System.out.println("afterLogin userId: " + userId);
				System.out.println("afterLogin name: " + name);
				token = RongCloudUtils.getInstance().getToken(userId, name, url);
				System.out.println("afterLogin 196: " + token);
				memberService.updateUserTokenForId(userId, token);
			} catch (Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		} else {
			token = member.getToken();
		}
		System.out.println("afterLogin 205: " + token);
		logger.info(token);
		
		//设置用户session
		SessionUser su = new SessionUser();
		
		su.setId(member.getId());
		su.setAccount(member.getAccount());
		su.setFullname(member.getFullname());
		su.setToken(token);
		setSessionUser(su);
		
		
		//2.设置权限
		List privList = privService.getRoleIdForId(member.getId());
		SessionPrivilege sp = new SessionPrivilege();
		ArrayList<JSONObject> ja = new ArrayList<JSONObject>();
		
		if (privList != null) {
			Iterator it = privList.iterator();
			
			while(it.hasNext()) {
				Object[] o = (Object[])it.next();
				JSONObject js = new JSONObject();
				js.put("privid", o[0]);
				js.put("priurl", o[1]);
				ja.add(js);
			}
		
		} 
		
		sp.setPrivilige(ja);
		setSessionAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONPRIVILEGE, sp);
		JSONObject text = JSONUtils.getInstance().modelToJSONObj(member);
		
		text.remove("password");
		text.remove("createtokendate");
		text.remove("groupmax");
		text.remove("groupuse");
		text.put("token", token);
		text.put("priv", JSONUtils.getInstance().modelToJSONObj(sp));
		
		result.put("code", 1);
		result.put("text", text.toString());
		
		returnToClient(result.toString());
		
		return "text";
	}

	/**
	 * 免登陆web端
	 * @return
	 * @throws ServletException
	 */
	public String freeLandingForWeb() throws ServletException {
		
		TMember member = memberService.getMemberByToken(token);
		if (member == null) {
			request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.WRONGTOKEN.getName());
			return "loginPage";
		} 
		
		SessionUser su = new SessionUser();
		
		su.setId(member.getId());
		su.setAccount(member.getAccount());
		su.setFullname(member.getFullname());
		su.setToken(member.getToken());
		setSessionUser(su);
		
		//2.设置权限session
		List privList = privService.getRoleIdForId(member.getId());
		SessionPrivilege sp = new SessionPrivilege();
		
		if (privList != null) {
			Iterator it = privList.iterator();
			ArrayList<JSONObject> ja = new ArrayList<JSONObject>();
			while(it.hasNext()) {
				Object[] o = (Object[])it.next();
				JSONObject js = new JSONObject();
				js.put("privid", o[0]);
				js.put("priurl", o[1]);
				ja.add(js);
			}
		
			sp.setPrivilige(ja);
		}
		setSessionAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONPRIVILEGE, sp);
		
		return "loginSuccess";
			
	}
	
	/**
	 * 验证免登陆app端
	 * @return
	 * @throws ServletException
	 */
	public String freeLandingForApp() throws ServletException {
		JSONObject result = new JSONObject();
		TMember member = memberService.getMemberByToken(token);
		if (member == null) {
			request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.WRONGTOKEN.getName());
			return "loginPage";
		} 
		
		//2.设置权限
		List privList = privService.getRoleIdForId(member.getId());
		SessionPrivilege sp = new SessionPrivilege();
		ArrayList<JSONObject> ja = new ArrayList<JSONObject>();
		
		if (privList != null) {
			Iterator it = privList.iterator();
			
			while(it.hasNext()) {
				Object[] o = (Object[])it.next();
				JSONObject js = new JSONObject();
				js.put("privid", o[0]);
				js.put("priurl", o[1]);
				ja.add(js);
			}
		
		} 
		
		sp.setPrivilige(ja);
		JSONObject text = JSONUtils.getInstance().modelToJSONObj(member);
		
		text.remove("password");
		text.remove("createtokendate");
		text.remove("groupmax");
		text.remove("groupuse");
		text.put("token", token);
		text.put("priv", JSONUtils.getInstance().modelToJSONObj(sp));
		
		result.put("code", 1);
		result.put("text", text.toString());
		
		returnToClient(result.toString());
		
		return "text";
	}
	
	/**
	 * 登出
	 * @return
	 * @throws Exception
	 */
	public String logOut() throws IOException, ServletException
	{
		request.getSession().removeAttribute(Constants.ATTRIBUTE_NAME_OF_SESSIONUSER);
		request.getSession().invalidate();
		return "loginPage";
	}
	
	/**
	 * 跳转修改密码页(仅web端使用)
	 * @return
	 * @throws Exception
	 */
	public String fogetPassword() throws ServletException {
		//request.getRequestDispatcher("/page/web/forgotpassword.jsp").forward(request, response);
		
		return "forgetpwd";
	}
	
	/**
	 * 中转短信平台
	 * @return
	 * @throws Exception
	 */
	public String requestText() throws IOException, ServletException {

		JSONObject text = new JSONObject();
		
		if (!StringUtils.getInstance().isBlank(phone)) {
			String dbCode = memberService.getTextCode(phone);
			String endText = PropertiesUtils.getStringByKey("code.endtext");
			String code = "";
			String context = "";
			
			if (dbCode == null || dbCode.equals("-1")) {
				int codeBit = StringUtils.getInstance().strToInt(PropertiesUtils.getStringByKey("code.bit"));
				code = String.valueOf(MathUtils.getInstance().getRandomSpecBit(codeBit));
				context = code + endText;
				memberService.saveTextCode(phone, code);
			} else {
				context = dbCode + endText;
			}
			
			//发送短信代码
			String sendText = TextHttpSender.getInstance().sendText(phone, context);
			
			if ("0".equals(sendText)) {
				text.put("code", 1);
				text.put("text", Tips.SENDTEXTS.getText());
			} else {
				text.put("code", 0);
				text.put("text", Tips.SENDERR.getText());
			}
		} else {
			text.put("code", 0);
			text.put("text", Tips.NULLPHONE.getText());
		}
		
		returnToClient(text.toString());
		
		return "text";
	}

	
	/**
	 * 验证短信(仅web端使用)
	 * @return
	 */
	public String testText() throws ServletException {
		
		JSONObject text = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(phone)) {
			text.put("code", -1);
			text.put("text", Tips.NULLPHONE.getText());
		} else if (StringUtils.getInstance().isBlank(textcode)) {
			text.put("code", -1);
			text.put("text", Tips.NULLTEXTS.getText());
		} else {
			String dbCode = memberService.getTextCode(phone);
			
			if (dbCode != null && !dbCode.equals("-1") && dbCode.equals(textcode)) {
				text.put("code", 1);
				text.put("text", Tips.TRUETEXTS.getText());
			} else {
				text.put("code", 0);
				text.put("text", Tips.FAIL.getText());
			}
		}
		
		returnToClient(text.toString());
		
		return "text";
	}
	
	/**
	 * 验证旧密码
	 * @return
	 * @throws ServletException
	 */
	public String valideOldPwd() throws ServletException {
		JSONObject text = new JSONObject();
		
		if (!StringUtils.getInstance().isBlank(oldpwd)) {				//登陆后修改密码
			boolean validOldPwd = memberService.valideOldPwd(account, oldpwd);
			if (!validOldPwd) {
				text.put("code", 0);
				text.put("text", Tips.WRONGOLDPWD.getText());
			} else {
				text.put("code", 1);
				text.put("text", Tips.OK.getText());
			}
		} else { 
			text.put("code", 0);
			text.put("text", Tips.WRONGOLDPWD.getText());
		}
		
		returnToClient(text.toString());
		
		return "text";
	}
	
	/**
	 * 修改新密码
	 * @return
	 */
	public String newPassword() throws ServletException {
		JSONObject text = new JSONObject();
		
		System.out.println("newPassword: " + account);
		if (StringUtils.getInstance().isBlank(account)) {
			text.put("code", "0");
			text.put("text", Tips.NULLUSER.getText());
			returnToClient(text.toString());
			return "text";
		}
		
		boolean status = true;
		int flag = 0;
				
		if (!StringUtils.getInstance().isBlank(oldpwd)) {				//登陆后修改密码
			boolean validOldPwd = memberService.valideOldPwd(account, oldpwd);
			flag = 1;		//后台修改
			if (!validOldPwd) {
				text.put("code", -1);
				text.put("text", Tips.WRONGOLDPWD.getText());
				status = false;
			}
		} else { //忘记密码修改密码 app端(web端这里不传textcode,)
			if (!StringUtils.getInstance().isBlank(textcode)) {
				if (textcode == null || "".equals(textcode)) {
					text.put("code", -1);
					text.put("text", Tips.NULLTEXTS.getText());
					status = false;
				} else {
					String dbCode = memberService.getTextCode(phone);
					
					if (dbCode != null && !dbCode.equals("-1") && dbCode.equals(textcode)) {
						text.put("code", 1);
						text.put("text", Tips.TRUETEXTS.getText());
					} else {
						text.put("code", 0);
						text.put("text", Tips.FAIL.getText());
					}
				}
			}
		}
		
		if (status) {
			if (!newpwd.equals(comparepwd)) {
				request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.FALSECOMPAREPWD.getText());
				return "fogetpwd";
			}
			
			boolean updateState = false;
			
			if (flag == 1) {
				updateState = memberService.updateUserPwdForAccount(account, newpwd);
			} else {
				updateState = memberService.updateUserPwdForPhone(phone, newpwd);
			}
			
			if (updateState == true) {
				text.put("code", "1");
				text.put("text", Tips.CHANGEPWDSUC.getText());
			} else {
				text.put("code", "0");
				text.put("text", Tips.CHANGEPWDFAIL.getText());
			}
		}
		returnToClient(text.toString());
		
		return "text";
	}
	
	/**
	 * 跳转后台管理 
	 */
	@Deprecated
	public String backManager() throws ServletException {
		return "back";
	}
	
	private MemberService memberService;
	private PrivService privService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public PrivService getPrivService() {
		return privService;
	}

	public void setPrivService(PrivService privService) {
		this.privService = privService;
	}

	private String account;
	private String userpwd;
	private String oldpwd;
	private String newpwd;
	private String textcode;
	private String comparepwd;
	private String dataSource;
	private String phone;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public String getTextcode() {
		return textcode;
	}

	public void setTextcode(String textcode) {
		this.textcode = textcode;
	}

	public String getComparepwd() {
		return comparepwd;
	}

	public void setComparepwd(String comparepwd) {
		this.comparepwd = comparepwd;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public MemberService getMemberService() {
		return memberService;
	}

}
