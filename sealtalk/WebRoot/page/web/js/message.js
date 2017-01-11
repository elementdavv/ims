/**
 * Created by zhu_jq on 2017/1/10.
 */
$(function(){



    //获取好友列表
    var sAccount = localStorage.getItem('account');
    var account = JSON.parse(sAccount).account;
    sendAjax('friend!getMemberFriends',{account:account},function(data){
        console.log(data);
        var $ParendtDom = $('.usualChatList').find('ul.groupChatListUl');
        var sHTML = '';
        for(var i = 0;i<data.length;i++){
            sHTML += ' <li>'+
                        '<div>'+
                            '<img class="groupImg" src="css/img/group_chart.png" alt=""/>'+
                            '<span class="groupName">产品部<em>(15/20)</em>'+
                            '</span>'+
                        '</div>'+
                    '</li>';
        }
        $ParendtDom.append($(sHTML));
    })


    //左侧组织树状图

    //sendAjax('branch!getBranchTree','',function(data){
	sendAjax('branch!getBranchTreeAndMember','',function(data){
        console.log(data);
        var $ParendtDom = $('.organizationList').find('ul');


    })



    //店家查看组织结构图
    $('.seeOrgnizeTree').click(function(){
        seeOrgnizeTree();
    })


    /*
     * 点击加号 “+”
     * */

    $('.operMenuList').click(function(e){
        var index = $(e.target).closest('ul').find('li').index($(e.target));
        switch (index)
        {
            case 0:
                //添加好友
                //creatDialogTree四个参数 1结构数据 2类名(groupConvers/privateConvers) 3title 4已选联系人
                creatDialogTree('','privateConvers','添加好友')
                break;
            case 1:
                //发起聊天
                creatDialogTree('','privateConvers','发起聊天')
                break;
            case 2:
                //创建群组
                creatDialogTree('','groupConvers','创建群组')
                break;
        }
    })
    /*点击"+"*/
    $('.footerPlus').click(function(){
        $(this).find('.operMenuList').slideToggle();
    })


    //群组右键的弹窗操作
    $('.newsTabContent').delegate('.groupChatListUl li','mousedown',function(e){

        if(e.buttons==2){
            var left = e.clientX;
            var top = e.clientY;
            var arr = ['群成员管理','解散群','转让群']
            var style = 'left:'+left+'px;top:'+top+'px';
            var id = 'groupLeftClick'
            fshowContexMenu(arr,style,id)
        }
        $('.groupChatListUl li').removeClass('active');
        $(this).addClass('active');
        return false;
    })

    //群组右键
    $('body').delegate('#groupLeftClick li','click',function(){
        $('.myContextMenu').remove();
        var index = $(this).closest('ul').find('li').index($(this));
        switch (index)
        {
            case 0:
                //群成员管理
                //creatDialogTree四个参数 1结构数据 2类名(groupConvers/privateConvers) 3title 4已选联系人
                creatDialogTree('','groupConvers','群组管理')
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
                    handlerForSure : null
                });
                break;
            case 2:
                //转让群
                transfer();
                break;
        }
    })
})


function creatDialogTree(data,className,title,selected){
    $('.WindowMask').find('.conversWindow').attr('class','conversWindow '+className)
    $('.WindowMask').find('.conversWindow').attr('class','conversWindow '+className)
    $('.WindowMask').find('.dialogHeader').html(title);
    $('.WindowMask').show();
}

function transfer(data){
    $('.WindowMask2').show();
}