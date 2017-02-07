/**
 * Created by zhu_jq on 2017/1/12.
 */
$(function(){

    //点击会话标题上的地图跳到定位
    $('.mr-Location').click(function(){
        var $parentNode = $(this).parents('.mesContainer');
        var targetType = $parentNode.attr('targettype');
        var targetId = $parentNode.attr('targetid');
        if(targetType=='PRIVATE'){
            $('.chatMenu li')[1].click();
            $('.usualChatList').find('li[targetid='+targetId+']').dblclick();
        }else if(targetType=='GROUP'){
            $('.chatMenu li')[1].click();
            $('.groupChatList').find('li[targetid='+targetId+']').dblclick();
        }
    })
    //文件拖拽上传
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

//包括单聊，群聊，聊天室
function sendMsg(content,targetId,way,extra,callback){
    //发出去的消息 先显示到盒子里,
    //sendInBox(content,way,callback);
    if(extra=='uploadFile'){//如果是上传文件
        //var content = JSON.parse(content);
        var sendMsg = JSON.parse(content);
        var uniqueTime = sendMsg.uniqueTime;
        var Msize = KBtoM(sendMsg.size);
        switch (sendMsg.type){
            case 'image/png':
                var imgSrc = 'css/img/formatImg.png';
                break;
            default :
                var imgSrc = 'css/img/formatUnknew.png';
        }
        //var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
        var sHTML = '<li class="mr-chatContentRFile clearfix">'+
            '<div class="mr-ownChat">'+
            '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
            '<div class="file_content fl">' +
            '<p class="p1 file_name">'+sendMsg.name+'</p>' +
            '<p class="p2 file_size">'+Msize+'</p>' +
            '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>' +
            '</div>' +
            '</div>' +
            '<a class="downLoadFile" src="'+globalVar.qiniuDOMAN+sendMsg.name+'"></a>'+
            '<button id="downLoadFile"></button>'+

            '</li>';
    }else{//如果是普通消息
        var str = RongIMLib.RongIMEmoji.symbolToHTML(content);
        var sHTML = '<li class="mr-chatContentR clearfix">'+
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
    //将消息放入盒子
    parentNode.append($(sHTML));
    //滚动条滚动到最低
    eDom.scrollTop = eDom.scrollHeight;
    //写消息区域清空
    parent.find('.textarea').html('');
    callback&&callback();
    //调用融云的发送文件
    if(extra!='uploadFile'){
        sendByRong(content,targetId,way);
    }
}

function sendByRong(content,targetId,way,extra){
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
//function sendInBox(msg,way,callback){
//
//    //var sendMsg = msg.content.content;
//    //var extra = msg.content.extra;
//    if(extra=='uploadFile'){
//        var sendMsg = JSON.parse(sendMsg);
//        var uniqueTime = sendMsg.uniqueTime;
//        var Msize = KBtoM(sendMsg.size);
//        switch (sendMsg.type){
//            case 'image/png':
//                var imgSrc = 'page/web/css/img/backstage.png';
//                break;
//        }
//        //var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
//        var sHTML = '<li messageUId="'+msg.messageUId+'" sentTime="'+msg.sentTime+'" class="mr-chatContentRFile clearfix">'+
//            '<div class="mr-ownChat">'+
//            '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
//            '<div class="file_content fl">' +
//            '<p class="p1 file_name">'+sendMsg.name+'</p>' +
//            '<p class="p2 file_size">'+Msize+'</p>' +
//            '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>' +
//            '</div>' +
//            '</div>' +
//            '<a class="downLoadFile" src="'+globalVar.qiniuDOMAN+sendMsg.name+'"></a>'+
//            '</li>';
//    }else{
//        var str = RongIMLib.RongIMEmoji.symbolToHTML(sendMsg);
//        var sHTML = '<li messageUId="'+msg.messageUId+'" sentTime="'+msg.sentTime+'" class="mr-chatContentR clearfix">'+
//            '<div class="mr-ownChat">'+
//            '<span>'+str+'</span>'+
//            '<i></i>'+
//            '</div>'+
//            '</li>';
//    }
//    if(way=='PRIVATE'){
//        var parent = $('.mesContainerSelf');
//        var parentNode = $('.mesContainerSelf .mr-chatview .mr-chatContent');
//        var eDom=document.querySelector('#perContainer .mr-chatview');
//
//    }else{
//        var parent = $('.mesContainerGroup');
//        var parentNode = $('.mesContainerGroup .mr-chatview .mr-chatContent');
//        var eDom=document.querySelector('#groupContainer .mr-chatview');
//    }
//    parentNode.append($(sHTML));
//    eDom.scrollTop = eDom.scrollHeight;
//    parent.find('.textarea').html('');
//    callback&&callback();
//}

function fillGroupPage(targetID,targetType,groupName){
    RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[targetType], targetID, 0, 20, {
        onSuccess: function(list, hasMsg) {
            if(list.length==0 && !hasMsg){
                $('#groupContainer .mr-chatview').attr('data-on',0);
            }else{
                $('#groupContainer .mr-chatview').attr('data-on',1);
            }
            var sDoM = '<ul class="mr-chatContent">';
            sDoM=createConversationList(sDoM,list,targetType);
            sDoM+='</ul>';
            $('#groupContainer .mr-chatview').empty();
            $('#groupContainer .mr-chatview').append(sDoM);
            var eDom=document.querySelector('#groupContainer .mr-chatview');
            //console.log(eDom.scrollHeight);
            if(eDom.scrollHeight>$('#groupContainer .mr-chatview').height()){
                eDom.scrollTop = eDom.scrollHeight;
            }
        },
        onError: function(error) {
            // APP未开启消息漫游或处理异常
            // throw new ERROR ......
        }
    });
}

function conversationGroup(targetID,targetType,groupName){
    //噗页面
    fillGroupPage(targetID,targetType,groupName)
    //清空消息盒子
    $('.message-content').html();
    //换title
    $('.perSetBox-title span').html(groupName);
    //将重要信息放到title的属性上
    $('.mesContainerGroup').attr('targetID',targetID)
    $('.mesContainerGroup').attr('targetType',targetType)
    //页面滚动条
    var $container = $('#groupContainer .mr-chatview');
    var eDom=document.querySelector('#groupContainer .mr-chatview');
    if(eDom.scrollHeight>$('#groupContainer .mr-chatview').height()){
        $container.perfectScrollbar();
        $container.scroll(function(e) {
            if($container.scrollTop() === 0) {
                if($container.attr('data-on')==0){
                    return;
                }
                var stampTime=parseInt($('#groupContainer .mr-chatview').find('ul li').first().attr('data-t'));
                RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[targetType], targetID, stampTime, 20, {
                    onSuccess: function(list, hasMsg) {
                        if(list.length==0 && !hasMsg){
                            $('#groupContainer .mr-chatview').attr('data-on',0)
                        }
                        var sDoM = '';
                        sDoM=createConversationList(sDoM,list);
                        $('#groupContainer .mr-chatview ul').prepend(sDoM);
                    },
                    onError: function(error) {
                        // APP未开启消息漫游或处理异常
                        // throw new ERROR ......
                    }
                });
            }
        });
    }
    //点击emoji表情
    $('.rongyun-emoji>span').unbind('click');
    $('.rongyun-emoji>span').on('click',function(){
        var name = $(this).find('span').attr('name');
        $('.textarea').append(name);
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
    //回车的时候
    $('#message-content').click(function(){
        $(this).keypress(function(event) {
            if (event.which == 13) {
                console.log(1111);
            }
        })
    })
    //发送消息
    $('.sendMsgBTN').unbind('click')
    $('.sendMsgBTN').click(function(){
        var content = $(this).prev().html();
        if(content){
            var targetId = $('.mesContainerGroup').attr('targetID');
            var targetType = $('.mesContainerGroup').attr('targetType');

            //显示到盒子里
            sendMsg(content,targetId,targetType);
            //sendMsg(content,targetId,targetType)
        }
    });

    $('.mr-record').addClass('active');
    $('.mesContainerGroup').removeClass('mesContainer-translateL');
    getGroupDetails(targetID);
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
function createConversationList(sDoM,list,targetType){
    var timestamp = new Date().getTime();//获取当前时间戳
    var sStartTime=0;
    var sCurrentTime = changeTimeFormat(timestamp, 'yh');
    var sCurrentDateTime = changeTimeFormat(timestamp, 'y');
    //var sfiveBeforeTime=changeTimeFormat(timestamp+300000,'yh');
    for (var i = 0; i < list.length; i++) {
        var sSentTime = list[i].sentTime;
        var sContent = list[i].content.content || '';
        var extra = list[i].content.extra || '';
        switch(targetType){
                case 'GROUP':
                    var sTargetId = list[i].senderUserId;
                    var sData=window.localStorage.getItem("datas");
                    var oData= JSON.parse(sData);
                    var sId=oData.id;
                    if(sId==sTargetId){
                        sTargetId='';
                    }
                   /*sendAjax('group!listGroupMemebers',{groupid:sTargetId},function(data) {
                        var oGroupidList = JSON.parse(data);
                        var aMember=oGroupidList.text;
                    });*/
                    break;
                case 'PRIVATE':
                    var sTargetId = list[i].targetId;
                    break;
            }
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
                sDoM += ' <li data-t="'+sSentTime+'">\
                    <p class="mr-Date">' + sHoursTime + '</p>\
                    </li>';
            }else{
                sDoM += ' <li data-t="'+sSentTime+'">\
                    <p class="mr-Date">' + sCurrentTime + '</p>\
                    </li>';
            }
            sDoM=sessionContent(sDoM,sTargetId,sContent,extra,sSentTime,targetType);
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
                sDoM += '<li data-t="'+sSentTime+'">\
                        <p class="mr-Date">' + sfiveBeforeTime + '</p>\
                        </li>';
                sDoM=sessionContent(sDoM,sTargetId,sContent,extra,sSentTime,targetType)
            } else {
                sDoM=sessionContent(sDoM,sTargetId,sContent,extra,sSentTime,targetType)
            }
            sStartTime=sSentTime;
        }
        //aList=list;
        // return list;
        // hasMsg为boolean值，如果为true则表示还有剩余历史消息可拉取，为false的话表示没有剩余历史消息可供拉取。
        // list 为拉取到的历史消息列表
    }
    return sDoM;
}
function ondayTime(sCurrentTime,sContrastTime){
   // var sDateTime=changeTimeFormat(sContrastTime,'y');
    //var sDateHoursTime=changeTimeFormat(sContrastTime,'yh');
}
function sessionContent(sDoM,sTargetId,sContent,extra,sSentTime,targetType){
    if (sTargetId) {//别人的
        var oData=findMemberInList(sTargetId);
        if(oData){
            var sImg=oData.logo?globalVar.imgSrc+oData.logo:globalVar.defaultLogo;
        }else {
            var sImg=globalVar.defaultLogo;
        }
        if(extra=="uploadFile"){
            //var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
            var sendMsg = JSON.parse(sContent);
            var imgSrc = '';
            var Msize = KBtoM(sendMsg.size);
            var uniqueTime = sendMsg.uniqueTime;
            switch (sendMsg.type){
                case 'image/png':
                    var imgSrc = 'css/img/formatImg.png';
                    break;
                default :
                    var imgSrc = 'css/img/formatUnknew.png';
            }
            sDoM += '<li class="mr-chatContentLFile clearfix" data-t="'+sSentTime+'">'+
                        '<img class="headImg" src="'+sImg+'">'+
                        '<div class="mr-ownChat">'+
                        '<div class="file_type fl"><img  class="fileImg" src="'+imgSrc+'"></div>'+
                        '<div class="file_content fl">' +
                        '<p class="p1 file_name">'+sendMsg.name+'</p>' +
                        '<p class="p2 file_size">'+Msize+'</p>' +
                        '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>'+
                        '</div>' +
                        '</div>' +
                        '<a class="downLoadFile" src="'+globalVar.qiniuDOMAN+sendMsg.name+'"></a>'+
                        '<button id="downLoadFile"></button>'+
                        '</li>';

        }else{
            var str = RongIMLib.RongIMEmoji.symbolToHTML(sContent);
            sDoM += ' <li class="mr-chatContentL clearfix" data-t="'+sSentTime+'">\
                    <img src="'+sImg+'">\
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
                    var imgSrc = 'css/img/formatImg.png';
                    break;
                default :
                    var imgSrc = 'css/img/formatUnknew.png';
            }
            sDoM += '<li class="mr-chatContentRFile clearfix" data-t="'+sSentTime+'">'+
                        '<div class="mr-ownChat">'+
                        '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
                        '<div class="file_content fl">' +
                        '<p class="p1 file_name">'+sendMsg.name+'</p>' +
                        '<p class="p2 file_size">'+Msize+'</p>' +
                        '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>'+
                        '</div>' +
                        '</div>' +
                        '<a class="downLoadFile" src="'+globalVar.qiniuDOMAN+sendMsg.name+'"></a>'+
                        '<button id="downLoadFile"></button>'+
                        '</li>';
        }else{
            var str = RongIMLib.RongIMEmoji.symbolToHTML(sContent);

            sDoM += ' <li class="mr-chatContentR clearfix" data-t="'+sSentTime+'">\
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
            if(list.length==0 && !hasMsg){
                $('#description').attr('data-on',0);
            }else{
                $('#description').attr('data-on',1);
            }
            var sDoM = '<ul class="mr-chatContent">';
            sDoM=createConversationList(sDoM,list,targetType);
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
   var $container = $('#perContainer .mr-chatview');
    var eDom=document.querySelector('#groupContainer .mr-chatview');
    if(eDom.scrollHeight>$('#groupContainer .mr-chatview').height()){
        $container.perfectScrollbar();
        $container.scroll(function(e) {
            if($container.scrollTop() === 0) {
                if($container.attr('data-on')==0){
                    return;
                }
                var stampTime=parseInt($('#perContainer .mr-chatview').find('ul li').first().attr('data-t'));
                RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[targetType], targetID, stampTime, 20, {
                    onSuccess: function(list, hasMsg) {
                        /*  console.log(list);
                         console.log(hasMsg);*/
                        if(list.length==0 && !hasMsg){
                            $('#perContainer .mr-chatview').attr('data-on',0)
                        }
                        var sDoM = '';
                        sDoM=createConversationList(sDoM,list,targetType);
                        //$('#perContainer .mr-chatview').empty();
                        $('#perContainer .mr-chatview ul').prepend(sDoM);
                        /*var eDom=document.querySelector('#perContainer .mr-chatview');
                         eDom.scrollTop = eDom.scrollHeight;*/
                    },
                    onError: function(error) {
                        // APP未开启消息漫游或处理异常
                        // throw new ERROR ......
                    }
                });
                //$status.text('it reaches the top!');
            }
        });
   }
    $('.message-content').html();
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
            var sImg=oCreator.logo?globalVar.imgSrc+oCreator.logo:globalVar.defaultLogo;
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
            <img src="'+sImg+'">\
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
        }
    }
    getGroupMembersList(groupId);
}
function getGroupMembersList(groupid){
    sendAjax('group!listGroupMemebers',{groupid:groupid},function(data) {
        var oGroupidList = JSON.parse(data);
        var aMember=oGroupidList.text;
        if(aMember){

            var smemship = JSON.stringify(aMember);
            var sDom='<div class="groupInfo-number clearfix">\
            <span>成员('+aMember.length+')</span>\
            <p class="clearfix">\
            <i class="groupInfo-noChat"></i>\
            <i class="groupInfo-groupManage" memship="'+smemship+'"></i>\
            </p>\
            </div>\
            <ul class="groupInfo-memberAll">';
            if(aMember.length>0){
                for(var i=0;i<aMember.length;i++){
                    var oCreator=findMemberInList(aMember[i]);
                    var sMemberName=oCreator.name;
                    var sJob=oCreator.account;
                    var sImg=oCreator.logo?globalVar.imgSrc+oCreator.logo:globalVar.defaultLogo;
                    sDom+=' <li>\
                            <img src="'+sImg+'">\
                            <p>'+sMemberName+'('+sJob+')</p>\
                            </li>';
                }
            }
            sDom+='</ul>';
            $('#groupData .group-data .groupInfo-memberList').empty();
            $('#groupData .group-data .groupInfo-memberList').append(sDom);
            console.log(oGroupidList)
        }

    });
}
/**
 *
 * @param oInfoDetails 个人资料
 */
function getPerInfo(oInfoDetails){
    var sName=oInfoDetails.name || '';//姓名
    var sLogo=oInfoDetails.logo?  globalVar.imgSrc+oInfoDetails.logo : globalVar.defaultLogo;//头像
    var sMobile=oInfoDetails.mobile || '';//手机
    var sEmail=oInfoDetails.email || '';//邮箱
    var sBranch=oInfoDetails.sex || '';//部门
    var sJob=oInfoDetails.sex || '';//职位
    var sOrg=oInfoDetails.sex || '';//组织
    var sAddress=oInfoDetails.address || '';//地址
    var sTargetId=oInfoDetails.id || '';//ID
    var sTargetType=oInfoDetails.flag==1?'PRIVATE':'GROUP';//成员类型
    var sDom='\
        <div class="infoDet-personal clearfix">\
    <img src="'+sLogo+'">\
    <div class="infoDet-text">\
    <p>'+sName+'</p>\
    <ul class="clearfix showPersonalInfo1" targetid="'+sTargetId+'" targettype="'+sTargetType+'">\
    <li class="sendMsg"></li>\
    <li class="checkPosition"></li>\
    <li class="addConver"></li>\
    </ul>\
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
    var defaultDate=0;
    if(aInfo.length>0){
        for(var i=0;i<aInfo.length;i++){
            var sTargetId=aInfo[i].targetId;
            var sSentTime=aInfo[i].sentTime;//发送时间
            var sContent=aInfo[i].content.content||'';
            var sMessageId=aInfo[i].messageId;//信息id
            var sSentTimeReg=changeTimeFormat(sSentTime,'h');
            var sSentDate=changeTimeFormat(sSentTime,'y');
            //aDate.push(sSentDate);
                if(sSentDate != defaultDate){
                    sLi+='<li >\
                    <p class="infoDet-timeRecord">'+sSentDate+'</p>\
                </li>';
                    defaultDate=sSentDate;
                }
            if(sTargetId){
               var oThers=findMemberInList(sTargetId);
                var sName=oThers.name || '';
               sLi+='<li class="infoDet-OthersSay" data-time="'+sSentTime+'">\
                   <span>'+sName+'&nbsp&nbsp&nbsp'+sSentTimeReg+'</span>\
                <p>'+sContent+'</p>\
                </li>';
            }else{
                //var sAccount = localStorage.getItem('account');
                var sdata = localStorage.getItem('datas');
                //var account = JSON.parse(sAccount).account;
                var accountID = JSON.parse(sdata).text.id;
                var sSelfName=JSON.parse(sdata).text.fullname;
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
    RongIMLib.RongIMClient.getInstance().searchMessageByContent(RongIMLib.ConversationType[Type],targetId,null,0,20,1,{
            onSuccess:function(data, count){
                console.log(data);
                console.log(count);
                // @param {<Message>[]}     data      - 搜索的结果
                // @param {number}          count     - 搜索的消息总条数
            },
            onError:function(error){
            }
        });
    var oPagetest = new PageObj({divObj:$('.infoDet-chatRecord').find('.infoDet-pageQuery'),pageSize:20,conversationtype:Type,targetId:targetId,pageCount:60},function(type,list,callback)//声明page1
    {
        getChatRecord(list);
        //showHistoryMessages(list);

    });
    RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[Type], targetId, 0, 20, {
        onSuccess: function(list, hasMsg) {
           // console.log(list,hasMsg);
            var aDatas=list;
            for(var i=0;i<aDatas.length;i++){
                //console.log(changeTimeFormat(aDatas[i].sentTime,'yh'));
            }
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
function showHistoryMessages(list){

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
    console.log('list',list);
    for(var i = 0;i<list.length;i++){
        var curList = list[i];
        var conversationType = curList.conversationType
        var content = curList.latestMessage.content.content;
        var extra = curList.latestMessage.content.extra;
        if(extra=="uploadFile"){
            content="发送文件";
        }
        var targetId = curList.targetId
        var timeNow = new Date().getTime();
        var deltTime = timeNow-curList.sentTime;
        if(deltTime>=86400000){
            var lastTime = changeTimeFormat(curList.sentTime,'y');
        }else{
            var lastTime = changeTimeFormat(curList.sentTime,'h');
        }
        var unreadMessageCount = curList.unreadMessageCount;
        var sNum = unreadMessageCount==0?'':'<i class="notReadMsg">'+unreadMessageCount+'</i>'

        //changeTimeFormat(mSec,format)
        if(conversationType==1){
            var member = findMemberInList(targetId);
            if(member){
                //console.log('member',member);
                var logo = member.logo?globalVar.imgSrc+member.logo:globalVar.defaultLogo;
                var name = member.name || '';
                sHTML += ' <li targetid="'+targetId+'" targetType="PRIVATE">'+
                '<div><img class="groupImg" src="'+logo+'" alt=""/>'+
                sNum+
                '<span class="groupName">'+name+'</span>'+
                '<span class="usualLastMsg">'+content+'</span>'+
                '<span class="lastTime">'+lastTime+'</span>'+
                '</div>'+
                '</li>'
            }
        }else if(conversationType==3){
            var curGroup = groupInfo(targetId);
            //if(curGroup){
                sHTML += ' <li targetid="'+targetId+'" targetType="GROUP">'+
                '<div><img class="groupImg" src="'+globalVar.defaultDepLogo+'" alt=""/>'+
                sNum+
                '<span class="groupName">'+curGroup.name+'</span>'+
                '<span class="usualLastMsg">'+content+'</span>'+
                '<span class="lastTime">'+lastTime+'</span>'+
                '</div>'+
                '</li>'
            //}
        }
    }
    $('.usualChatListUl').html(sHTML);
}

//查询单个群信息
function groupInfo(id){
    var groupInfo = localStorage.getItem('groupInfo');
    if(groupInfo){
        groupInfo = JSON.parse(groupInfo);
    }
    var curInfo = '';
    for(var i = 0;i<groupInfo.text.length;i++){
        if(groupInfo.text[i].id==id){
            curInfo = groupInfo.text[i]
        }
    }
    return curInfo;
    //sendAjax(url,data,callback)
}



function KBtoM(kb){
    return kb/1024;
}
//接收到的消息显示在盒子里或者在消息列表中显示
function reciveInBox(msg){


    if (window.Electron) {
        window.Electron.updateBadgeNumber(2);

        //setTimeout(function(){
        //    window.Electron.updateBadgeNumber(0);
        //},2000)
        //var option = {};
        //option.body = 'aaaa';
        //window.Electron.displayBalloon('title',option);
    }

    console.log(msg);
    var targetID = msg.targetId;
    var content = msg.content.content;
    var extra = msg.content.extra;
    var targetType = msg.conversationType;
    //var
    debugger;
    if(targetType==3){//qunliao
        var oData=findMemberInList(targetID);
        var sImg=oData.logo?globalVar.imgSrc+oData.logo:globalVar.defaultLogo;
        var $MesContainer = $('.mesContainerGroup')
        var eDom = document.querySelector('#groupContainer .mr-chatview');
    }else if(targetType==1){//个人聊天
        var oData=findMemberInList(targetID);
        var sImg=oData.logo?globalVar.imgSrc+oData.logo:globalVar.defaultLogo;
        var $MesContainer = $('.mesContainerSelf')
        var eDom = document.querySelector('#perContainer .mr-chatview');
    }
    if(extra=='uploadFile'){
        if (!$MesContainer.hasClass('chatHide') || $MesContainer.attr('targetID') == targetID) {
            var content = JSON.parse(content);
            console.log('sendMsg',content);

            var Msize = KBtoM(content.size);
            switch (content.type){
                case 'image/png':
                    var imgSrc = 'css/img/formatImg.png';
                    break;
                default :
                    var imgSrc = 'css/img/formatUnknew.png';
            }
            var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
            var sHTML = '<li class="mr-chatContentLFile clearfix">'+
                '<img class="headImg" src="'+sImg+'">'+
                '<div class="mr-chatBox">'+
                '<div class="file_type fl"><img class="fileImg" src="'+imgSrc+'"></div>'+
                '<div class="file_content fl">' +
                '<p class="p1 file_name">'+content.name+'</p>' +
                '<p class="p2 file_size">'+Msize+'</p>' +
                '<div id="up_process"><div id="up_precent"></div>' +
                '</div>' +
                '</div>' +
                '<a class="downLoadFile" src="'+globalVar.qiniuDOMAN+sendMsg.name+'"></a>'+
                '<button id="downLoadFile"></button>'+
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
                '<img src="'+globalVar.defaultLogo+'">' +
                '<div class="mr-chatBox">' +
                '<span>' + str + '</span>' +
                '<i></i>' +
                '</div>' +
                '</li>';

            var parentNode = $MesContainer.find('.mr-chatview .mr-chatContent');
            parentNode.append($(sHTML));

            eDom.scrollTop = eDom.scrollHeight;
        } else {
            //消息列表里显示红色小圆圈
            //刷新消息列表
        }
    }

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