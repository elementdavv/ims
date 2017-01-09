/**
 * Created by zhu_jq on 2017/1/4.
 */
window.onload = function() {

    $('.newsTabContent').bind('contextmenu',function(){
        event.preventDefault();
        return false;
    });



    //群组右键
    $('body').delegate('#groupLeftClick li','click',function(){
        $('.myContextMenu').remove();
        var index = $(this).closest('ul').find('li').index($(this));
        switch (index)
        {
            case 0:
                console.log(0);
                break;
            case 1:
                console.log(1);
                break;
            case 2:
                console.log(2);
                break;
            case 3:
                //解散群
                new Window().alert({
                    title   : '解散群',
                    content : '确定要解散群么？',
                    hasCloseBtn : true,
                    hasImg : true,
                    textForSureBtn : '确定',              //确定按钮
                    textForcancleBtn : '取消',            //取消按钮
                    handlerForCancle : null,
                    //handlerForClose : null,
                    handlerForSure : null
                });
                break;
            case 4:
                console.log(4);
                break;

        }
    })
    $('.newsTabContent').delegate('.groupChatListUl li','mousedown',function(e){

        if(e.buttons==2){
            var left = e.clientX;
            var top = e.clientY;
            var arr = ['发起群聊','查看群资料','群成员管理','解散群','转让群']
            var style = 'left:'+left+'px;top:'+top+'px';
            var id = 'groupLeftClick'
            fshowContexMenu(arr,style,id)
        }
        $('.groupChatListUl li').removeClass('active');
        $(this).addClass('active');
        return false;
    })




    $('.seeOrgnizeTree').click(function(){
        seeOrgnizeTree();
    })




    $('.organizationList').delegate('ul li .groupCollspan','click',function(e){


        //按钮样式
        var $groupCollspanO = $(this)
        var bOpen = $groupCollspanO.hasClass('groupCollspanO')
        if(bOpen){
            $groupCollspanO.removeClass('groupCollspanO');
            $groupCollspanO.addClass('groupCollspanC');
        }else{
            $groupCollspanO.removeClass('groupCollspanC');
            $groupCollspanO.addClass('groupCollspanO');
        }
        //内容显示隐藏
        $(this).closest('li').next('ul').slideToggle();
    })


    /*顶部&&左侧导航切换*/
    $('.chatHeaderMenu,.chatMenu').click(function (e) {
        $(e.target).addClass('active')
        $(e.target).siblings('li').removeClass('active');
        var nShowClass = $(e.target).attr('bindPanel');
        switch(nShowClass)
        {
            case 'news':
                $('.chatBox').html('aaaaa')
                break;
            case 'orgnized':
                $('.chatBox').html('bbbb')
                break;
            case 'back':
                $('.chatBox').html('cccc')
                break;
        }
        nShowClass&&showPanel(nShowClass);
    })

    /*展开关闭子级列表*/
    $('.listCtrl').click(function(){
        var $chatLeftIcon = $(this).find('.chatLeftIcon')
        var bOpen = $chatLeftIcon.hasClass('triOpen')
        if(bOpen){
            $chatLeftIcon.removeClass('triOpen');
            $chatLeftIcon.addClass('triClose');
        }else{
            $chatLeftIcon.removeClass('triClose');
            $chatLeftIcon.addClass('triOpen');
        }
        $(this).find('.chatLeftIcon').hasClass('.triOpen')
        $(this).next('ul').slideToggle();
    })

    /*点击"+"*/
    $('.footerPlus').click(function(){
        $(this).find('.operMenuList').slideToggle();
    })

}



function fshowContexMenu(arr,style,id){

    var listHTML = '';
    for(var i = 0;i<arr.length;i++){
        listHTML+='<li>'+arr[i]+'</li>'
    }
    var sHTML = '<div class="myContextMenu" id="'+id+'" style="'+style+'">'+
        '<div class="contextTri"></div>'+
        '<ul>'+listHTML+'</ul>'+
        '</div>';
    $('body').append($(sHTML));
}
function showPanel(panelClass){
    var eShowNode = $("."+panelClass);
    if(eShowNode){
        eShowNode.removeClass('chatHide');
        eShowNode.siblings(".chatContent").addClass('chatHide');
    }
}