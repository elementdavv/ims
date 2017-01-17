package com.sealtalk.utils;

import com.bcloud.msg.http.HttpSender;

/**
 * 短信验证码发送
 * @author hao_dy
 *
 */
public class TextHttpSender {
	
	private TextHttpSender() {};
	
	private static class Inner {
		private static final TextHttpSender THS = new TextHttpSender();
	}
	
	public static TextHttpSender getInstance() {
		return Inner.THS;
	}
	
	
	/**
	 * 单发
	 * @param uri应用地址
	 * @param account账号
	 * @param pswd密码
	 * @param mobiles手机号码，多个号码使用","分割
	 * @param content短信内容
	 * @param needstatus是否需要状态报告，需要true，不需要false
	 * @param product产品ID
	 * @param extrno扩展码
	 * @return
	 */
	public String sendPrivateText(String uri, String account, String pswd, String mobiles, String content, boolean needstatus, String product, String extno) {
		try {
			String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno);
			System.out.println(returnString);
			//TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
		
		return extno;
	}
	
	/**
	 * 群发
	 * @param uri应用地址
	 * @param account账号
	 * @param pswd密码
	 * @param mobiles手机号码，多个号码使用","分割
	 * @param content短信内容
	 * @param needstatus是否需要状态报告，需要true，不需要false
	 * @param product产品ID
	 * @param extrno扩展码
	 * @return
	 */
	public String sendGroupText(String uri, String account, String pswd, String mobiles, String content, boolean needstatus, String product, String extno) {
		try {
			String returnString = HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product, extno);
			System.out.println(returnString);
			//TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			//TODO 处理异常
			e.printStackTrace();
		}
		return extno;
	}
	
}
