/**
 * Created by zhu_jq on 2017/1/4.
 */
window.onload = function(){

    //点击发送验证码
    $('.SendCheakCode').click(function(){
        fSendCheakCode();
        var phoneNum = $('#phoneNum').val();
        var data = JSON.stringify({phoneNum:phoneNum});
        sendAjax('system!requestText',data,function(){
            console.log('验证码发送成功');

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
    var phoneNum = $('#username').val();
    var textcode = $('#checkCode').val();
    var data = JSON.stringify({'phone':phoneNum,textcode:textcode});
    $('.sealtalk-forgetpassword').attr('account',phoneNum);
    sendAjax('system!testText',data,function(){
        fToNext(dom)
    });
}
function fToStep3(dom){
    var newpwd = $('#newpassword').val();
    var comparepwd = $('#newpasswordCertain').val();
    var account = $('.sealtalk-forgetpassword').attr('account');
    var data = JSON.stringify({'newpwd':newpwd,comparepwd:comparepwd})
    sendAjax('system!newPassword',data,function(){
        fToNext(dom)
    });
}


/*
*
* 发送验证码
*
*/
function fSendCheakCode(){
    var phoneNum = $('#username').val();
    var data = JSON.stringify({'phoneNum':phoneNum})
    sendAjax('system!requestText',data);
}

/*
*
* 登录
*
*/
function signin(){
    var accout = $('#username').val();
    var userpwd = hex_md5($('#pwdIn').val());
    var data = {'account':accout,'userpwd':userpwd};
    //验证
    sendAjax('system!afterLogin',data,function(data){
        var datas = JSON.parse(data);
        if(datas &&	datas.code == 1){
           window.location.href = 'page/web/main.jsp';
        }
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
