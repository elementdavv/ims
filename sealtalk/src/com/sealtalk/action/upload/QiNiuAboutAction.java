package com.sealtalk.action.upload;

import javax.servlet.ServletException;

import com.googlecode.sslplugin.annotation.Secured;
import com.sealtalk.common.BaseAction;
import com.sealtalk.service.upload.UploadService;

@Secured
public class QiNiuAboutAction extends BaseAction {

	private static final long serialVersionUID = -853201080265155177L;
	
	
	public String getUploadQiniuToken() throws ServletException {
		String result = null;
		
		try {
			result = uploadService.getUploadQiniuToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		returnToClient(result);
		return "text";
	}
	
	private UploadService uploadService;

	public UploadService getUploadService() {
		return uploadService;
	}

	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}
	
	
	
}
