
  function login(){
		var loginForm = document.getElementById("loginForm");
		var username  = loginForm.username.value;
		var userpwd  = loginForm.userpwd.value;
    if (username.length <= 0) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>请您输入账号!</font>"
  		loginForm.username.focus();
  		return false;
  	} else if (!/^[a-zA-Z0-9]+$/.test(username)) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>账号只能输入英文字母或数字!</font>"
  		loginForm.username.focus();
  		return false;
  	} else if (username.length > 30) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>账号最大长度为30个英文字母或数字!</font>"
  		loginForm.username.focus();
  		return false;
  	}
  	if (userpwd.length <= 0) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>请您输入密码!</font>"
  		loginForm.userpwd.focus();
  		return false;
  	} else if (userpwd.length > 30) {
  		document.getElementById("warning").style.display = "block";
  		document.getElementById("warning").innerHTML = "<font color='#d32838'>密码最大长度为30个字符!</font>"
  		loginForm.userpwd.focus();
  		return false;
  	}
    loginForm.submit();  
	}