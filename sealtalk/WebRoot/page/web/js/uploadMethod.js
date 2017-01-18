/**
* Created by zhu_jq on 2017/1/16.
*/
$(function(){
    //拖拽上传
    var messageContent  =document.getElementById('message-content')
    messageContent.addEventListener('drop', function(event){
        if (event.preventDefault) event.preventDefault();
        console.log('000000');
        console.log(event);
    })

    //点击button上传
    //var sAccount = localStorage.getItem('account');
    var token = 'iN7NgwM31j4-BZacMjPrOQBs34UG1maYCAQmhdCV:4IStvK3nVJGbF-bpZ6vjoLr8rCE=:eyJzY29wZSI6InF0ZXN0YnVja2V0IiwiZGVhZGxpbmUiOjE0ODQ3Mjk3ODF9'
    var config = {
        domain: 'https://up.qbox.me',                              // default : '' ,必须设置文件服务器地址。
        file_data_name  : 'file',                                       // default : file , 文件对象的 key 。
        base64_size     : 10,                                           // default : 10 单位 MB 。
        chunk_size      : 10,                                           // default : 10 单位 MB 。
        //headers         : { "Content-Type" : 'multipart/form-data'},      // default : { Content-Type : 'multipart/form-data'} , 按需扩展。
        multi_parmas    : { },                                          // default : {} 扩展上传属性 。
        query           : { },                                          // default : {} 扩展 url 参数 e.g. http://rongcloud.cn?name=zhangsan 。
        support_options : true,                                         // default : true, 文件服务器不支持 OPTIONS 请求需设置为 false。
        data            : UploadClient.dataType.form,                   // default : 默认提供：form、json、data 数据直传三种方式。
        getToken: function(callback){
            callback(token);
        }
    };
    var callback = {
        onError	: function (errorCode) {
            console.log(errorCode);
        },
        onProgress : function (loaded, total) {
            console.log('onProgress',loaded, total);
        },
        onCompleted : function (data) {
            document.getElementById('showImage').src = 'data:image/png;base64,' + data.thumbnail;
            console.log(data);
        }
    };



    var file = document.getElementById("upload_file");
    console.log(file);
    file.onchange = function(){
        var _file = this.files[0];
        UploadClient.initImage(config, function(uploadFile){
            console.log('_file',_file);
            uploadFile.upload(_file, callback);
        });
    };
})