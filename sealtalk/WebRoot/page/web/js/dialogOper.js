/**
 * Created by zhu_jq on 2017/1/9.
 */
$(function(){




    window.converseACount = [];


    $('.selectedList').delegate('.deleteMemberIcon','click',function(){
        var name = $(this).prev().html();
        var curObj = delObjFromArr(converseACount,'name',name);
        changeSelected(converseACount);
        var account = curObj.account;
        $('li[account='+account+']').find('.dialogCheckBox').click();
    })


    //弹窗中的树形结构的收起展开
    $('.conversWindow').delegate('.dialogCollspan','click',function(){
        $(this).toggleClass('dialogCollspanC','dialogCollspanO');
        $(this).closest('li').next('ul').slideToggle();
    });

    //弹窗中树形结构的选中
    $('.conversWindow').delegate('.dialogCheckBox','click',function(){
        converseACount = [];
        var bPrivate = $(this).parents('.conversWindow').hasClass('privateConvers');
        var id =  $(this).closest('li').attr('id');
        if(bPrivate){//创建个人的聊天页面
            var account =  $(this).closest('li').attr('account');
            $('.dialogCheckBox').removeClass('CheckBoxChecked');
            $(this).addClass('CheckBoxChecked');
            converseACount.push(account);
        }else{//创建群组的聊天

            //首先自己的选中状态
            $(this).toggleClass('CheckBoxChecked','dialogCheckBox');
            //然后子级的选中状态
            var member = $(this).closest('li').next('ul').find('div.member');
            if(member){
                for(var i = 0;i<member.length;i++){
                    $(member[i]).parent().attr('account');
                }
            }
            if($(this).hasClass('CheckBoxChecked')){//选中的push
                $(this).closest('li').next('ul').find('.dialogCheckBox').addClass('CheckBoxChecked');
            }else{//未选中，移出数组
                $(this).closest('li').next('ul').find('.dialogCheckBox').removeClass('CheckBoxChecked');
            }
            //父级的选中状态
            var sonBox = $(this).closest('ul').find('.dialogCheckBox');
            var allBox = 0;
            for(var i = 0;i<sonBox.length;i++){
                if($(sonBox[i]).hasClass('CheckBoxChecked')){
                    allBox++;
                }
            }
            if(allBox==0){
                $(this).closest('ul').prev('li').find('.chatLeftIcon').removeClass('CheckBoxChecked');
            }
            var dialogCheckBox = $('.contactBox').find('.dialogCheckBox');

            for(var i = 0;i<dialogCheckBox.length;i++){
                var diacjeck = $(dialogCheckBox[i])
                if(diacjeck.hasClass('CheckBoxChecked')&&diacjeck.closest('div').hasClass('member')){
                    var account = diacjeck.closest('li').attr('id');
                    var name = diacjeck.next().html();
                    converseACount.push(account);
                }
            }
        }
        console.log(converseACount);
        changeSelected(converseACount);
    })

})

function delObjFromArr(arr,name,value){
    for(var i = 0;i<arr.length;i++){
        var curObj = arr[i];
        for(var key in curObj){
            if(curObj[key]==value&&key==name){
                deleteElement(arr,curObj);
            }
        }
    }
    return curObj;
}


function changeSelected(converseACount){
    var dom = $('.selectedList ul');
    var sHTML = '';
    for(var i = 0;i<converseACount.length;i++){
        sHTML+='<li><span class="memberName">'+converseACount[i].name+'</span><span class="chatLeftIcon deleteMemberIcon"></span></li>'
    }
    dom.html($(sHTML));
}


function deleteElement(converseACount,account){
    console.log(converseACount);
    for(var i = 0;i<converseACount.length;i++){
        if(converseACount[i]==account){
            converseACount.splice(i,1);
            break;
        }
    }
}