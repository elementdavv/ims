/**
 * Created by zhu_jq on 2017/1/4.
 */
$(function(){


    //$('#contactsList').perfectScrollbar();
    //$('body').perfectScrollbar();
    $('.dialogClose,.manageCancle').click(function(){
        $('.WindowMask,.WindowMask2').hide();
    });

    $(document).bind('contextmenu',function(){
        event.preventDefault();
        return false;
    });

    //document.oncontextmenu = function(){
    //
    //    return false;
    //};

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
                //$('.chatBox').html('aaaaa')
                break;
            case 'orgnized':
                //$('.chatBox').html('bbbb')
                break;
            case 'back':
                //$('.chatBox').html('cccc')
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
})

//memShip表示与此操作相关的人员account
function fshowContexMenu(arr,style,id,memShip){

    var listHTML = '';
    for(var i = 0;i<arr.length;i++){
        listHTML+='<li>'+arr[i]+'</li>'
    }
    var sHTML = '<div memShip="'+memShip+'" class="myContextMenu" id="'+id+'" style="'+style+'">'+
        '<div class="contextTri"></div>'+
        '<ul>'+listHTML+'</ul>'+
        '</div>';
    $('body').append($(sHTML));
    return false;
}
function showPanel(panelClass){
    var eShowNode = $("."+panelClass);
    if(eShowNode){
        eShowNode.removeClass('chatHide');
        eShowNode.siblings(".chatContent").addClass('chatHide');
        $('#chatBox').children().each(function(){
            if(!$(this).hasClass('chatHide')){
                $(this).addClass('chatHide');
            }
        });
    }
}


function sendAjax(url,data,callback){
    $.ajax({
        type: "POST",
        url: url,
        data:data,
        success: function(data){
            callback && callback(data);
        }
    })
}