function seeOrgnizeTree(){
    $('.orgNavClick').addClass('chatHide');
    $('.orgNavClick3 ').removeClass('chatHide');
    var data =  [
        {
            kDepartName  : "董事长",
            kChildDepart :

                [   {
                    kDepartName  : "总经理000",
                    kChildDepart : [
                        {
                            kDepartName  : "部门_02-01",
                            kChildDepart : [
                                {
                                    kDepartName  : "部门_02-01",
                                    kChildDepart : []
                                },

                                {
                                    kDepartName: "部门_02-02",
                                    kChildDepart : []
                                },
                                {
                                    kDepartName  : "部门_02-01",
                                    kChildDepart : []
                                }
                            ]
                        },

                        {
                            kDepartName: "部门_02-02",
                            kChildDepart : []
                        },
                        {
                            kDepartName  : "部门_02-01",
                            kChildDepart : []
                        },

                        {
                            kDepartName: "部门_02-02",
                            kChildDepart : []
                        },
                        {
                            kDepartName  : "部门_02-01",
                            kChildDepart : []
                        },

                        {
                            kDepartName  : "部门_02-01",
                            kChildDepart : []
                        }
                    ]
                },
                    {
                        kDepartName  : "总经理000",
                        kChildDepart : []
                    },
                    {
                        kDepartName  : "总经理000",
                        kChildDepart : []
                    },
                    {
                        kDepartName  : "总经理000",
                        kChildDepart : []
                    }

                ]
        }
    ];
    var outerWidth = $('#organizeList').width();

    showOrganizeList(data,outerWidth);

}

function showOrganizeList(data){
    var sHTML = '';
    var i = '';


    var HTML = loop(data,sHTML,i);

    $('.organizeListOuter').append(HTML);


    calaPosition();



}


function calaPosition(){


    var nodes = $('.organizeListOuter>div');
    //console.log(nodes);

    var oNode = {};
    oNode[1] = [];
    oNode[2] = [];
    oNode[3] = [];
    for(var i = 0;i<nodes.length;i++){
        var level = $(nodes[i]).attr('level')
        //var level = nodes[i].attr('level');
        var aLevel = level.split('-');
        switch(aLevel.length)
        {
            case 1:
                //console.log(1)
                oNode[1].push(aLevel);
                break;
            case 2:
                //console.log(2)
                oNode[2].push(aLevel);
                break;
            default:
                //console.log(3);
                $(nodes[i])
                oNode[3].push(aLevel);

        }
    }
    //console.log('========')
    //console.log(oNode);
    var levelHeight = [];
    for(var key in oNode){
        var thisNode = oNode[key]
        if(thisNode.length<=3){
            //console.log()
            for(var k = 0;k<thisNode.length;k++){
                var nodeI = thisNode[k];
                nodeI = nodeI.join('-');
                $('[level='+nodeI+']').addClass('outerHorizontal')
            }
            levelHeight.push(56);
        }else{
            for(var k = 0;k<thisNode.length;k++){
                var nodeI = thisNode[k];
                if(nodeI.length==1){
                    nodeI = nodeI[0];
                }else{
                    nodeI = nodeI.join('-');
                }
                $('[level='+nodeI+']').addClass('outerVertical')
            }
            //oNode[key]
            //console.log(oNode,key)
            levelHeight.push(174);
        }
    }


    //console.log(oNode,levelHeight);
    var NoChild = $('.NoChild').length;
    var vNoChild = $('.NoChild.outerVertical').length;
    var hNoChild = $('.NoChild.outerHorizontal').length;
    //console.log(NoChild,vNoChild,hNoChild);
    //叶子节点们的总宽度 vNoChild*56 +hNoChild*174 +(NoChild-1)*60
    //树高为levelHeight[0]+levelHeight[1]+levelHeight[2]+(levelHeight.length-1)*50;
    var leafList = $('.NoChild');
    //console.log(leafList);
    var left = 0;

    for(var i = 0;i<leafList.length;i++){
        var aLevel = $(leafList[i]).attr('level').split('-');
        //console.log(aLevel.pop(),aLevel);
        //var pNode = aLevel.pop()
        //if(aLevel.length==0){//当前元素是根节点
        //    var nodeParent = undefined
        //}else if(aLevel.length == 1){//父节点是根节点
        //    var nodeParent = aLevel[0];
        //}else{
        //    var nodeParent = aLevel.join('-');
        //}
        ////var nodeParent = pNode.length<=1?pNode:;//pNode [] [0]
        //if(nodeParent){
        //    //alert(1);
        //
        //    console.log(nodeParent);
        //}else{
            //    console.log('===========')
            //}



            var top = 0
        for(var j = 0;j<aLevel.length-1;j++){
            top += levelHeight[j]+100;
        }
        //console.log(aLevel,levelHeight);
        $(leafList[i]).css({top:top,left:i*50+left});
        if($(leafList[i]).hasClass('outerHorizontal')){
            left += 174;
        }else{
            left += 56;
        }
    };

    parentNode(levelHeight);


}

function parentNode(levelHeight){
    var leafList = $('.NoChild');
    for(var t = 0;t<leafList.length;t++){
        var aLevel = $(leafList[t]).attr('level').split('-');
        //console.log(aLevel.pop(),aLevel);
        aLevel.pop()
        positionAAA(aLevel,levelHeight);
    }
}

function positionAAA(aLevel,levelHeight){
    if(aLevel.length==0){//当前元素是根节点
        var nodeParent = undefined
    }else if(aLevel.length == 1){//父节点是根节点
        var nodeParent = aLevel[0];
    }else{
        var nodeParent = aLevel.join('-');
    }
    //var nodeParent = pNode.length<=1?pNode:;//pNode [] [0]
    if(nodeParent){
        //alert(1);

        //console.log(nodeParent);
        var childcount = $('[level='+nodeParent+']').attr('childcount');
        var level = nodeParent.length==1?1:nodeParent.split('-').length;
        //console.log(level);
        var top = 0
        for(var j = 0;j<level-1;j++){
            top += levelHeight[j]+100;
        }

        var left = parseInt($('[level='+nodeParent+'-0'+']').css('left'));
        if($('[level='+nodeParent+'-0'+']').hasClass('outerHorizontal')){
            left += (childcount*174 + 50*(childcount-1)-$('[level='+nodeParent+']').width())/2;
        }else{
            left += (childcount*56 + 50*(childcount-1)-$('[level='+nodeParent+']').width())/2;

        }
        $('[level='+nodeParent+']').css({top:top,left:left})
        if(level>1){
            aLevel.pop();
            positionAAA(aLevel,levelHeight)
        }

    }else{
        console.log('===========')
    }
}


function loop(aData,sHTML,level){
    //console.log(aData);
    //level++;
    var k = aData.length;
    var outerCName = '';
    var innerCName = Direct(k).innerCName;
    //sHTML += '<div class="level'+level+'">'+sHTML+'</div>';
    for(var i = 0;i<aData.length;i++){
        var oData = aData[i];
        var hasChild = oData.kChildDepart.length==0?false:true;
        if(hasChild){
            var childCount = oData.kChildDepart.length;
        }
        var levelNum = level+i
        if(i == 0&&hasChild){

            var outer = leftHasChild(outerCName,levelNum,childCount)

        }else if(i == aData.length-1&&hasChild){

            var outer = rightHasChild(outerCName,levelNum,childCount)

        }else if(i == 0&&!hasChild){

            var outer = leftNoChild(outerCName,levelNum)

        }else if(i == aData.length-1&&!hasChild){

            var outer = rightNoChild(outerCName,levelNum);

        }else{
            if(oData.kChildDepart.length!=0){
                var outer = middleHasChild(outerCName,levelNum,childCount)
            }else{
                var outer = middleNoChild(outerCName,levelNum)
            }
        }

        sHTML += outer+'<p class="'+innerCName+'">'+oData.kDepartName+'</p></div>'

        if(hasChild){
            sHTML = loop(oData.kChildDepart,sHTML,levelNum+"-");
        }
    }

    return sHTML;

}


function Direct(num){
    return num>=3?{innerCName:'innerVertical',outerCName:'outerVertical'}:{innerCName:'innerHorizontal',outerCName:'outerHorizontal'};
}


function topOuter(outerCName,level){
    var sHTML = '<div class="topOuter '+outerCName+' " level="'+level+'">';
    return sHTML
}
function leftNoChild(outerCName,level){
    var sHTML = '<div class="NoChild leftNoChild '+outerCName+'"level="'+level+'">';
    return sHTML
}
function rightNoChild(outerCName,level){
    var sHTML = '<div class="NoChild rightNoChild ' +outerCName+'"level="'+level+'">';
    return sHTML
}
function rightHasChild(outerCName,level,childCount){
    var sHTML = '<div class="rightHasChild '+outerCName+'"level="'+level+'" childCount="'+childCount+'">';
    return sHTML
}
function leftHasChild(outerCName,level,childCount){
    var sHTML = '<div class="leftHasChild '+outerCName+'"level="'+level+'"childCount="'+childCount+'">';
    return sHTML
}
function middleNoChild(outerCName,level){
    var sHTML = '<div class="NoChild middleNoChild '+outerCName+'"level="'+level+'">';
    return sHTML
}
function middleHasChild(outerCName,level,childCount){
    var sHTML = '<div class="middleHasChild '+outerCName+'"level="'+level+'"childCount="'+childCount+'">';
    return sHTML
}
