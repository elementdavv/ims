var ipcRenderer = require('electron').ipcRenderer
var remote = require('electron').remote
const path = require('path')
const fs = require('fs')
// Platform flag.
const platform = {
  OSX: process.platform === 'darwin',
  Windows: process.platform === 'win32'
}

var appInfo, RongIMClientC, configInfo

try {
  appInfo = remote.require('./package.json')
} catch (err) {
  appInfo = null
}

try {
  configInfo = remote.require('./config.js')
  // console.log('configInfo', configInfo)
} catch (err) {
  configInfo = null
}

const downloadSavePath = remote.app.getPath('downloads') + '/' + configInfo.AUTHOR;
const Utils = remote.require('./utils.js')



try {
  RongIMClientC = remote.require('RongIMLib')
} catch (err) {
  console.log(err);
  RongIMClientC = null
}

window.Electron = {
  ipcRenderer: ipcRenderer,
  // appInfo: appInfo,
  configInfo: configInfo,
  require: require,
  remote: remote,
  addon: RongIMClientC,
  updateBadgeNumber: function (number) {
    // console.log('updateBadgeNumber')
    this.ipcRenderer.send('unread-message-count-changed', number)
  },
  kickedOff: function () {
    // Notification里处理win7的提示
    this.ipcRenderer.send('kicked-off')
    var options = {
         title: "Basic Notification",
         body: "测试lalwindows baloon"

    }
    new Notification(options.title, options)
  },
  webQuit: function () {
    //修改图标
    console.log('webQuit')
    this.ipcRenderer.send('webQuit')
  },
  screenShot: function () {
    //修改图标
    this.ipcRenderer.send('screenShot')
  },
  displayBalloon: function (title, options){
    if (platform.Windows){
       this.ipcRenderer.send('displayBalloon', title, options)
    }
  },
  openFile: function (url){
    if(remote.shell){
      var savePath = path.join(downloadSavePath, Utils.getSavePath(url));
      remote.shell.openItem(savePath);
    }
  },
  openFileDir: function (url){
    if(remote.shell){
      var savePath = path.join(downloadSavePath, Utils.getSavePath(url));
      remote.shell.showItemInFolder(savePath);
    }
  },
  chkFileExists: function (url){
    console.log('bababababab');
    var savePath = path.join(downloadSavePath, Utils.getSavePath(url));
    var exist = fileExists(savePath);
    return exist ? savePath : '';
  },
  logRequest: function (){
    this.ipcRenderer.send('logRequest')
  },
  drag: function (file){
    this.ipcRenderer.send('ondragstart', file)
  }
}

function fileExists(filePath)
{
    try
    {
        return fs.statSync(filePath).isFile()
    }
    catch (err)
    {
        return false
    }
}

window.Electron.ipcRenderer.on('logOutput', (event, msg) => {
  console.log('logOutput:', msg)

})

window.Electron.ipcRenderer.on('menu.edit.search', () => {
  // console.log('menu.edit.search')
  var input = document.querySelector('div#search-friend input')
  if (input) {
    input.focus()
  }
})

window.Electron.ipcRenderer.on('menu.main.account_settings', () => {
  if (typeof(eval('_open_account_settings')) == "function") {
    _open_account_settings()
  }
  else{
    console.log('_open_account_settings do not exist');
  }
})

window.Electron.ipcRenderer.on('screenshot', () => {
  // if(typeof(upload_base64) == "undefined"){
  //   console.log('upload_base64 do not exist');
  //   return
  // }
  // if (upload_base64 && typeof(eval(upload_base64)) == "function") {
  //   upload_base64()
  // }
    var obj = document.getElementById("message-content");
            if (obj) {
                obj.focus();
                document.execCommand("Paste");
            }
})

window.Electron.ipcRenderer.on('balloon-click', (event, opt) => {
  if(typeof(BalloonClick) == "undefined"){
    console.log('BalloonClick do not exist');
    return
  }
  if (BalloonClick && typeof(eval(BalloonClick)) == "function") {
    BalloonClick(opt)
  }
})

window.Electron.ipcRenderer.on('logout', () => {

  if(typeof(logout) == "undefined"){
    console.log('logout do not exist');
    return
  }
  if (logout && typeof(eval(logout)) == "function") {
    logout()
  }
})

window.Electron.ipcRenderer.on('chDownloadProgress', (event, url, state, progress) => {
  if(typeof(chDownloadProgress) == "undefined"){
    console.log('chDownloadProgress do not exist');
    return
  }
  if (chDownloadProgress && typeof(eval(chDownloadProgress)) == "function") {
    chDownloadProgress(url, state, progress)
  }
})

window.Electron.ipcRenderer.on('chDownloadState', (event, url, state) => {
  if(typeof(chDownloadState) == "undefined"){
    console.log('chDownloadState do not exist');
    return
  }
  if (chDownloadState && typeof(eval(chDownloadState)) == "function") {
    chDownloadState(url, state)
  }
})

// window.Electron.require('electron-cookies')
/* eslint-disable no-native-reassign, no-undef */
// Extend and replace the native notifications.

function checkWin7(){
   var sUserAgent = navigator.userAgent
   var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1
   return isWin7
}

const NativeNotification = Notification

Notification = function (title, options) {
  if(platform.OSX){
    delete options.icon
  }
  const notification = new NativeNotification(title, options)
  // 消息提示均由app端调用Notification做,这里只处理win7情况(win7不支持Notification)
  notification.addEventListener('click', () => {
    console.log('click')
    window.Electron.ipcRenderer.send('notification-click')
  })
  if (platform.Windows){
    if(checkWin7() && title && options.body){
      window.Electron.displayBalloon(title, options)
    }
  }

  return notification
}

Notification.prototype = NativeNotification.prototype
Notification.permission = NativeNotification.permission
Notification.requestPermission = NativeNotification.requestPermission.bind(Notification)
/* eslint-enable no-native-reassign, no-undef */

function chDownloadProgress(url, state, progress){
  if (state == 'progressing') {
    console.log(url, state, progress);
    var fileName = url.split('attname=')[1];
    var file = fileName.split('.')[0];
    var targetA = $("a[fileName=" + file + "]");
    var targetParent = targetA.parents('.mr-ownChat').length==1?targetA.parents('.mr-ownChat'):targetA.parents('.mr-chatBox');
    //var targetParent = targetA.parents('.mr-chatBox');
    if($(targetParent[0]).hasClass('.mr-ownChat') || $(targetParent[0]).hasClass('mr-chatBox')){
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
  }
  console.log(targetA);
}


function chDownloadState(url, state){
  if (state == 'completed') {
    var fileName = url.split('attname=')[1];
    var file = fileName.split('.')[0];
    var targetA = $("a[fileName=" + file + "]");
    console.log(targetA.length);
    for(var i=0;i<targetA.length;++i){
      if(targetA.eq(i).closest('.mr-ownChat').length>0 || targetA.eq(i).closest('.mr-chatBox').length>0){
        $('#down_process[uniquetime=' + file + ']').remove();
        var sHTML = '<div id="fileOperate" uniquetime="1486626340273">' +
            '<span class="openFile">打开文件</span><span class="openFloder">打开文件夹</span>' +
            '</div>';
        var targetParent = targetA.eq(i).parents('.mr-ownChat').length==1?targetA.eq(i).parents('.mr-ownChat'):targetA.eq(i).parents('.mr-chatBox');
        targetParent.append(sHTML);
      }else if(targetA.eq(i).closest('.downLoadFileInfo').length>0){
        targetA.eq(i).closest('.downLoadFileInfo').find('#fileOperate1').remove();
        var sHTML = '<div id="fileOperate" uniquetime="1486626340273">' +
            '<span class="openFile">打开文件</span>' +
            '<span class="openFloder">打开文件夹</span>' +
            '</div>'
        targetA.eq(i).closest('.downLoadFileInfo').append($(sHTML));
      }else{
        targetA.eq(i).closest('strong').remove();
        $('.chatFile-folder').find('strong').remove();
        var sHtml='<strong  data-url="'+url+'" class="hosOpenFile">打开</strong>\
            <strong data-url="'+url+'" class="hosOpenFloder">打开文件夹</strong>';
        $('.chatFile-folder').append(sHtml);
      }
    }
    targetA.each(function(index){
    });
    //var targetParent = targetA.parents('.mr-ownChat').length==1?targetA.parents('.mr-ownChat'):targetA.parents('.mr-chatBox');
    //if(targetA.closest('.mr-ownChat') || targetA.closest('.mr-chatBox')){
    //  $('#down_process[uniquetime=' + file + ']').remove();
    //  var sHTML = '<div id="fileOperate" uniquetime="1486626340273">' +
    //      '<span class="openFile">打开文件</span><span class="openFloder">打开文件夹</span>' +
    //      '</div>';
    //  var targetParent = targetA.parents('.mr-ownChat').length==1?targetA.parents('.mr-ownChat'):targetA.parents('.mr-chatBox');
    //  targetParent.append(sHTML);
    //}else if (targetA.closest('.downLoadFileInfo').length==1){
    //  targetA.closest('.downLoadFileInfo').find('#fileOperate').remove();
    //  var sHTML = '<div id="fileOperate" uniquetime="1486626340273">' +
    //      '<span class="openFile">打开文件</span>' +
    //      '<span class="openFloder">打开文件夹</span>' +
    //      '</div>'
    //  targetA.closest('.downLoadFileInfo').append($(sHTML));
    //}else{
    //    targetA.closest('strong').remove();
    //    $('.chatFile-folder').find('strong').remove();
    //    var sHtml='<strong  data-url="'+url+'" class="hosOpenFile">打开</strong>\
    //        <strong data-url="'+url+'" class="hosOpenFloder">打开文件夹</strong>';
    //      $('.chatFile-folder').append(sHtml);
    //}
  }
}