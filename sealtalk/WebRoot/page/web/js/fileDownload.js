/**
 * Created by zhu_jq on 2017/2/7.
 */
$(function(){
    var _fnDown = function(ev){
        if(window.Electron){
            var url = $(this).attr('href');
            var localPath = window.Electron.chkFileExists(url);
            console.log(localPath);
            if(localPath){//本地有这个文件
                var parentNode = $(this).parent();
                parentNode.find('#fileOperate').remove();
                var sHTML = '<div id="fileOperate" uniquetime="1486626340273">' +
                '<span class="openFile">打开文件</span>' +
                '<span class="openFloder">打开文件夹</span>' +
                '</div>'
                parentNode.append($(sHTML));
                return false;
            }
        }
    };

    //var _down = function(ev){
    //    console.info(ev.target, ev);
    //};

    $('.mr-chatview').delegate('.downLoadFile', 'click', _fnDown);
    /*$('.mr-chatview').delegate('.downLoadFileMask','click',function(){
        if(window.Electron){
            var url = $(this).prev().attr('href');
            var localPath = window.Electron.chkFileExists(url);
            console.log(localPath);
            $(this).prev('a').click();
        }
    });*/

    $('.mr-chatview').undelegate('.openFile','click')
    $('.mr-chatview').delegate('.openFile','click',function(){
        var URL = $(this).parent().prev().attr('href');
        window.Electron.openFile(URL);
    })
    $('.mr-chatview').undelegate('.openFloder','click');
    $('.mr-chatview').delegate('.openFloder','click',function(){
        var URL = $(this).parent().prev().attr('href');
        window.Electron.openFileDir(URL);
    })
})


function chDownloadProgress(){
    console.log('filedownload');
}