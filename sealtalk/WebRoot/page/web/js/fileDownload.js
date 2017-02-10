/**
 * Created by zhu_jq on 2017/2/7.
 */
$(function(){
    $('.mr-chatview').delegate('#downLoadFile','click',function(){
        if(window.Electron){
            var url = $(this).prev().attr('src');
            var localPath = window.Electron.chkFileExists(url);
            console.log(localPath);
            //$(this).prev().click();
            //var LinkSources = $('<a href="'+url+'"></a>a>')
            //var mainWindow = window.Electron.ipcRenderer.chDownloadProgress(url);
            //mainWindow.webContents.session.on('will-download'
        }
    })


    $('.mr-chatview').delegate('.openFile','click',function(){
        var URL = $(this).parent().prev().attr('href');
        window.Electron.openFile(URL);
    })

    $('.mr-chatview').delegate('.openFloder','click',function(){
        var URL = $(this).parent().prev().attr('href');
        window.Electron.openFileDir(URL);
    })
})


function chDownloadProgress(){
    console.log('filedownload');
}


function chDownloadProgress(url, state, progress){
    if (state == 'progressing') {
        console.log(url, state, progress);
        var fileName = url.split('attname=')[1];
        var file = fileName.split('.')[0];
        var targetA = $("a[fileName=" + file + "]");
        var targetParent = targetA.parents('.mr-ownChat');
        if ($('#down_process[uniquetime=' + file + ']').length == 0) {
            $('#down_process[uniquetime=' + file + ']').remove();
            var sHTML = '<div id="down_process" uniquetime="' + file + '">' +
                '<div id="down_precent" uniquetime="' + file + '" style="width: 0%;">' +
                '</div>' +
                '</div>'
            targetParent.append(sHTML);
        } else {
            $('#down_process[uniquetime=' + file + ']').find('#down_precent').css('width', '100%');
        }
    }
    console.log(targetA);
}


function chDownloadState(url, state){
    console.log(state);
    if (state == 'completed') {
        var fileName = url.split('attname=')[1];
        var file = fileName.split('.')[0];
        var targetA = $("a[fileName=" + file + "]");
        var targetParent = targetA.parents('.mr-ownChat');
        $('#down_process[uniquetime=' + file + ']').remove();
        var sHTML = '<div id="fileOperate" uniquetime="1486626340273">' +
            '<span class="openFile">打开文件</span><span class="openFloder">打开文件夹</span>' +
            '</div>';
        targetParent.append(sHTML);
    }
}