/**
 * Created by zhu_jq on 2017/1/9.
 */
$(function(){


    var userid = $('body').attr('userid');

    var token = $('body').attr('token');
    sendAjax('member!getOneOfMember',{userid:userid},function(data){
        window.localStorage.datas=data;
        var datas = JSON.parse(data);
        var changeFormatData = {};
        changeFormatData.text = datas
        if(datas){
            //data.token = datas.text.token;
            window.localStorage.account=JSON.stringify(changeFormatData);

           // RongIMClient.init(globalVar.rongKey);
            if(RongIMLib.VCDataProvider&&window.Electron){
                RongIMClient.init(globalVar.rongKey,new RongIMLib.VCDataProvider(window.Electron.addon));
            }else{
                RongIMClient.init(globalVar.rongKey);
            }
            //RongIMLib.RongIMClient.init("e5t4ouvpe564a",new RongIMLib.WebSQLDataProvider());
            //RongIMLib.RongIMClient.init("e5t4ouvpe564a",new RongIMLib.WebSQLDataProvider());
            //var sAccount = datas.account;
            //if(sAccount){
            //    var oAccount = JSON.parse(sAccount);
            //    var token = datas.token;
                var account = datas.account;
                var accountID = datas.id;
                //获取常用联系人
                getMemberFriends(account);
                //获取左侧组织树状图
                getBranchTreeAndMember();
                //获取会话列表(只能在与服务器连接成功之后调用)
                //getConverList();
                //获取群组列表
                getGroupList(accountID);
                //获取系统提示音
                getSysTipVoice(accountID);
                //鼠标在联系人上悬停

                // 设置连接监听状态 （ status 标识当前连接状态）
                // 连接状态监听器
                RongIMClient.setConnectionStatusListener({
                    onChanged: function (status) {
                        switch (status) {
                            //链接成功
                            case RongIMLib.ConnectionStatus.CONNECTED:
                                console.log('链接成功');
                                if($('.window_mask')){
                                    $('.window_mask').remove()
                                }
                                //显示会话列表
                                getConverList()
                                break;
                            //正在链接
                            case RongIMLib.ConnectionStatus.CONNECTING:
                                console.log('正在链接');
                                break;
                            //重新链接
                            case RongIMLib.ConnectionStatus.DISCONNECTED:
                                //new Window().alert({
                                //    title   : '',
                                //    content : '断开连接！',
                                //    hasCloseBtn : false,
                                //    hasImg : true,
                                //    textForSureBtn : false,
                                //    textForcancleBtn : false
                                //    //,
                                //    //autoHide:true
                                //});
                                console.log('断开连接');
                                break;
                            //其他设备登录
                            case RongIMLib.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT:
                                new Window().alert({
                                    title   : '',
                                    content : '其他设备登录！',
                                    hasCloseBtn : false,
                                    hasImg : true,
                                    textForSureBtn : false,
                                    textForcancleBtn : false
                                    //,
                                    //autoHide:true
                                });
                                console.log('其他设备登录');
                                break;
                            //网络不可用
                            case RongIMLib.ConnectionStatus.NETWORK_UNAVAILABLE:
                                new Window().alert({
                                    title   : '',
                                    content : '网络不可用！',
                                    hasCloseBtn : false,
                                    hasImg : true,
                                    textForSureBtn : false,
                                    textForcancleBtn : false
                                    //,
                                    //autoHide:true
                                });
                                console.log('网络不可用');
                                break;
                        }
                    }});

                // 消息监听器
                RongIMClient.setOnReceiveMessageListener({
                    // 接收到的消息
                    onReceived: function (message) {
                        // 判断消息类型
                        switch(message.messageType){
                            case RongIMClient.MessageType.TextMessage:
                                //1.获取系统提示音接口
                                //2.获取单独的群消息设置
                                if(globalVar.SYSTEMSOUND){
                                    if(message.conversationType==3){
                                        var targetId = message.targetId;
                                        sendAjax('fun!getNotRecieveMsg',{groupid:targetId,userid:userid},function(data){
                                            if(data){
                                                var datas = JSON.parse(data);
                                                if(datas&&datas.code==1&&datas.text==true){
                                                    console.log(4444);
                                                }else{
                                                    voicePlay();
                                                }
                                            }
                                        })
                                    }else{
                                        voicePlay();
                                    }
                                    //1。获取targetID 查询群禁言设置  if(禁言)、、声音不播放

                                }
                                reciveInBox(message);
                                break;
                            case RongIMClient.MessageType.VoiceMessage:
                                // 对声音进行预加载
                                // message.content.content 格式为 AMR 格式的 base64 码
                                RongIMLib.RongIMVoice.preLoaded(message.content.content);
                                break;
                            case RongIMClient.MessageType.ImageMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.DiscussionNotificationMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.LocationMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.RichContentMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.DiscussionNotificationMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.InformationNotificationMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.ContactNotificationMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.ProfileNotificationMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.CommandNotificationMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.CommandMessage:
                                // do something...
                                break;
                            case RongIMClient.MessageType.UnknownMessage:
                                // do something...
                                break;
                            default:
                            // 自定义消息
                            // do something...
                        }
                    }
                });

                // 连接融云服务器。
                RongIMClient.connect(token, {
                    onSuccess: function(userId) {
                        console.log('连接成功');
                    },
                    onTokenIncorrect: function() {
                        new Window().alert({
                            title   : '',
                            content : 'token无效！',
                            hasCloseBtn : false,
                            hasImg : true,
                            textForSureBtn : false,
                            textForcancleBtn : false
                            //,
                            //autoHide:true
                        });
                        console.log('token无效');
                    },
                    onError:function(errorCode){
                        var info = '';
                        switch (errorCode) {
                            case RongIMLib.ErrorCode.TIMEOUT:
                                info = '超时';
                                break;
                            case RongIMLib.ErrorCode.UNKNOWN_ERROR:
                                info = '未知错误';

                                break;
                            case RongIMLib.ErrorCode.UNACCEPTABLE_PaROTOCOL_VERSION:
                                info = '不可接受的协议版本';

                                break;
                            case RongIMLib.ErrorCode.IDENTIFIER_REJECTED:
                                info = 'appkey不正确';

                                break;
                            case RongIMLib.ErrorCode.SERVER_UNAVAILABLE:
                                info = '服务器不可用';

                                break;
                        }
                        new Window().alert({
                            title   : '',
                            content : info+'！',
                            hasCloseBtn : false,
                            hasImg : true,
                            textForSureBtn : false,
                            textForcancleBtn : false
                            //,
                            //autoHide:true
                        });
                        console.log(errorCode);
                    }
                },'');
            //}
            //初始化emoji表情
            initEmoji();
            //初始化声音库
            RongIMLib.RongIMVoice.init();
        }
    })



})
function voicePlay(){
    var systemSound_recive = document.getElementById('systemSound_recive');
    systemSound_recive.play();
}

function setConverToTop(Type,targetId) {
    var conversationtype = RongIMLib.ConversationType[Type]; // 私聊
    RongIMLib.RongIMClient.getInstance().setConversationToTop(conversationtype, targetId, {
        onSuccess: function() {
            console.log("setDiscussionInviteStatus Successfully");
        },
        onError: function(error) {
            console.log("setDiscussionInviteStatus:errorcode:" + error);
        }
    });
}

