var sealtalk_config = {
  //http://electron.atom.io/docs/api/crash-reporter/
  REPORT_URL: 'http://electron.atom.io/docs/api/crash-reporter/',
  //http://electron.atom.io/docs/api/browser-window/  win.loadURL(APP_ONLINE)
  //APP_ONLINE: 'http://35.164.107.27:8080/im/system!login',
  
  APP_ONLINE: 'http://localhost:8080/sealtalk/system!login',
  //your homepage for menu link
  HOME: 'http://localhost:8080/sealtalk/system!loginForWeb',
  //这个参数的理解可以参考  http://electron.atom.io/docs/api/app/  setAsDefaultProtocolClient
  PROTOCAL: 'sealtalk-ent',
  // base on 'res' dir
  //  The window Icon, BrowserWindow.icon
  WINICON: 'app.ico', 
  //以下参数设置需对照 配置说明 中 e 项列出的工具参数理解
  PRODUCTNAME: "SealTalk_Ent_Test",
  APPNAME: "SealTalk_Ent_Test",
  VERSION: "1.0.2",
  DESCRIPTION: "SealTalk Desktop application.",
  AUTHOR: "RongCloud",
  LICENSE: "MIT",
  PACKAGE: {
     RUNTIMEVERSION: "1.4.3",
     COPYRIGHT: ""
  },
  WIN: {
    APPICON: 'app.ico',
    //app.setAppUserModelId
    //参照 https://msdn.microsoft.com/en-us/library/windows/desktop/dd378459(v=vs.85).aspx
    APP_USER_MODEL_ID: 'im.sealtalk.SealTalk.SealTalkEntTest',  
    //  WINDOWS ONLY,TRAY BLINK ON
    //  new Tray,tray.setImage    
    TRAY: 'Windows_icon.png',  
    //  WINDOWS ONLY,TRAY BLINK OFF
    //  tray.setImage
    TRAY_OFF: 'Windows_Remind_icon.png',  
    //  tray.displayBalloon
    BALLOON_ICON: 'app.png',
    ICON_URL: 'http://7i7gc6.com1.z0.glb.clouddn.com/image/sealtalk.ico',
    LOADING_GIF: './res/loading.gif'
  },
  MAC: {
    APPICON: 'app.icns',
    APP_BUNDLE_ID: 'im.rongcloud.sealtalkenttest',
    HELPER_BUNDLE_ID: 'SealTalk_Ent_Test',
    //  new Tray
    TRAY: 'Mac_Template.png',
    //  tray.setPressedImage
    PRESSEDIMAGE: 'Mac_TemplateWhite.png',
    BACKGROUND: 'bg.png',
    CF_BUNDLE_VERSION: '1.0.3'
  },
  DEBUG: true
}


module.exports = sealtalk_config

