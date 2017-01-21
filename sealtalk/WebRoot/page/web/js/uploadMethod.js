/**
* Created by zhu_jq on 2017/1/16.
*/
$(function(){
    //拖拽上传
    var messageContent  =document.getElementById('message-content')
    messageContent.addEventListener('drop', function(event){
        if (event.preventDefault) event.preventDefault();
    })
    var uploading = false;
    //点击button上传
    var token = globalVar.qiniuTOKEN;
    var config = {
        domain: globalVar.qiniuDOMAN,                              // default : '' ,必须设置文件服务器地址。
        file_data_name  : 'file',                                       // default : file , 文件对象的 key 。
        base64_size     : 4096,
       chunk_size      : '4M',                                           // default : 10 单位 MB 。
        multi_parmas    : { },                                          // default : {} 扩展上传属性 。
        query           : { },                                          // default : {} 扩展 url 参数 e.g. http://rongcloud.cn?name=zhangsan 。
        support_options : true,                                         // default : true, 文件服务器不支持 OPTIONS 请求需设置为 false。
        getToken: function(callback){
            callback(token);
        }
    };

    var $file = $(".comment-pic-upd");
    $file.on('change',function(){
        var _this = this;
        var _file = this.files[0];
        UploadClient.initImage(config, function(uploadFile){
            uploading = true;
            var callback = {
                onError: function (errorCode) {
                    //console.log(errorCode);
                    uploading = false;
                },
                onProgress: function (loaded, total) {
                    //console.log('onProgress', loaded, total, this);
                    var className = this._self.uniqueTime;
                    var percent = Math.floor(loaded / total * 100);
                    var progressContent = $('#up_precent[uniquetime="'+className+'"]');
                    progressContent.width(percent + '%');
                    return percent;
                },
                onCompleted: function (data) {
                    var className = this._self.uniqueTime;
                    var downloadLink = globalVar.qiniuDOWNLOAD+data.filename;
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
    })
})

function sendFile(_file,_this,callback){
    var filedetail = {};
    filedetail.name = _file.name;
    _file.uniqueTime = new Date().getTime();
    filedetail.uniqueTime = _file.uniqueTime;
    filedetail.size = _file.size;
    filedetail.type = _file.type;

    var content = JSON.stringify(filedetail);
    var extra = "uploadFile";

    var targetId = $(_this).parents('.mesContainer').attr('targetid');
    var targetType = $(_this).parents('.mesContainer').attr('targettype');
    sendMsg(content,targetId,targetType,extra,callback);
}