/**
 * Created by zhu_jq on 2017/1/9.
 */
$(function(){
    RongIMClient.init("e5t4ouvpe564a");
    //RongIMLib.RongIMClient.init("e5t4ouvpe564a",new RongIMLib.WebSQLDataProvider());
    var sAccount = localStorage.getItem('account');
    if(sAccount){
        var oAccount = JSON.parse(sAccount);
        var token = oAccount.token;
        console.log(token);
        RongIMClient.setConnectionStatusListener({
            onChanged: function (status) {
                switch (status) {
                    //链接成功
                    case RongIMLib.ConnectionStatus.CONNECTED:
                        console.log('链接成功');
                        //显示会话列表
                        getConverList()
                        break;
                    //正在链接
                    case RongIMLib.ConnectionStatus.CONNECTING:
                        console.log('正在链接');
                        break;
                    //重新链接
                    case RongIMLib.ConnectionStatus.DISCONNECTED:
                        console.log('断开连接');
                        break;
                    //其他设备登录
                    case RongIMLib.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT:
                        console.log('其他设备登录');
                        break;
                    //网络不可用
                    case RongIMLib.ConnectionStatus.NETWORK_UNAVAILABLE:
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
                        // 发送的消息内容将会被打印
                        //console.log('接收到的 信息',message);
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


        RongIMClient.connect(token, {
            onSuccess: function(userId) {
                console.log("Login successfully." + userId);
                var imageOpts = {
                    drop_element:'message-content',
                    container:'upload_conainer',
                    browse_button:'upload_file'
                }

                var fileOpts = {
                    drop_element:'message-content',
                    container:'upload_conainer',
                    browse_button:'upload_file'
                }

                // 初始化 Upload 插件。
                //console.log('初始化 Upload 插件。',imageOpts,fileOpts);
                //RongIMLib.RongUploadLib.init(imageOpts,fileOpts);
                //
                //// 设置 RongUploadLib 监听器。
                //RongIMLib.RongUploadLib.getInstance().setListeners({
                //    onFileAdded:function(file){
                //        // 触发时机：每个文件被添加后。
                //    },
                //    onBeforeUpload:function(file){
                //        // 触发时机：每个文件上传之前。
                //    },
                //    onUploadProgress:function(file){
                //        // 触发时机：每个文件上传中。
                //    },
                //    onFileUploaded:function(file,message,type){
                //        // 触发时机：每个文件上传完成。
                //    },
                //    onError:function(err, errTip){
                //        // 触发时机：每个文件上传失败。
                //    },
                //    onUploadComplete:function(){
                //        // 触发时机：所有文件上传完成。
                //    }
                //});
            },
            onTokenIncorrect: function() {
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
                console.log(errorCode);
            }
        });
    }
    initEmoji();
})