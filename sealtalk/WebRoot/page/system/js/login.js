
  function login(){
		var loginForm = document.getElementById("loginForm");
		var um_name  = loginForm.um_name.value;
		var um_pwd  = loginForm.um_pwd.value;
    if (um_name.length <= 0) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>请您输入账号!</font>"
  		loginForm.um_name.focus();
  		return false;
  	} else if (!/^[a-zA-Z0-9]+$/.test(um_name)) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>账号只能输入英文字母或数字!</font>"
  		loginForm.um_name.focus();
  		return false;
  	} else if (um_name.length > 30) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>账号最大长度为30个英文字母或数字!</font>"
  		loginForm.um_name.focus();
  		return false;
  	}
  	if (um_pwd.length <= 0) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>请您输入密码!</font>"
  		loginForm.um_pwd.focus();
  		return false;
  	} else if (um_pwd.length > 30) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>密码最大长度为30个字符!</font>"
  		loginForm.um_pwd.focus();
  		return false;
  	}
    loginForm.submit();  
	}