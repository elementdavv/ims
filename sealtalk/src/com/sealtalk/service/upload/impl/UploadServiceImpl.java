package com.sealtalk.service.upload.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.sealtalk.common.Tips;
import com.sealtalk.dao.member.MemberDao;
import com.sealtalk.dao.upload.CutLogoTempDao;
import com.sealtalk.model.TCutLogoTemp;
import com.sealtalk.service.upload.UploadService;
import com.sealtalk.utils.FileUtil;
import com.sealtalk.utils.ImageUtils;
import com.sealtalk.utils.PropertiesUtils;
import com.sealtalk.utils.StringUtils;
import com.sealtalk.utils.TimeGenerator;

public class UploadServiceImpl implements UploadService {

	@Override
	public String cutImage(String userId, String x, String y, String width,
			String height, String angle, File imageFile, String realPath) {

		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(userId) || 
				StringUtils.getInstance().isBlank(x) ||
				StringUtils.getInstance().isBlank(y) ||
				StringUtils.getInstance().isBlank(width) ||
				StringUtils.getInstance().isBlank(height) ||
				StringUtils.getInstance().isBlank(angle) ||
				imageFile == null) {
			jo.put("code", -1);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			String seperate = PropertiesUtils.getStringByKey("dir.seperate");
	        String resourcePath="upload" + seperate +  "images" + seperate;  
	        boolean status = true;

			int userIdInt = StringUtils.getInstance().strToInt(userId);
	        
	        if(imageFile != null){  
	        	 ArrayList<String> names = new ArrayList<String>();
	        	 
	             try{  
		            //文件名  
		             String name= imageFile.getName();  
		             
		             File dir = new File(realPath + resourcePath);  
		             
		             if (!dir.exists()){  
		                 dir.mkdirs();  
		             }  
		             
		             //先把用户上传到原图保存到服务器上  
		             File file = new File(dir, name);  
		             
		             FileUtil.copyFile(imageFile, file);
		             
		             if(file.exists()){  
		            	 String suffix = PropertiesUtils.getStringByKey("upload.suffix");
		                 String size = PropertiesUtils.getStringByKey("upload.size");
		            	 String newName = userId + "-" + TimeGenerator.getInstance().getUnixTime();
		                 String srcImg = realPath + resourcePath + name;
		                 String newFileName = realPath + resourcePath + newName;
		                
		                 String destImg = newFileName + "." + suffix;
		                 String[] sizes = StringUtils.getInstance().stringSplit(size, ",");
		                 
		                 int scaleWidth = StringUtils.getInstance().strToInt(sizes[0]);
		                 int scaleHeight = StringUtils.getInstance().strToInt(sizes[1]);
		                  
		                 String scaleFilename = newFileName + "_" + scaleWidth + "_" + scaleHeight + "." + suffix;
		                 
		                 System.out.println("newFileName: " + newFileName);
		                 
		                 names.add(srcImg);
		                 names.add(destImg);
		                 
		                 int xInt = StringUtils.getInstance().strToInt(StringUtils.getInstance().clearNumPoint(x));
		                 int yInt = StringUtils.getInstance().strToInt(StringUtils.getInstance().clearNumPoint(y));
		                 int widthInt = StringUtils.getInstance().strToInt(StringUtils.getInstance().clearNumPoint(width));
		                 int heightInt = StringUtils.getInstance().strToInt(StringUtils.getInstance().clearNumPoint(height));
		                 int angleInt = StringUtils.getInstance().strToInt(StringUtils.getInstance().clearNumPoint(angle));
		                 
		                 boolean[] flag=new boolean[2];  
		                 
		                 //旋转后剪裁图片  
		                 flag[0] = ImageUtils.cutAndRotateImage(srcImg, destImg, xInt, yInt, widthInt, heightInt, angleInt, suffix); 
		                 
//		                 //缩放图片,生成不同大小的图片，应用于不同的大小的头像显示  
		                 flag[1] = ImageUtils.scale2(destImg, scaleFilename , scaleWidth, scaleHeight, true, suffix);  
		                   
		                 if (flag[0] && flag[1]) {
		                	 List<TCutLogoTemp> cltList = new ArrayList<TCutLogoTemp>();
		                	 
		                	 for(int i = 0; i < names.size(); i++) {
		                		 System.out.println(names.get(i));
		                		FileUtil.deleteFile(names.get(i));
		                	 }
		                	 
		                	 TCutLogoTemp clte = new TCutLogoTemp();
		                	 
		                	 clte.setLogoName(newName + "_" + scaleWidth + "_" + scaleHeight + "." + suffix);
		                	 clte.setUserId(userIdInt);
		                	 
		                	 cutLogoTempDao.saveTempPic(clte);
		                	 
		                	jo.put("code", 1);
		         			jo.put("text", clte.getLogoName());
		                 } else {
		                	 status = false;
		                 }
		             } else {
		            	 status = false;
		             }
		             
		             if (!status) {
		            	jo.put("code", 0);
	         			jo.put("text", Tips.FAIL.getText());
		             }
	             }catch (Exception e) {  
	                 e.printStackTrace();  
	            }  
	        }
         }  
		
		return jo.toString();
	}
	
	@Override
	public String saveSelectedPic(String userId, String picName) {
		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(userId) || StringUtils.getInstance().isBlank(picName)) {
			jo.put("code", -1);
 			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			try {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				TCutLogoTemp cltList = cutLogoTempDao.getTempLogoForIdAndPicName(userIdInt, picName);
				
				if (cltList != null && cltList.getLogoName().equals(picName)) {
					int ret = memberDao.updateUserLogo(userIdInt, picName);
					
					if (ret > 0) {
						jo.put("code", 1);
						jo.put("text", Tips.OK.getText());
					} else {
						jo.put("code", 0);
						jo.put("text", Tips.FAIL.getText());
					}
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.NOLOGOERR.getText());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jo.toString();
	}
	

	@Override
	public String delUserLogos(String userId, String picName) {
		JSONObject jo = new JSONObject();
		if (StringUtils.getInstance().isBlank(userId) || StringUtils.getInstance().isBlank(picName)) {
			jo.put("code", -1);
 			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			try {
				int userIdInt = StringUtils.getInstance().strToInt(userId);
				boolean used = memberDao.isUsedPic(userIdInt, picName);
				
				if (used) {
					jo.put("code", -1);
		 			jo.put("text", Tips.USEDLOGO.getText());
				} else {
					int ret = cutLogoTempDao.delUserLogos(userIdInt, picName);
					
					if (ret > 0) {
						jo.put("code", 1);
						jo.put("text", Tips.OK.getText());
					} else {
						jo.put("code", 0);
						jo.put("text", Tips.FAIL.getText());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jo.toString();
	}
	
	
	@Override
	public String getUserLogos(String userId) {
		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(userId)) {
			jo.put("code", -1);
 			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			try {
				int userIdInt = StringUtils.getInstance().strToInt(userId);

				List<TCutLogoTemp> clTList = cutLogoTempDao.getUserLogos(userIdInt);
				
				if (clTList != null) {
					ArrayList<String> picArr = new ArrayList<String>();
					
					for(int i = 0; i < clTList.size(); i++) {
						picArr.add(clTList.get(i).getLogoName());
					} 
					
					jo.put("code", 1);
					jo.put("text", picArr);
				} else {
					jo.put("code", 0);
					jo.put("text", Tips.NOLOGOERR.getText());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jo.toString();
	}

	@Override
	public String uploadUserLogNotCut(String userId, File imageFile, String realPath) {
		JSONObject jo = new JSONObject();
		
		if (StringUtils.getInstance().isBlank(userId) || 
				imageFile == null) {
			jo.put("code", -1);
			jo.put("text", Tips.WRONGPARAMS.getText());
		} else {
			String seperate = PropertiesUtils.getStringByKey("dir.seperate");
	        String resourcePath="upload" + seperate +  "images" + seperate;  

			int userIdInt = StringUtils.getInstance().strToInt(userId);
	        
	        if(imageFile != null){  
	             try{  
		            //文件名  
		             //String name= imageFile.getName();  
		             
		             File dir = new File(realPath + resourcePath);  
		             
		             if (!dir.exists()){  
		                 dir.mkdirs();  
		             }  
		             
		             String suffix = PropertiesUtils.getStringByKey("upload.suffix");
	            	 String newName = userId + "-" + TimeGenerator.getInstance().getUnixTime() + "." + suffix;
	            	 
		             File file = new File(dir, newName);  
		             
		             FileUtil.copyFile(imageFile, file);
		            	
                	 TCutLogoTemp clte = new TCutLogoTemp();
                	 
                	 clte.setLogoName(newName);
                	 clte.setUserId(userIdInt);
                	 
                	 cutLogoTempDao.saveTempPic(clte);
                	 
                	 this.saveSelectedPic(userId, newName);
                	 
                	jo.put("code", 1);
         			jo.put("text", clte.getLogoName());
	             } catch (Exception e) {  
	            	jo.put("code", 0);
         			jo.put("text", Tips.FAIL.getText());
	                e.printStackTrace();  
	            }  
	        }
         }  
		
		return jo.toString();
	}
	
	private MemberDao memberDao;
	private CutLogoTempDao cutLogoTempDao;

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public CutLogoTempDao getCutLogoTempDao() {
		return cutLogoTempDao;
	}

	public void setCutLogoTempDao(CutLogoTempDao cutLogoTempDao) {
		this.cutLogoTempDao = cutLogoTempDao;
	}

}
