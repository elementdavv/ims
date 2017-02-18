/**
 * Created by gao_yn on 2017/1/9.
 */
$(document).ready(function(){
    var groupTimer=null,groupTimer1 = null;
   // var sAccount = localStorage.getItem('account');
   $('#perInfo').on('click','li',function(){
        $('#perInfo li').removeClass('active');
       $(this).addClass('active');
       $('#infoDetailsBox>div').addClass('chatHide');
       var sType=$(this).attr('data-type');
       $('#infoDetailsBox>div').eq($(this).index()).removeClass('chatHide');
       switch(sType){
           case 'd':
               //$('.infoDetailsBox').find('.infoDetails-data').removeClass('chatHide');
               break;
           case 'r':
               var sTargettype=$('#perContainer').attr('targettype');
               var sTargetid=$('#perContainer').attr('targetid');
               var $perEle=$('#infoDetailsBox .infoDet-chatRecord').find('.infoDet-page');
               var oPagetest = new PageObj({divObj:$perEle,pageSize:20,conversationtype:sTargettype,targetId:sTargetid},function(type,list,callback)//声明page1
               {
                   getChatRecord(list,'#infoDetailsBox .infoDet-chatRecord .chatRecordSel');
               });
               break;
           case 'f':
               var sTargettype=$('#perContainer').attr('targettype');
               var sTargetid=$('#perContainer').attr('targetid');
               var $perEle=$('#infoDetailsBox .infoDet-flieRecord').find('.infoDet-page');
               var oPagetest = new PageObj({divObj:$perEle,pageSize:20,conversationtype:sTargettype,targetId:sTargetid,hosFile:'RC:FileMsg'},function(type,list,callback)//声明page1
               {
                   getFileRecord(list,'#infoDetailsBox .infoDet-flieRecord .chatRecordSel');
               });
               break;
       }
   });
    //复制粘贴
    $('#chatBox').delegate('.mr-chatContent .mr-ownChat,.mr-chatContent .mr-chatBox,.mr-chatContent .uploadImgFile','mousedown',function(e){
        $('.myContextMenu').remove();
        if(e.buttons==2){
            var left = e.clientX+10;
            var top = e.clientY-20;
            var memship = $(this).closest('.orgNavClick').attr('targetid');
            var targeType = $(this).closest('.orgNavClick').attr('targettype');
            var arr = [{limit:'',value:'复制'}];
            var style = 'left:'+left+'px;top:'+top+'px';
            var id = 'infoCopy';
            $('#chatBox .mr-ownChat').removeClass('active');
            $('#chatBox .mr-chatBox').removeClass('active');
            $('#chatBox .uploadImgFile').removeClass('active');
            $(this).addClass('active');
            fshowContexMenu(arr,style,id,memship,targeType,false);
        }
    });
    //消息复制
    $('body').on('click','#infoCopy li',function(){
        $('.myContextMenu').remove();
        var eTarget=$('#chatBox .mr-chatContent li .active');
        var sImgSrc='';
        var sInfoContent='';
        var oCopy={};
        if(eTarget.hasClass('uploadImgFile')){
            sImgSrc=eTarget.attr('src');
            oCopy.img=sImgSrc;
            var sCopy=JSON.stringify(oCopy);
            window.localStorage.setItem('copy',sCopy);
        }else{
            if(eTarget.find('.file_content').length>0){
                var eFile=eTarget.find('.file_content');
                var sFileName=eFile.find('.file_name').html();
                var sFileSize=eFile.find('.file_size').attr('data-s');
                var sFileType=eFile.find('.file_name').attr('data-type');
                oCopy.file={};
                oCopy.file.name=sFileName;
                oCopy.file.size=sFileSize;
                oCopy.file.type=sFileType;
                var sCopy=JSON.stringify(oCopy);
                window.localStorage.setItem('copy',sCopy);
            }else{
                sInfoContent=eTarget.find('span').html();
                oCopy={};
                oCopy.infoContent=sInfoContent;
               var sCopy=JSON.stringify(oCopy);
                window.localStorage.setItem('copy',sCopy);
            }
        }
    });
    $('#chatBox').on('mousedown','#message-content',function(e){
        if(window.localStorage.getItem('copy')){
            $('.myContextMenu').remove();
            if(e.buttons==2) {
                var left = e.clientX + 10;
                var top = e.clientY - 20;
                var memship = $(this).closest('.orgNavClick').attr('targetid');
                var targeType = $(this).closest('.orgNavClick').attr('targettype');
                var arr = [{limit:'',value:'粘贴'}];
                var style = 'left:'+left+'px;top:'+top+'px';
                var id = 'infoPaste';
                fshowContexMenu(arr,style,id,memship,targeType,false);

            }
        }
    });
    $('body').on('click','#infoPaste li',function(){
        $('.myContextMenu').remove();
        var oPast=JSON.parse(window.localStorage.getItem('copy'));
        var sImgSrc=oPast.img;
        var sInfoContent=oPast.infoContent;
        var sFile=oPast.file;
        var sOldInfo=$('#chatBox #message-content').html();
        $('#chatBox #message-content').html('');
        if(sImgSrc){
            var sImg='<img src="'+sImgSrc+'"; class="uploadImgFile"/>';
            var sNewInfo=sOldInfo+sImg;
            $('#chatBox #message-content').html(sImg);
        }else if(sInfoContent){
            var sNewInfo=sOldInfo+sInfoContent;
            $('#chatBox #message-content').html(sNewInfo);
        }else if(sFile){
            sFile.uniqueTime=new Date().getTime();
            sFile.filepaste=1;
            var extra = "uploadFile";
            var targetId = $('.mesContainerGroup').attr('targetID');
            var targetType = $('.mesContainerGroup').attr('targetType');
            var fileInfo=JSON.stringify(sFile);
                sendMsg(fileInfo,targetId,targetType,extra);
                //sendByRongFile(sFile,targetId,targetType);
        }

    });
    //搜索常用人历史记录
    $('#personalData').on('click','.searchHostoryInfo',function(){
        var sTargettype=$('#perContainer').attr('targettype');
        var sTargetid=$('#perContainer').attr('targetid');
        var sVal=$(this).prev().val();
        sVal=sVal.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
        if(sVal==''){
            new Window().alert({
                title   : '',
                content : '请输入您要搜索的内容！',
                hasCloseBtn : false,
                hasImg : true,
                textForSureBtn : false,
                textForcancleBtn : false,
                autoHide:true
            });
        }else{
            var $perEle=$('#infoDetailsBox .infoDet-chatRecord').find('.infoDet-page');
            var oPagetest = new PageObj({divObj:$perEle,pageSize:20,searchstr:sVal,conversationtype:sTargettype,targetId:sTargetid},function(type,list,callback)//声明page1
            {
                getChatRecord(list,'#infoDetailsBox .infoDet-chatRecord .chatRecordSel');

            });
        }

    });
    //搜索群组历史记录
    $('#groupData').on('click','.searchHostoryInfo',function(){
        var sTargettype=$('#groupContainer').attr('targettype');
        var sTargetid=$('#groupContainer').attr('targetid');
        var sVal=$(this).prev().val();
        sVal=sVal.replace(/^\s+|\s+$/g,'').replace(/\s+/g,' ');
        if(sVal==''){
            new Window().alert({
                title   : '',
                content : '请输入您要搜索的内容！',
                hasCloseBtn : false,
                hasImg : true,
                textForSureBtn : false,
                textForcancleBtn : false,
                autoHide:true
            });
        }else{
            var $groupEle=$('#groupDetailsBox .infoDet-chatRecord').find('.infoDet-page');
            var oPagetest = new PageObj({divObj:$groupEle,pageSize:20,searchstr:sVal,conversationtype:sTargettype,targetId:sTargetid},function(type,list,callback)//声明page1
            {
                getChatRecord(list,'#groupDetailsBox .infoDet-chatRecord .chatRecordSel');
                //showHistoryMessages(list);

            });
        }
    });
    //群组消息面打扰
    $('#groupData').delegate('.voiceSet','click',function(){
        var _this = $(this);
        var flag = _this.hasClass('active');
        var states = flag?0:1;
        //设置消息免打扰的接口
        var groupid = $('#groupContainer').attr('targetid');
        var sdata = localStorage.getItem('datas');
        var userid = JSON.parse(sdata).id;
        sendAjax('fun!setNotRecieveMsg',{status:states,groupid:groupid,userid:userid},function(data){
            if(data){
                var datas = JSON.parse(data);
                if(datas&&datas.code==1){
                    flag?_this.removeClass('active'):_this.addClass('active');
                }
            }
        });
    })


    //群禁言设置
    $('#groupData').on('click','.groupInfo-noChat',function(){

        var groupid=$(this).attr('data-groupid');
        var sdata = localStorage.getItem('datas');
        var accountID = JSON.parse(sdata).id;
        var groupInfo = groupInfoFromList(groupid);
        //console.log(groupInfo);
        if(accountID!=groupInfo.mid){//任何人都可以禁言？？
            //return false;
        }
        var sChat=$(this).attr('data-chat');
        if(sChat==1){
            new Window().alert({
                title   : '关闭全员禁言',
                content : '确定要关闭全员禁言吗？',
                hasCloseBtn : true,
                hasImg : true,
                textForSureBtn : '确定',              //确定按钮
                textForcancleBtn : '取消',            //取消按钮
                handlerForCancle : null,
                handlerForSure : function(){
                    //解散群组接口
                    var datas = localStorage.getItem('datas');
                    //if(sAccount){
                    var data = JSON.parse(datas);
                    var userid = data.id;
                    sendAjax('group!unShutUpGroup',{groupid:groupid},function(data){
                        if(data){
                            var oData=JSON.parse(data);
                            if(oData.code==1){
                                $('#groupData .groupInfo-noChat').attr('data-chat',0);
                                $('#groupContainer #message-content').html('');
                                $('#groupContainer #message-content').attr('contenteditable','true');
                                $('#groupContainer #message-content').attr('placeholder','请输入文字...');
                            }
                        }
                    },function(){
                        console.log('失败');
                    })
                }
            });
        }else{
            new Window().alert({
                title   : '开启全员禁言',
                content : '确定要开启全员禁言吗？',
                hasCloseBtn : true,
                hasImg : true,
                textForSureBtn : '确定',              //确定按钮
                textForcancleBtn : '取消',            //取消按钮
                handlerForCancle : null,
                handlerForSure : function(){
                    //解散群组接口
                    var datas = localStorage.getItem('datas');
                    var data = JSON.parse(datas);
                    var userid = data.id;
                    sendAjax('group!shutUpGroup',{groupid:groupid},function(data){
                        if(data){
                            var oData=JSON.parse(data);
                            if(oData.code==1&&accountID!=groupInfo.mid){
                                $('#groupData .groupInfo-noChat').attr('data-chat',1);
                                $('#groupContainer #message-content').attr('contenteditable','false');
                                $('#groupContainer #message-content').html('群主已开启禁言!');
                            }
                        }
                    },function(){
                        console.log('失败');
                    })
                }
            });
        }
    });
    //点击侧边栏
    $('#perContainer').on('click','.messageRecord .mr-record',function(){
        if($('#perContainer').hasClass('mesContainer-translateL')){
            $('#perContainer').removeClass('mesContainer-translateL');
            $(this).removeClass('active');
            $('#personalData').addClass('chatHide');
        }else{
            $('#perContainer').addClass('mesContainer-translateL');
            $(this).addClass('active');
            $('#personalData').removeClass('chatHide');
            $('#personalData .infoDetails li').removeClass('active');
            $('#personalData .infoDetails li').eq(0).addClass('active');
            $('#personalData .infoDetailsBox>div').addClass('chatHide');
            $('#personalData .infoDetailsBox>div').eq(0).removeClass('chatHide');
            var targetID=$('#perContainer').attr('targetid');
            getPerInfo(findMemberInList(targetID));
        }
    });
//    后台管理
    fPersonalSet();
    $('#backstageMgId').on('click','li',function(){
        $('#backstageMgId li').removeClass('active');
        $(this).addClass('active');
        $('.perSetBox').addClass('chatHide');
        $('.perSetBox').eq($(this).index()).removeClass('chatHide');
    });
    $('#chatBox').on('click','#changeHeadImgId',function(){
        $('.bMgMask').removeClass('chatHide');
        $('#crop-avatar').removeClass('chatHide');
        var sImgsrc=$('.perSetBox-rightCont img').attr('src');
        $('#crop-avatar .avatar-view').empty();
        $('#crop-avatar .avatar-view').append('<img src="'+sImgsrc+'"/>');
        //$('#crop-avatar .avatar-view img').attr('src',sImgsrc);
        $('.avatar-preview').empty();
        $('.avatar-preview').append('<img src="'+sImgsrc+'"/>');
        //$('.avatar-preview img').attr('src',sImgsrc);
       getHeadImgList();
    });
    //群组悬停
    $('.groupChatList').delegate('li .groupImg','mouseenter',function(e){
        var _this = $(this).closest('li');
        var sId=_this.attr('targetid');
        groupTimer=setTimeout(function(){
            var data = '';
            var pos = {};
            pos.top = e.clientY;
            pos.left = 300;
            var account = _this.attr('account');
            var datas = localStorage.getItem('groupInfo');
            var data = JSON.parse(datas);
            var aText=data.text;
            for(var i = 0;i<aText.length;i++){
                if(aText[i].GID==sId){
                    showGroupMemberInfo(aText[i],pos);
                }
            }
        },1000);
    });
    $('.groupChatList').delegate('li .groupImg','mouseleave',function(e){
        clearTimeout(groupTimer);
        groupTimer1 = setTimeout(function(){
            $('.groupDataBox').remove();
        },1000)
    });
    $('body').delegate('.groupDataBox','mouseenter',function(){
        clearTimeout(groupTimer1);
    })
    $('body').delegate('.groupDataBox','mouseleave',function(){
        $('.groupDataBox').remove();
    })
    /*群组打开右边栏*/
    $('#groupContainer').on('click','.messageRecord .mr-record',function(){
        if($('#groupContainer').hasClass('mesContainer-translateL')){
            $('#groupContainer').removeClass('mesContainer-translateL');
            $(this).removeClass('active');
            $('#groupData').addClass('chatHide');
        }else{
            $('#groupContainer').addClass('mesContainer-translateL');
            $(this).addClass('active');
            $('#groupData').removeClass('chatHide');
            $('#groupData .infoDetails li').removeClass('active');
            $('#groupData .infoDetails li').eq(0).addClass('active');
            $('#groupData .infoDetailsBox>div').addClass('chatHide');
            $('#groupData .infoDetailsBox>div').eq(0).removeClass('chatHide');
            var targetID=$('#groupContainer').attr('targetid');
            getGroupDetails(targetID);

        }
    });
    /*点击群组右边选项卡*/
    $('#groupData').on('click','.infoDetails li',function(){
        $('#groupData .infoDetails li').removeClass('active');
        $(this).addClass('active');
        $('#groupData .infoDetailsBox>div').addClass('chatHide');
        var sType=$(this).attr('data-type');
        $('#groupData .infoDetailsBox>div').eq($(this).index()).removeClass('chatHide');
        switch(sType){
            case 'd':
                //$('.infoDetailsBox').find('.infoDetails-data').removeClass('chatHide');
                break;
            case 'r':
                var sTargettype=$('#groupContainer').attr('targettype');
                var sTargetid=$('#groupContainer').attr('targetid');
                var $groupEle=$('#groupDetailsBox .infoDet-chatRecord').find('.infoDet-page');
                console.log($groupEle);
                var oPagetest = new PageObj({divObj:$groupEle,pageSize:20,conversationtype:sTargettype,targetId:sTargetid},function(type,list,callback)//声明page1
                {
                    getChatRecord(list,'#groupDetailsBox .infoDet-chatRecord .chatRecordSel');
                    //showHistoryMessages(list);

                });
                //historyMsg(sTargettype,sTargetid,0,20);
                break;
            case 'f':
                var sTargettype=$('#groupContainer').attr('targettype');
                var sTargetid=$('#groupContainer').attr('targetid');
                var $groupEle=$('#groupDetailsBox .infoDet-flieRecord').find('.infoDet-page');
                var oPagetest = new PageObj({divObj:$groupEle,pageSize:20,conversationtype:sTargettype,targetId:sTargetid,hosFile:'RC:FileMsg'},function(type,list,callback)//声明page1
                {
                    getFileRecord(list,'#groupDetailsBox .infoDet-flieRecord .chatRecordSel');
                });
                break;
        }
    });
    $('#chatBox').on('keyup change','#cp-newPasswordId',function(){
        checklevel(this.value)
    });
    $('#chatBox').on('blur','#oldPassword',function(event){
        $('.oldPassworderror').html('');
        var sAccount=localStorage.getItem('account');
        var oAccount=JSON.parse(sAccount);
        if(oAccount) {
            var sOldAccount = oAccount.text.account;
            sendAjax('system!valideOldPwd', {account:sOldAccount,oldpwd: hex_md5(this.value)}, function (data) {
                var oData = JSON.parse(data);
                if (oData.code == 1) {
                    $('.oldPassworderror').html('');
                } else {
                    $('.oldPassworderror').html('原始密码错误');
                }
            });
        }
        event.stopPropagation();
    });
    $('#chatBox').on('keydown','#oldPassword',function(){
        $('.oldPassworderror').html('');
    });
    $('#chatBox').on('keydown','#comparepwd',function(){
        $('.retMewPw').html('');
    });
    /*修改密码保存*/
    $('#systemSet-keep').click(function(){
        var sAccount=localStorage.getItem('account');
        var oAccount=JSON.parse(sAccount);
        if(oAccount) {
            var sOldAccount = oAccount.text.account;
            sendAjax('system!valideOldPwd', {account:sOldAccount,oldpwd: hex_md5($('#oldPassword').val())}, function (data) {
                var oData = JSON.parse(data);
                if (oData.code == 1) {
                    $('.oldPassworderror').html('');
                    if($('#cp-newPasswordId').val()==''){
                        return;
                    }
                    var sNewPw=hex_md5($('#cp-newPasswordId').val());
                    var sComPd=hex_md5($('#comparepwd').val());
                    if(sNewPw == sComPd){
                        keerNewPw(hex_md5($('#oldPassword').val()),sNewPw,sComPd);
                        $('.retMewPw').html('');
                    }else{
                        $('.retMewPw').html('两次输入密码不一致');
                    }
                } else {
                    $('.oldPassworderror').html('原始密码错误');
                    return;
                }
            });
        }

    });
    /*系统提示音*/
    $('#chatBox').on('click','#systemSet .systemVoiceBtn',function(){
        //var status=parseInt($(this).attr('data-state'));//0 代表关闭  1代表开启
        //var eParent=$(this);
        if($(this).hasClass('active')){
            $(this).removeClass('active');
        }else{
            $(this).addClass('active');
        }
        /*switch(status){
            case 0:
                eParent.addClass('active');
                break;
            case 1:
                eParent.removeClass('active');
                break;
        }*/
    });
    $('#chatBox').on('click','#systemSet .systemSet-keep',function(){
       // var status=parseInt($(this).attr('data-state'));//0 代表关闭  1代表开启
        var status;
        var eVoice=$('#chatBox #systemSet .systemVoiceBtn');
        if(eVoice.hasClass('active')){
            status=0;
        }else{
            status=1;
        }
        var sdata = localStorage.getItem('datas');
        var accountObj = JSON.parse(sdata);
        //var account = accountObj.account;
        var accountID = accountObj.id;
        systemBeep(status,accountID);
    });
    $('#chatBox').on('click','.dateIcon',function(){
        $('.calendar-inputWrap').click();
        $('.calendar-inputWrap').focus();
    });
    $('#groupMap').on('click','.messageRecord b',function(e){
        var targetID = $(e.target).closest('.groupMap').attr('targetid');
        var targeType = $(e.target).parents('.groupMap').attr('targettype');
        var grounpName = $(e.target).prev('span').html();
        switch(targeType){
            case 'GROUP':
                conversationGroup(targetID,targeType,grounpName);
                $('.orgNavClick').addClass('chatHide');
                $('.mesContainerGroup').removeClass('chatHide');
                break;
            case 'PRIVATE':
                conversationSelf(targetID,targeType);
                $('.orgNavClick').addClass('chatHide');
                $('.mesContainerSelf').removeClass('chatHide');
                break;
        }
    });
        $('#avatarInput').change(function(e){
            var file=e.target.files || e.dataTransfer.files;
            if(file){
                $('.bMg-cropImgSet').addClass('chatHide');
                $('.bMg-cropImgBox').removeClass('chatHide');
                $('.bMg-gravityImg').addClass('active');
                $('.bMg-confirm').removeClass('chatHide');
                $('.bMg-preserve').addClass('chatHide');
            }
        });
    //点击保存头像
    $('#crop-avatar').on('click','.bMg-preserve .bMg-keepImg',function(){
        var sData=window.localStorage.getItem("datas");
        var oData= JSON.parse(sData);
        var sId=oData.id;
        var picname='';
        var nDelImg;
        $('.bMg-cropImgSet .bMg-imgList li').each(function(index){
            if($(this).hasClass('active')){
                picname=$(this).attr('data-name');
                nDelImg=index;
            }
        });
       if(!picname){
           new Window().alert({
               title   : '',
               content : '请选择一个照片作为您的头像！！！',
               hasCloseBtn : false,
               hasImg : true,
               textForSureBtn : false,
               textForcancleBtn : false,
               autoHide:true
           });
           return false;
       }else{
           sendAjax('upload!secUserLogos',{userid:sId,picname:picname},function(data){
               var oDatas=JSON.parse(data);
               if(oDatas.code==1){
                   $('#personSettingId .perSetBox-head').attr('src','upload/images/'+picname);
                   $('.bMgMask').addClass('chatHide');
                   $('#crop-avatar').addClass('chatHide');
                   oData.logo=picname;
                   var sNewData=JSON.stringify(oData);
                   localStorage.setItem("datas",sNewData);
               }
           });
       }
    });
    $('#crop-avatar').on('click','.bMg-confirm .bMg-cancel',function(){
        $('.bMg-cropImgSet').removeClass('chatHide');
        $('.bMg-cropImgBox').addClass('chatHide');
        $('.bMg-gravityImg').removeClass('active');
        $('.bMg-confirm').addClass('chatHide');
        $('.bMg-preserve').removeClass('chatHide');
        //$('.avatar-preview').empty();
        //$('.avatar-preview').append('');
    });
    $('#crop-avatar').on('click','.bMg-preserve .bMg-cancel,#bMg-closeBtn',function(){
        $('.bMgMask').addClass('chatHide');
        $('#crop-avatar').addClass('chatHide');
    });
    $('#crop-avatar').on('click','.bMg-gravityImg .bMg-delImg',function(){
        var picname;
        var nDelImg;
        $('.bMg-cropImgSet .bMg-imgList li').each(function(index){
            if($(this).hasClass('active')){
                picname=$(this).attr('data-name');
                nDelImg=index;
            }
        });
        var sData=window.localStorage.getItem("datas");
        var oData= JSON.parse(sData);
        var sId=oData.id;
        sendAjax('upload!delUserLogos',{userid:sId,picname:picname},function(data){
            var oDatas=JSON.parse(data);
            if(oDatas.code==1){
                $('.bMg-cropImgSet .bMg-imgList li').eq(nDelImg).remove();
            }
        });
    });
    $('#personSettingId').on('click','.perSetBox-keep',function(){
        var sEmail=$('#personSettingId .perSetBox-email').val();
        var sSex=$('#personSettingId .perSetBox-selSex').val();
        switch (sSex){
            case "男":
                sSex=1;
                break;
            case "女":
                sSex=0;
                break;
        }
        var sTelephone=$('#personSettingId .perSetBox-telephone').val();
        var sSign=$('#personSettingId .perSetBox-textarea').text();
        var sData=window.localStorage.getItem("datas");
        var oData= JSON.parse(sData);
        var sId=oData.id;
        sendAjax('member!updateMemberInfoForWeb',{userid:sId,sex:sSex,email:sEmail,phone:sTelephone,sign:sSign},function(data){
            var oDatas=JSON.parse(data);
           if(oDatas.code==1){
               //var sData=window.localStorage.getItem("datas");
              // var oData= JSON.parse(sData);

               //var sSelfImg=oData.logo;
               oData.sex=sSex;
               oData.email=sEmail;
               oData.phone=sTelephone;
               oData.sign=sSign;
               var sDatas=JSON.stringify(oData);
               window.localStorage.setItem("datas",sDatas);
               new Window().alert({
                   title   : '',
                   content : '个人资料保存成功！',
                   hasCloseBtn : false,
                   hasImg : true,
                   textForSureBtn : false,
                   textForcancleBtn : false,
                   autoHide:true
               });
           }
        });

    });
    $('#crop-avatar').on('click','.bMg-cropImgSet .bMg-imgList li',function(){
        $('.bMg-cropImgSet .bMg-imgList li').removeClass('active');
        $(this).addClass('active');
        var sSelImg=$(this).attr('data-name');
        $('.avatar-preview').empty();
        $('.avatar-preview').append('<img src="'+globalVar.imgSrc+sSelImg+'"/>');
        //$('.avatar-preview img').attr('src',globalVar.imgSrc+sSelImg);
    });
    $('#Uploader').delegate('#showGrid','click',function(){
        if($('.cropper-crop-box').hasClass('cropper-hidden')){
            $('.cropper-crop-box').removeClass('cropper-hidden');
        }else{
            $('.cropper-crop-box').addClass('cropper-hidden');
        }
    });
    $('#personSettingId').delegate('.perSetBox-telephone','keyup',function(){
        $(this).val($(this).val().replace(/[^\d]/ig,''));
    });
    //getGroupMembersList(1);
});

//查询单个群信息
function groupInfoFromList(id){
    var groupInfo = localStorage.getItem('groupInfo');
    if(groupInfo){
        groupInfo = JSON.parse(groupInfo);
    }
    var curInfo = '';
    for(var i = 0;i<groupInfo.text.length;i++){
        if(groupInfo.text[i].GID==id){
            curInfo = groupInfo.text[i]
        }
    }
    return curInfo;
    //sendAjax(url,data,callback)
}


function fPersonalSet(){
   var sData=window.localStorage.getItem("datas");
    var oData= JSON.parse(sData);
    if(oData){
        var sName=oData?oData.name|| '' : '';//姓名
        var sAccountNum=oData?oData.account|| '' : '';//成员账号
        var sSex=oData.sex;//性别
        switch(sSex){
            case 1:
                sSex= '男';
                break;
            case 0:
                sSex= '女';
                break;
            default :
                sSex= '女';
                break;
        }
        var sPosition=oData.positionname || '';//职位
        var sBranch=oData.branchname || '';//部门
        var sEmail=oData.email || '';//邮箱
        var sTelephone=oData.telephone || '';//电话
        var sSign=oData.organname || '';//工作签名
        var sHeaderImg=oData.logo?globalVar.imgSrc+oData.logo:globalVar.defaultLogo;//头像
        var sHtml='<h3 class="perSetBox-title">个人设置</h3>\
    <div class="perSetBox-content clearfix">\
    <div class="perSetBox-leftCont">\
    <ul class="perSetBox-contDetails">\
    <li >\
    <span>成员账号:</span>\
    <p class="perSetBox-account">'+sAccountNum+'</p>\
    </li>\
    <li>\
    <span>姓名：</span>\
    <p class="perSetBox-name">'+sName+'</p>\
    </li>\
    <li>\
    <span>性别：</span>\
    <p>\
    <select class="perSetBox-selSex">\
    <option value="男" selected>男</option>\
    <option value="女">女</option>\
    </select>\
    </p>\
    </li>\
    <li>\
    <span>职位：</span>\
    <p class="perSetBox-position">'+sPosition+'</p>\
    </li>\
    <li>\
    <span>部门：</span>\
    <p class="perSetBox-branch">'+sBranch+'</p>\
    </li>\
    <li>\
    <span>邮箱：</span>\
    <p>\
    <input value="'+sEmail+'" class="perSetBox-editText perSetBox-email"/>\
    </p>\
    </li>\
    <li>\
    <span>电话：</span>\
    <p>\
    <input value="'+sTelephone+'" class="perSetBox-editText perSetBox-telephone"/>\
    </p>\
    </li>\
    <li>\
    <span>工作签名：</span>\
    <p>\
    <textarea class="perSetBox-textarea" >'+sSign+'</textarea>\
    </p>\
    </li>\
    </ul>\
    <button class="perSetBox-keep">保存</button>\
    </div>\
    <div class="perSetBox-rightCont">\
    <img src="'+sHeaderImg+'" class="perSetBox-head"/>\
    <p class="perSetBox-modifyHead" id="changeHeadImgId">修改头像</p>\
    </div>\
    </div>';
        $('#chatBox #personSettingId').empty();
        $('#chatBox #personSettingId').append(sHtml);
        $('.perSetBox-selSex').val(sSex);
    }
}
function showGroupMemberInfo(oGroupInfo,pos){
    var sName=oGroupInfo.name || '';//群名称
    var sCreatorId=oGroupInfo.mid;//群创建者id
    var sCreatorAcconut=oGroupInfo.account//群创建者帐号
    var sCreatedate=subTimer(oGroupInfo.createdate);//创建时间
    var oCreator=findMemberInList(sCreatorId);
    if(!oCreator.logo){
        var sImg=globalVar.defaultLogo;
    }else{
        var sImg=globalVar.imgSrc+oCreator.logo;
    }
   /* var sImg=oCreator.logo || globalVar.defaultLogo;*/
    //console.log(findMemberInList(sCreatorId));
    //var aCreatedate=sCreatedate.join('-');
    var sHTML ='<div class="groupDataBox" style="left:'+pos.left+'px;top:'+pos.top+'px">\
        <ul>\
        <li>\
        <span>群组名称:</span>\
    <b>'+sName+'</b>\
    </li>\
    <li>\
    <span>创建时间:</span>\
    <b>'+sCreatedate+'</b>\
    </li>\
    <li>\
    <span>群主/管理员:</span>\
    <i>\
    <img src="'+sImg+'">\
    <span class="groupCreater">'+sCreatorAcconut+'</span></i>\
    </li>\
    </ul>\
    </div>';
    $('body').append($(sHTML));
}
function subTimer(string){
    var y=string.substring(0,4);
    var m=string.substring(4,6);
    var d=string.substring(6,8);
    return y+'-'+m+'-'+d;
}
function checkstr(str)
{
    if (str >= 48 && str <= 57)//数字
    {
        return 1;

    } else if (str >= 65 && str <= 90)//大写字母
    {
        return 2;
    } else if (str >= 97 && str <= 122)//小写字母
    {
        return 3;
    }else//特殊字符
    {
        return 4;
    }
}
function checkl(string)
{
    n = false;
    s = false;
    t = false;
    l_num = 0;
    if(string.length <= 0){
        l_num = 0;
    }
    if (string.length < 6&& string.length > 0)
    {
        l_num = 1;
    } else {
        for (i = 0; i < string.length; i++)
        {
            asc= checkstr(string.charCodeAt(i));
            if (asc == 1 && n == false)
            {
                l_num += 1;
                n= true;
            }
            if ((asc == 2 || asc == 3) && s == false)
            {
                l_num += 1;
                s= true;
            }
            if (asc == 4 && t == false)
            {
                l_num += 1;
                t= true;
            }
        }

    }
    return l_num;
}
function checklevel(psw)
{
    color= "#ededed";

    color_l= "#ff0000";

    color_m= "#ff9900";

    color_h= "#33cc00";
    if (psw == null || psw == '')
    {
        lcor= color;

        mcor= color;

        hcor= color;
    } else
    {
        thelev= checkl(psw);
        switch (thelev) {
            case 0:
                lcor= hcor= mcor= color;
                break;
            case 1:
                lcor= color_l;
                hcor= mcor= color;
                break;
            case 2:
                mcor= lcor= color_m;
                hcor= color;
                break;
            case 3:
                hcor= mcor= lcor= color_h;
                break;
            default:
                lcor= mcor= hcor= color;
        }
    }

    document.getElementById("strength_L").style.background= lcor;

    document.getElementById("strength_M").style.background= mcor;

    document.getElementById("strength_H").style.background= hcor;

}
function editOldPassword(sNewPw){
    var sAccount=localStorage.getItem('account');
    var oAccount=JSON.parse(sAccount);
    if(oAccount){
       var sAccount=oAccount.text.account;
        sendAjax('system!valideOldPwd',{oldpwd:sAccount},function(data){
            var oData=JSON.parse(data);
            if(oData.code==1){
                var sPassword=oAccount.userpwd;
                if(sNewPw == sPassword){
                    return true;
                }else{
                    return false;
                }
            }
        });
    }
}
function keerNewPw(oldpwd,newPw,comparepwd){
    var sAccount=localStorage.getItem('account');
    var oAccount=JSON.parse(sAccount);
    //var sPassword=oAccount.userpwd;
    var sAccNum=oAccount?oAccount.text.account :'';
    sendAjax('system!newPassword',{account:sAccNum,oldpwd:oldpwd,newpwd:newPw,comparepwd:comparepwd},function(data){
        console.log(JSON.parse(data));
        var oData=JSON.parse(data);
        if(oData.code==1){
            new Window().alert({
                title   : '',
                content : '密码保存成功！',
                hasCloseBtn : false,
                hasImg : true,
                textForSureBtn : false,
                textForcancleBtn : false,
                autoHide:true
            });
            oAccount.userpwd=newPw;
            window.localStorage.account=JSON.stringify(oAccount);
        }
    });
}
/*系统提示音*/
function systemBeep(status,accountID){
    sendAjax('fun!setSysTipVoice',{status:status,userid:accountID},function(data){
        var oData=JSON.parse(data);
        //var eParent=$('#chatBox #systemSet .systemVoiceBtn');
        if(oData.code==1){
            globalVar.SYSTEMSOUND=!globalVar.SYSTEMSOUND;
        }
    });
}
function getHeadImgList(){
    var sData=window.localStorage.getItem("datas");
    var oData= JSON.parse(sData);
    var sId=oData.id;
    var sSelfImg=oData.logo;
    sendAjax('upload!getUserLogos',{userid:sId},function(data){
        var oDatas=JSON.parse(data);
        var aImgList=oDatas.text;
        var sDom='';
        if(oDatas.code==1){
           if(aImgList.length>0){
               for(var i=0;i<aImgList.length;i++){
                   var sImg=aImgList[i];
                   if(sSelfImg==sImg){
                       sDom+='<li data-name="'+sImg+'" class="active">\
                   <img src="'+globalVar.imgSrc+sImg+'"/>\
                   </li>';
                   }else{
                       sDom+='<li data-name="'+sImg+'">\
                   <img src="'+globalVar.imgSrc+sImg+'"/>\
                   </li>';
                   }
               }
               $('.bMg-cropImgSet .bMg-imgList').empty();
               $('.bMg-cropImgSet .bMg-imgList').append(sDom);
           }
        }
    });
}

