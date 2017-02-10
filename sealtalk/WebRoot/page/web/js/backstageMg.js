/**
 * Created by gao_yn on 2017/1/9.
 */
$(document).ready(function(){
    var groupTimer=null,groupTimer1 = null;
   // var sAccount = localStorage.getItem('account');
   $('#perInfo').on('click','li',function(){
        $('#perInfo li').removeClass('active');
       $(this).addClass('active');
       $('#infoDetailsBox>div').addClass('chatHide');
       var sType=$(this).attr('data-type');
       $('#infoDetailsBox>div').eq($(this).index()).removeClass('chatHide');
       switch(sType){
           case 'd':
               //$('.infoDetailsBox').find('.infoDetails-data').removeClass('chatHide');
               break;
           case 'r':
               var sTargettype=$('#perContainer').attr('targettype');
               var sTargetid=$('#perContainer').attr('targetid');
               var $perEle=$('#infoDetailsBox .infoDet-chatRecord').find('.infoDet-page');
               var oPagetest = new PageObj({divObj:$perEle,pageSize:20,conversationtype:sTargettype,targetId:sTargetid},function(type,list,callback)//声明page1
               {
                   getChatRecord(list,'#infoDetailsBox .infoDet-chatRecord .chatRecordSel');
               });
               break;
           case 'f':
               break;
       }
   });
    //搜索常用人历史记录
    $('#personalData').on('click','.searchHostoryInfo',function(){
        var sTargettype=$('#perContainer').attr('targettype');
        var sTargetid=$('#perContainer').attr('targetid');
        var sVal=$(this).prev().val();
        var oPagetest = new PageObj({divObj:$('.infoDet-chatRecord').find('.infoDet-page'),pageSize:20,searchstr:sVal,conversationtype:sTargettype,targetId:sTargetid},function(type,list,callback)//声明page1
        {
            getChatRecord(list);

        });
        //RongIMLib.RongIMClient.getInstance().searchMessageByContent(RongIMLib.ConversationType[sTargettype],sTargetid,sVal,0,20,1,{
        //    onSuccess:function(data, count){
        //        alert(data,count);
        //        console.log(data);
        //        console.log(count);
        //    },
        //    onError:function(error){
        //    }
        //});
    });
    $('#groupData').on('click','.groupInfo-noChat',function(){
        var groupid=$(this).attr('data-groupid');
        var sChat=$(this).attr('data-chat');
        if(sChat==1){
            new Window().alert({
                title   : '关闭全员禁言',
                content : '确定要关闭全员禁言吗？',
                hasCloseBtn : true,
                hasImg : true,
                textForSureBtn : '确定',              //确定按钮
                textForcancleBtn : '取消',            //取消按钮
                handlerForCancle : null,
                handlerForSure : function(){
                    //解散群组接口
                    var datas = localStorage.getItem('datas');
                    //if(sAccount){
                    var data = JSON.parse(datas);
                    var userid = data.id;
                    sendAjax('group!unShutUpGroup',{groupid:groupid},function(data){
                        if(data){
                            var oData=JSON.parse(data);
                            if(oData.code==1){
                                $('#groupData .groupInfo-noChat').attr('data-chat',0);
                            }
                        }
                    },function(){
                        console.log('失败');
                    })
                }
            });
        }else{
            new Window().alert({
                title   : '开启全员禁言',
                content : '确定要开启全员禁言吗？',
                hasCloseBtn : true,
                hasImg : true,
                textForSureBtn : '确定',              //确定按钮
                textForcancleBtn : '取消',            //取消按钮
                handlerForCancle : null,
                handlerForSure : function(){
                    //解散群组接口
                    var datas = localStorage.getItem('datas');
                    var data = JSON.parse(datas);
                    var userid = data.id;
                    sendAjax('group!shutUpGroup',{groupid:groupid},function(data){
                        if(data){
                            var oData=JSON.parse(data);
                            if(oData.code==1){
                                $('#groupData .groupInfo-noChat').attr('data-chat',1);
                            }
                        }
                    },function(){
                        console.log('失败');
                    })
                }
            });
        }
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
        $('#crop-avatar .avatar-view').empty();
        $('#crop-avatar .avatar-view').append('<img src="'+sImgsrc+'"/>');
        //$('#crop-avatar .avatar-view img').attr('src',sImgsrc);
        $('.avatar-preview').empty();
        $('.avatar-preview').append('<img src="'+sImgsrc+'"/>');
        //$('.avatar-preview img').attr('src',sImgsrc);
       getHeadImgList();
    });
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
                if(aText[i].GID==sId){
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
        $('#groupData .infoDetailsBox>div').eq($(this).index()).removeClass('chatHide');
        switch(sType){
            case 'd':
                //$('.infoDetailsBox').find('.infoDetails-data').removeClass('chatHide');
                break;
            case 'r':
                var sTargettype=$('#groupContainer').attr('targettype');
                var sTargetid=$('#groupContainer').attr('targetid');
                var $groupEle=$('#groupDetailsBox .infoDet-chatRecord').find('.infoDet-page');
                console.log($groupEle);
                var oPagetest = new PageObj({divObj:$groupEle,pageSize:20,conversationtype:sTargettype,targetId:sTargetid},function(type,list,callback)//声明page1
                {
                    getChatRecord(list,'#groupDetailsBox .infoDet-chatRecord .chatRecordSel');
                    //showHistoryMessages(list);

                });
                //historyMsg(sTargettype,sTargetid,0,20);
                break;
            case 'f':
                break;
        }
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
    $('#chatBox').on('click','#systemSet .systemVoiceBtn',function(){
        //var status=parseInt($(this).attr('data-state'));//0 代表关闭  1代表开启
        //var eParent=$(this);
        if($(this).hasClass('active')){
            $(this).removeClass('active');
        }else{
            $(this).addClass('active');
        }
        /*switch(status){
            case 0:
                eParent.addClass('active');
                break;
            case 1:
                eParent.removeClass('active');
                break;
        }*/
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
        var sdata = localStorage.getItem('datas');
        var accountObj = JSON.parse(sdata);
        //var account = accountObj.account;
        var accountID = accountObj.id;
        systemBeep(status,accountID);
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
    //点击保存头像
    $('#crop-avatar').on('click','.bMg-preserve .bMg-keepImg',function(){
        var sData=window.localStorage.getItem("datas");
        var oData= JSON.parse(sData);
        var sId=oData.id;
        var picname='';
        var nDelImg;
        $('.bMg-cropImgSet .bMg-imgList li').each(function(index){
            if($(this).hasClass('active')){
                picname=$(this).attr('data-name');
                nDelImg=index;
            }
        });
       if(!picname){
           new Window().alert({
               title   : '',
               content : '请选择一个照片作为您的头像！！！',
               hasCloseBtn : false,
               hasImg : true,
               textForSureBtn : false,
               textForcancleBtn : false,
               autoHide:true
           });
           return false;
       }else{
           sendAjax('upload!secUserLogos',{userid:sId,picname:picname},function(data){
               var oDatas=JSON.parse(data);
               if(oDatas.code==1){
                   $('#personSettingId .perSetBox-head').attr('src','upload/images/'+picname);
                   $('.bMgMask').addClass('chatHide');
                   $('#crop-avatar').addClass('chatHide');
                   oData.logo=picname;
                   var sNewData=JSON.stringify(oData);
                   localStorage.setItem("datas",sNewData);
               }
           });
       }
    });
    $('#crop-avatar').on('click','.bMg-confirm .bMg-cancel',function(){
        $('.bMg-cropImgSet').removeClass('chatHide');
        $('.bMg-cropImgBox').addClass('chatHide');
        $('.bMg-gravityImg').removeClass('active');
        $('.bMg-confirm').addClass('chatHide');
        $('.bMg-preserve').removeClass('chatHide');
        //$('.avatar-preview').empty();
        //$('.avatar-preview').append('');
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
        var sId=oData.id;
        sendAjax('upload!delUserLogos',{userid:sId,picname:picname},function(data){
            var oDatas=JSON.parse(data);
            if(oDatas.code==1){
                $('.bMg-cropImgSet .bMg-imgList li').eq(nDelImg).remove();
            }
        });
    });
    $('#personSettingId').on('click','.perSetBox-keep',function(){
        var sEmail=$('#personSettingId .perSetBox-email').val();
        var sSex=$('#personSettingId .perSetBox-selSex').val();
        switch (sSex){
            case "男":
                sSex=1;
                break;
            case "女":
                sSex=0;
                break;
        }
        var sTelephone=$('#personSettingId .perSetBox-telephone').val();
        var sSign=$('#personSettingId .perSetBox-textarea').text();
        var sData=window.localStorage.getItem("datas");
        var oData= JSON.parse(sData);
        var sId=oData.id;
        sendAjax('member!updateMemberInfoForWeb',{userid:sId,sex:sSex,email:sEmail,phone:sTelephone,sign:sSign},function(data){
            var oDatas=JSON.parse(data);
           if(oDatas.code==1){
               var sData=window.localStorage.getItem("datas");
               var oData= JSON.parse(sData);
               var sId=oData.id;
               var sSelfImg=oData.logo;
           }
        });

    });
    $('#crop-avatar').on('click','.bMg-cropImgSet .bMg-imgList li',function(){
        $('.bMg-cropImgSet .bMg-imgList li').removeClass('active');
        $(this).addClass('active');
        var sSelImg=$(this).attr('data-name');
        $('.avatar-preview').empty();
        $('.avatar-preview').append('<img src="'+globalVar.imgSrc+sSelImg+'"/>');
        //$('.avatar-preview img').attr('src',globalVar.imgSrc+sSelImg);
    });
    $('#Uploader').delegate('#showGrid','click',function(){
        if($('.cropper-crop-box').hasClass('cropper-hidden')){
            $('.cropper-crop-box').removeClass('cropper-hidden');
        }else{
            $('.cropper-crop-box').addClass('cropper-hidden');
        }
    });
    //getGroupMembersList(1);
});

function fPersonalSet(){
   var sData=window.localStorage.getItem("datas");
    var oData= JSON.parse(sData);
    var sName=oData?oData.name : '';//姓名
    var sAccountNum=oData.account || '';//成员账号
    var sSex=oData.sex;//性别
    switch(sSex){
        case '男':
            sSex= '男';
            break;
        case '女':
            sSex= '女';
            break;
        default :
            sSex= '女';
            break;
    }
    var sPosition=oData.positionname || '';//职位
    var sBranch=oData.branchname || '';//部门
    var sEmail=oData.email || '';//邮箱
    var sTelephone=oData.telephone || '';//电话
    var sSign=oData.organname || '';//工作签名
    var sHeaderImg=oData.logo?globalVar.imgSrc+oData.logo:globalVar.defaultLogo;//头像
    var sHtml='<h3 class="perSetBox-title">个人设置</h3>\
    <div class="perSetBox-content clearfix">\
    <div class="perSetBox-leftCont">\
    <ul class="perSetBox-contDetails">\
    <li >\
    <span>成员账号:</span>\
    <p class="perSetBox-account">'+sAccountNum+'</p>\
    </li>\
    <li>\
    <span>姓名：</span>\
    <p class="perSetBox-name">'+sName+'</p>\
    </li>\
    <li>\
    <span>性别：</span>\
    <p>\
    <select class="perSetBox-selSex">\
    <option value="男" selected>男</option>\
    <option value="女">女</option>\
    </select>\
    </p>\
    </li>\
    <li>\
    <span>职位：</span>\
    <p class="perSetBox-position">'+sPosition+'</p>\
    </li>\
    <li>\
    <span>部门：</span>\
    <p class="perSetBox-branch">'+sBranch+'</p>\
    </li>\
    <li>\
    <span>邮箱：</span>\
    <p>\
    <input value="'+sEmail+'" class="perSetBox-editText perSetBox-email"/>\
    </p>\
    </li>\
    <li>\
    <span>电话：</span>\
    <p>\
    <input value="'+sTelephone+'" class="perSetBox-editText perSetBox-telephone"/>\
    </p>\
    </li>\
    <li>\
    <span>工作签名：</span>\
    <p>\
    <textarea class="perSetBox-textarea" >'+sSign+'</textarea>\
    </p>\
    </li>\
    </ul>\
    <button class="perSetBox-keep">保存</button>\
    </div>\
    <div class="perSetBox-rightCont">\
    <img src="'+sHeaderImg+'" class="perSetBox-head"/>\
    <p class="perSetBox-modifyHead" id="changeHeadImgId">修改头像</p>\
    </div>\
    </div>';
    $('#chatBox #personSettingId').empty();
    $('#chatBox #personSettingId').append(sHtml);
    $('.perSetBox-selSex').val(sSex);
}
function showGroupMemberInfo(oGroupInfo,pos){
    var sName=oGroupInfo.name || '';//群名称
    var sCreatorId=oGroupInfo.mid;//群创建者id
    var sCreatedate=subTimer(oGroupInfo.createdate);//创建时间
    var oCreator=findMemberInList(sCreatorId);
    if(!oCreator.logo){
        var sImg=globalVar.defaultLogo;
    }else{
        var sImg=globalVar.imgSrc+oCreator.logo;
    }
   /* var sImg=oCreator.logo || globalVar.defaultLogo;*/
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
    <img src="'+sImg+'">\
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
function systemBeep(status,accountID){
    sendAjax('fun!setSysTipVoice',{status:status,userid:accountID},function(data){
        var oData=JSON.parse(data);
        //var eParent=$('#chatBox #systemSet .systemVoiceBtn');
        if(oData.code==1){
            globalVar.SYSTEMSOUND=!globalVar.SYSTEMSOUND;
        }
    });
}
function getHeadImgList(){
    var sData=window.localStorage.getItem("datas");
    var oData= JSON.parse(sData);
    var sId=oData.id;
    var sSelfImg=oData.logo;
    sendAjax('upload!getUserLogos',{userid:sId},function(data){
        var oDatas=JSON.parse(data);
        var aImgList=oDatas.text;
        var sDom='';
        if(oDatas.code==1){
           if(aImgList.length>0){
               for(var i=0;i<aImgList.length;i++){
                   var sImg=aImgList[i];
                   if(sSelfImg==sImg){
                       sDom+='<li data-name="'+sImg+'" class="active">\
                   <img src="'+globalVar.imgSrc+sImg+'"/>\
                   </li>';
                   }else{
                       sDom+='<li data-name="'+sImg+'">\
                   <img src="'+globalVar.imgSrc+sImg+'"/>\
                   </li>';
                   }
               }
               $('.bMg-cropImgSet .bMg-imgList').empty();
               $('.bMg-cropImgSet .bMg-imgList').append(sDom);
           }
        }
    });
}