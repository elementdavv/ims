/**
* Created by zhu_jq on 2017/1/16.
*/
$(function(){
    var sAccount = localStorage.getItem('account');
    var token = 'CddrKW5AbOMQaDRwc3ReDNvo3-sL_SO1fSUBKV3H:0ZIWXsNEMmwHFSWKb2F_I_jBV2Y=:eyJzY29wZSI6InJvbmdjbG91ZC1pbWFnZSIsInJldHVybkJvZHkiOiJ7XCJuYW1lXCI6ICQoZm5hbWUpLFwic2l6ZVwiOiAkKGZzaXplKSxcIndcIjogJChpbWFnZUluZm8ud2lkdGgpLFwiaFwiOiAkKGltYWdlSW5mby5oZWlnaHQpLFwiaGFzaFwiOiAkKGV0YWcpfSIsImRlYWRsaW5lIjoxNDg0MTI2NTU2fQ=='
    var config = {
        domain: 'https://upload.qiniu.com',
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
            uploadFile.upload(_file, callback);
        });
    };
})