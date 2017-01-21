/**
 * Created by gao_yn on 2017/1/9.
 */
$(document).ready(function(){
    var groupTimer=null,groupTimer1 = null;
   $('#perInfo').on('click','li',function(){
        $('#perInfo li').removeClass('active');
       $(this).addClass('active');
       $('#infoDetailsBox>div').addClass('chatHide');
       var sType=$(this).attr('data-type');
       switch(sType){
           case 'd':
               //$('.infoDetailsBox').find('.infoDetails-data').removeClass('chatHide');
               break;
           case 'r':
               var sTargettype=$('#perContainer').attr('targettype');
               var sTargetid=$('#perContainer').attr('targetid');
               historyMsg(sTargettype,sTargetid,0,20);
               break;
           case 'f':
               break;
       }
       $('#infoDetailsBox>div').eq($(this).index()).removeClass('chatHide');
   });
    //点击侧边栏
    $('#perContainer').on('click','.messageRecord .mr-record',function(){
        if($('#perContainer').hasClass('mesContainer-translateL')){
            $('#perContainer').removeClass('mesContainer-translateL');
            $(this).removeClass('active');
            $('#personalData').addClass('chatHide');
        }else{
            $('#perContainer').addClass('mesContainer-translateL');
            $(this).addClass('active');
            $('#personalData').removeClass('chatHide');
        }
    });
//    后台管理
    fPersonalSet();
    $('#backstageMgId').on('click','li',function(){
        $('#backstageMgId li').removeClass('active');
        $(this).addClass('active');
        $('.perSetBox').addClass('chatHide');
        $('.perSetBox').eq($(this).index()).removeClass('chatHide');
    });
    $('#chatBox').on('click','#changeHeadImgId',function(){
        $('.bMgMask').removeClass('chatHide');
        $('#crop-avatar').removeClass('chatHide');
        var sImgsrc=$('.perSetBox-rightCont img').attr('src');
        $('#crop-avatar .avatar-view img').attr('src',sImgsrc);
        $('.avatar-preview img').attr('src',sImgsrc);
       getHeadImgList();
    });
    //首页
    $('#infoDetailsBox').on('click','.infoDet-pageQuery i',function(){
        var sTargettype=$('#perContainer').attr('targettype');
        var sTargetid=$('#perContainer').attr('targetid');
        var timestrap;
        if($(this).hasClass('infoDet-pageQuery')){
            //timestrap=0;
        }else if($(this).hasClass('infoDet-prePage')){
            timestrap=null;
            $('.infoDet-nextPage').addClass('allowClick');
            $('.infoDet-pageQuery').addClass('allowClick');

        }else if($(this).hasClass('infoDet-firstPage')){
            $('.infoDet-prePage').removeClass('allowClick');
            $('.infoDet-firstPage').removeClass('allowClick');
        }else if($(this).hasClass('infoDet-nextPage')){
            //timestrap=null;

            //timestrap=JSON.parse($('.infoDet-contentDet').find('li').last().attr('data-time'));
        }
        if($(this).hasClass('allowClick')){
            historyMsg(sTargettype,sTargetid,timestrap,20,$(this));
        }
    });
    //$('#infoDetailsBox').on('click','.infoDet-prePage',function(){
    //    var sTargettype=$('#perContainer').attr('targettype');
    //    var sTargetid=$('#perContainer').attr('targetid');
    //    historyMsg(sTargettype,sTargetid,0,20);
    //});
    //$('#infoDetailsBox').on('click','.infoDet-firstPage',function(){
    //    var sTargettype=$('#perContainer').attr('targettype');
    //    var sTargetid=$('#perContainer').attr('targetid');
    //    historyMsg(sTargettype,sTargetid,0,20);
    //});
    //$('#infoDetailsBox').on('click','.infoDet-prePage',function(){
    //    var sTargettype=$('#perContainer').attr('targettype');
    //    var sTargetid=$('#perContainer').attr('targetid');
    //    historyMsg(sTargettype,sTargetid,0,20);
    //});
    //群组悬停
    $('.groupChatList').delegate('li','mouseenter',function(e){
        var _this = $(this);
        var sId=$(this).attr('targetid');
        groupTimer=setTimeout(function(){
            var data = '';
            var pos = {};
            pos.top = e.clientY;
            pos.left = e.clientX;
            var account = _this.attr('account');
            var datas = localStorage.getItem('groupInfo');
            var data = JSON.parse(datas);
            var aText=data.text;
            //console.log(data);
            //console.log('----------');
            for(var i = 0;i<aText.length;i++){
                //console.log(account,data[i].account);
                if(aText[i].id==sId){
                    showGroupMemberInfo(aText[i],pos);
                }
            }
        },1000);
    });
    $('.groupChatList').delegate('li','mouseleave',function(e){
        clearTimeout(groupTimer);
        groupTimer1 = setTimeout(function(){
            $('.groupDataBox').remove();
        },1000)
    });
    $('body').delegate('.groupDataBox','mouseenter',function(){
        clearTimeout(groupTimer1);
    })
    $('body').delegate('.groupDataBox','mouseleave',function(){
        $('.groupDataBox').remove();
    })
    /*群组打开右边栏*/
    $('#groupContainer').on('click','.messageRecord .mr-record',function(){
        if($('#groupContainer').hasClass('mesContainer-translateL')){
            $('#groupContainer').removeClass('mesContainer-translateL');
            $(this).removeClass('active');
            $('#groupData').addClass('chatHide');
        }else{
            $('#groupContainer').addClass('mesContainer-translateL');
            $(this).addClass('active');
            $('#groupData').removeClass('chatHide');
        }
    });
    /*点击群组右边选项卡*/
    $('#groupData').on('click','.infoDetails li',function(){
        $('#groupData .infoDetails li').removeClass('active');
        $(this).addClass('active');
        $('#groupData .infoDetailsBox>div').addClass('chatHide');
        var sType=$(this).attr('data-type');
        switch(sType){
            case 'd':
                //$('.infoDetailsBox').find('.infoDetails-data').removeClass('chatHide');
                break;
            case 'r':
                //var sTargettype=$('#perContainer').attr('targettype');
                //var sTargetid=$('#perContainer').attr('targetid');
                //historyMsg(sTargettype,sTargetid,0,20);
                break;
            case 'f':
                break;
        }
        $('#groupData .infoDetailsBox>div').eq($(this).index()).removeClass('chatHide');
    });
    $('#chatBox').on('keyup change','#cp-newPasswordId',function(){
        checklevel(this.value)
    });
    $('#chatBox').on('blur','#oldPassword',function(){
        if(!editOldPassword(hex_md5(this.value))){
            $('.oldPassworderror').html('原始密码错误');
        }else{
            $('.oldPassworderror').html('');
        }
    });
    /*修改密码保存*/
    $('#systemSet-keep').click(function(){
        if(!editOldPassword(hex_md5($('#oldPassword').val()))){
            $('.oldPassworderror').html('原始密码错误');
            return;
        }else{
            $('.oldPassworderror').html('');
            if($('#cp-newPasswordId').val()==''){
                return;
            }
           var sNewPw=hex_md5($('#cp-newPasswordId').val());
            var sComPd=hex_md5($('#comparepwd').val());
            if(sNewPw == sComPd){
                keerNewPw(hex_md5($('#oldPassword').val()),sNewPw,sComPd);
                $('.retMewPw').html('');
            }else{
                $('.retMewPw').html('两次输入密码不一致');
            }
        }

    });
    /*系统提示音*/
    $('#chatBox').on('click','#systemSet .systemVoiceBtn i',function(){
        var status=parseInt($(this).attr('data-state'));//0 代表关闭  1代表开启
        var eParent=$(this).parent();
        switch(status){
            case 0:
                eParent.addClass('active');
                break;
            case 1:
                eParent.removeClass('active');
                break;
        }
    });
    $('#chatBox').on('click','#systemSet .systemSet-keep',function(){
       // var status=parseInt($(this).attr('data-state'));//0 代表关闭  1代表开启
        var status;
        var eVoice=$('#chatBox #systemSet .systemVoiceBtn');
        if(eVoice.hasClass('active')){
            status=0;
        }else{
            status=1;
        }
        systemBeep(status);
    });
    $('#groupMap').on('click','.messageRecord b',function(e){
        var targetID = $(e.target).closest('.groupMap').attr('targetid');
        var targeType = $(e.target).parents('.groupMap').attr('targettype');
        var grounpName = $(e.target).prev('span').html();
        switch(targeType){
            case 'GROUP':
                conversationGroup(targetID,targeType,grounpName);
                $('.orgNavClick').addClass('chatHide');
                $('.mesContainerGroup').removeClass('chatHide');
                break;
            case 'PRIVATE':
                conversationSelf(targetID,targeType);
                $('.orgNavClick').addClass('chatHide');
                $('.mesContainerSelf').removeClass('chatHide');
                break;
        }
    });
        $('#avatarInput').change(function(e){
            var file=e.target.files || e.dataTransfer.files;
            if(file){
                $('.bMg-cropImgSet').addClass('chatHide');
                $('.bMg-cropImgBox').removeClass('chatHide');
                $('.bMg-gravityImg').addClass('active');
                $('.bMg-confirm').removeClass('chatHide');
                $('.bMg-preserve').addClass('chatHide');
            }
        });
    $('#crop-avatar').on('click','.bMg-preserve .bMg-keepImg',function(){
        var sData=window.localStorage.getItem("datas");
        var oData= JSON.parse(sData);
        var sId=oData.text.id;
        var picname;
        var nDelImg;
        $('.bMg-cropImgSet .bMg-imgList li').each(function(index){
            if($(this).hasClass('active')){
                picname=$(this).attr('data-name');
                nDelImg=index;
            }
        });
        sendAjax('upload!secUserLogos',{userid:sId,picname:picname},function(data){
            var oDatas=JSON.parse(data);
            if(oDatas.code==1){
                $('#personSettingId .perSetBox-head').attr('src','upload/images/'+picname);
                $('.bMgMask').addClass('chatHide');
                $('#crop-avatar').addClass('chatHide');

            }
        });
    });
    $('#crop-avatar').on('click','.bMg-confirm .bMg-cancel',function(){
        $('.bMg-cropImgSet').removeClass('chatHide');
        $('.bMg-cropImgBox').addClass('chatHide');
        $('.bMg-gravityImg').removeClass('active');
        $('.bMg-confirm').addClass('chatHide');
        $('.bMg-preserve').removeClass('chatHide');
    });
    $('#crop-avatar').on('click','.bMg-preserve .bMg-cancel,#bMg-closeBtn',function(){
        $('.bMgMask').addClass('chatHide');
        $('#crop-avatar').addClass('chatHide');
    });
    $('#crop-avatar').on('click','.bMg-gravityImg .bMg-delImg',function(){
        var picname;
        var nDelImg;
        $('.bMg-cropImgSet .bMg-imgList li').each(function(index){
            if($(this).hasClass('active')){
                picname=$(this).attr('data-name');
                nDelImg=index;
            }
        });
        var sData=window.localStorage.getItem("datas");
        var oData= JSON.parse(sData);
        var sId=oData.text.id;
        sendAjax('upload!delUserLogos',{userid:sId,picname:picname},function(data){
            var oDatas=JSON.parse(data);
            if(oDatas.code==1){
                $('.bMg-cropImgSet .bMg-imgList li').eq(nDelImg).remove();
            }
        });
    });
    $('#crop-avatar').on('click','.bMg-cropImgSet .bMg-imgList li',function(){
        $('.bMg-cropImgSet .bMg-imgList li').removeClass('active');
        $(this).addClass('active');
    });
    //getGroupMembersList(1);
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
    var sHeaderImg=oData.text.logo|| '/sealtalk/page/web/css/img/PersonImg.png';//头像
    var sHtml='<h3 class="perSetBox-title">个人设置</h3>\
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
    </div>';
    $('#chatBox #personSettingId').empty();
    $('#chatBox #personSettingId').append(sHtml);
}
function showGroupMemberInfo(oGroupInfo,pos){
    var sName=oGroupInfo.name || '';//群名称
    var sCreatorId=oGroupInfo.creatorId;//群创建者id
    var sCreatedate=subTimer(oGroupInfo.createdate);//创建时间
    var oCreator=findMemberInList(sCreatorId);
    var sImg=oCreator.logo || '/sealtalk/page/web/css/img/PersonImg.png';
    //console.log(findMemberInList(sCreatorId));
    //var aCreatedate=sCreatedate.join('-');
    var sHTML ='<div class="groupDataBox" style="left:'+pos.left+'px;top:'+pos.top+'px">\
        <ul>\
        <li>\
        <span>群组名称:</span>\
    <b>'+sName+'</b>\
    </li>\
    <li>\
    <span>创建时间:</span>\
    <b>'+sCreatedate+'</b>\
    </li>\
    <li>\
    <span>群主/管理员:</span>\
    <i>\
    <img src="upload/images/'+sImg+'">\
    </i>\
    </li>\
    </ul>\
    </div>';
    $('body').append($(sHTML));
}
function subTimer(string){
    var y=string.substring(0,4);
    var m=string.substring(4,6);
    var d=string.substring(6,8);
    return y+'-'+m+'-'+d;
}
function checkstr(str)
{
    if (str >= 48 && str <= 57)//数字
    {
        return 1;

    } else if (str >= 65 && str <= 90)//大写字母
    {
        return 2;
    } else if (str >= 97 && str <= 122)//小写字母
    {
        return 3;
    }else//特殊字符
    {
        return 4;
    }
}
function checkl(string)
{
    n = false;
    s = false;
    t = false;
    l_num = 0;
    if(string.length <= 0){
        l_num = 0;
    }
    if (string.length < 6&& string.length > 0)
    {
        l_num = 1;
    } else {
        for (i = 0; i < string.length; i++)
        {
            asc= checkstr(string.charCodeAt(i));
            if (asc == 1 && n == false)
            {
                l_num += 1;
                n= true;
            }
            if ((asc == 2 || asc == 3) && s == false)
            {
                l_num += 1;
                s= true;
            }
            if (asc == 4 && t == false)
            {
                l_num += 1;
                t= true;
            }
        }

    }
    return l_num;
}
function checklevel(psw)
{
    color= "#ededed";

    color_l= "#ff0000";

    color_m= "#ff9900";

    color_h= "#33cc00";
    if (psw == null || psw == '')
    {
        lcor= color;

        mcor= color;

        hcor= color;
    } else
    {
        thelev= checkl(psw);
        switch (thelev) {
            case 0:
                lcor= hcor= mcor= color;
                break;
            case 1:
                lcor= color_l;
                hcor= mcor= color;
                break;
            case 2:
                mcor= lcor= color_m;
                hcor= color;
                break;
            case 3:
                hcor= mcor= lcor= color_h;
                break;
            default:
                lcor= mcor= hcor= color;
        }
    }

    document.getElementById("strength_L").style.background= lcor;

    document.getElementById("strength_M").style.background= mcor;

    document.getElementById("strength_H").style.background= hcor;

}
function editOldPassword(sNewPw){
    var sAccount=localStorage.getItem('account');
    var oAccount=JSON.parse(sAccount);
    var sPassword=oAccount.userpwd;
    if(sNewPw == sPassword){
        return true;
    }else{
        return false;
    }
}
function keerNewPw(oldpwd,newPw,comparepwd){
    var sAccount=localStorage.getItem('account');
    var oAccount=JSON.parse(sAccount);
    var sPassword=oAccount.userpwd;
    var sAccNum=oAccount.account;
    sendAjax('system!newPassword',{account:sAccNum,oldpwd:oldpwd,newpwd:newPw,comparepwd:comparepwd},function(data){
        console.log(JSON.parse(data));
        var oData=JSON.parse(data);
        if(oData.code==1){
            oAccount.userpwd=newPw;
            window.localStorage.account=JSON.stringify(oAccount);
        }
    });
}
/*系统提示音*/
function systemBeep(status){
    sendAjax('function!setSysTipVoice',{status:status},function(data){
        var oData=JSON.parse(data);
        var eParent=$('#chatBox #systemSet .systemVoiceBtn');
        if(oData.code==1){
            globalVar.SYSTEMSOUND=status;
          /*  switch(status){
                case 0:
                    eParent.addClass('active');
                    break;
                case 1:
                    eParent.removeClass('active');
                    break;
            }*/
        }
    });
}
function getHeadImgList(){
    var sData=window.localStorage.getItem("datas");
    var oData= JSON.parse(sData);
    var sId=oData.text.id;
    sendAjax('upload!getUserLogos',{userid:sId},function(data){
        var oDatas=JSON.parse(data);
        var aImgList=oDatas.text;
        var sDom='';
        if(oDatas.code==1){
           if(aImgList.length>0){
               for(var i=0;i<aImgList.length;i++){
                   var sImg=aImgList[i];
                   sDom+='<li data-name="'+sImg+'">\
                   <img src="upload/images/'+sImg+'"/>\
                   </li>';
               }
               $('.bMg-cropImgSet .bMg-imgList').empty();
               $('.bMg-cropImgSet .bMg-imgList').append(sDom);
           }
        }
    });
}