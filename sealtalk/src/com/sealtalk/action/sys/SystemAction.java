package com.sealtalk.action.sys;

import java.io.IOException;
import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
import com.sealtalk.common.Constants;
import com.sealtalk.common.Tips;
import com.sealtalk.model.SessionUser;
import com.sealtalk.model.TMember;
import com.sealtalk.service.member.MemberService;
import com.sealtalk.utils.JSONUtils;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.RongCloudUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TimeGenerator;

/**
 * 系统相关
 * @since jdk1.7
 * @author hao_dy
 *
 */
@Secured
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
	 * 登陆验证
	 * @return
	 * @throws Exception
	 */
	public String afterLogin() throws IOException, ServletException {
		JSONObject result = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(account)) {
			result.put("code", 0);
			result.put("text", Tips.NULLUSER.getText());
			//request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.NULLUSER.getName());
			//return "loginPage";
			returnToClient(result.toString());
			return "text";
		}
		
		TMember member = memberService.searchSigleUser(account, userpwd);
		
		if(member == null) {
			result.put("code", 0);
			result.put("text", Tips.ERRORUSERORPWD.getText());
			//request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.ERRORUSERORPWD.getName());
			//return "loginPage";
			returnToClient(result.toString());
			return "text";
		}
		
		logger.debug("That logining account is " + account);
		
		String userId = "" + member.getId();
		String name = member.getFullname();
		String token = null;
		String tokenMaxAge = PropertiesUtils.getStringByKey("db.tokenMaxAge");
		
		long tokenMaxAgeLong = 0;
		long firstTokenDate = member.getCreatetokendate();
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
		
		SessionUser su = new SessionUser();
		
		if (member != null) {
			su.setAccount(member.getAccount());
			su.setFullname(member.getFullname());
			su.setToken(token);
		}
		
		//2.相关权限信息
		//code...
		
		setSessionUser(su);
		
		JSONObject text = JSONUtils.getInstance().modelToJSONObj(member);
		
		text.remove("password");
		text.put("token", token);
		
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
		//中转代码
		//.....
		
		JSONObject text = new JSONObject();
		
		text.put("code", 1);
		text.put("text", Tips.SENDTEXTS.getText());
		
		returnToClient(text.toString());
		
		return "text";
	}

	
	/**
	 * 验证短信(暂时没有短信平台，模拟,仅web端使用)
	 * @return
	 */
	public String testText() throws ServletException {
		//String phone = request.getParameter("phoneNum");
		//String textCode = request.getParameter("textcode");
		
		JSONObject text = new JSONObject();
		
		if (textcode == null || "".equals(textcode)) {
			text.put("code", -1);
			text.put("text", Tips.NULLTEXTS.getText());
		} else if (!textcode.equals("9999")) {
			text.put("code", 0);
			text.put("text", Tips.ERRORTEXTS.getText());
		} else {
			text.put("code", 1);
			text.put("text", Tips.TRUETEXTS.getText());
		}
		
		returnToClient(text.toString());
		
		return "text";
	}
	
	/**
	 * 修改新密码
	 * @return
	 */
	public String newPassword() throws ServletException {
		/*String account = request.getParameter("account");
		String newPwd = request.getParameter("newpwd");
		String comparePwd = request.getParameter("comparepwd");
		String textCode = request.getParameter("textcode");*/
		
		JSONObject text = new JSONObject();
		
		if (account == null || "".equals(account)) {
			text.put("code", "0");
			text.put("text", Tips.NULLUSER.getText());
			returnToClient(text.toString());
			return "textText";
		}
		
		boolean status = true;
		
		if (!StringUtils.getInstance().isBlank(oldpwd)) {
			boolean validOldPwd = memberService.valideOldPwd(account, oldpwd);
			if (!validOldPwd) {
				text.put("code", -1);
				text.put("text", Tips.WRONGOLDPWD.getText());
				status = false;
			}
		} else {
			if (!StringUtils.getInstance().isBlank(textcode)) {
				if (textcode == null || "".equals(textcode)) {
					text.put("code", -1);
					text.put("text", Tips.NULLTEXTS.getText());
					status = false;
				} else if (!textcode.equals("9999")) {
					text.put("code", 0);
					text.put("text", Tips.ERRORTEXTS.getText());
					status = false;
				} else {
					text.put("code", 1);
					text.put("text", Tips.TRUETEXTS.getText());
				}
			}
		}
		
		if (status) {
			if (!newpwd.equals(comparepwd)) {
				request.setAttribute(LOGIN_ERROR_MESSAGE, Tips.FALSECOMPAREPWD.getText());
				return "fogetpwd";
			}
			
			boolean updateState = memberService.updateUserPwd(account, newpwd);
			
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
	
	private MemberService memberService;
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	private String account;
	private String userpwd;
	private String oldpwd;
	private String newpwd;
	private String textcode;
	private String comparepwd;
	private String dataSource;
	private String phone;

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
