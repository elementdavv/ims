<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/main.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/window.css"/>
    <script src="<%=request.getContextPath() %>/page/web/js/jquery-2.1.1.min.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/main.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/window.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/seeOrgnizeTree.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/dialogOper.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/creatGroup.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/message.js"></script>
    <script src="http://cdn.ronghub.com/RongIMLib-2.2.4.min.js"></script>

</head>
<body>


    <%--<div class="menberHover">--%>
        <%--<div class="contextTri"></div>--%>
        <%--&lt;%&ndash;<div class="memberInfoHover">&ndash;%&gt;--%>
            <%--<ul class="memberInfoHover">--%>
                <%--<li>--%>
                    <%--<div class="showImgInfo">--%>
                        <%--<img src="css/img/PersonImg.png" alt="">--%>
                    <%--</div>--%>
                    <%--<div class="showPersonalInfo">--%>
                        <%--<span>张三（产品总监）</span>--%>
                        <%--<ul class="personalOperaIcon">--%>
                            <%--<li class="sendMsg"></li>--%>
                            <%--<li class="checkPosition"></li>--%>
                            <%--<li class="addConver"></li>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <%--<li>asdasdad</li>--%>
                <%--<li>asdasdad</li>--%>
            <%--</ul>--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--</div>--%>



    <div class="chatHeader">
        <ul class="chatHeaderMenu">
            <li class="active" bindPanel="news">消息</li>
            <li class="" bindPanel="orgnized">组织通讯录</li>
            <li bindPanel="back">后台管理</li>
        </ul>
    </div>


    <!--消息-->
    <div class="chatContent news">
        <ul class="chatMenu">
            <li class="chatLeftIcon active " bindPanel="groupChatList"></li>
            <li class="chatLeftIcon " bindPanel="usualChatList"></li>
            <li class="chatLeftIcon" bindPanel="newsChatList"></li>
        </ul>
        <div class="newsTabContent">
            <div class="chatContent groupChatList">
                <div class="listCtrl myGroup">
                    <span class="triOpen chatLeftIcon"></span>
                    <span class="discrib">我的组群</span>
                </div>
                <ul class="groupChatListUl">
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                </ul>
                <div class="selfCreatGroup listCtrl">
                    <span class="triOpen chatLeftIcon"></span>
                    <span class="discrib">我创建的组群</span>
                </div>
                <ul class="groupChatListUl">
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                </ul>
            </div>
            <div class="chatContent usualChatList chatHide">
                <div class="listCtrl myGroup">
                    <span class="triOpen chatLeftIcon"></span>
                    <span class="discrib">我的常用联系人</span>
                </div>
                <ul class="groupChatListUl">
                    <%--<li>--%>
                        <%--<div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>--%>
                    <%--</li>--%>
                </ul>
            </div>
            <div class="chatContent newsChatList chatHide">
                <!--<div class="listCtrl myGroup">-->
                    <!--<span class="triOpen chatLeftIcon"></span>-->
                    <!--<span class="discrib">我的常用联系人</span>-->
                <!--</div>-->
                <ul class="groupChatListUl" style="margin: 6px 0px 35px 10px">
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="footerPlus chatLeftIcon">
            <div class="operMenuList">
                <ul>
                    <li class="chatLeftIcon">添加好友</li>
                    <li class="chatLeftIcon">发起聊天</li>
                    <li class="chatLeftIcon">创建群组</li>
                </ul>
            </div>
        </div>

    </div>

    <!--组织通讯录-->
    <div class="chatContent orgnized chatHide">
        <!--<div></div>-->
       <div class="serachMenber">
           <input class="searchInput chatLeftIcon">
               <span class="defaultText">搜索人员</span>
           </input>
       </div>
        <div class="searchResult">
            <ul class="searchResultUL">
                <%--搜索没有结果--%>
                <li class="searchNoResult">
                    <%--<img src='css/img/touxiang.png'/>--%>
                    <span>没有搜索结果</span>
                </li>
                <%--搜索结果--%>
                <%--<li><img src='css/img/touxiang.png'/>1111(产品总监)</li>--%>
                <%--<li><img src='css/img/touxiang.png'/>1111(产品总监)</li>--%>
                <%--<li><img src='css/img/touxiang.png'/>1111(产品总监)</li>--%>
            </ul>
        </div>

        <div class="organizationList">
            <ul>
                <li>
                    <div level="1">
                        <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                        <span class="groupName">产品部</span>
                        <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                    </div>
                </li>
                <ul>
                    <li>
                        <div level="2">
                            <span style="height: 20px;width: 32px;display:inline-block;float: left;"></span>
                            <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                            <span class="groupName">adsfadsfadsf</span>
                            <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                        </div>
                    </li>
                    <ul>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                    </ul>
                </ul>
                <li>
                    <div level="1">
                        <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                        <span class="groupName">产品部</span>
                        <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                    </div>
                </li>
                <ul>
                    <li>
                        <div level="2">
                            <span style="height: 20px;width: 32px;display:inline-block;float: left;"></span>
                            <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                            <span class="groupName">adsfadsfadsf</span>
                            <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                        </div>
                    </li>
                    <ul>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                    </ul>
                </ul>
                <li>
                    <div level="1">
                        <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                        <span class="groupName">产品部</span>
                        <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                    </div>
                </li>
                <ul>
                    <li>
                        <div level="2">
                            <span style="height: 20px;width: 32px;display:inline-block;float: left;"></span>
                            <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                            <span class="groupName">adsfadsfadsf</span>
                            <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                        </div>
                    </li>
                    <ul>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                    </ul>
                </ul>
            </ul>
        </div>
    </div>

    <!--后台管理-->
    <div class="chatContent back chatHide" >

        <div class="newsTabContent">
            <div class="orgnizedgroupChatList"></div>
            <div class="orgnizedusualChatList"></div>
            <div class="orgnizednewsChatList"></div>
        </div>
        <div class="footerPlus"></div>

    </div>


    <!--聊天部分-->
    <div class="chatBoxOuter">
        <div class="chatBox">
            <div class="BreadcrumbsOuter">
                <ul class="Breadcrumbs">
                    <li><a href=""> 1111 </a> ></li>
                    <li><a href=""> 2222 </a> ></li>
                    <li><a href=""> 3333 </a> ></li>
                </ul>
                <a class="chatLeftIcon seeOrgnizeTree"></a>
            </div>
            <!--组织的层级导航-->
            <div class="orgNavClick orgNavClick1 " id="orgnizedLevel">
                <div class="orgNavTitle">标题</div>
                <ul>
                    <li>
                        <div class="showImgInfo">
                            <img src="css/img/PersonImg.png" alt=""/>
                        </div>
                        <div class="showPersonalInfo">
                            <span>张三（产品总监）</span>
                            <ul class="personalOperaIcon">
                                <li class="sendMsg"></li>
                                <li class="checkPosition"></li>
                                <li class="addConver"></li>
                            </ul>
                        </div>
                    </li>

                </ul>
                <div class="orgNavTitle">标题</div>
                <ul>

                    <li>
                        <div class="showImgInfo">
                            <img src="css/img/PersonImg.png" alt=""/>
                        </div>
                        <div class="showPersonalInfo">
                            <span>张三（产品总监）</span>
                            <ul class="personalOperaIcon">
                                <li class="sendMsg"></li>
                                <li class="checkPosition"></li>
                                <li class="addConver"></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div class="showImgInfo">
                            <img src="css/img/PersonImg.png" alt=""/>
                        </div>
                        <div class="showPersonalInfo">
                            <span>张三（产品总监）</span>
                            <ul class="personalOperaIcon">
                                <li class="sendMsg"></li>
                                <li class="checkPosition"></li>
                                <li class="addConver"></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div class="showImgInfo">
                            <img src="css/img/PersonImg.png" alt=""/>
                        </div>
                        <div class="showPersonalInfo">
                            <span>张三（产品总监）</span>
                            <ul class="personalOperaIcon">
                                <li class="sendMsg"></li>
                                <li class="checkPosition"></li>
                                <li class="addConver"></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div class="showImgInfo">
                            <img src="css/img/PersonImg.png" alt=""/>
                        </div>
                        <div class="showPersonalInfo">
                            <span>张三（产品总监）</span>
                            <ul class="personalOperaIcon">
                                <li class="sendMsg"></li>
                                <li class="checkPosition"></li>
                                <li class="addConver"></li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="orgNavClick orgNavClick2 chatHide" id="personalDetail ">
                <div class="personalDetailContent">

                    <div class="selfImgInfo">
                        <img src="css/img/PersonImg.png" alt=""/>
                        <div>
                            <p>张三</p>
                            <ul class="selfImgOpera">
                                <li class="sendMsg"></li>
                                <li class="checkPosition"></li>
                                <li class="addConver"></li>
                            </ul>
                        </div>
                    </div>
                    <div class="showPersonalInfo">
                       <ul>
                           <li>
                               <div>aaaaa:</div>
                               <div>ddddd</div>

                           </li>
                           <li>
                               <div>aaaaa:</div>
                               <div>ddddd</div>

                           </li>
                           <li>
                               <div>aaaaa:</div>
                               <div>ddddd</div>

                           </li>
                           <li>
                               <div>aaaaa:</div>
                               <div>ddddd</div>

                           </li>
                           <li>
                               <div>aaaaa:</div>
                               <div>ddddd</div>

                           </li>

                       </ul>
                    </div>

                </div>
            </div>
            <div class="orgNavClick orgNavClick3 chatHide" id="organizeList">
                <div class="organizeListOuter">
                    <!--<div class="topOuter"><p class="horizontal">董事长</p></div>-->
                </div>

            </div>

        </div>
    </div>


    <div class="WindowMask">

        <div class="conversWindow groupConvers"><!--//privateConvers-->
            <span class="dialogClose">×</span>
            <div class="dialogHeader">群组管理</div>
            <div class="dialogBody">
                <div class="contactListOuter">
                    <p class="outerTitle">选择联系人：</p>
                    <div class="contactBox">
                        <input class="contactsSearch chatLeftIcon" placeholder="查找联系人..."/>
                        <div class="contactsList">
                            <!--<div class="organizationList">-->
                            <%--<ul>--%>
                                <%--<li>--%>
                                    <%--<div level="1" class="department">--%>
                                        <%--<span class="dialogCollspan chatLeftIcon dialogCollspanO"></span>--%>
                                        <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                        <%--<span class="dialogGroupName">产品部</span>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                                <%--<ul>--%>
                                    <%--<li>--%>
                                        <%--<div level="2" class="department">--%>
                                            <%--<span style="height: 20px;width: 22px;display:inline-block;float: left;"></span>--%>
                                            <%--<span class="dialogCollspan chatLeftIcon dialogCollspanO"></span>--%>
                                            <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                            <%--<span class="dialogGroupName">产品部</span>--%>
                                        <%--</div>--%>
                                    <%--</li>--%>
                                    <%--<ul>--%>
                                        <%--<li>--%>
                                            <%--<div level="3" class="member">--%>
                                                <%--<span style="height: 20px;width: 44px;display:inline-block;float: left;"></span>--%>
                                                <%--<span class="dialogCollspan chatLeftIcon"></span>--%>
                                                <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                                <%--<span class="dialogGroupName">李四</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<div level="3" class="member">--%>
                                                <%--<span style="height: 20px;width: 44px;display:inline-block;float: left;"></span>--%>
                                                <%--<span class="dialogCollspan chatLeftIcon"></span>--%>
                                                <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                                <%--<span class="dialogGroupName">李四</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<div level="3" class="member">--%>
                                                <%--<span style="height: 20px;width: 44px;display:inline-block;float: left;"></span>--%>
                                                <%--<span class="dialogCollspan chatLeftIcon"></span>--%>
                                                <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                                <%--<span class="dialogGroupName">李四</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                    <%--</ul>--%>
                                <%--</ul>--%>
                                <%--<li>--%>
                                    <%--<div level="1" class="department">--%>
                                        <%--<span class="dialogCollspan chatLeftIcon dialogCollspanO"></span>--%>
                                        <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                        <%--<span class="dialogGroupName">产品部</span>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                                <%--<ul>--%>
                                    <%--<li>--%>
                                        <%--<div level="2" class="department">--%>
                                            <%--<span style="height: 20px;width: 22px;display:inline-block;float: left;"></span>--%>
                                            <%--<span class="dialogCollspan chatLeftIcon dialogCollspanO"></span>--%>
                                            <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                            <%--<span class="dialogGroupName">产品部</span>--%>
                                        <%--</div>--%>
                                    <%--</li>--%>
                                    <%--<ul>--%>
                                        <%--<li>--%>
                                            <%--<div level="3" class="department">--%>
                                                <%--<span style="height: 20px;width: 44px;display:inline-block;float: left;"></span>--%>
                                                <%--<span class="dialogCollspan chatLeftIcon dialogCollspanO"></span>--%>
                                                <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                                <%--<span class="dialogGroupName">产品部</span>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                        <%--<ul>--%>
                                            <%--<li>--%>
                                                <%--<div level="3" class="member">--%>
                                                    <%--<span style="height: 20px;width: 66px;display:inline-block;float: left;"></span>--%>
                                                    <%--<span class="dialogCollspan chatLeftIcon"></span>--%>
                                                    <%--<span class="chatLeftIcon CheckBoxChecked"></span>--%>
                                                    <%--<span class="dialogGroupName">李四</span>--%>
                                                <%--</div>--%>
                                            <%--</li>--%>
                                        <%--</ul>--%>
                                    <%--</ul>--%>
                                <%--</ul>--%>
                                <%--<li>--%>
                                    <%--<div level="1">--%>
                                        <%--<span class="dialogCollspan chatLeftIcon dialogCollspanO"></span>--%>
                                        <%--<span class="chatLeftIcon dialogCheckBox"></span>--%>
                                        <%--<span class="dialogGroupName">产品部</span>--%>
                                    <%--</div>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                            <!--</div>-->
                        </div>
                    </div>
                </div>
                <div class="selectedContactOuter">
                    <p class="outerTitle">已选择联系人 <em>(2/99)</em>：</p>
                    <div class="contactBox">
                        <input class="selectedSearch chatLeftIcon" placeholder="查找联系人..."/>
                        <div class="selectedList">
                            <ul>
                                <li><span class="memberName">赵四</span><span class="chatLeftIcon deleteMemberIcon"></span></li>
                                <li><span class="memberName">赵四</span><span class="chatLeftIcon deleteMemberIcon"></span></li>
                                <li><span class="memberName">赵四</span><span class="chatLeftIcon deleteMemberIcon"></span></li>
                                <li><span class="memberName">赵四</span><span class="chatLeftIcon deleteMemberIcon"></span></li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
            <div class="dialogFooter">
                <input type="button" value="确定" class="manageSure">
                <input type="button" value="取消" class="manageCancle">
            </div>
        </div>

    </div>

    <div class="WindowMask2">
        <div class="conversWindow groupConvers"><!--//privateConvers-->
            <span class="dialogClose">×</span>
            <div class="dialogHeader">群组管理</div>
            <div class="dialogBody">
                <div class="transferInfo">
                    <table class="transferInfoBox">
                        <thead>
                            <tr>
                                <th>群成员</th>
                                <th>职位</th>
                                <th>是否具有管理权限</th>
                                <th>群成员</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <img src="" alt=""/>王二虎
                                </td>
                                <td>产品经理</td>
                                <td>是</td>
                                <td class="operate"><span>转让群</span></td>
                            </tr>
                            <tr>
                                <td>
                                    <img src="" alt=""/>王二虎
                                </td>
                                <td>产品经理</td>
                                <td>是</td>
                                <td class="operate"><span>转让群</span></td>
                            </tr>
                            <tr>
                                <td>
                                    <img src="" alt=""/>王二虎
                                </td>
                                <td>产品经理</td>
                                <td>是</td>
                                <td class="operate"><span>转让群</span></td>
                            </tr>
                            <tr>
                                <td>
                                    <img src="" alt=""/>王二虎
                                </td>
                                <td>产品经理</td>
                                <td>是</td>
                                <td class="operate"><span>转让群</span></td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            </div>
            <div class="dialogFooter">
                <input type="button" value="确定" class="manageSure">
                <input type="button" value="取消" class="manageCancle">
            </div>
        </div>


    </div>
</body>
</html>