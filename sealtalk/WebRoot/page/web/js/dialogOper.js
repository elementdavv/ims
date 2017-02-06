/**
 * Created by zhu_jq on 2017/1/9.
 */
$(function(){

    $('.contactBox').delegate('.contactSearchResult','mouseleave',function(){
        var _this = $(this);
        setTimeout(function(){
            _this.remove();
        },1000)
    })
    $('.contactBox').delegate('.contactSearchResult .dialogCheckBox','click',function(){
        if($(this).parents('li').attr('editable')=='true'){
            var targetId = $(this).closest('li').attr('id');
            var targetLi = $('#contactBox .contactsList').find('li.member[id="'+targetId+'"]');

            if($(this).hasClass('CheckBoxChecked')){//现状态是选中，取消选中（树中）并从已选联系人中移出
                //converseACount.push(targetId);
                targetLi.find('.dialogCheckBox').removeClass('CheckBoxChecked');
                deleteElement(converseACount,targetId)
                changeSelected(converseACount);
            }else{//现状态是未选中，选中（树中）并添加到已选联系人
                targetLi.find('.dialogCheckBox').addClass('CheckBoxChecked');
                converseACount.push(targetId);
                changeSelected(converseACount);
            }
        }
    })

    $('.contactsSearch').focus(function(){
        var sAccount = localStorage.getItem('account');
        var sdata = localStorage.getItem('datas');
        var account = JSON.parse(sAccount).account;
        var accountID = JSON.parse(sdata).id;
        var _this = $(this);
        _this.keypress(function(event) {
            if (event.which == 13) {
                $('.contactSearchResult').remove();
                var keyWord = _this.val();
                sendAjax('member!searchUser',{account:keyWord},function(data){
                    var datas = JSON.parse(data);
                    var parentDom = $('#contactBox');
                    if(datas.length==0){
                        //没有用户
                        var sHTML = '';
                        //for(var i = 0;i<datas.length;i++){
                        sHTML = '<div class="contactSearchResult">没有搜索结果</div>'
                        //}
                        parentDom.append($(sHTML));
                    }else if(datas.length!=0){
                        //生成搜索结果
                        var sHTML = '';
                        console.log('converseACount',converseACount);

                        for(var i = 0;i<datas.length;i++){
                            if(hasItem(converseACount,datas[i].id)){
                                var className = 'CheckBoxChecked'
                            }else{
                                var className = ''
                            }
                            if(accountID==datas[i].id){
                                var editable = 'false';
                            }else{
                                var editable = 'true';
                            }
                            sHTML += '<li account="'+datas[i].account+'" id="'+datas[i].id+'" class="member" editable="'+editable+'">' +
                            '<div level="1" class="department">' +
                            '<span style="height: 20px;width: 0px;display:inline-block;float: left;"></span>' +
                            '<span class="chatLeftIcon dialogCheckBox '+className+'"></span>' +
                            '<span class="dialogGroupName">'+datas[i].name+'</span>' +
                            '</div>' +
                            '</li>';
                        }
                        var pHTML = '<ul class="contactSearchResult">'+sHTML+'</ul>';
                        parentDom.append($(pHTML));

                    }else{
                        console.log(datas.text);
                    }

                })

            }
        })
    })


    window.converseACount = [];
    //删除群租种的成员
    $('.selectedList').delegate('.deleteMemberIcon','click',function(){
        if($(this).parents('li').attr('editable')=='true'){
            var name = $(this).prev().html();
            var memberID = $(this).parent().attr('memberID');
            deleteElement(converseACount,memberID);
            $('.contactsList li.member[id='+memberID+']').find('.dialogCheckBox').removeClass('CheckBoxChecked');
            changeSelected(converseACount);
        }
    })

    //弹窗中的树形结构的收起展开
    $('.conversWindow').delegate('.dialogCollspan','click',function(){
        $(this).closest('li').next('ul').slideToggle();
        $(this).toggleClass('dialogCollspanC','dialogCollspanO');
    });

    //弹窗中树形结构的选中
    $('.conversWindow').undelegate('.dialogCheckBox','click')
    $('.conversWindow').delegate('.dialogCheckBox','click',function(){
        if($(this).parents('li').attr('editable')=='true'){
            converseACount = [];
            if($('.selectedList').find('li').length!=0){
                var selected = $('.selectedList').find('li');
                for(var i = 0;i<selected.length;i++){
                    var account = $(selected[i]).attr('memberid')
                    if(!$(this).hasClass('CheckBoxChecked')){
                        converseACount.push(account);

                    }else{
                        deleteElement(converseACount,account)

                    }
                }
            }


            //bPrivate是私聊还是群聊
            var bPrivate = $(this).parents('.conversWindow').hasClass('privateConvers');
            var id =  $(this).closest('li').attr('id');
            if(bPrivate){//创建个人的聊天页面 单选模式
                var account =  $(this).closest('li').attr('account');
                $('.dialogCheckBox').removeClass('CheckBoxChecked');
                $(this).addClass('CheckBoxChecked');
                //var bhas = arrHasElement(converseACount,account);
                //if(bhas){
                converseACount.push(account);

                //}
            }else{//创建群组的聊天 多选模式
                //首先自己的选中状态
                //$(this).toggleClass('CheckBoxChecked','dialogCheckBox');
                if($(this).hasClass('CheckBoxChecked')){
                    $(this).removeClass('CheckBoxChecked');
                }else{
                    $(this).addClass('CheckBoxChecked');
                }


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
                    //if($(this).closest('li').attr('editable')=='true'){
                    var $dialogCheckBox = $(this).closest('li').next('ul').find('.dialogCheckBox');
                    for(var i = 0;i<$dialogCheckBox.length;i++){
                        if($($dialogCheckBox[i]).closest('li').attr('editable')=='true'){
                            $($dialogCheckBox[i]).removeClass('CheckBoxChecked');
                        }
                    }
                    //}
                }


                //父级的选中状态
                var sonBox = $(this).closest('ul').find('.dialogCheckBox');
                var allBox = 0;
                for(var i = 0;i<sonBox.length;i++){
                    if($(sonBox[i]).hasClass('CheckBoxChecked')){
                        allBox++;
                    }
                }
                if(allBox==0){//全没选中
                    $(this).closest('ul').prev('li').find('.chatLeftIcon').removeClass('CheckBoxChecked');
                }else if(allBox==sonBox.length){//全选中
                    $(this).closest('ul').prev('li').find('.chatLeftIcon').removeClass('CheckBoxChecked');
                }
                var dialogCheckBox = $('.contactBox').find('.dialogCheckBox');

                for(var i = 0;i<dialogCheckBox.length;i++){
                    var diacjeck = $(dialogCheckBox[i])
                    if(diacjeck.hasClass('CheckBoxChecked')&&diacjeck.closest('div').hasClass('member')){
                        var account = diacjeck.closest('li').attr('id');
                        var name = diacjeck.next().html();
                        var bhas = arrHasElement(converseACount,account);
                        if(!bhas){
                            converseACount.push(account);
                        }
                        //converseACount.push(account);
                    }
                }
                changeSelected(converseACount);

            }
        }
    })
})


//数组中是否包含某个元素
function arrHasElement(converseACount,account){
    var bHas = false;
    for(var i = 0;i<converseACount.length;i++){
        if(converseACount[i]==account){
            bHas = true;
            break;
        }
    }
    return bHas;
}

function hasItem(parentLevelarr,parentLevel){
    var bhas = 0;
    for(i = 0;i<parentLevelarr.length;i++){
        if(parentLevel==parentLevelarr[i]){
            bhas = 1;
        }
    }
    return bhas;
}

function creatDialogTree(data,className,title,callback,selected){
    //console.log('00000');
    //console.log(data);
    $('.WindowMask').find('.conversWindow').attr('class','conversWindow '+className);
    $('.WindowMask').find('.dialogHeader').html(title);
    $('.WindowMask').show();
    var sHTML = '';
    var level = 0;
    var dataAll = localStorage.getItem('datas');
    var datasAll = JSON.parse(dataAll);
    var userID = datasAll.id;

    var HTML = DialogTreeLoop(data,sHTML,level,userID);
    $('.contactsList').html(HTML);
    var dom = $('.selectedList ul');
    if(selected){
        console.log('selected',selected);
        //找到自己的id 不用放到左侧管理

        var sHTML = '';

        converseACount = [];
        for(var i = 0;i<selected.length;i++){
            converseACount.push(selected[i]);
            var targetList = findMemberInList(selected[i]);
            if(selected[i]==userID){
                var editable = false;
                $('.contactsList').find('li[account='+targetList.account+'][id='+selected[i]+']').find('.dialogCheckBox').addClass('CheckBoxChecked');
                $('.contactsList').find('li[account='+targetList.account+'][id='+selected[i]+']').attr('editable','false');

            }else {
                var editable = true;
                $('.contactsList').find('li[account='+targetList.account+'][id='+selected[i]+']').find('.dialogCheckBox').addClass('CheckBoxChecked');
            }
                sHTML += '<li memberID="'+selected[i]+'" editable="'+editable+'"><span class="memberName">'+targetList.name+'</span><span class="chatLeftIcon deleteMemberIcon"></span></li>'

        }
        dom.html(sHTML);
        selectedNum(selected)
    }else{
        dom.html('');
    }
    //console.log(HTML);

    $('.manageSure').unbind('click');
    $('.manageSure').click(function(){
        callback&&callback();
    });
}

//删除数组中的某个对象
function deleteElement(arr,name,value){
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

//修改select里面的成员 以及已选联系人数量
function changeSelected(converseACount){
    //var sAccount = localStorage.getItem('account');
    var sdata = localStorage.getItem('datas');
    //var account = JSON.parse(sAccount).account;
    var accountID = JSON.parse(sdata).id;
    var dom = $('.selectedList ul');
    var sHTML = '';
    for(var i = 0;i<converseACount.length;i++){
        if(accountID==converseACount[i]){
            var editable = 'false';
        }else{
            var editable = 'true';
        }
        var name = findMemberInList(converseACount[i]).name;
        sHTML+='<li memberID="'+converseACount[i]+'" editable="'+editable+'"><span class="memberName">'+name+'</span><span class="chatLeftIcon deleteMemberIcon"></span></li>'
    }
    dom.html($(sHTML));
    selectedNum(converseACount);
}

function selectedNum(converseACount){
    var selectNum = converseACount.length;
    var parentDom = $('.selectedContactOuter .outerTitle em');
    var sHTML = '('+selectNum+'/99)';
    parentDom.html(sHTML);
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