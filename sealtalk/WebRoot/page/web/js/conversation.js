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



    $('.sendMsg').unbind('click')
    $('.sendMsg').click(function(){
        var content = $(this).prev().val();
        //var targetID = targetId
        var targetId = $('.mesContainerSelf').attr('targetID');
        var targetType = $('.mesContainerSelf').attr('targetType');
        //if()
        sendMsg(content,targetId,targetType)
    })
    $('.orgNavClick').addClass('chatHide');
    $('.mesContainerSelf').removeClass('chatHide');
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
                showInBox(message);
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


function showInBox(msg){
    console.log('%%%%%%%%%%%%%%%%%%%%%%')
    console.log(msg.content.content);
    var sendMsg = msg.content.content;
    var sHTML = '<li class="mr-chatContentR">'+
                    '<div class="mr-ownChat">'+
                        '<span>'+sendMsg+'</span>'+
                        '<i></i>'+
                    '</div>'+
                '</li>';
    var parentNode = $('.mr-chatview .mr-chatContent');
    parentNode.append($(sHTML));
}