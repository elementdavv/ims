/**
* Created by zhu_jq on 2017/1/16.
*/
$(function(){
    //拖拽上传
    var messageContent  =document.getElementById('message-content')
    messageContent.addEventListener('drop', function(event){
        if (event.preventDefault) event.preventDefault();
        //console.log('000000');
        //console.log(event);
    })
    var uploading = false;
    //点击button上传
    //var sAccount = localStorage.getItem('account');
    //var token = "CddrKW5AbOMQaDRwc3ReDNvo3-sL_SO1fSUBKV3H:0ZIWXsNEMmwHFSWKb2F_I_jBV2Y=:eyJzY29wZSI6InJvbmdjbG91ZC1pbWFnZSIsInJldHVybkJvZHkiOiJ7XCJuYW1lXCI6ICQoZm5hbWUpLFwic2l6ZVwiOiAkKGZzaXplKSxcIndcIjogJChpbWFnZUluZm8ud2lkdGgpLFwiaFwiOiAkKGltYWdlSW5mby5oZWlnaHQpLFwiaGFzaFwiOiAkKGV0YWcpfSIsImRlYWRsaW5lIjoxNDg0MTI2NTU2fQ==";
    var token = '5aZfPtTZP10rsriqnktyCKXjq99EX9z9wUAq-Yll:DJMQqJxwhqRWzVkLE2AVDD6DJ_4=:eyJlbmRVc2VyIjoieSIsInNjb3BlIjoiZWR1cGljdHVyZSIsImRlYWRsaW5lIjo5MjIzMzcyMDM2ODU0Nzc1ODA3fQ=='
    var config = {
        domain: 'https://up.qbox.me',                              // default : '' ,必须设置文件服务器地址。
        file_data_name  : 'file',                                       // default : file , 文件对象的 key 。
        base64_size     : 4096,
        //headers         : { 'Content-Type' : 'multipart/form-data'},
        //max_file_size: '100mb',                                           // default : 10 单位 MB 。
        chunk_size      : '4M',                                           // default : 10 单位 MB 。
        multi_parmas    : { },                                          // default : {} 扩展上传属性 。
        query           : { },                                          // default : {} 扩展 url 参数 e.g. http://rongcloud.cn?name=zhangsan 。
        support_options : true,                                         // default : true, 文件服务器不支持 OPTIONS 请求需设置为 false。
        //data            : 'default',                   // default : 默认提供：form、json、data 数据直传三种方式。
        getToken: function(callback){
            callback(token);
        }
    };








    var file = document.getElementById("upload_file");
    file.onchange = function(){
        var _this = this;
        var _file = this.files[0];
        UploadClient.initImage(config, function(uploadFile){
            uploading = true;
            var callback = {
                onError: function (errorCode) {
                    console.log(errorCode);
                    uploading = false;

                },
                onProgress: function (loaded, total) {
                    console.log('onProgress', loaded, total, this);
                    var className = this._self.uniqueTime;
                    var percent = Math.floor(loaded / total * 100);
                    var progressContent = $('#up_precent[uniquetime="'+className+'"]');
                    progressContent.width(percent + '%');
                    return percent;
                },
                onCompleted: function (data) {
                    var className = this._self.uniqueTime;
                    var downloadLink = 'https://ocsys6mwy.bkt.clouddn.com/'+data.filename;
                    $('#up_process[uniquetime="'+className+'"]').parent().next().attr('href',downloadLink)
                    //document.getElementById('showImage').src = 'data:image/png;base64,' + data.thumbnail;
                    console.log(data);
                    uploading = false;
                },
                _self: _file
            }

            _file.callback = callback;

            sendFile(_file,_this,function(){
                uploadFile.upload(_file, callback);
            });
        });
    };


})



function sendFile(_file,_this,callback){
    var filedetail = {};
    filedetail.name = _file.name;
    _file.uniqueTime = new Date().getTime();
    filedetail.uniqueTime = _file.uniqueTime;
    filedetail.size = _file.size;
    filedetail.type = _file.type;
    //filedetail.type = _file.type;

    var content = JSON.stringify(filedetail);
    var extra = "uploadFile";
    //{content:"hello",extra:"附加信息"}

    var targetId = $(_this).parents('.mesContainer').attr('targetid');
    var targetType = $(_this).parents('.mesContainer').attr('targettype');
    sendMsg(content,targetId,targetType,extra,callback);
}