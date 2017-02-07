/**
 * Created by zhu_jq on 2017/1/4.
 */
window.onload = function(){

    //忘记密码
    $('#forgetPwd').click(function(){
        if (window.Electron) {
            var curWindow = window.Electron.remote.shell.openExternal('http://localhost:8080/sealtalk/system!fogetPassword');
            console.log(curWindow);
        }
    })


    //点击发送验证码
    $('#pwdIn').unbind('focus');
    $('#pwdIn').focus(function(){
        $(this).keypress(function(event) {
            if (event.which == 13) {
                signin();
            }
        })
    })

    $('.SendCheakCode').click(function(){
        //fSendCheakCode();
        var phoneNum = $('#phoneNum').val();
        //var data = JSON.stringify({phoneNum:phoneNum});
        sendAjax('system!requestText',{phone:phoneNum},function(){
            console.log('验证码发送成功')
        })
    })
}


/*
* 通用AJAX
*/
function sendAjax(url,data,callback){
    $.ajax({
        type: "POST",
        url: url,
        data:data,
        success: function(data){
            callback && callback(data);
        }
    })
}


/*
*
* 跳转到第二部
* */
function fToStep2(dom){
    var phoneNum = $('#phoneNum').val();
    var textcode = $('#checkCode').val();
    //var data = JSON.stringify({'phone':phoneNum,textcode:textcode});
    $('.sealtalk-forgetpassword').attr('account',phoneNum);
    sendAjax('system!testText',{phone:phoneNum,textcode:textcode},function(data){
        if(data){
            var datas = JSON.parse(data);
            if(datas.code=='1'){
                fToNext(dom)
            }
        }
    });
}
function fToStep3(dom){
    var newpwd = $('#newpassword').val();
    var comparepwd = $('#newpasswordCertain').val();
    var newPWD = hex_md5(newpwd);
    var comparePWD = hex_md5(comparepwd);
    if(newPWD!=comparePWD){
        alert('两次密码不一致')
    }else{
        var account = $('.sealtalk-forgetpassword').attr('account');
        sendAjax('system!newPassword',{newpwd:newPWD,comparepwd:comparePWD,account:account},function(data){
            if(data){
                var datas = JSON.parse(data);
                if(datas.code=='1'){
                    fToNext(dom)
                }
            }
        });
    }



}


/*
*
* 发送验证码
*
*/
//function fSendCheakCode(){
//    var phoneNum = $('#username').val();
//    var data = JSON.stringify({'phoneNum':phoneNum})
//    sendAjax('system!requestText',data);
//}

/*
*
* 登录
*
*/
function signin(){
    var accout = $('#username').val();
    var userpwd = hex_md5($('#pwdIn').val());
    var data = {account:accout,userpwd:userpwd};
    //验证
    sendAjax('system!afterLogin',data,function(datas){
        window.localStorage.datas=datas;
        var datas = JSON.parse(datas);
        if(datas &&	datas.code == 1){
            data.token = datas.text.token;
            window.localStorage.account=JSON.stringify(data);
        }
        //event.preventDefault()
        //window.Electron.remote.BrowserWindow.loadURL('http://localhost:8080/sealtalk/page/web/main.jsp')
        //window.Electron.configInfo.loadURL('http://localhost:8080/sealtalk/page/web/main.jsp');
        //window.open('http://localhost:8080/sealtalk/page/web/main.jsp','_parent');
        //if(window.Electron){
        //    window.Electron.remote.getCurrentWindow().loadUrl('http://localhost:8080/sealtalk/page/web/main.jsp');
        //}
        //shell.openExternal('http://localhost:8080/sealtalk/page/web/main.jsp');
        //if(2!=1){
        //    event.preventDefault();
        //    window.location.href="http://www.baidu.com";
        //    window.event.returnValue=false;
        //}

        window.location.href = 'system!login';
        //window.open('http://localhost:8080/sealtalk/page/web/main.jsp','_parent');
        //console.log(window.location.href)
        //window.location.href = 'http://localhost:8080/sealtalk/page/web/main.jsp';
        //console.log(window.location.href);
        //document.URL=location.href;
        //location.replace('main.jsp');
        //console.log(window.location.href);
        //var pageURL = window.location.href.split('?')[0];
        //remote.getCurrentWindow().loadUrl('http://localhost:8080/sealtalk/page/web/main.jsp')
        //window.location.href = pageURL;
        //window.navigate("system!login");
        //window.location.reload(true);
        //history.go(0)
        //window.location=window.location;
    },function(){
        new Window().alert({
            title   : '',
            content : '用户名或密码输入错误！',
            hasCloseBtn : false,
            hasImg : true,
            textForSureBtn : false,
            textForcancleBtn : false,
            autoHide:true
        });
    });
}

/*
*
*跳转到登录页面
*
*/
function fBackToSignin(){
    window.location.href = 'system!login';
}

function addMD5(){
    //alert(11);
    var userpwd = hex_md5($('#pwdIn').val());
    $('#pwdIn').val(userpwd);
}


/*
*
*跳转到下一页处理
*
*/
function fToNext(dom){
    console.log(dom);
    var sStep = $(dom).closest('.form-inline').attr('step');
    var aStep = sStep.split('-');
    var i = aStep[1]?aStep[1]:undefined;
    if(i){
        i++;
        i>=4?fBackToSignin():fToNextStep(i);
    }
}

/*
 *
 *跳转到上一页处理
 *
 */
function fToPrev(dom){
    console.log(dom);
    var sStep = $(dom).closest('.form-inline').attr('step');
    var aStep = sStep.split('-');
    var i = aStep[1]?aStep[1]:undefined;
    if(i){
        i--;
        i<=0?fBackToSignin():fToNextStep(i);
    }
}

/*
 *
 *跳转
 *
 */
function fToNextStep(i){
    $('.resetStep').addClass('chatHide');
    $('[step=resetStep-'+i+']').closest('.resetStep').removeClass('chatHide');
}
