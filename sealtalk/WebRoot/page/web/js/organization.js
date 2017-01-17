/**
 * Created by zhu_jq on 2017/1/14.
 */
$(function(){
    $('.BreadcrumbsOuter').undelegate('ul li','click')
    $('.BreadcrumbsOuter').delegate('ul li','click',function(){
        var targetID = $(this).attr('id');
        var className = $(this).attr('class');
        console.log('.organizationList li.'+className+'[id='+targetID+']')
        $('.organizationList li.'+className+'[id='+targetID+']').click();
    })

    $('.orgNavClick2,.orgNavClick1').delegate('.sendMsg','click',function(){
        //跳转到消息下面的常用联系人
        $('.chatHeaderMenu li')[0].click();
        $('.chatContent ul li')[1].click();
        //会话区显示
    })

    //点击查看组织结构图
    $('.seeOrgnizeTree').click(function(){
        //var getBranchTree = localStorage.getItem('getBranchTree');
        //var datas = JSON.parse(getBranchTree);
        //var sCanvas = $('<canvas id="bgCanvas" height="800" width="800">');
        //$('.orgNavClick').addClass('chatHide');
        //$('.orgNavClick3').removeClass('chatHide');
        //$('.orgNavClick3').append(sCanvas);
        //console.log(datas);
        seeOrgnizeTree();
    })

    //点击组织通讯录
    $('.organizationList').undelegate('li','click')
    $('.organizationList').delegate('li','click',function(){
        var state = $(this).attr('class');
        var id = $(this).attr('id');
        //从list中找到点击的这条信息
        $('.orgNavClick').addClass('chatHide');
        var SHTML = BreadcrumbGuid($(this));
        $('.Breadcrumbs').html(SHTML);
        if(state=='member'){//点击的是成员
            var data = searchFromList(1,id);
            var sHTML = changeClick2Content(data);
            $('.orgNavClick2').html(sHTML);
            $('.orgNavClick2').removeClass('chatHide');
            $('.BreadcrumbsOuter').removeClass('chatHide')
            //更换面包屑导航
        }else{//点击的是部门
            //branch!getBranchMember查询部门结构
            sendAjax('branch!getBranchMember',{branchId:id},function(data){
                var datas = JSON.parse(data);
                console.log(datas);
                var sHTML = changeClick1Content(datas);
                $('.orgNavClick1').html(sHTML);
                $('.orgNavClick1').removeClass('chatHide');
                $('.BreadcrumbsOuter').removeClass('chatHide');
                //$('.BreadcrumbsOuter').removeClass('chatHide')
                //var SHTML = BreadcrumbGuid($(this));
                //更换面包屑导航
                //$('.Breadcrumbs').html(SHTML);

            })
        }
    })

    //搜索
    $('.defaultText').click(function(){
        $('.searchInput').focus();
    })
    $('.searchInput').focus(function(){
        $('.defaultText').hide();
        $(this).css({backgroundPosition:'-380px -365px'});
        $(this).keypress(function(event) {
            if (event.which == 13) {
                console.log(111);
                var inputVal = $(this).val();
                if(inputVal){
                    sendAjax('member!searchUser',{account:inputVal},function(data){
                        var datas = JSON.parse(data);
                        console.log(data);
                        if(datas.code==0){
                            //没有用户

                        }else if(datas.code==1){
                            //生成搜索结果
                        }else{
                            console.log(datas.text);
                        }

                    })
                }
            }
        })
    });
    $('.searchInput').blur(function(){
        $(this).val('');
        $('.defaultText').show();
        $(this).css({backgroundPosition:'-281px -365px'});
    });

})