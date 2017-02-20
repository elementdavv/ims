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

})

//包括单聊，群聊，聊天室
function sendMsg(content,targetId,way,extra,callback){
    //发出去的消息 先显示到盒子里,
    //权限有没有
    var limit = $('body').attr('limit');
    if(limit.indexOf('ltszfqgrlt')==-1&&way== 'GROUP'){//没有权限
        var sGroupConverLisit = '<p class="converLimit">!</p>';
        new Window().alert({
            title   : '',
            content : '您无群组聊天权限！',
            hasCloseBtn : false,
            hasImg : true,
            textForSureBtn : false,
            textForcancleBtn : false,
            autoHide:true
        });
    }else{
        var sGroupConverLisit = ''
    }

    if(extra=='uploadFile'){//如果是上传文件
        //var content = JSON.parse(content);
        var sendMsg = JSON.parse(content);
        var uniqueTime = sendMsg.uniqueTime;
        var sFilePaste=sendMsg.filepaste;
        var Msize = KBtoM(sendMsg.size);
        if(sendMsg.type!='image/png'&&sendMsg.type!='image/jpeg'){
            var imgSrc = imgType(sendMsg.type);
            var file = sendMsg.name.split('.')[0];
            //var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
            var sHTML = '<li class="mr-chatContentRFile clearfix">'+
                '<div class="mr-ownChat">'+
                '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
                '<div class="file_content fl">' +
                '<p class="p1 file_name" data-type="'+sendMsg.type+'">'+sendMsg.name+'</p>' +
                '<p class="p2 file_size" data-s="'+sendMsg.size+'">'+Msize+'</p>' +
                '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>' +
                '</div>' +
                '</div>' +
                '<a fileName="'+uniqueTime+'" class="downLoadFile" href="'+returnDLLink(sendMsg.name)+'"></a>' +
                    //'<button class="downLoadFileMask"></button>' +
                '</li>';
        }else{//上传的是图片类型的文件
            var sHTML = '<li class="mr-chatContentRFile clearfix">'+
                    '<img uniqueTime="'+uniqueTime+'" src="'+globalVar.cssImgSrc+'imgLoading.gif" class="uploadImg uploadImgFile">'+
                    '</li>';
        }
    }else{//如果是普通消息
        var str = RongIMLib.RongIMEmoji.symbolToHTML(content);
        var sHTML = '<li class="mr-chatContentR clearfix">'+
            '<div class="mr-ownChat">'+
            '<span>'+str+'</span>'+
            '<i></i>'+
            '</div>'+
            sGroupConverLisit+
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
    $('.uploadImgFile').on('load',function(){
        eDom.scrollTop = eDom.scrollHeight;
    })
    eDom.scrollTop = eDom.scrollHeight;

    //写消息区域清空
    parent.find('.textarea').html('');
    callback&&callback();
    //调用融云的发送文件
    if(extra!='uploadFile'&&(limit.indexOf('ltszwjsc')!=-1||way== 'PRIVATE')){
        sendByRong(content,targetId,way);
    }
}
//上传文件
function sendByRongFile(content,targetId,way,extra){

    var msg = new RongIMLib.FileMessage(content);
    var conversationtype = RongIMLib.ConversationType[way]; // 私聊,其他会话选择相应的消息类型即可。
    RongIMClient.getInstance().sendMessage(conversationtype, targetId, msg, {
            onSuccess: function (message) {
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
//上传文件为图片类型
function sendByRongImg(content,targetId,way){
    var conversationtype = RongIMLib.ConversationType[way]; // 私聊,其他会话选择相应的消息类型即可。
    var msg = new RongIMLib.ImageMessage(content);
    RongIMClient.getInstance().sendMessage(conversationtype, targetId, msg, {
            onSuccess: function (message) {
                //message 为发送的消息对象并且包含服务器返回的消息唯一Id和发送消息时间戳
                console.log("Send successfully");
                getConverList();
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
                        //info = x;
                        break;
                }
                console.log('发送失败:' + info);
            }
        }
    );
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
                        info = '已禁言';
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
            var sDoM = '<div class="mr-chatview"><ul class="mr-chatContent">';
            sDoM=createConversationList(sDoM,list,targetType);
            sDoM+='</ul></div>';
            $('.orgNavClick').addClass('chatHide');
            $('.mesContainerGroup').removeClass('chatHide');
            $('.mesContainerGroup .mr-chatview').remove();
            $('.mr-record').addClass('active');
            $('.mesContainerGroup').removeClass('mesContainer-translateL');
            //$('#groupContainer .mr-chatview').empty();
            $('#groupContainer .mr-chateditBox').before(sDoM);
            var eDom=document.querySelector('#groupContainer .mr-chatview');
            if(eDom.scrollHeight>$('#groupContainer .mr-chatview').height()){
                if($('#groupContainer .uploadImgFile').length!=0){
                    $('.uploadImgFile').on('load',function(){
                        eDom.scrollTop = eDom.scrollHeight;
                    })
                }else{
                    eDom.scrollTop = eDom.scrollHeight;
                }
            }
            var $container = $('#groupContainer .mr-chatview');
            var eDom=document.querySelector('#groupContainer .mr-chatview');
            if(eDom.scrollHeight>$('#groupContainer .mr-chatview').height()){
                //$container.perfectScrollbar();
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
        }
        //if($(this).prev().find('.uploadImgFile').length>0){
        //    var sImg = $(this).prev().find('.uploadImgFile').attr('src');
        //    var content={};
        //    content.name=sImg.split('attname=')[1];
        //   var  sType=sImg.split('.')[sImg.split('.').length-1];
        //    switch (sType){
        //        case "png":
        //            sType="images/png";
        //            break;
        //        case 'jpg':
        //            sType="image/jpeg";
        //            break;
        //    }
        //    content.type=sType;
        //   content = JSON.stringify(content);
        //    var targetId = $('.mesContainerGroup').attr('targetID');
        //    var targetType = $('.mesContainerGroup').attr('targetType');
        //    var extra = "uploadFile";
        //    //显示到盒子里
        //    sendMsg(content,targetId,targetType,extra);
        //}else{
        //}
    });

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
function createConversationList(sDoM,list,targetType){
    var timestamp = new Date().getTime();//获取当前时间戳
    var sStartTime=0;
    var sCurrentTime = changeTimeFormat(timestamp, 'yh');
    var sCurrentDateTime = changeTimeFormat(timestamp, 'y');
    for (var i = 0; i < list.length; i++) {
        var sSentTime = list[i].sentTime;
        var extra = list[i].messageType || '';
        var sContent = extra=='TextMessage'?list[i].content.content:list[i].content ;
        switch(targetType){
                case 'GROUP':
                    var sTargetId = list[i].senderUserId;
                    var sData=window.localStorage.getItem("datas");
                    var oData= JSON.parse(sData);
                    var sId=oData.id;
                    if(sId==sTargetId){
                        sTargetId=sId;
                    }
                    break;
                case 'PRIVATE':
                    var sTargetId = list[i].senderUserId;
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
        } else {
            var sNowTime1 = new Date().getTime();//获取当前时间戳
            var sNowCurrentTime1 = changeTimeFormat(sNowTime1, 'y');
            if (sSentTime - sStartTime >300000) {
                if(sNowCurrentTime1==sDateTime){
                    var sfiveBeforeTime = changeTimeFormat(sSentTime, 'h');
                }else{
                    var sfiveBeforeTime = changeTimeFormat(sSentTime, 'yh');
                }
                sDoM += '<li data-t="'+sSentTime+'">\
                        <p class="mr-Date">' + sfiveBeforeTime + '</p>\
                        </li>';
            }
            sStartTime=sSentTime;
        }
        sDoM=sessionContent(sDoM,sTargetId,sContent,extra,sSentTime,targetType);
    }
    return sDoM;
}
//function ondayTime(sCurrentTime,sContrastTime){
//   // var sDateTime=changeTimeFormat(sContrastTime,'y');
//    //var sDateHoursTime=changeTimeFormat(sContrastTime,'yh');
//}
function sessionContent(sDoM,sTargetId,sContent,extra,sSentTime,targetType){
    var sdata = localStorage.getItem('datas');
    var accountObj = JSON.parse(sdata);
    var accountID = accountObj.id;
    if (sTargetId!=''&&sTargetId !=accountID) {//别人的发的
        var oData=findMemberInList(sTargetId);
        if(oData){
            var sImg=oData.logo?globalVar.imgSrc+oData.logo:globalVar.defaultLogo;
        }else {
            var sImg=globalVar.defaultLogo;
        }
        switch(extra){
            case "FileMessage":
                var Msize = KBtoM(sContent.size);
                var fileURL = sContent.fileUrl;
                var imgSrc = imgType(sContent.type)
                var file = getFileUniqueName(fileURL);
                var fileOperate = '';
                var downLoadFile = '';
                if(window.Electron) {
                    var localPath = window.Electron.chkFileExists(fileURL);
                    if (localPath) {
                        fileOperate = '<div id="fileOperate">' +
                        '<span class="openFile">打开文件</span>' +
                        '<span class="openFloder">打开文件夹</span>' +
                        '</div>'
                    } else {
                        downLoadFile = '<a fileName="' + file + '"  class="downLoadFile" href="' + fileURL + '"></a>' ;
                    }
                }

                sDoM += '<li class="mr-chatContentLFile clearfix" data-t="' + sSentTime + '">' +
                            '<img class="headImg" src="' + sImg + '">' +
                            '<div class="mr-ownChat">' +
                                '<div class="file_type fl"><img  class="fileImg" src="' + imgSrc + '"></div>' +
                                '<div class="file_content fl">' +
                                '<p class="p1 file_name">' + sContent.name + '</p>' +
                                '<p class="p2 file_size data-s="'+sContent.size+'">' + Msize + '</p>' +
                            '</div>' +
                            downLoadFile+fileOperate+
                        '</li>';
                break;
            case "ImageMessage":
                sDoM += ' <li class="mr-chatContentL clearfix" data-t="'+sSentTime+'">'+
                            '<img class="headImg" src="'+sImg+'">'+
                            '<img src="'+sContent.imageUri+'" class="uploadImgLeft uploadImgFile">'+
                        '</li>';
                break;
            case "InformationNotificationMessage":
                sDoM += '<li data-t="1486971032807"><p class="mr-Date">'+sContent.message+'</p></li>'
                break;
            case "TextMessage":
                var str = RongIMLib.RongIMEmoji.symbolToHTML(sContent);
                sDoM += ' <li class="mr-chatContentL clearfix" data-t="' + sSentTime + '">\
                            <img class="headImg" src="' + sImg + '">\
                            <div class="mr-chatBox">\
                                <span>' + str + '</span>\
                                <i></i>\
                            </div>\
                        </li>';
                break;
            case "VoiceMessage":
                var base64Str = sContent.content;
                var duration = base64Str.length/1024;
                //w:20px~170px  durating:1s~50s
                var curWidth = duration*3+20;
                if(curWidth>170){
                    curWidth = 170;
                }
                RongIMLib.RongIMVoice.preLoaded(base64Str);
                RongIMLib.RongIMVoice.play(base64Str,duration);
                RongIMLib.RongIMVoice.stop(base64Str);


                sDoM += ' <li class="mr-chatContentL clearfix" data-t="">' +
                    '<img class="headImg" src="'+sImg+'">'+
                    '<div class="mr-chatBox">'+
                    '<p class="voiceMsgContent" style="width:'+curWidth+'px" base64Str="'+base64Str+'"></p>'+
                    '</div>'+
                    '<p class="voiceSecond"><span>'+sContent.duration+'S</span></p>'+
                    '</li>';
                break;
        }
    }else {//自己的
        switch(extra){
            case "FileMessage":
                var sendMsg = sContent;
                var Msize = KBtoM(sendMsg.size);
                var imgSrc = imgType(sContent.type)
                var file = getFileUniqueName(sendMsg.fileUrl);
                var fileOperate = '';
                var downstyle = '';
                if(window.Electron) {
                    if(sendMsg.fileUrl){
                        var localPath = window.Electron.chkFileExists(sendMsg.fileUrl);
                        if (localPath) {
                            fileOperate = '<div id="fileOperate">' +
                            '<span class="openFile">打开文件</span>' +
                            '<span class="openFloder">打开文件夹</span>' +
                            '</div>'
                        } else {
                            downLoadFile = '<a fileName="'+file+'" class="downLoadFile" href="'+sendMsg.fileUrl+'"></a>' ;
                        }
                    }else{
                        //downLoadFile = '<a fileName="'+file+'" class="downLoadFile" href="'+sendMsg.fileUrl+'"></a>' ;
                    }
                }
                sDoM += '<li class="mr-chatContentRFile clearfix" data-t="'+sSentTime+'">'+
                            '<div class="mr-ownChat">'+
                                '<div class="file_type fl"><img src="'+imgSrc+'"></div>'+
                                '<div class="file_content fl">' +
                                '<p class="p1 file_name">'+sendMsg.name+'</p>' +
                                '<p class="p2 file_size" data-s="'+sendMsg.size+'">'+Msize+'</p>' +
                            '</div>' +
                            '<a fileName="'+file+'" class="downLoadFile" href="'+sendMsg.fileUrl+'"></a>'+
                            fileOperate+
                        '</li>';
                break;
            case "ImageMessage":
                sDoM += ' <li class="mr-chatContentL clearfix" data-t="'+sSentTime+'">'+
                        '<img src="'+sContent.imageUri+'" class="uploadImg uploadImgFile">'+
                        '</li>';
                break;
            case "InformationNotificationMessage":
                sDoM += '<li data-t="1486971032807"><p class="mr-Date">'+sContent.message+'</p></li>'
                break;
            case "TextMessage":
                var str = RongIMLib.RongIMEmoji.symbolToHTML(sContent);
                sDoM += ' <li class="mr-chatContentR clearfix" data-t="'+sSentTime+'">\
                            <div class="mr-ownChat">\
                            <span>' + str + '</span>\
                            <i></i>\
                            </div>\
                            </li>';
                break;
        }
    }
    return sDoM;
}

function fillSelfPage(targetID,targetType){
    RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[targetType], targetID, 0, 20, {
        onSuccess: function(list, hasMsg) {
            if(list.length==0 && !hasMsg){
                $('#perContainer .mr-chatview').attr('data-on',0);
                //$('#description').attr('data-on',0);
            }else{
                $('#perContainer .mr-chatview').attr('data-on',1);
            }
            var sDoM = ' <div class="mr-chatview"><ul class="mr-chatContent">';
            sDoM=createConversationList(sDoM,list,targetType);
            sDoM+='</ul></div>';
            $('#perContainer').find('.mr-chatview').remove();
            $('#perContainer .mr-chatview').empty();
            $('.orgNavClick').addClass('chatHide');
            $('.mesContainerSelf').removeClass('chatHide');
            $('.mr-record').addClass('active');
            $('#perContainer .mr-chateditBox').before(sDoM);
            var eDom=document.querySelector('#perContainer .mr-chatview');
            eDom.scrollTop = eDom.scrollHeight;
            if($('#perContainer .uploadImgFile').length!=0){
                $('.uploadImgFile').on('load',function(){
                    eDom.scrollTop = eDom.scrollHeight;
                })
            }else{
                eDom.scrollTop = eDom.scrollHeight;
            }
            var $container = $('#perContainer .mr-chatview');
            var eDom=document.querySelector('#perContainer .mr-chatview');
            if(eDom.scrollHeight>$('#perContainer .mr-chatview').height()){
                //$container.perfectScrollbar();
                $container.scroll(function(e) {
                    if($container.scrollTop() === 0) {
                        if($container.attr('data-on')==0){
                            return;
                        }
                        var stampTime=parseInt($('#perContainer .mr-chatview').find('ul li').first().attr('data-t'));
                        RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[targetType], targetID, stampTime, 20, {
                            onSuccess: function(list, hasMsg) {
                                if(list.length==0 && !hasMsg){
                                    $('#perContainer .mr-chatview').attr('data-on',0)
                                }
                                var sDoM = '';
                                sDoM=createConversationList(sDoM,list,targetType);
                                $('#perContainer .mr-chatview ul').prepend(sDoM);
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
    $('.mesContainerSelf').removeClass('mesContainer-translateL');
    var curTargetList = findMemberInList(targetID);
    var name = curTargetList.name;
    $('.perSetBox-title span').html(name);
    $('.mesContainerSelf').attr('targetID',targetID);
    $('.mesContainerSelf').attr('targetType',targetType);
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
    //获取右侧的联系人资料聊天记录
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
    var sdata = localStorage.getItem('datas');
    var accountID = JSON.parse(sdata).id;
    var voiceState = '';
    sendAjax('fun!getNotRecieveMsg',{groupid:groupId,userid:accountID},function(data){
        if(data){
            var datas = JSON.parse(data);
            if(datas&&datas.code==1){
                voiceState = datas.code==true?'active':'';
                $('.voiceSet').addClass('active');
            }
        }
    })
    for(var i = 0;i<aText.length;i++){
        if(aText[i].GID==groupId){
             var sName=aText[i].name || '';//群名称
            var sCreatorId=aText[i].mid;//群创建者id
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
            <p class="voiceSet '+voiceState+'">\
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
            <i class="groupInfo-noChat" data-groupid="'+groupid+'"></i>\
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
            console.log(oGroupidList);
            //查询群禁言状态
            sendAjax('group!getShutUpGroupStatus',{groupid:groupid},function(data){
                var sdata = localStorage.getItem('datas');
                var accountID = JSON.parse(sdata).id;
                var groupInfo = groupInfoFromList(groupid);
                if(data){
                    var datas = JSON.parse(data);
                    if(datas&&datas.code==1&&datas.text==true&&accountID!=groupInfo.mid){
                        $('.groupInfo-noChat').attr('data-chat','1');
                        $('#groupContainer #message-content').attr('contenteditable','false');
                        $('#groupContainer #message-content').html('群主已开启禁言!');

                    }else if(datas&&datas.code==0&&datas.text==false){
                        $('.groupInfo-noChat').attr('data-chat','0');
                        $('#groupContainer #message-content').attr('contenteditable','true');
                        $('#groupContainer #message-content').attr('placeholder','请输入文字...');

                    }
                }
            })
        }

    });
}
/**
 *
 * @param oInfoDetails 个人资料
 */
function getPerInfo(oInfoDetails){
    console.log(oInfoDetails);
    var sTargetId = oInfoDetails.id
    //var memberInfoFromList = searchFromList(1,sTargetId);
    var sName=oInfoDetails.name || '';//姓名
    var sLogo=oInfoDetails.logo?  globalVar.imgSrc+oInfoDetails.logo : globalVar.defaultLogo;//头像
    var sMobile=oInfoDetails.mobile || '';//手机
    var sEmail=oInfoDetails.email || '';//邮箱
    var sBranch=oInfoDetails.branchname || '';//部门
    var sJob=oInfoDetails.postitionname || '';//职位
    var sOrg=oInfoDetails.organname || '';//组织
    var sAddress=oInfoDetails.address || '';//地址
    var sTargetType=oInfoDetails.flag==1?'PRIVATE':'GROUP';//成员类型
    var sDom='\
        <div class="infoDet-personal clearfix">\
    <img src="'+sLogo+'">\
    <div class="infoDet-text">\
    <p>'+sName+'</p>\
    <ul class="clearfix showPersonalInfo showPerCainter" targetid="'+sTargetId+'" targettype="'+sTargetType+'">\
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
function getChatRecord(aList,sClass){
    var sDom='<ul class="infoDet-contentDet">';
    var sLi='';
    var aInfo=aList;
    $(sClass).empty();
    var aDate=[];
    var defaultDate=0;
    if(aInfo.length>0){
        for(var i=0;i<aInfo.length;i++){
            var sTargetId=aInfo[i].senderUserId;//f发送者id
            var sSentTime=aInfo[i].sentTime;//发送时间
            var sExtra=aInfo[i].messageType;//信息信息类型
            var sContent=aInfo[i].content;
            if(sExtra=='FileMessage'){
                //var sendMsg = JSON.parse(sContent);
               // var imgSrc = '';
                var imgSrc = imgType(sContent.type);
                var Msize = KBtoM(sContent.size);
                var uniqueTime = sContent.uniqueTime;
                var fileURL=sContent.fileUrl;
                var file = getFileUniqueName(fileURL);
                sContent= '<div class="downLoadFileInfo clearfix">'+
                '<div class="file_typeHos fl"><img src="'+imgSrc+'"></div>'+
                '<div class="file_contentHos fl">' +
                '<p class="p1 file_nameHos">'+sContent.name+'</p>' +
                '<p class="p2 file_sizeHos">'+Msize+'</p>' +
                '<div id="up_process" uniqueTime="'+uniqueTime+'"><div id="up_precent" uniqueTime="'+uniqueTime+'"></div>' +
                '</div>' +
                '</div>' +
                '<a fileName="' + file + '"  class="downLoadFile" href="' + fileURL + '"></a>';
            }else if(sExtra=="ImageMessage"){
                var imgURL=sContent.imageUri;
                sContent='<img src="'+imgURL+'">';
            }else{
                var sTextContent=sContent.content;
                var  str= RongIMLib.RongIMEmoji.symbolToHTML(sTextContent);
                sContent='<span>'+str+'</span><i></i>';
            }
            var sMessageId=aInfo[i].messageId;//信息id
            var sSentTimeReg=changeTimeFormat(sSentTime,'h');
            var sSentDate=changeTimeFormat(sSentTime,'y');
            //aDate.push(sSentDate);
            if(sSentDate != defaultDate){
                sLi+='<li >\
                    <p class="infoDet-timeRecord ">'+sSentDate+'</p>\
                </li>';
                defaultDate=sSentDate;
            }
            var sdata = localStorage.getItem('datas');
            var oLocData=JSON.parse(sdata);
            var accountID = oLocData.id;
            if(sTargetId !=accountID){
                var oThers=findMemberInList(sTargetId);
                var sName=oThers?oThers.name: '';
                sLi+='<li class="infoDet-OthersSay" data-time="'+sSentTime+'">\
                   <b>'+sName+'&nbsp&nbsp&nbsp'+sSentTimeReg+'</b>\
                <div class="pageHostoryBox clearfix">'+sContent+'</div>\
                </li>';
            }else{
                var sSelfName=oLocData.name;
                sLi+='<li class="infoDet-selfSay" data-time="'+sSentTime+'">\
                   <b>'+sSelfName+'&nbsp&nbsp&nbsp'+sSentTimeReg+'</b>\
                <div class="pageHostoryBox clearfix">'+sContent+'</div>\
                </li>';
            }
        }
        sDom+=sLi+'</ul>';
    }
    $(sClass).append(sDom);
    var eDom=document.querySelector(sClass);
    eDom.scrollTop = eDom.scrollHeight;
}
function getFileRecord(aList,sClass){
    var sDom='<ul class="chatFile">';
    var sLi='';
    var aInfo=aList;
    $(sClass).empty();
    var aDate=[];
    var defaultDate=0;
    if(aInfo.length>0) {
        for (var i = 0; i < aInfo.length; i++) {
            var sTargetId = aInfo[i].senderUserId;//f发送者id
            var sSentTime = aInfo[i].sentTime;//发送时间
            var sContent = aInfo[i].content;
            var fileSrc = sContent.fileUrl;
            var file = getFileUniqueName(fileSrc);
            var sSentTimeReg = changeTimeFormat(sSentTime, 'ym');
            var Msize = KBtoM(sContent.size);
            var sFileName = sContent.name;
            var sFileType = sContent.type;//文件类型
            var uniqueTime = sContent.uniqueTime;
            var sdata = localStorage.getItem('datas');
            var oLocData = JSON.parse(sdata);
            var accountID = oLocData.id;
            if (sTargetId != accountID) {
                var oThers = findMemberInList(sTargetId);
                var sSendfName = oThers ? oThers.name : '';
            } else {
                var sSendfName = oLocData.name;
            }
            if(window.Electron){
                var localPath = window.Electron.chkFileExists(fileSrc);
                if(localPath){
                    sLi += ' <li class="chatFile-folder">\
            <i></i>\
            <p>\
            <b class="clearfix"><em class="hosFileName">'+sFileName+'</em><em>(' + Msize + ')</em></b>\
            <span>' + sSentTimeReg + sSendfName + '</span>\
            </p>\
            <strong  data-url="'+fileSrc+'" class="hosOpenFile">打开</strong>\
            <strong data-url="'+fileSrc+'" class="hosOpenFloder">打开文件夹</strong>\
            </li>';
                }else{
                    sLi += ' <li class="chatFile-folder">\
            <i></i>\
            <p>\
            <b class="clearfix"><em class="hosFileName">'+sFileName+'</em><em>(' + Msize + ')</em></b>\
            <span>' + sSentTimeReg + sSendfName + '</span>\
            </p>\
            <strong  data-url="'+fileSrc+'" class="hosOpenFile"><a fileName="' + file + '"  class="downLoadFile" href="' + fileSrc + '"></a></strong>\
            </li>';
                }
            }
        }

        sDom += sLi + '</ul>';
    }
    $(sClass).append(sDom);
    var eDom=document.querySelector(sClass);
    eDom.scrollTop = eDom.scrollHeight;
}
function scrollTop(eDom){
    eDom.scrollTop = eDom.scrollHeight;
}


function imgType(type){
    switch (type){
        case 'image/png':
            var imgSrc = 'page/web/css/img/formatImg.jpg';
            break;
        case 'image/jpeg':
            var imgSrc = 'page/web/css/img/formatImg.jpg';
            break;
        default :
            var imgSrc = 'page/web/css/img/formatUnknew.jpg';
    }
    return imgSrc;
}


//获取历史消息、消息记录
function historyMsg(Type,targetId){
    var aList;
    //RongIMLib.RongIMClient.getInstance().getMessagesFromConversation(targetId,RongIMLib.ConversationType[Type], 'rrr', 0,20).then(function (data) {
    //    _self._pageNum= Math.ceil(data.count / _self._pageSize) || 0;
    //    if (_self._pageNow == _self._pageNum) {
    //        _self.hasmoreMessage = false;
    //    }
    //    _self.lastTime = (data.message[0] || {}).sentTime || 0;
    //    console.log(data);
    //});
    //RongIMLib.RongIMClient.getInstance().searchMessageByContent(RongIMLib.ConversationType[Type],targetId,'rrr',0,20,1,{
    //        onSuccess:function(data, count){
    //            alert(data,count);
    //            console.log(data);
    //            console.log(count);
    //            // @param {<Message>[]}     data      - 搜索的结果
    //            // @param {number}          count     - 搜索的消息总条数
    //        },
    //        onError:function(error){
    //        }
    //    });
    //var oPagetest = new PageObj({divObj:$('.infoDet-chatRecord').find('.infoDet-page'),pageSize:20,conversationtype:Type,targetId:targetId,pageCount:120},function(type,list,callback)//声明page1
    //{
    //    getChatRecord(list);
    //
    //});
    //RongIMClient.getInstance().getHistoryMessages(RongIMLib.ConversationType[Type], targetId, 0, 20, {
    //    onSuccess: function(list, hasMsg) {
    //       // console.log(list,hasMsg);
    //        var aDatas=list;
    //        for(var i=0;i<aDatas.length;i++){
    //            //console.log(changeTimeFormat(aDatas[i].sentTime,'yh'));
    //        }
    //       //aList=list;
    //       // return list;
    //        // hasMsg为boolean值，如果为true则表示还有剩余历史消息可拉取，为false的话表示没有剩余历史消息可供拉取。
    //        // list 为拉取到的历史消息列表
    //    },
    //    onError: function(error) {
    //        // APP未开启消息漫游或处理异常
    //        // throw new ERROR ......
    //    }
    //});
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
            break;
        case 'ym':
            time=y+'-'+month+'-'+d+' '+h+':'+m;
            break;
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
    var sData=window.localStorage.getItem("datas");
    var oData= JSON.parse(sData);
    var sId=oData.id;
    sendAjax('fun!getMsgTop',{userid:sId},function(data){
        var oData=JSON.parse(data);
        var aText=oData.text;
        $('.usualChatListUl').empty();
        if(oData.code==1){
            var aTopList=[];
            for(var i=0;i<aText.length;i++){
                var sTopType=aText[i].type;
                var nTopId=aText[i].topId;
                for(var j=0;j<list.length;j++){
                    if(nTopId==list[j].targetId){
                        var sTopList=list[j];
                        list.splice(j,1);
                        j--;
                        aTopList.unshift(sTopList);
                    }
                }
            }
            sHTML+=creatTopList(sHTML,aTopList,true);
            sHTML=creatTopList(sHTML,list,false);
            $('.usualChatListUl').html(sHTML);
        }else{
            sHTML=creatTopList(sHTML,list,false);
            $('.usualChatListUl').html(sHTML);
        }
    });
    //sendAjax('fun!getMsgTop',{userid:sId},function(data){
    //    var oData=JSON.parse(data);
    //    var aText=oData.text;
    //    if(oData.code==1){
    //        for(var i=0;i<aText.length;i++){
    //           // var oTopText=aText[i].text;
    //            var sTopType=aText[i].type;
    //            var nTopId=aText[i].topId;
    //            $('.usualChatListUl li').each(function(index){
    //                var targetEle=$(this);
    //                var sTopId=$(this).attr('targetid');
    //                if(sTopId==nTopId){
    //                    $('.usualChatListUl li').eq(index).remove();
    //                    $('.usualChatListUl').prepend(targetEle);
    //                    targetEle.addClass('top');
    //                }
    //            });
    //        }
    //}
    //});
    console.log('list',list);
}
function creatTopList(sHTML,list,bFlg){
    //console.log('creatTopList',list);
    for(var i = 0;i<list.length;i++){
        var curList = list[i];
        var conversationType = curList.conversationType
        var content = curList.latestMessage.content.content;
        var extra = curList.latestMessage.messageType;
        var sendTime = curList.sentTime;
        var nowTime = new Date().getTime();

        if(extra=="FileMessage"){
            content="[发送文件]";
        }else if(extra=="ImageMessage"){
            content="[发送图片]";
        }else if(extra=="VoiceMessage"){
            content="[语音]";
        }
        var targetId = curList.targetId;
         if(nowTime - sendTime>=2505600000){//消息列表的显示时间为最近一月内的消息，超过一月的消息将从消息列表中删除
             conversationType==1?removeConvers('PRIVATE',targetId):removeConvers('GROUP',targetId);
             continue;
         }
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
        if(conversationType==1){ //个人聊天
            var member = findMemberInList(targetId);
            if(member){
                //console.log('member',member);
                var logo = member.logo?globalVar.imgSrc+member.logo:globalVar.defaultLogo;
                var name = member.name || '';
                if(bFlg){
                    sHTML += ' <li targetid="'+targetId+'" targetType="PRIVATE" class="top">'+
                    '<div><img class="groupImg" src="'+logo+'" alt=""/>'+
                    sNum+
                    '<span class="groupName">'+name+'</span>'+
                    '<span class="usualLastMsg">'+content+'</span>'+
                    '<span class="lastTime">'+lastTime+'</span>'+
                    '</div>'+
                    '</li>'
                }else{
                    sHTML += ' <li targetid="'+targetId+'" targetType="PRIVATE">'+
                    '<div><img class="groupImg" src="'+logo+'" alt=""/>'+
                    sNum+
                    '<span class="groupName">'+name+'</span>'+
                    '<span class="usualLastMsg">'+content+'</span>'+
                    '<span class="lastTime">'+lastTime+'</span>'+
                    '</div>'+
                    '</li>'
                }
            }else{
                removeConvers('PRIVATE',targetId);
            }
        }else if(conversationType==3){
            var curGroup = groupInfo(targetId);
            //if(curGroup){
            if(bFlg){
                sHTML += ' <li targetid="'+targetId+'" targetType="GROUP" class="top">'+
                '<div><img class="groupImg" src="'+globalVar.defaultDepLogo+'" alt=""/>'+
                sNum+
                '<span class="groupName">'+curGroup.name+'</span>'+
                '<span class="usualLastMsg">'+content+'</span>'+
                '<span class="lastTime">'+lastTime+'</span>'+
                '</div>'+
                '</li>'
            }else{
                sHTML += ' <li targetid="'+targetId+'" targetType="GROUP">'+
                '<div><img class="groupImg" src="'+globalVar.defaultDepLogo+'" alt=""/>'+
                sNum+
                '<span class="groupName">'+curGroup.name+'</span>'+
                '<span class="usualLastMsg">'+content+'</span>'+
                '<span class="lastTime">'+lastTime+'</span>'+
                '</div>'+
                '</li>'
            }
            //}
        }
    }
    return sHTML;
}
//查询单个群信息
function groupInfo(id){
    var groupInfo = localStorage.getItem('groupInfo');
    if(groupInfo){
        groupInfo = JSON.parse(groupInfo);
    }
    var curInfo = '';
    for(var i = 0;i<groupInfo.text.length;i++){
        if(groupInfo.text[i].GID==id){
            curInfo = groupInfo.text[i]
        }
    }
    return curInfo;
    //sendAjax(url,data,callback)
}



function KBtoM(kb){
    return Math.floor(kb/1024 * 100) / 100;
}
//接收到的消息显示在盒子里或者在消息列表中显示
function reciveInBox(msg){
    //打包后的程序在托盘里闪烁
    if (window.Electron) {
        window.Electron.updateBadgeNumber(2);
    }

    var targetID = msg.targetId;
    var messageType = msg.messageType;
    var content = messageType=="TextMessage"?msg.content.content:msg.content;
    var targetType = msg.conversationType;
    //clearNoReadMsg(targetType,targetID);
    var oData=findMemberInList(targetID);
    if(oData){
        var sImg=oData.logo?globalVar.imgSrc+oData.logo:globalVar.defaultLogo;
    }else{
        var sImg=globalVar.defaultLogo;
    }

    if(targetType==3){//群聊 找到各自的消息容器
        var $MesContainer = $('.mesContainerGroup');
        var eDom = document.querySelector('#groupContainer .mr-chatview');
    }else if(targetType==1){//个人聊天
        var $MesContainer = $('.mesContainerSelf');
        var eDom = document.querySelector('#perContainer .mr-chatview');
    }

    if (!$MesContainer.hasClass('chatHide') || $MesContainer.attr('targetID') == targetID) {//如果当前页面正是你要聊的对象
        switch (messageType){
            case "FileMessage":
                var Msize = KBtoM(content.size);
                var fileURL = content.fileUrl;
                var imgSrc = imgType(content.type);
                var file = getFileUniqueName(fileURL);
                //var str = RongIMLib.RongIMEmoji.symbolToHTML('成功发送文件');
                var sHTML = '<li class="mr-chatContentLFile clearfix">'+
                    '<img class="headImg" src="'+sImg+'">'+
                    '<div class="mr-chatBox">'+
                    '<div class="file_type fl"><img class="fileImg" src="'+imgSrc+'"></div>'+
                    '<div class="file_content fl">' +
                    '<p class="p1 file_name">'+content.name+'</p>' +
                    '<p class="p2 file_size" data-s="'+content.size+'">'+Msize+'</p>' +
                    '</div>' +
                    '<a fileName="'+file+'" class="downLoadFile" href="'+fileURL+'"></a>'+
                    '</li>';
                var parentNode = $MesContainer.find('.mr-chatview .mr-chatContent');
                parentNode.append($(sHTML));
                break;
            case "ImageMessage":
                var content = msg.content;
                var fileURL = content.imageUri;
                var file = getFileUniqueName(fileURL);
                var sHTML = ' <li class="mr-chatContentL clearfix" data-t="">'+
                    '<img class="headImg" src="'+sImg+'">'+
                    '<img src="'+content.imageUri+'" class="uploadImgLeft uploadImgFile">'+
                    '</li>';
                var parentNode = $MesContainer.find('.mr-chatview .mr-chatContent');
                parentNode.append($(sHTML));
                $('.uploadImgFile').on('load',function(){
                    var eDom=document.querySelector('#perContainer .mr-chatview');
                    eDom.scrollTop = eDom.scrollHeight;
                })
                break;
            case "VoiceMessage":
                var base64Str = content.content;
                var duration = base64Str.length/1024;
                //w:20px~170px  durating:1s~50s
                var curWidth = duration*3+20;
                if(curWidth>170){
                    curWidth = 170;
                }
                RongIMLib.RongIMVoice.preLoaded(base64Str);
                RongIMLib.RongIMVoice.play(base64Str,duration);
                RongIMLib.RongIMVoice.stop(base64Str);
                var sHTML = '<li messageUId="' + msg.messageUId + '" sentTime="' + msg.sentTime + '" class="mr-chatContentL clearfix">' +
                    '<img class="headImg" src="'+sImg+'">'+
                    '<div class="mr-chatBox">'+
                    '<p class="voiceMsgContent" style="width:'+curWidth+'px" base64Str="'+base64Str+'"></p>'+
                    '</div>'+
                    '<p class="voiceSecond"><span class="msgUnread"></span><span>'+content.duration+'S</span></p>'+
                    '</li>';
                var parentNode = $MesContainer.find('.mr-chatview .mr-chatContent');
                parentNode.append($(sHTML));
                break;
            case "TextMessage":
                var str = RongIMLib.RongIMEmoji.symbolToHTML(content);
                var sHTML = '<li messageUId="' + msg.messageUId + '" sentTime="' + msg.sentTime + '" class="mr-chatContentL clearfix">' +
                    '<img class="headImg" src="'+sImg+'">' +
                    '<div class="mr-chatBox">' +
                    '<span>' + str + '</span>' +
                    '<i></i>' +
                    '</div>' +
                    '</li>';
                var parentNode = $MesContainer.find('.mr-chatview .mr-chatContent');
                parentNode.append($(sHTML));
                break;
        }
        //eDom.scrollTop = eDom.scrollHeight;
        var targetType = targetType == 1?'PRIVATE':'GROUP';
        clearNoReadMsg(targetType,targetID,function(){
            getConverList();
        });
    }else{
        getConverList();
    }
}
//从URL连接中取得文件名
function getFileUniqueName(fileURL){
    if(fileURL){
        var aURM = fileURL.split('attname=')[1];
        var fileName = aURM.split('.')[0];
        return fileName;
    }else{
        return "";
    }
}



function changeClassProcess(dom,fileName){
    var file = fileName.split('.')[0]
    $(dom).attr('fileName',file);
}

//清除未读消息数

function clearNoReadMsg(Type,targetId,callback){
    var conversationType = RongIMLib.ConversationType[Type];
    //var targetId = "xxx";
    RongIMClient.getInstance().clearUnreadCount(conversationType,targetId,{
        onSuccess:function(){
            callback&&callback();
            //alert('11111111111');
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