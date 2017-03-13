/**
 * Created by zhu_jq on 2017/3/10.
 */
$(function(){
    window.onload=window.onresize=function(){
        document.documentElement.style.fontSize=20*document.documentElement.clientWidth/375+'px';
    };
    $('.checkMem .dialogCheckBox').click(function(){
        var _this = $(this);
        _this.toggleClass('CheckBoxChecked');
    })
    $('#checkAll .dialogCheckBox').click(function(){
        var _this = $(this);
        _this.toggleClass('CheckBoxChecked');_this.hasClass('');
        if(_this.hasClass('CheckBoxChecked')){
            $('.checkMem .dialogCheckBox').addClass('CheckBoxChecked')
        }else{
            $('.checkMem .dialogCheckBox').removeClass('CheckBoxChecked')
        }
    })
})