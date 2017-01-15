/**
 * Created by zhu_jq on 2017/1/10.
 */
$(function(){

    //鼠标在联系人上悬停
    var timer=null,timer1 = null;
    $('.usualChatList').delegate('li','mouseenter',function(e){
        var _this = $(this);
        timer=setTimeout(function(){
            var pos = {};
            pos.top = e.clientY;
            pos.left = e.clientX;
            var data = '';
            var account = _this.attr('account');
            var datas = localStorage.getItem('MemberFriends');
            var data = JSON.parse(datas);
            //console.log(data);
            //console.log('----------');
            for(var i = 0;i<data.length;i++){
                //console.log(account,data[i].account);
                if(data[i].account == account){
                    showMemberInfo(data[i],pos);
                    console.log(i);
                    break;
                }
            }

        },1000);
    })
    $('.usualChatList').delegate('li','mouseleave',function(e){
        clearTimeout(timer);
        timer1 = setTimeout(function(){
            $('.memberHover').remove();
        },1000)
    })
    $('body').delegate('.memberHover','mouseenter',function(){
        clearTimeout(timer1);
    })
    $('body').delegate('.memberHover','mouseleave',function(){
        $('.memberHover').remove();
    })

    //点击的事件  弹窗上的
    $(window).click(function(e){

        console.log(e.target)
        if($('.memberHover').length!=0&&$(e.target).parents('.memberHover').length==0){
            $('.memberHover').remove();
        }else{
            switch (e.target.className){
                case 'sendMsg'://发起聊天
                    var targetID = $(e.target).parents('.showPersonalInfo').attr('targetid');
                    var targeType = $(e.target).parents('.showPersonalInfo').attr('targettype');
                    conversationSelf(targetID,targeType);
                    $('.orgNavClick').addClass('chatHide');
                    $('.mesContainerSelf').removeClass('chatHide');
                    break;
                case 'checkPosition'://查看位置
                    console.log('checkPosition');
                    break;
                case 'addConver'://添加群聊
                    console.log('addConver');
                    break;
                default :
                    console.log(';;;;;');
            }
        }
        $('.myContextMenu').remove();
    })


    $('body').delegate('#newsLeftClick li','click',function(){
        //var targetID =
        var targetID = $(this).parents('.myContextMenu').attr('memship');
        $('.myContextMenu').remove();
        var index = $(this).closest('ul').find('li').index($(this));
        switch (index)
        {
            case 0:
                //置顶会话
                break;
            case 1:
                //发送文件
                break;
            case 2:
                //查看资料
                break;
            case 3:
                //添加新成员
                break;
            case 4:
                //定位到所在组织
                break;
            case 5:
                //从消息列表删除
                new Window().alert({
                    title   : '删除会话',
                    content : '确定要从会话列表中删除么？',
                    hasCloseBtn : true,
                    hasImg : true,
                    textForSureBtn : '确定',              //确定按钮
                    textForcancleBtn : '取消',            //取消按钮
                    handlerForCancle : null,
                    handlerForSure : function(){
                        removeConvers('PRIVATE',targetID);
                    }
                });

                break;
        }
    })
    //点击消息列表
    $('.newsChatList').delegate('li','mousedown',function(e){
        $('.myContextMenu').remove();
        if(e.buttons==2){
            var left = e.clientX;
            var top = e.clientY;
            var arr = ['置顶会话','发送文件','查看资料','添加新成员','定位到所在组织','从消息列表删除'];
            var style = 'left:'+left+'px;top:'+top+'px';
            var id = 'newsLeftClick';
            var memberShip = $(this).attr('targetid')
            //var memberShip =
            fshowContexMenu(arr,style,id,memberShip)
        }else{//单击常用联系人
            var targetID = $(this).attr('targetid');
            var targeType = 'PRIVATE';
            conversationSelf(targetID,targeType);
            $('.orgNavClick').addClass('chatHide');
            $('.mesContainerSelf').removeClass('chatHide');
            //showConverList();
        }
        $('.newsChatList li').removeClass('active');
        $(this).addClass('active');
        return false;
    })
    //消息列表右键菜单
    $('body').delegate('#usualLeftClick li','click',function(){
        var memShip = $('.myContextMenu').attr('memship');
        $('.myContextMenu').remove();
        var index = $(this).closest('ul').find('li').index($(this));
        switch (index)
        {
            case 0:
                new Window().alert({
                    title   : '解除好友',
                    content : '确定要解除好友吗？',
                    hasCloseBtn : true,
                    hasImg : true,
                    textForSureBtn : '确定',              //确定按钮
                    textForcancleBtn : '取消',            //取消按钮
                    handlerForCancle : null,
                    handlerForSure : function(){
                        cancleRelation(account,memShip);
                    }
                });
                break;
        }
    })
    //点击常用联系人（左右键）
    $('.usualChatList').delegate('li','mousedown',function(e){
        $('.myContextMenu').remove();
        if(e.buttons==2){
            var left = e.clientX;
            var top = e.clientY;
            var arr = ['解除好友']
            var style = 'left:'+left+'px;top:'+top+'px';
            var id = 'usualLeftClick';
            var friend = $(this).attr('account');
            //var memShip = JSON.stringify()
            fshowContexMenu(arr,style,id,friend);
        }else{//单击常用联系人
            var targetID = $(this).attr('targetid');
            var targeType = 'PRIVATE';
            conversationSelf(targetID,targeType);
            $('.orgNavClick').addClass('chatHide');
            $('.mesContainerSelf').removeClass('chatHide');
        }
        $('.usualChatList li').removeClass('active');
        $(this).addClass('active');
        return false;
    })
    //常用联系人右键菜单
    $('body').delegate('#usualLeftClick li','click',function(){
        var memShip = $('.myContextMenu').attr('memship');
        $('.myContextMenu').remove();
        var index = $(this).closest('ul').find('li').index($(this));
        switch (index)
        {
            case 0:
                //解除好友
                //if(memShip){
                //    var friend = JSON.parse(memShip);
                //}
                new Window().alert({
                    title   : '解除好友',
                    content : '确定要解除好友吗？',
                    hasCloseBtn : true,
                    hasImg : true,
                    textForSureBtn : '确定',              //确定按钮
                    textForcancleBtn : '取消',            //取消按钮
                    handlerForCancle : null,
                    handlerForSure : function(){
                        cancleRelation(account,memShip);
                    }
                });
                break;
        }
    })
    //点击群组
    $('.groupChatList').delegate('li','mousedown',function(e){
        $('.myContextMenu').remove();
        if(e.buttons==2){
            var left = e.clientX;
            var memship = $(this).attr('targetid');
            var top = e.clientY;
            var arr = ['群成员管理','解散群','转让群']
            var style = 'left:'+left+'px;top:'+top+'px';
            var id = 'groupLeftClick'
            fshowContexMenu(arr,style,id,memship);
        }else{//点击群组
            var targetID = $(this).attr('targetid');
            var targeType = 'GROUP';
            var groupName = $(this).find('.groupName').html();
            conversationGroup(targetID,targeType,groupName)
            $('.orgNavClick').addClass('chatHide');
            $('.mesContainerGroup').removeClass('chatHide');
        }
        $('.groupChatListUl li').removeClass('active');
        $(this).addClass('active');
        return false;
    })
    //群组右键菜单
    $('body').delegate('#groupLeftClick li','click',function(){
        $('.myContextMenu').remove();
        var _this = $(this);
        //查询群成员
        var memShipArr = [];
        var groupid = _this.parents('.myContextMenu').attr('memship');
        sendAjax('group!listGroupMemebers',{groupid:groupid},function(data){
            if(data) {
                var datas = JSON.parse(data);
                if(datas&&datas.code==1){
                    memShipArr = datas.text;
                    var index = _this.closest('ul').find('li').index(_this);
                    var data = localStorage.getItem('getBranchTree');
                    var datas = JSON.parse(data)
                    switch (index)
                    {
                        case 0:
                            //群成员管理
                            //creatDialogTree四个参数 1结构数据 2类名(groupConvers/privateConvers) 3title 4已选联系人
                            creatDialogTree(datas,'groupConvers','群组管理',function(){
                                var sConverseACount = JSON.stringify(converseACount);
                                sendAjax('group!manageGroupMem',{groupid:groupid,groupids:sConverseACount},function(data){
                                    if(data){
                                        $('.manageCancle').click();
                                        var datas = JSON.parse(data);
                                        if(datas.code==200){
                                            new Window().alert({
                                                title   : '',
                                                content : '修改群组成功！',
                                                hasCloseBtn : false,
                                                hasImg : true,
                                                textForSureBtn : false,
                                                textForcancleBtn : false,
                                                autoHide:true
                                            });
                                            getGroupList(accountID);
                                        }else{
                                            alert('失败',datas.text);
                                        }
                                    }
                                })
                            },memShipArr);
                            break;
                        case 1:
                            //解散群
                            new Window().alert({
                                title   : '解散群',
                                content : '确定要解散群么？',
                                hasCloseBtn : true,
                                hasImg : true,
                                textForSureBtn : '确定',              //确定按钮
                                textForcancleBtn : '取消',            //取消按钮
                                handlerForCancle : null,
                                handlerForSure : function(){
                                    //解散群组接口
                                    //sendAjax(url,data,callback)
                                }
                            });
                            break;
                        case 2:
                            //转让群
                            transfer();
                            break;
                    }
                }
            }
        })

    })


    //获取常用联系人左侧
    var sAccount = localStorage.getItem('account');
    var sdata = localStorage.getItem('datas');
    var account = JSON.parse(sAccount).account;
    var accountID = JSON.parse(sdata).text.id;

    //获取常用联系人
    getMemberFriends(account);
    //获取左侧组织树状图
    getBranchTreeAndMember();
    //获取会话列表(只能在与服务器连接成功之后调用)
    //getConverList();
    //获取群组列表
    getGroupList(accountID)



    /*
     * 点击加号 “+”
     * */
    $('.operMenuList').unbind('click');
    $('.operMenuList').click(function(e){
        var getBranchTree = localStorage.getItem('getBranchTree');
        if(getBranchTree){
            var data = JSON.parse(getBranchTree);
        }

        var index = $(e.target).closest('ul').find('li').index($(e.target));
        switch (index)
        {
            case 0:
                //添加好友
                //creatDialogTree四个参数 1结构数据 2类名(groupConvers/privateConvers) 3title 4已选联系人
                creatDialogTree(data,'privateConvers','添加好友',function(){
                    sendAjax('friend!addFriend',{account:account,friend:converseACount[0]},function(data){
                        var datas = JSON.parse(data);
                        console.log(data);
                        if(datas.code==1){
                            //刷新常用联系人
                            getMemberFriends(account);
                            $('.manageCancle').click();
                            new Window().alert({
                                title   : '添加好友',
                                content : '好友添加成功！',
                                hasCloseBtn : false,
                                hasImg : true,
                                textForSureBtn : false,
                                textForcancleBtn : false,
                                autoHide:true
                            });
                        }else{
                            alert('失败!'+datas.text);
                        }
                    })
                });
                break;
            case 1:
                //发起聊天
                creatDialogTree(data,'privateConvers','发起聊天',function(){
                    var targetID = converseACount[0];
                    $('.manageCancle').click();

                })
                break;
            case 2:
                //创建群组
                creatDialogTree(data,'groupConvers','创建群组',function(){
                    var sConverseACount = JSON.stringify(converseACount);
                    sendAjax('group!createGroup',{userid:accountID,groupids:sConverseACount,groupname:''},function(data){
                        if(data){
                            $('.manageCancle').click();
                            var datas = JSON.parse(data);
                            if(datas.code==200){
                                new Window().alert({
                                    title   : '',
                                    content : '创建群组成功！',
                                    hasCloseBtn : false,
                                    hasImg : true,
                                    textForSureBtn : false,
                                    textForcancleBtn : false,
                                    autoHide:true
                                });
                                getGroupList(accountID);
                            }else{
                                alert('失败',datas.text);
                            }

                        }
                    })
                })
                break;
        }
    })
    /*点击"+"*/
    $('.footerPlus').click(function(){
        $(this).find('.operMenuList').slideToggle();
    })
})


function getGroupList(accountID){
    var dom = $('.groupChatList .groupChatListUl');
    var sHTML = '';
    sendAjax('group!groupList',{userid:accountID},function(data){
        //window.localStorage.normalInfo = data;
        if(data){
            var datas = JSON.parse(data);
            var groupArr = datas.text;
            for(var i = 0;i<groupArr.length;i++){
                var curGroup = groupArr[i];
                sHTML+='<li targetid="'+curGroup.id+'">'+
                            '<div>'+
                                '<img class="groupImg" src="page/web/css/img/group_chart.png" alt="">'+
                                '<span class="groupName">'+curGroup.name+
                                    '<em>(15/20)'+
                                    '</em>'+
                                '</span>'+
                            '</div>'+
                        '</li>'
            }
            dom.html(sHTML);

        }
    })

}

//更新面包屑导航
function BreadcrumbGuid(target){
    console.log(target);
    var sHTML = '';
    sHTML += findParentCatalog(target,sHTML);
    return sHTML;
}

function findParentCatalog(target,sHTML){
    var category = target.find('.groupName').html();
    var sClass = target.attr('class');
    var sID = target.attr('id');

    sHTML = '<li class="'+sClass+'" id="'+sID+'"><a href=""> '+category+' </a> &gt;</li>';
    if(target.parent().prev().length!=0&&target.parent().prev()[0].tagName=='LI'){
        sHTML = findParentCatalog(target.parent().prev(),sHTML)+sHTML
    }
    return sHTML;
}

function removeConvers(type,id){

    RongIMClient.getInstance().removeConversation(RongIMLib.ConversationType[type],id,{
        onSuccess:function(bool){
            // 删除会话成功。
            console.log('删除会话列表成功');
            getConverList();
        },
        onError:function(error){
            // error => 删除会话的错误码
        }
    });
}


//点击的是部门
function changeClick1Content(data){
    var sHTML = '<div class="orgNavTitle">标题</div><ul>';
        console.log('+++++++++++++++++++',data);
    for(var i = 0;i<data.length;i++){
        if(data[i].logo){
            var imgHTML = '<img src="'+data[i].logo+'" alt="">';
        }else{
            var imgHTML = '<img src="page/web/css/img/PersonImg.png" alt="">';

        }
        sHTML += '<li id="'+data[i].id+'" account="'+data[i].account+'">'+
        '<div class="showImgInfo">'+
        imgHTML+
        '</div>'+
        '<div class="showPersonalInfo">'+
        '<span>'+data[i].fullname+'</span>'+
        '<ul class="personalOperaIcon">'+
        '<li class="sendMsg"></li>'+
        '<li class="checkPosition"></li>'+
        '<li class="addConver"></li>'+
        '</ul>'+
        '</div>'+
        '</li>';
    }
    sHTML+='</ul>'
    return sHTML;
}


//点击的是成员
function changeClick2Content(data){
    var sHTML = '<div class="personalDetailContent">'+
                '<div class="selfImgInfo">'+
                    '<img src="'+data.logo+'" alt=""/><div>'+
                '<p>'+data.name+'</p>'+
                '<ul class="selfImgOpera">'+
                    '<li class="sendMsg"></li><li class="checkPosition"></li><li class="addConver"></li>'+
                '</ul></div></div><div class="showPersonalInfo" targetID="'+data.id+'" targetTpe="PRIVATE">'+
                '<ul>'+
                    '<li><div>手机:</div><div>'+data.telephone+'</div></li>'+
                    '<li><div>邮箱:</div><div>'+data.email+'</div></li>'+
                    '<li><div>部门:</div><div>'+data.telephone+'</div></li>'+
                    '<li><div>职位:</div><div>'+data.workno+'</div></li>'+
                    '<li><div>组织:</div><div>'+data.groupuse+'</div></li>'+
                    '<li><div>地址:</div><div>'+data.address+'</div></li>'+
                '</ul></div></div></div></div>';
    return sHTML;
}


//从组织结构中找到相应的成员、部门数据
function searchFromList(flag,id){
    var curList;
    var normalInfo = localStorage.getItem('normalInfo');
    if(normalInfo){
        var data = JSON.parse(normalInfo);
    }
    console.log(flag,id,data);
    for(var i = 0;i<data.length;i++){
        if(data[i].id==id&&data[i].flag==flag){
            curList = data[i];
        }
    }
    return curList;
}
//获取组织结构图
function getBranchTreeAndMember(){
    sendAjax('branch!getBranchTreeAndMember','',function(data){
        window.localStorage.normalInfo = data;
        var datas = JSON.parse(data);
        if(datas && data.length!=0){
            var myData = changeFormat(data);
            if(myData){
                window.localStorage.getBranchTree = JSON.stringify(myData);
            }
            var $ParendtDom = $('.organizationList');
            var i = 0;
            var sHTML = '';
            var HTML = createOrganizList(myData,sHTML,i);
            $ParendtDom.html(HTML);
        }
    })
}
//获取常用联系人
function getMemberFriends(account){
    sendAjax('friend!getMemberFriends',{account:account},function(data){
        //console.log(data);
        window.localStorage.MemberFriends = data;
        var myData = JSON.parse(data);
        //var myData = changeFormat(data);
        var $ParendtDom = $('.usualChatList').find('ul.groupChatListUl');
        var sHTML = '';
        //var memberInfos = [];
        //var member = {};
        for(var i = 0;i<myData.length;i++){
            var account = myData[i].account;
            var fullname = myData[i].fullname;
            var workno = myData[i].workno?' ('+myData[i].workno+')':''
            var logo = myData[i].logo?myData[i].logo:'page/web/css/img/touxiang.png';
            var curData = myData[i];
            sHTML += ' <li account="'+account+'" targetid="'+myData[i].id+'">'+
            '<div>'+
            '<img class="groupImg" src="'+logo+'" alt=""/>'+
            '<span class="groupName">'+fullname+workno+'</span>'+
            '</div>'+
            '</li>';
        }
        $ParendtDom.html(sHTML);
    })
}

function cancleRelation(account,friend){
    sendAjax('friend!delFriend',{friend:friend,account:account},function(data){
        var datas = JSON.parse(data);
        console.log(data);
        if(datas.code==1){
            //刷新常用联系人
            getMemberFriends(account);
            new Window().alert({
                title   : '解除成功',
                content : '好友解除成功！',
                hasCloseBtn : false,
                hasImg : true,
                textForSureBtn : false,
                textForcancleBtn : false,
                autoHide:true
            });
        }
    })
}

function loop(data,small,temp){
    var tempdata = [];
    for(var p = 0;p<data.length;p++){
        tempdata[p] = data[p];
    }
    for(var i = 0;i<data.length;i++){
        for(var j = 0;j<small.length;j++){
            if(data[i].pid==small[j].id&&small[j].flag!=1){
                small[j].hasChild.push(data[i]);
                remove(tempdata,0);

            }
        }


    }
    //console.log(small);
    for(var k = 0;k<small.length;k++){
        if(tempdata.length !=0&&small[k].flag!=1){
            loop(tempdata,small[k].hasChild);
        }
    }
    return small;
}

//删除数组元素
function remove(arr,dx)
{
    if(isNaN(dx)||dx>arr.length){return false;}
    for(var i=0,n=0;i<arr.length;i++)
    {
        if(arr[i]!=arr[dx])
        {
            arr[n++]=arr[i]
        }
    }
    arr.length-=1
}

function showMemberInfo(data,pos){
    //console.log('=================',data);
    var sHTML = '<div class="memberHover" style="left:'+pos.left+'px;top:'+pos.top+'px">'+
                    '<div class="contextTri"></div>'+
                    '<ul class="memberInfoHover">'+
                        '<li>'+
                            '<div class="showImgInfo">'+
                                '<img src="page/web/css/img/PersonImg.png" alt="">'+
                            '</div>'+
                            '<div class="showPersonalInfo" targetID="'+data.id+'"targetType="PRIVATE">'+
                                '<span>张三（产品总监）</span>'+
                                '<ul class="personalOperaIcon">'+
                                    '<li class="sendMsg"></li>'+
                                    '<li class="checkPosition"></li>'+
                                    '<li class="addConver"></li>'+
                                '</ul>'+
                            '</div>'+
                        '</li>'+
                        '<li><span>手机：</span><span>'+data.telephone+'</span></li>'+
                        '<li><span>邮箱：</span><span>'+data.email+'</span></li>'+
                        '<li><span>部门：</span><span>'+data.email+'</span></li>'+
                        '<li><span>职位：</span><span>'+data.workno+'</span></li>'+
                    '</ul>'+
                '</div>';
    $('body').append($(sHTML));
}


function changeFormat(data){

    var data = JSON.parse(data);
    for(var i = 0;i<data.length;i++){
        for(var j = 0;j<data.length;j++){
            if(data[j]&&data[j+1]&&data[j].pid>data[j+1].pid) {
                var num = data[j+1];
                data[j+1] = data[j];
                data[j] = num;
            }
            data[j].hasChild = [];

        }
    }
    var small = [];
    small.push(data[0]);
    remove(data,0)
    for(var i = 0;i<data.length;i++){
        if(small[0].pid==data[i].pid){
            small.push(data[i]);
            remove(data,i)
        }
    }


    return loop(data,small);
}


function createOrganizList(data,sHTML,level){
    sHTML += '<ul>';
    var k = data.length;
    for(var i = 0;i<data.length;i++){
        var num = level
        var oData = data[i];
        var hasChild = oData.hasChild.length==0?false:true;
        var state = oData.flag==1?'member':'department';
        if(oData.flag==1||oData.hasChild.length==0){
            var collspan = ''
        }else{
            var collspan = '<span class="groupCollspanO chatLeftIcon groupCollspan"></span>'

        }
        sHTML += '<li class="'+state+'" id="'+oData.id+'">'+
                    '<div level="">'+
                    '<span style="height: 20px;width: '+level*32+'px;display:inline-block;float: left;"></span>'+
                    '<img class="groupImg" src="page/web/css/img/group_chart.png" alt="">'+
                    '<span class="groupName">'+oData.name+'</span>'+collspan+''+
                    //'<span class="groupCollspanO chatLeftIcon groupCollspan"></span>'+collspan+''+
                    '</div>'+
                '</li>'
        if(hasChild){
            num ++;
            sHTML = createOrganizList(oData.hasChild,sHTML,num);
        }
    }
    sHTML += '</ul>';
    return sHTML;
}

function creatDialogTree(data,className,title,callback,selected){
    //console.log('00000');
    //console.log(data);
    $('.WindowMask').find('.conversWindow').attr('class','conversWindow '+className);
    $('.WindowMask').find('.dialogHeader').html(title);
    $('.WindowMask').show();
    var sHTML = '';
    var level = 0
    var HTML = DialogTreeLoop(data,sHTML,level);
    var dom = $('.selectedList ul');
    if(selected){
        console.log('selected',selected);
        //找到自己的id 不用放到左侧管理
        var data = localStorage.getItem('datas');
        var datas = JSON.parse(data);
        var userID = datas.text.id;
        var sHTML = '';

        converseACount = [];
        for(var i = 0;i<selected.length;i++){
            if(selected[i]==userID){
                continue;
            }else{
                converseACount.push(selected[i]);
                var targetList = findMemberInList(selected[i]);
                sHTML += '<li memberID="'+selected[i]+'"><span class="memberName">'+targetList.name+'</span><span class="chatLeftIcon deleteMemberIcon"></span></li>'
            }
        }
        dom.html(sHTML);
    }else{
        dom.html('');
    }
    //console.log(HTML);
    $('.contactsList').html(HTML);
    $('.manageSure').unbind('click');
    $('.manageSure').click(function(){
        callback&&callback();
    });
}


function DialogTreeLoop(data,sHTML,level){
    sHTML += '<ul>';
    var k = data.length;
    for(var i = 0;i<data.length;i++){
        var num = level
        var oData = data[i];
        var hasChild = oData.hasChild.length==0?false:true;
        if(oData.flag==1){//成员
            var collspan =  '<span class="dialogCollspan chatLeftIcon"></span>'+
                            '<span class="chatLeftIcon dialogCheckBox"></span>';
            var department = 'member';
        }else{//部门
            var collspan =  '<span class="dialogCollspan chatLeftIcon dialogCollspanO"></span>'+
                            '<span class="chatLeftIcon dialogCheckBox"></span>';
            var department = 'department';
        }
        if(oData.hasChild.length==0){
            var collspan =  '<span class="dialogCollspan chatLeftIcon"></span>'+
                            '<span class="chatLeftIcon dialogCheckBox"></span>';
        }
        //console.log('*******************************');
        sHTML +=  '<li account = '+data[i].account+' id="'+data[i].id+'">'+
                        '<div level="1" class="'+department+'">'+
                            '<span style="height: 20px;width: '+level*22+'px;display:inline-block;float: left;"></span>'+
                            ''+collspan+'<span class="dialogGroupName">'+oData.name+'</span>'+
                        '</div>'+
                    '</li>';
        if(hasChild){
            num ++;
            sHTML = DialogTreeLoop(oData.hasChild,sHTML,num);
        }
    }
    sHTML += '</ul>';
    return sHTML;
}





function transfer(data){
    $('.WindowMask2').show();
}