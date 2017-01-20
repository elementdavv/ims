/**
 * Created by zhu_jq on 2017/1/12.
 */
$(function(){
    var messageContent = document.getElementById('message-content');
    messageContent.ondragover = function(e){
        if (e.preventDefault) e.preventDefault();
        else e.returnValue = false;
    }
    messageContent.ondrop = function(e){
        if (e.preventDefault) e.preventDefault();
        else e.returnValue = false;
        //console.log(e);
        var file = e.dataTransfer.files[0];
        //console.log(file);
        var filedetail = {};
        filedetail.name = file.name;
        filedetail.uniqueName = file.uniqueName;
        filedetail.size = file.size;
        filedetail.type = file.type;
        var content = JSON.stringify(filedetail);
        var extra = "uploadFile";
        //{content:"hello",extra:"附加信息"}
        var targetId = $(this).parents('.mesContainer').attr('targetid');
        var targetType = $(this).parents('.mesContainer').attr('targettype');
        sendMsg(content,targetId,targetType,extra);
    }
})

function conversationGroup(targetID,targetType,groupName){
    $('.perSetBox-title span').html(groupName);
    $('.mesContainerGroup').attr('targetID',targetID)
    $('.mesContainerGroup').attr('targetType',targetType)
    $('.rongyun-emoji>span').unbind('click');
    $('.rongyun-emoji>span').on('click',function(){
        var name = $(this).find('span').attr('name');
        $('.textarea').append(name);
    })
    $('.showEmoji').click(function(){
        $('.rongyun-emoji').show();
    });
    $('.sendMsgBTN').unbind('click')
    $('.sendMsgBTN').click(function(){
        var content = $(this).prev().html();
        if(content){
            var targetId = $('.mesContainerGroup').attr('targetID');
            var targetType = $('.mesContainerGroup').attr('targetType');
            sendMsg(content,targetId,targetType)
        }

    })
    $('.mr-record').addClass('active');
    $('.mesContainerGroup').removeClass('mesContainer-translateL');
    clearNoReadMsg(targetType,targetID);
    getConverList();
}

function po_Last_Div(obj) {
    if (window.getSelection) {//ie11 10 9 ff safari
        obj.focus(); //解决ff不获取焦点无法定位问题
        var range = window.getSelection();//创建range
        range.selectAllChildren(obj);//range 选择obj下所有子内容
        range.collapseToEnd();//光标移至最后
    }
    else if (document.selection) {//ie10 9 8 7 6 5
        var range = document.selection.createRange();//创建选择对象
        //var range = document.body.createTextRange();
        range.moveToElementText(obj);//range定位到obj
        range.collapse(false);//光标移至最后
        range.select();
    }
}
/*function transdate(endTime){
    var date=new Date();
    date.setFullYear(endTime.substring(0,4));
    date.setMonth(endTime.substring(5,7)-1);
    date.setDate(endTime.substring(8,10));
    date.setHours(endTime.substring(11,13));
    date.setMinutes(endTime.substring(14,16));
    date.setSeconds(endTime.substring(17,19));
    return Date.parse(date)/1000;
}*/
function ondayTime(sCurrentTime,sContrastTime){
   // var sDateTime=changeTimeFormat(sContrastTime,'y');
    //var sDateHoursTime=changeTimeFormat(sContrastTime,'yh');
}
function sessionContent(sDoM,sTargetId,sContent,extra){
    if (sTargetId) {//别人的
        var oData=findMemberInList(sTargetId);
        var sImg=oData.logo || 'PersonImg.png';
        if(extra=="uploadFile"){
            //var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
            var sendMsg = JSON.parse(sContent);
            var imgSrc = '';
            var Msize = KBtoM(sendMsg.size);
            var uniqueTime = sendMsg.uniqueTime;
            switch (sendMsg.type){
                case 'image/png':
                    var imgSrc = 'page/web/css/img/backstage.png';
                    break;
            }
            sDoM += '<li class="mr-chatContentLFile clearfix">'+
                        '<div class="mr-ownChat">'+
                        '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
                        '<div class="file_content fl">' +
                        '<p class="p1 file_name">'+sendMsg.name+'</p>' +
                        '<p class="p2 file_size">'+Msize+'</p>' +
                        '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>'+
                        '</div>' +
                        '</div>' +
                        '<a class="downLoadFile"></a>'+
                        '</li>';

        }else{
            var str = RongIMLib.RongIMEmoji.symbolToHTML(sContent);
            sDoM += ' <li class="mr-chatContentL clearfix">\
                    <img src="upload/images/'+sImg+'">\
                    <div class="mr-chatBox">\
                    <span>' + str + '</span>\
                    <i></i>\
                    </div>\
                    </li>';
        }
    } else {//自己的
        if(extra=="uploadFile"){
            var sendMsg = JSON.parse(sContent);
            var imgSrc = '';
            var Msize = KBtoM(sendMsg.size);
            var uniqueTime = sendMsg.uniqueTime
            switch (sendMsg.type){
                case 'image/png':
                    var imgSrc = 'page/web/css/img/backstage.png';
                    break;
            }
            sDoM += '<li class="mr-chatContentRFile clearfix">'+
                        '<div class="mr-ownChat">'+
                        '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
                        '<div class="file_content fl">' +
                        '<p class="p1 file_name">'+sendMsg.name+'</p>' +
                        '<p class="p2 file_size">'+Msize+'</p>' +
                        '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>'+
                        '</div>' +
                        '</div>' +
                        '<a class="downLoadFile"></a>'
                        '</li>';
        }else{
            var str = RongIMLib.RongIMEmoji.symbolToHTML(sContent);

            sDoM += ' <li class="mr-chatContentR clearfix">\
                    <div class="mr-ownChat">\
                    <span>' + str + '</span>\
                    <i></i>\
                    </div>\
                    </li>';
        }
    }
    return sDoM;
}

function fillSelfPage(targetID,targetType){
    RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[targetType], targetID, 0, 20, {
        onSuccess: function(list, hasMsg) {
            var timestamp = new Date().getTime();//获取当前时间戳
            var sStartTime=0;
            var sCurrentTime = changeTimeFormat(timestamp, 'yh');
            var sCurrentDateTime = changeTimeFormat(timestamp, 'y');
            //var sfiveBeforeTime=changeTimeFormat(timestamp+300000,'yh');
            var sDoM = '<ul class="mr-chatContent">';
            for (var i = 0; i < list.length; i++) {
                var sSentTime = list[i].sentTime;
                var sContent = list[i].content.content || '';
                var extra = list[i].content.extra || '';
                var sTargetId = list[i].targetId;
                var sDateTime = changeTimeFormat(sSentTime, 'y');
                var sDateHoursTime = changeTimeFormat(sSentTime, 'yh');
                var sHoursTime=changeTimeFormat(sSentTime, 'h');
                if (sDateTime != sCurrentDateTime) {
                    sCurrentDateTime = sDateTime;
                    sCurrentTime = sDateHoursTime;
                    sStartTime=sSentTime;
                    var sNowTime = new Date().getTime();//获取当前时间戳
                    var sNowCurrentTime = changeTimeFormat(sNowTime, 'y');
                    if(sNowCurrentTime == sDateTime){
                        sDoM += ' <li>\
                    <p class="mr-Date">' + sHoursTime + '</p>\
                    </li>';
                    }else{
                        sDoM += ' <li>\
                    <p class="mr-Date">' + sCurrentTime + '</p>\
                    </li>';
                    }
                    sDoM=sessionContent(sDoM,sTargetId,sContent,extra);
                } else {
                    var sNowTime1 = new Date().getTime();//获取当前时间戳
                    var sNowCurrentTime1 = changeTimeFormat(sNowTime, 'y');
                    //var sCurrentDateTime = changeTimeFormat(timestamp, 'y');
                    if (sSentTime - sStartTime >300000) {
                        //sStartTime=sSentTime;
                        if(sNowCurrentTime1==sDateTime){
                            var sfiveBeforeTime = changeTimeFormat(sSentTime, 'h');
                        }else{
                            var sfiveBeforeTime = changeTimeFormat(sSentTime, 'yh');
                        }
                        //var sfiveBeforeTime = changeTimeFormat(sSentTime, 'yh');
                        sDoM += '<li>\
                        <p class="mr-Date">' + sfiveBeforeTime + '</p>\
                        </li>';
                        sDoM=sessionContent(sDoM,sTargetId,sContent,extra)
                    } else {
                        sDoM=sessionContent(sDoM,sTargetId,sContent,extra)
                    }
                    sStartTime=sSentTime;
                }
                //aList=list;
                // return list;
                // hasMsg为boolean值，如果为true则表示还有剩余历史消息可拉取，为false的话表示没有剩余历史消息可供拉取。
                // list 为拉取到的历史消息列表
            }
            sDoM+='</ul>';
            $('#perContainer .mr-chatview').empty();
            $('#perContainer .mr-chatview').append(sDoM);
            var eDom=document.querySelector('#perContainer .mr-chatview');
            eDom.scrollTop = eDom.scrollHeight;
        },
        onError: function(error) {
            // APP未开启消息漫游或处理异常
            // throw new ERROR ......
        }
    });
}
function conversationSelf(targetID,targetType){
    //var target = targetID;
    //噗页面 把targetID放进去
    fillSelfPage(targetID,targetType);

    var curTargetList = findMemberInList(targetID);
    var name = curTargetList.name;
    $('.perSetBox-title span').html(name);

    $('.mesContainerSelf').attr('targetID',targetID);
    $('.mesContainerSelf').attr('targetType',targetType);
    $('.rongyun-emoji>span').off('click')
    $('.rongyun-emoji>span').on('click',function(){
        $('.textarea b').attr('contenteditable','false');
        var name = $(this).find('span').attr('name');
        //var newEmo = $(this).clone();
        $('.textarea').append(name);
        var textarea = document.getElementById('message-content')
        //po_Last_Div(textarea);
    })
    $('.showEmoji').click(function(){

        $('.rongyun-emoji').show();
        $('.rongyun-emoji').blur(function(){
            console.log(1);
        })

    });
    var rimerEmoji = null;
    $('.rongyun-emoji').on('mouseenter',function(){
        clearTimeout(rimerEmoji);
    })
    $('.rongyun-emoji').on('mouseleave',function(){
        rimerEmoji = setTimeout(function(){
            $('.rongyun-emoji').hide();
        },1000)
    })
    $('.sendMsgBTN').unbind('click')
    $('.sendMsgBTN').click(function(){
        var content = $(this).prev().html();
        if(content){
            var targetId = $('.mesContainerSelf').attr('targetID');
            var targetType = $('.mesContainerSelf').attr('targetType');
            sendMsg(content,targetId,targetType)
        }
    })
    $('.orgNavClick').addClass('chatHide');
    $('.mesContainerSelf').removeClass('chatHide');
    $('.mr-record').addClass('active');
    $('.mesContainerSelf').removeClass('mesContainer-translateL');
    //获取右侧的联系人资料聊天记录
    getInfoDetails(targetID,targetType,findMemberInList(targetID));
    clearNoReadMsg(targetType,targetID);
    getConverList();
}
function getInfoDetails(targetID,targetType,oInfoDetails){
    getPerInfo(oInfoDetails);
}
/*获取群组资料*/
function getGroupDetails(groupId){
    var datas = localStorage.getItem('groupInfo');
    var data = JSON.parse(datas);
    var aText=data.text;
    var sDom='';
    //var sId=$('#groupContainer').attr('targetid');
    for(var i = 0;i<aText.length;i++){
        if(aText[i].id==groupId){
             var sName=aText[i].name || '';//群名称
            var sCreatorId=aText[i].creatorId;//群创建者id
            var sCreatedate=subTimer(aText[i].createdate);//创建时间
            var oCreator=findMemberInList(sCreatorId);
            var sImg=oCreator.logo || 'PersonImg.png';
            sDom+='<ul class="groupInfo">\
                <li class="groupInfo-name">\
            <span>群组名称：</span>\
            <b>'+sName+'</b>\
            </li>\
            <li class="groupInfo-setTime">\
            <span>创建时间：</span>\
            <b>'+sCreatedate+'</b>\
            </li>\
            <li class="groupInfo-Controller">\
            <span>群主/管理员：</span>\
            <img src="upload/images/'+sImg+'">\
            </li>\
            <li class="groupInfo-disturb">\
            <span>消息免打扰：</span>\
            <p>\
            <i></i>\
            <i></i>\
            </p>\
            </li>\
            </ul><div class="groupInfo-memberList"></div>';
            $('#groupData .group-data').empty();
            $('#groupData .group-data').append(sDom);
            //showGroupMemberInfo(aText[i],pos);
        }
    }
    getGroupMembersList(groupId);
}
function getGroupMembersList(groupid){
    sendAjax('group!listGroupMemebers',{groupid:groupid},function(data) {
        var oGroupidList = JSON.parse(data);
        var aMember=oGroupidList.text;
        var sDom='<div class="groupInfo-number clearfix">\
            <span>成员('+aMember.length+')</span>\
            <p class="clearfix">\
        <i class="groupInfo-noChat"></i>\
        <i></i>\
        </p>\
        </div>\
        <ul class="groupInfo-memberAll">';
        if(aMember.length>0){
            for(var i=0;i<aMember.length;i++){
                var oCreator=findMemberInList(aMember[i]);
                var sMemberName=oCreator.name;
                var sJob=oCreator.account;
                var sImg=oCreator.logo || 'PersonImg.png';
                sDom+=' <li>\
                            <img src="upload/images/'+sImg+'">\
                            <p>'+sMemberName+'('+sJob+')</p>\
                            </li>';
            }
        }
        sDom+='</ul>';
        $('#groupData .group-data .groupInfo-memberList').empty();
        $('#groupData .group-data .groupInfo-memberList').append(sDom);
        console.log(oGroupidList)
    });
}
/**
 *
 * @param oInfoDetails 个人资料
 */
function getPerInfo(oInfoDetails){
    var sName=oInfoDetails.name || '';//姓名
    var sLogo=oInfoDetails.logo || 'PersonImg.png';//头像
    var sMobile=oInfoDetails.mobile || '';//手机
    var sEmail=oInfoDetails.email || '';//邮箱
    var sBranch=oInfoDetails.sex || '';//部门
    var sJob=oInfoDetails.sex || '';//职位
    var sOrg=oInfoDetails.sex || '';//组织
    var sAddress=oInfoDetails.address || '';//地址
    var sDom='\
        <div class="infoDet-personal clearfix">\
    <img src="upload/images/'+sLogo+'">\
    <div class="infoDet-text">\
    <p>'+sName+'</p>\
    <div class="clearfix">\
    <span class="infoDet-postInfo"></span>\
    <span class="infoDet-position"></span>\
    <span class="infoDet-addPer"></span>\
    </div>\
    </div>\
    </div>\
    <ul class="infoDetList clearfix">\
    <li>\
    <span>手机：</span>\
    <b>'+sMobile+'</b>\
    </li>\
    <li>\
    <span>邮箱：</span>\
    <b>'+sEmail+'</b>\
    </li>\
    <li>\
    <span>部门：</span>\
    <b>'+sBranch+'</b>\
    </li>\
    <li>\
    <span>职位：</span>\
    <b>'+sJob+'</b>\
    </li>\
    <li>\
    <span>组织：</span>\
    <b>'+sOrg+'</b>\
    </li>\
    <li>\
    <span>地址：</span>\
    <b>'+sAddress+'</b>\
    </li>\
    </ul>\
    ';
    $('.infoDetails-data').empty();
    $('.infoDetails-data').append(sDom);
}
function getChatRecord(aList,hasMsg){
    var sDom='<ul class="infoDet-contentDet">';
    var sLi='';
    var aInfo=aList;
    $('#personalData .chatRecordSel').empty();
    var aDate=[];
    if(aInfo.length>0){
        for(var i=0;i<aInfo.length;i++){
            var sTargetId=aInfo[i].targetId;
            var sSentTime=aInfo[i].sentTime;//发送时间
            var sContent=aInfo[i].content.content||'';
            var sMessageId=aInfo[i].messageId;//信息id
            var sSentTimeReg=changeTimeFormat(sSentTime);
            var sSentDate=changeTimeFormatYMD(sSentTime);
            aDate.push(sSentDate);
            if(aDate.length>1){
                if(aDate[aDate.length-1] != aDate[aDate.length-2]){
                    sLi+='<li >\
                    <p class="infoDet-timeRecord">'+aDate[aDate.length-2]+'</p>\
                </li>';
                }
            }
            if(sTargetId){
               var oThers=findMemberInList(sTargetId);
                var sName=oThers.name || '';
               sLi+='<li class="infoDet-OthersSay" data-time="'+sSentTime+'">\
                   <span>'+sName+'&nbsp&nbsp&nbsp'+sSentTimeReg+'</span>\
                <p>'+sContent+'</p>\
                </li>';
            }else{
                var sSelfName=$('.perSetBox-title span').html();
                sLi+='<li class="infoDet-selfSay" data-time="'+sSentTime+'">\
                   <span>'+sSelfName+'&nbsp&nbsp&nbsp'+sSentTimeReg+'</span>\
                <p>'+sContent+'</p>\
                </li>';
            }
        }
        sDom+=sLi+'</ul>';
    }
    $('#personalData .chatRecordSel').append(sDom);
    var eDom=document.querySelector('#personalData .chatRecordSel');
    eDom.scrollTop = eDom.scrollHeight;
}
function scrollTop(eDom){
    eDom.scrollTop = eDom.scrollHeight;
}
//获取历史消息、消息记录
function historyMsg(Type,targetId){
    var aList;
    RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[Type], targetId, null, 20, {
        onSuccess: function(list, hasMsg) {
            console.log(list,hasMsg);
           //aList=list;
           // return list;
            // hasMsg为boolean值，如果为true则表示还有剩余历史消息可拉取，为false的话表示没有剩余历史消息可供拉取。
            // list 为拉取到的历史消息列表
        },
        onError: function(error) {
            // APP未开启消息漫游或处理异常
            // throw new ERROR ......
        }
    });
}

//显示会话列表
function getConverList(){
    RongIMClient.getInstance().getConversationList({
        onSuccess: function(list) {
            usualChatList(list);
            //console.log(list);
        },
        onError: function(error) {
            console.log('同步会话列表ERROR');
        }
    },null);
}


function changeTimeFormat(mSec,format){
    //var oldTime = (new Date("2012/12/25 20:11:11")).getTime(); //得到毫秒数
    var time;
    var newTime = new Date(mSec); //就得到普通的时间了
    var y=ifPlusZero(newTime.getFullYear());
    var month=ifPlusZero(newTime.getMonth()+1);
    var d=ifPlusZero(newTime.getDate());
    var w=ifPlusZero(newTime.getDay());
    var h = ifPlusZero(newTime.getHours()); //获取系统时，
    var m = ifPlusZero(newTime.getMinutes()); //分
    var s = ifPlusZero(newTime.getSeconds()); //秒
    switch(format){
        case 'y':
          time = y+'-'+month+'-'+d;
            break;
        case 'h':
            time = h+':'+m+':'+s;
            break;
        case 'yh':
            time=y+'-'+month+'-'+d+' '+h+':'+m+':'+s;
    }
    return time;
}
function changeTimeFormatYMD(mSec){
    //var oldTime = (new Date("2012/12/25 20:11:11")).getTime(); //得到毫秒数
    var newTime = new Date(mSec); //就得到普通的时间了
    var y=ifPlusZero(newTime.getFullYear());
    var m=ifPlusZero(newTime.getMonth()+1);
    var d=ifPlusZero(newTime.getDate());
    var time = y+'-'+m+'-'+d;
    return time;
}
function ifPlusZero(num){
    if(num<10){
        num = '0'+num;
    }
    return num;
}

function findMemberInList(targetId){
    var normalInfo = localStorage.getItem('normalInfo');
    if(normalInfo){
        var aNormalInfo = JSON.parse(normalInfo);
        for(var i = 0;i<aNormalInfo.length;i++){
            var curInfo = aNormalInfo[i];
            if(curInfo.id==targetId&&curInfo.flag!=0){
                var targetInfo = curInfo;
            }
        }
    }
    return targetInfo;
}

//显示会话列表
function usualChatList(list){
    var sHTML = '';
    for(var i = 0;i<list.length;i++){
        var curList = list[i];
        var content = curList.latestMessage.content.content;
        var extra = curList.latestMessage.content.extra;
        if(extra=="uploadFile"){
            content="发送文件";
        }
        var targetId = curList.targetId
        var timeNow = new Date().getTime();
        var deltTime = timeNow-curList.sentTime;
        if(deltTime>=86400000){
            var lastTime = changeTimeFormat(curList.sentTime,'yh');
        }else{
            var lastTime = changeTimeFormat(curList.sentTime,'h');
        }
        //changeTimeFormat(mSec,format)
        var member = findMemberInList(targetId);
        if(member){
            //console.log('member',member);
            var logo = member.logo;
            var name = member.name;
            var unreadMessageCount = curList.unreadMessageCount;
            var sNum = unreadMessageCount==0?'':'<i class="notReadMsg">'+unreadMessageCount+'</i>'

            sHTML += ' <li targetid="'+targetId+'">'+
            '<div><img class="groupImg" src="'+logo+'" alt=""/>'+
            sNum+
            '<span class="groupName">'+name+'</span>'+
            '<span class="usualLastMsg">'+content+'</span>'+
            '<span class="lastTime">'+lastTime+'</span>'+
            '</div>'+
            '</li>'
        }
    }

    $('.usualChatListUl').html(sHTML);
}
//包括单聊，群聊，聊天室
function sendMsg(content,targetId,way,extra,callback){
    // 定义消息类型,文字消息使用 RongIMLib.TextMessage
    var msg = new RongIMLib.TextMessage({content:content,extra:extra});
    //或者使用RongIMLib.TextMessage.obtain 方法.具体使用请参见文档
    //var msg = RongIMLib.TextMessage.obtain("hello");
    var conversationtype = RongIMLib.ConversationType[way]; // 私聊
    var targetId = targetId; // 目标 Id
    RongIMClient.getInstance().sendMessage(conversationtype, targetId, msg, {
            // 发送消息成功
            onSuccess: function (message) {
                //var
                //message 为发送的消息对象并且包含服务器返回的消息唯一Id和发送消息时间戳
                sendInBox(message,way,callback);
                getConverList();
                console.log("Send successfully");
            },
            onError: function (errorCode,message) {
                var info = '';
                switch (errorCode) {
                    case RongIMLib.ErrorCode.TIMEOUT:
                        info = '超时';
                        break;
                    case RongIMLib.ErrorCode.UNKNOWN_ERROR:
                        info = '未知错误';
                        break;
                    case RongIMLib.ErrorCode.REJECTED_BY_BLACKLIST:
                        info = '在黑名单中，无法向对方发送消息';
                        break;
                    case RongIMLib.ErrorCode.NOT_IN_DISCUSSION:
                        info = '不在讨论组中';
                        break;
                    case RongIMLib.ErrorCode.NOT_IN_GROUP:
                        info = '不在群组中';
                        break;
                    case RongIMLib.ErrorCode.NOT_IN_CHATROOM:
                        info = '不在聊天室中';
                        break;
                    default :
                        info = x;
                        break;
                }
                console.log('发送失败:' + info);
            }
        }
    );
}

//发送出去的的信息显示在盒子里
function sendInBox(msg,way,callback){

    var sendMsg = msg.content.content;
    var extra = msg.content.extra;
    console.log(msg);
    if(extra=='uploadFile'){
        var sendMsg = JSON.parse(sendMsg);
        console.log('sendMsg',sendMsg);
        var uniqueTime = sendMsg.uniqueTime;
        var name = sendMsg.name;
        var imgSrc = '';
        var Msize = KBtoM(sendMsg.size);
        switch (sendMsg.type){

            case 'image/png':
                var imgSrc = 'page/web/css/img/backstage.png';
                break;
        }


        var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
        //var className = uniqueName.uniqueName;
        //var class0 = uniqueName.split('.')[0];
        var sHTML = '<li messageUId="'+msg.messageUId+'" sentTime="'+msg.sentTime+'" class="mr-chatContentRFile clearfix">'+
                        '<div class="mr-ownChat">'+
                            '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
                            '<div class="file_content fl">' +
                                '<p class="p1 file_name">'+sendMsg.name+'</p>' +
                                '<p class="p2 file_size">'+Msize+'</p>' +
                                '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>' +
                            '</div>' +
                        '</div>' +
                        '<a class="downLoadFile" src="http://ocsys6mwy.bkt.clouddn.com/'+sendMsg.name+'"></a>'+
                    '</li>';
    }else{
        var str = RongIMLib.RongIMEmoji.symbolToHTML(sendMsg);
        var sHTML = '<li messageUId="'+msg.messageUId+'" sentTime="'+msg.sentTime+'" class="mr-chatContentR clearfix">'+
            '<div class="mr-ownChat">'+
            '<span>'+str+'</span>'+
            '<i></i>'+
            '</div>'+
            '</li>';
    }
    if(way=='PRIVATE'){
        var parent = $('.mesContainerSelf');
        var parentNode = $('.mesContainerSelf .mr-chatview .mr-chatContent');
        var eDom=document.querySelector('#perContainer .mr-chatview');

    }else{
        var parent = $('.mesContainerGroup');
        var parentNode = $('.mesContainerGroup .mr-chatview .mr-chatContent');
        var eDom=document.querySelector('#groupContainer .mr-chatview');
    }
    parentNode.append($(sHTML));
    eDom.scrollTop = eDom.scrollHeight;
    parent.find('.textarea').html('');
    callback&&callback();
}

function KBtoM(kb){
    return kb/1024;
}
//接收到的消息显示在盒子里或者在消息列表中显示
function reciveInBox(msg){
    console.log(msg);
    var targetID = msg.targetId;
    var content = msg.content.content;
    var extra = msg.content.extra;
    var targetType = msg.conversationType;
    if(targetType==3){//qunliao
        var $MesContainer = $('.mesContainerGroup')
    }else if(targetType==1){//个人聊天
        var $MesContainer = $('.mesContainerSelf')
    }
    if(extra=='uploadFile'){
        if (!$MesContainer.hasClass('chatHide') || $MesContainer.attr('targetID') == targetID) {
            var content = JSON.parse(content);
            console.log('sendMsg',content);

            var imgSrc = '';
            var Msize = KBtoM(content.size);
            switch (content.type){
                case 'image/png':
                    var imgSrc = 'page/web/css/img/backstage.png';
                    break;
            }
            var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
            var sHTML = '<li messageUId="'+msg.messageUId+'" sentTime="'+msg.sentTime+'" class="mr-chatContentLFile clearfix">'+
                '<div class="mr-chatBox">'+
                '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
                '<div class="file_content fl">' +
                '<p class="p1 file_name">'+content.name+'</p>' +
                '<p class="p2 file_size">'+Msize+'</p>' +
                '<div id="up_process"><div id="up_precent"></div>' +
                '</div>' +
                '</div>' +
                '<a class="downLoadFile" href="https://ocsys6mwy.bkt.clouddn.com/'+data.filename+'"></a>'+
                '</li>';
            var parentNode = $MesContainer.find('.mr-chatview .mr-chatContent');
            parentNode.append($(sHTML));
            var eDom=document.querySelector('#perContainer .mr-chatview');
            eDom.scrollTop = eDom.scrollHeight;
        }else{

        }
    }else {
        var str = RongIMLib.RongIMEmoji.symbolToHTML(content);
        if (!$MesContainer.hasClass('chatHide') || $MesContainer.attr('targetID') == targetID) {
            //在盒子里显示
            //头像需要自己找？、？
            var sHTML = '<li messageUId="' + msg.messageUId + '" sentTime="' + msg.sentTime + '" class="mr-chatContentL clearfix">' +
                '<img src="page/web/css/img/1.jpg">' +
                '<div class="mr-chatBox">' +
                '<span>' + str + '</span>' +
                '<i></i>' +
                '</div>' +
                '</li>';

            //clearNoReadMsg($('.mesContainer').attr('targettype'),targetID);
            //msgReaded(msg.messageUId,msg.sentTime,$('.mesContainer').attr('targettype'));
            var parentNode = $MesContainer.find('.mr-chatview .mr-chatContent');
            parentNode.append($(sHTML));
            var eDom=document.querySelector('#perContainer .mr-chatview');
            eDom.scrollTop = eDom.scrollHeight;
        } else {
            //消息列表里显示红色小圆圈
            //刷新消息列表
        }
    }

}



//已读通知消息

function msgReaded(messageUId,lastMessageSendTime,converseType){
    //var messageUId = "消息唯一 Id";
    //var lastMessageSendTime = "最后一条消息的发送时间";
    var type = "1";// 备用，默认赋值 1 即可。
    // 以上 3 个属性在会话的最后一条消息中可以获得。
    var msg = new RongIMLib.ReadReceiptMessage({ messageUId: messageUId, lastMessageSendTime: lastMessageSendTime, type: type });
    //var conversationtype = RongIMLib.ConversationType[converseType]; // 私聊,其他会话选择相应的消息类型即可。
    //var targetId = "xxx"; // 目标 Id
    //RongIMClient.getInstance().sendMessage(conversationtype, targetId, msg, {
    //        onSuccess: function (message) {
    //            //message 为发送的消息对象并且包含服务器返回的消息唯一Id和发送消息时间戳
    //            console.log("Send successfully");
    //        },
    //        onError: function (errorCode,message) {
    //            var info = '';
    //            switch (errorCode) {
    //                case RongIMLib.ErrorCode.TIMEOUT:
    //                    info = '超时';
    //                    break;
    //                case RongIMLib.ErrorCode.UNKNOWN_ERROR:
    //                    info = '未知错误';
    //                    break;
    //                case RongIMLib.ErrorCode.REJECTED_BY_BLACKLIST:
    //                    info = '在黑名单中，无法向对方发送消息';
    //                    break;
    //                case RongIMLib.ErrorCode.NOT_IN_DISCUSSION:
    //                    info = '不在讨论组中';
    //                    break;
    //                case RongIMLib.ErrorCode.NOT_IN_GROUP:
    //                    info = '不在群组中';
    //                    break;
    //                case RongIMLib.ErrorCode.NOT_IN_CHATROOM:
    //                    info = '不在聊天室中';
    //                    break;
    //                default :
    //                    info = x;
    //                    break;
    //            }
    //            console.log('发送失败:' + info);
    //        }
    //    }
    //);
}



//清除未读消息数

function clearNoReadMsg(Type,targetId){
    var conversationType = RongIMLib.ConversationType[Type];
    //var targetId = "xxx";
    RongIMClient.getInstance().clearUnreadCount(conversationType,targetId,{
        onSuccess:function(){
            // 清除未读消息成功。
        },
        onError:function(error){
            // error => 清除未读消息数错误码。
        }
    });
}


function drop(event){
    event.preventDefault();
    console.log('ondrop',event);
}