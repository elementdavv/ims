/**
 * Created by zhu_jq on 2017/2/7.
 */
$(function(){
    $('.mr-chatview').delegate('#downLoadFile','click',function(){
        if(window.Electron){
            var url = $(this).prev().attr('src');
            var localPath = window.Electron.chkFileExists(url);
            console.log(localPath);
        }
    })
})