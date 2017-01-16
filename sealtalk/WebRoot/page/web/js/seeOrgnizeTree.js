
$(function(){
    $('#organizeListOuter').undelegate('.node','click');
    $('#organizeListOuter').delegate('.node','click',function(){
        var className = $(this).attr('class');
        var classArr = className.split(' ');
        deleteElement(classArr,'node');
        console.log(classArr);
        console.log('********');
        var className = classArr[0];
        var idName = classArr[1];
        var targetNode = $('.organizationList').find('ul li.'+className+'[id='+idName+']');
        targetNode.click();

    })
})


function seeOrgnizeTree(){
    var data = localStorage.getItem('getBranchTree');
    var getBranchTree = JSON.parse(data);
    console.log(getBranchTree);
    var sHTML = '';
    var SHTML = loopTree(getBranchTree,sHTML,1);
    $('#organizeListOuter').html(SHTML);
    $('.orgNavClick').addClass('chatHide');
    $('.orgNavClick3').removeClass('chatHide');
    $('.BreadcrumbsOuter').removeClass('chatHide');
    //console.log(SHTML);
    $("#orgTree").jOrgChart({
        chartElement : '#organizeListOuter'
        //,
        //dragAndDrop  : true
    });
}

function loopTree(data,sHTML,level){
    if(level == 1){
        sHTML += '<ul id="orgTree" style="display:none">';
    }else{
        sHTML += '<ul>';

    }
    for(var i = 0;i<data.length;i++){
        var datas = data[i];
        var className = datas.flag==0?'department':'member';

        sHTML +='<li class="'+className+' '+datas.id+'" targetid="'+datas.id+'">'+datas.name;

        if(datas.hasChild.length!=0){//有子级
            sHTML =loopTree(datas.hasChild,sHTML,0)
        }else{
            sHTML+='</li>';
        }
    }
    sHTML+='</ul>';

    return sHTML;
}