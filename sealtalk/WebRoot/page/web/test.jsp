<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/uploadImg/style.css"/>
<link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/bootstrap.min.css"/>
<link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/cropper.min.css"/>
</head>
<body>
	<h1>测试获取地图位置</h1>
	<form action="<%=request.getContextPath() %>/map!getPosition" method="post">
		userid: <input type="text" name="userid" />
		targetid: <input type="text" name="targetid" />
		type: <input type="text" name="type" />
		<input type="submit" value="test" />
	</form>
	<h1>测试上传非裁剪头像</h1>
	<form action="<%=request.getContextPath() %>/upload!uploadUserLogoNotCut" enctype="multipart/form-data" method="post">
		userId: <input name="userid" type="text" value="1" />
		Image: <input name="file" type="file" accept="image/gif, image/jpeg"/>
 		<input name="upload" type="submit" value="上传" />
	</form>
	<h1>测试app免登陆接</h1>
	<form action="<%=request.getContextPath() %>/system!freeLandingForApp" method="post">
		Token: <input type="text" name="token" value="" />
		<input type="submit" value="submit" />
	</form>
</body>
<html/>