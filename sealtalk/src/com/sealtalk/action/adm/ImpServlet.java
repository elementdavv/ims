package com.sealtalk.action.adm;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class ImpServlet extends HttpServlet {

	/**
	 * 文件上传
	 * by alopex
	 * 2017.2.2
	 */
	private static final long serialVersionUID = 4368072561206144721L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		Part p = req.getPart("impfile");
		String name = getFileName(p);
		String path = getServletContext().getRealPath("./upload/");
		
		p.write(path + name);
		
		res.setContentType("text/plain;charset=utf-8");
		PrintWriter out = res.getWriter();
		
		out.println(name);
	}

	private String getFileName(Part part) {

		for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
}