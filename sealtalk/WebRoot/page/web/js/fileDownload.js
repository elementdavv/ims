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