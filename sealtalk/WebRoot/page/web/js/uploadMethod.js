/**
 * Created by zhu_jq on 2017/1/16.
 */
$(function(){
    var sAccount = localStorage.getItem('account');
    if(sAccount) {
        var oAccount = JSON.parse(sAccount);
        var token = oAccount.token;
    }
    var config = {
        domain: 'https://upload.qiniu.com',
        getToken: function(callback){
            //var token = "CddrKW5AbOMQaDRwc3ReDNvo3-sL_SO1fSUBKV3H:0ZIWXsNEMmwHFSWKb2F_I_jBV2Y=:eyJzY29wZSI6InJvbmdjbG91ZC1pbWFnZSIsInJldHVybkJvZHkiOiJ7XCJuYW1lXCI6ICQoZm5hbWUpLFwic2l6ZVwiOiAkKGZzaXplKSxcIndcIjogJChpbWFnZUluZm8ud2lkdGgpLFwiaFwiOiAkKGltYWdlSW5mby5oZWlnaHQpLFwiaGFzaFwiOiAkKGV0YWcpfSIsImRlYWRsaW5lIjoxNDg0MTI2NTU2fQ==";
            callback(token);
        }
    };
    var callback = {
        onError	: function (errorCode) {
            console.log(errorCode);
        },
        onProgress : function (loaded, total) {
            console.log('上传成功',loaded, total);
            //var percent = Math.floor(loaded/total*100);
            //var progressBar 	= document.getElementById('progressBar'),
            //    progressContent = document.getElementById('progressContent');
            //progressBar.style.width = percent + '%';
            //progressContent.innerHTML = percent + "%";
        },
        onCompleted : function (data) {
            // document.getElementById('success-log').innerHTML += JSON.stringify(data) + '</br>';
            document.getElementById('showImage').src = 'data:image/png;base64,' + data.thumbnail;
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