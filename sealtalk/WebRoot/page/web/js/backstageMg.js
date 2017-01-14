/**
 * Created by gao_yn on 2017/1/9.
 */
$(document).ready(function(){
   $('#perInfo').on('click','li',function(){
        $('#perInfo li').removeClass('active');
       $(this).addClass('active');
       $('#infoDetailsBox>div').removeClass('active');
       var sType=$(this).attr('data-type');
       switch(sType){
           case 'd':
               break;
           case 'r':
               var sTargettype=$('#perContainer').attr('targettype');
               var sTargetid=$('#perContainer').attr('targetid');
               historyMsg(sTargettype,sTargetid);
               break;
           case 'f':
               break;
       }
       $('#infoDetailsBox>div').eq($(this).index()).addClass('active');
   });
    //点击侧边栏
    $('#chatBox').on('click','#mr-record',function(){
        if($('#perContainer').hasClass('mesContainer-translateL')){
            $('#perContainer').removeClass('mesContainer-translateL');
            $(this).addClass('active');
            $('#personalData').addClass('chatHide');
        }else{
            $('#perContainer').addClass('mesContainer-translateL');
            $(this).removeClass('active');
            $('#personalData').removeClass('chatHide');
        }
    });
//    后台管理
    $('#backstageMgId').on('click','li',function(){
        $('#backstageMgId li').removeClass('active');
        $(this).addClass('active');
        $('.perSetBox').addClass('chatHide');
        //$('.perSetBox').eq($(this).index()).removeClass('chatHide');
        if($(this).index()==0){
            fPersonalSet(0);
        }
    });
    $('#chatBox').on('click','#changeHeadImgId',function(){
        $('#iqs_iframe').attr('src','page/web/clipImg.jsp');
        $('#iqs_iframe').removeClass('chatHide');
    });
});
function fPersonalSet(){
   var sData=window.localStorage.getItem("datas");
    var oData= JSON.parse(sData);
    var sName=oData.text.fullname;//姓名
    var sAccountNum=oData.text.account;//成员账号
    var sSex=oData.text.sex;//性别
    var sPosition=oData.text.account;//职位
    var sBranch=oData.text.sex;//部门
    var sEmail=oData.text.email;//邮箱
    var sTelephone=oData.text.telephone;//电话
    var sSign=oData.text.sex;//工作签名
    var sHeaderImg=oData.text.logo|| 'PersonImg.png';//头像
    var sHtml='<div class="perSetBox orgNavClick" >\
        <h3 class="perSetBox-title">个人设置</h3>\
    <div class="perSetBox-content clearfix">\
    <div class="perSetBox-leftCont">\
    <ul class="perSetBox-contDetails">\
    <li >\
    <span>成员账号:</span>\
    <p>'+sAccountNum+'</p>\
    </li>\
    <li>\
    <span>姓名：</span>\
    <p>'+sName+'</p>\
    </li>\
    <li>\
    <span>性别：</span>\
    <p>\
    <select class="perSetBox-selSex">\
    <option value="男">男</option>\
    <option value="女">女</option>\
    </select>\
    </p>\
    </li>\
    <li>\
    <span>职位：</span>\
    <p>'+sPosition+'</p>\
    </li>\
    <li>\
    <span>部门：</span>\
    <p>'+sBranch+'</p>\
    </li>\
    <li>\
    <span>邮箱：</span>\
    <p>\
    <input value="'+sEmail+'" class="perSetBox-editText"/>\
    </p>\
    </li>\
    <li>\
    <span>电话：</span>\
    <p>\
    <input value="'+sTelephone+'" class="perSetBox-editText"/>\
    </p>\
    </li>\
    <li>\
    <span>工作签名：</span>\
    <p>\
    <textarea class="perSetBox-textarea" value="'+sSign+' readonly="readonly">\
    </textarea>\
    </p>\
    </li>\
    </ul>\
    <button class="perSetBox-keep">保存</button>\
    </div>\
    <div class="perSetBox-rightCont">\
    <img src="upload/images/'+sHeaderImg+'" class="perSetBox-head"/>\
    <p class="perSetBox-modifyHead" id="changeHeadImgId">修改头像</p>\
    </div>\
    </div>\
    </div>';
    $('#chatBox').append(sHtml);
}