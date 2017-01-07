/**
 * Created by zhu_jq on 2017/1/4.
 */
window.onload = function(){
    $('.prevfun').click(function(){
        console.log(111);
    })

}

function GetJsonData() {
	var accout = $('#username').val();
    var userpwd = $('#pwdIn').val();

    var json = {
		"account":accout,
        "userpwd":userpwd
    };
    return json;
}

function signin(){

    //验证
    $.ajax({
        type: "POST",
        url: "system!afterLogin.action",
        data: GetJsonData(),
        success: function (message) {
            if (message > 0) {
                console.log("请求已提交！");
            }
        },
        error: function (message) {
            console.log("提交数据失败！" +　JSON.stringify(message));
        }
    });
    
}

function fBackToSignin(){
    window.location.href = 'system!login';
}

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
function fToNextStep(i){
    $('.resetStep').addClass('chatHide');
    $('[step=resetStep-'+i+']').closest('.resetStep').removeClass('chatHide');
}
