/**
 * Created by zhu_jq on 2017/1/12.
 */
$(function(){


})

function conversationGroup(targetID,targetType,groupName){
    $('.perSetBox-title span').html(groupName);
    $('.mesContainerGroup').attr('targetID',targetID)
    $('.mesContainerGroup').attr('targetType',targetType)

    $('.rongyun-emoji>span').on('click',function(){
        var name = $(this).find('span').attr('name');
        //var newEmo = $(this).clone();
        $('.textarea').append(name);
    })
    $('.showEmoji').click(function(){
        $('.rongyun-emoji').show();
    });
    $('.sendMsgBTN').unbind('click')
    $('.sendMsgBTN').click(function(){
        var content = $(this).prev().val();
        var targetId = $('.mesContainerGroup').attr('targetID');
        var targetType = $('.mesContainerGroup').attr('targetType');
        //if()
        sendMsg(content,targetId,targetType)
    })
    //$('.orgNavClick').addClass('chatHide');
    //$('.mesContainerGroup').removeClass('chatHide');
   // $('.mr-record').removeClass('active');
    $('.mesContainerGroup').removeClass('mesContainer-translateL');
    //获取右侧的联系人资料聊天记录
    //getInfoDetails();
    getGroupDetails(targetID);
    //console.log(targetID);
    //console.log(findMemberInList(targetID));
    //findMemberInList(targetID)
    clearNoReadMsg(targetType,targetID);
    getConverList();
}


function conversationSelf(targetID,targetType){
        //聊天室页面显示
    //var target = targetID;
    //噗页面 把targetID放进去
    var curTargetList = findMemberInList(targetID);
    var name = curTargetList.name;
    $('.perSetBox-title span').html(name);

    $('.mesContainerSelf').attr('targetID',targetID);
    $('.mesContainerSelf').attr('targetType',targetType);


    //$('.rongyun-emoji span').off('click');
    $('.rongyun-emoji>span').on('click',function(){
        var name = $(this).find('span').attr('name');
        //var newEmo = $(this).clone();
        $('.textarea').append(name);
    })
    $('.showEmoji').click(function(){

        $('.rongyun-emoji').show();
    });
    $('.sendMsgBTN').unbind('click')
    $('.sendMsgBTN').click(function(){
        var content = $(this).prev().val();

        //var targetID = targetId
        var targetId = $('.mesContainerSelf').attr('targetID');
        var targetType = $('.mesContainerSelf').attr('targetType');
        //if()
        sendMsg(content,targetId,targetType)
    })
    $('.orgNavClick').addClass('chatHide');
    $('.mesContainerSelf').removeClass('chatHide');
   // $('.mr-record').addClass('active');
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
            //if(i==aInfo.length-1){
            //    sLi+='<li>\
            //       <p class="infoDet-timeRecord">'+sSentDate+'</p>\
            //    </li>';
            //}
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
function historyMsg(Type,targetId,timestrap,count,$eDom){
    RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[Type], targetId, timestrap, count, {
        onSuccess: function(list, hasMsg) {
            //console.log(list,hasMsg);
            //if($eDom.hasClass('infoDet-prePage')){
            //
            //}localStorage.setItem('prePage',list);
            if(!hasMsg){
                $eDom.removeClass('allowClick');
            }
            getChatRecord(list,hasMsg);
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


function changeTimeFormat(mSec){
    //var oldTime = (new Date("2012/12/25 20:11:11")).getTime(); //得到毫秒数
    var newTime = new Date(mSec); //就得到普通的时间了
    var h = ifPlusZero(newTime.getHours()); //获取系统时，
    var m = ifPlusZero(newTime.getMinutes()); //分
    var s = ifPlusZero(newTime.getSeconds()); //秒
    //console.log('+++++++++++++++++++++')
    //if()
    //console.log(h+':'+m+':'+s);
    var time = h+':'+m+':'+s;
    return time;
}
function changeTimeFormatYMD(mSec){
    //var oldTime = (new Date("2012/12/25 20:11:11")).getTime(); //得到毫秒数
    var newTime = new Date(mSec); //就得到普通的时间了
    var y=ifPlusZero(newTime.getFullYear());
    var m=ifPlusZero(newTime.getMonth()+1);
    var d=ifPlusZero(newTime.getDate());
    //console.log('+++++++++++++++++++++')
    //if()
    //console.log(h+':'+m+':'+s);
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
        //console.log(normalInfo,'findMemberInList');
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
    console.log(list);
    var sHTML = '';
    for(var i = 0;i<list.length;i++){
        var curList = list[i];
        var content = curList.latestMessage.content.content;
        var targetId = curList.targetId
        var lastTime = changeTimeFormat(curList.sentTime);
        var member = findMemberInList(targetId);
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

    $('.usualChatListUl').html(sHTML);
}


//包括单聊，群聊，聊天室
function sendMsg(content,targetId,way){
    // 定义消息类型,文字消息使用 RongIMLib.TextMessage
    var msg = new RongIMLib.TextMessage({content:content,extra:"附加信息"});
    //或者使用RongIMLib.TextMessage.obtain 方法.具体使用请参见文档
    //var msg = RongIMLib.TextMessage.obtain("hello");
    var conversationtype = RongIMLib.ConversationType[way]; // 私聊
    var targetId = targetId; // 目标 Id
    RongIMClient.getInstance().sendMessage(conversationtype, targetId, msg, {
            // 发送消息成功
            onSuccess: function (message) {
                //message 为发送的消息对象并且包含服务器返回的消息唯一Id和发送消息时间戳
                sendInBox(message);
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
function sendInBox(msg){
    var sendMsg = msg.content.content;
    var sHTML = '<li messageUId="'+msg.messageUId+'" sentTime="'+msg.sentTime+'" class="mr-chatContentR clearfix">'+
                    '<div class="mr-ownChat">'+
                        '<span>'+sendMsg+'</span>'+
                        '<i></i>'+
                    '</div>'+
                '</li>';
    var parentNode = $('.mr-chatview .mr-chatContent');
    parentNode.append($(sHTML));
    $('.textarea').val('');
}


//接收到的消息显示在盒子里或者在消息列表中显示
function reciveInBox(msg){
    console.log(msg);
    var targetID = msg.targetId;
    var content = msg.content.content;
    var $MesContainerSelf = $('.mesContainerSelf')
    if(!$MesContainerSelf.hasClass('chatHide')||$MesContainerSelf.attr('targetID')==targetID){
        //在盒子里显示
        //头像需要自己找？、？
        var sHTML = '<li messageUId="'+msg.messageUId+'" sentTime="'+msg.sentTime+'" class="mr-chatContentL clearfix">'+
                        '<img src="page/web/css/img/1.jpg">'+
                        '<div class="mr-chatBox">'+
                            '<span>'+content+'</span>'+
                            '<i></i>'+
                        '</div>'+
                    '</li>';
        var parentNode = $('.mesContainer').find('.mr-chatContent');
        parentNode.append($(sHTML));
        //clearNoReadMsg($('.mesContainer').attr('targettype'),targetID);
        //msgReaded(msg.messageUId,msg.sentTime,$('.mesContainer').attr('targettype'));

    }else{
        //消息列表里显示红色小圆圈
        //刷新消息列表
        getConverList();
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