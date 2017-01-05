/**
 * Created by zhu_jq on 2017/1/4.
 */
window.onload = function() {
    /*顶部&&左侧导航切换*/
    $('.chatHeaderMenu,.chatMenu').click(function (e) {
        $(e.target).addClass('active')
        $(e.target).siblings('li').removeClass('active');
        var nShowClass = $(e.target).attr('bindPanel');
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
function showPanel(panelClass){
    var eShowNode = $("."+panelClass);
    if(eShowNode){
        eShowNode.removeClass('chatHide');
        eShowNode.siblings(".chatContent").addClass('chatHide');
    }
}