/**
 * Created by zhu_jq on 2017/1/12.
 */
$(function(){


})

function conversationSelf(targetID,targetType){//群聊页面显示
    //var target = targetID;
    //噗页面 把targetID放进去
    $('.mesContainerSelf').attr('targetID',targetID)
    $('.mesContainerSelf').attr('targetType',targetType)


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
    $('.mr-record').addClass('active');
    $('.mesContainerSelf').removeClass('mesContainer-translateL');
    getInfoDetails();
    console.log(targetID);
    console.log(findMemberInList(targetID));
    //findMemberInList(targetID)
}
function getInfoDetails(){

}

//获取历史消息、消息记录
function historyMsg(Type,targetId){
    getConversation
    RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[Type], targetId, null, 20, {
        onSuccess: function(list, hasMsg) {
            console.log(list,hasMsg);
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
function showConverList(){
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
    var time = h+':'+m+':'+s
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

        sHTML += ' <li targetid="'+targetId+'">'+
        '<div><img class="groupImg" src="'+logo+'" alt=""/>'+
        '<i class="notReadMsg">'+unreadMessageCount+'</i>'+
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
    var sHTML = '<li class="mr-chatContentR clearfix">'+
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
        var sHTML = '<li class="mr-chatContentL clearfix">'+
                        '<img src="page/web/css/img/1.jpg">'+
                        '<div class="mr-chatBox">'+
                            '<span>'+content+'</span>'+
                            '<i></i>'+
                        '</div>'+
                    '</li>';
        var parentNode = $('.mesContainer').find('.mr-chatContent');
        parentNode.append($(sHTML));
        msgReaded(msg.messageUId,msg.sentTime,$('.mesContainer').attr('targettype'));

    }else{
        //消息列表里显示红色小圆圈


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