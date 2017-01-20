package com.sealtalk.action.upload;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.web.multipart.MultipartFile;

import com.sealtalk.common.BaseAction;
import com.sealtalk.utils.ImageUtils;
import com.sealtalk.utils.TimeGenerator;

public class UploadAction extends BaseAction {  
 
	private static final long serialVersionUID = 74195611146343183L;

	public String test() throws ServletException {
		returnToClient("{a:1}");
		return "text";
	}
	
	public String uploadUserLogo(String userId, Integer x, Integer y, Integer width, Integer height,Integer degree, MultipartFile imageFile) 
						throws IOException, ServletException {  
		response.setContentType("text/html;charset=utf-8");
		//获取服务器的实际路径  
        String realPath = request.getSession().getServletContext().getRealPath("/");  
        
        System.out.println("x:"+x+"y:"+y+"width:"+width+"height:"+height+"degree:"+degree);  
        System.out.println(realPath);  
        
        String resourcePath="upload/images";  
      
        if(imageFile!=null){  
             try{  
	            //文件名  
	             String name= imageFile.getOriginalFilename();  
	             
	             int suffixPos = name.lastIndexOf(".");
	             
	             String suffixAfter = name.substring(suffixPos, name.length());
	             
	             name = userId + "-" + TimeGenerator.getInstance().getUnixTime(); 
	             
	             File dir = new File(realPath + resourcePath);  
	             
	             if (!dir.exists()){  
	                 dir.mkdirs();  
	             }  
	             
	             //先把用户上传到原图保存到服务器上  
	             File file=new File(dir, name + suffixAfter);  
	             
	             imageFile.transferTo(file);  
	             
	             if(file.exists()){  
	                 String src = realPath + resourcePath + name;  
	                 String srcImg = src + suffixAfter;
	                 String destImg = "_s" + suffixAfter;
	                 
	                 boolean[] flag=new boolean[6];  
	                 
	                 //旋转后剪裁图片  
	                 flag[0] = ImageUtils.cutAndRotateImage(srcImg, destImg, x, y, width, height, degree); 
	                 
	                 //缩放图片,生成不同大小的图片，应用于不同的大小的头像显示  
	                 flag[1] = ImageUtils.scale2(destImg, src + "_s_200" + suffixAfter, 200, 200, true);  
	                 flag[2] = ImageUtils.scale2(destImg, src+"_s_100" + suffixAfter, 100, 100, true);  
	                 flag[3] = ImageUtils.scale2(destImg, src+"_s_50" + suffixAfter, 50, 50, true);  
	                 flag[4] = ImageUtils.scale2(destImg, src+"_s_30" + suffixAfter, 30, 30, true);  
	                 flag[5] = ImageUtils.scale2(file.getPath(), src + "_200" + suffixAfter, 200, 200, true);  
	                   
	                 if(flag[0]&&flag[1]&&flag[2]&&flag[3]&&flag[4]&&flag[5]){  
	                     //图像处理完成，将数据写入数据库中  
	                	 System.out.println("suc");
	                 }  
	             }  
             }catch (Exception e) {  
                 e.printStackTrace();  
            }  
  
         }  
        return null;  
    }  
}  
