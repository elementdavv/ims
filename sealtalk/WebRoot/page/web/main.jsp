<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/html">
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/main.css"/>
    <script src="<%=request.getContextPath() %>/page/web/js/jquery-2.1.1.min.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/main.js"></script>

</head>
<body>
    <div class="chatHeader">
        <ul class="chatHeaderMenu">
            <li class="" bindPanel="news">消息</li>
            <li class="active" bindPanel="orgnized">组织通讯录</li>
            <li bindPanel="back">后台管理</li>
        </ul>
    </div>


    <!--消息-->
    <div class="chatContent news chatHide">
        <ul class="chatMenu">
            <li class="chatLeftIcon active" bindPanel="groupChatList"></li>
            <li class="chatLeftIcon" bindPanel="usualChatList"></li>
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
    <div class="chatContent orgnized">
        <!--<div></div>-->
       <div class="serachMenber">
           <div class="searchInput">
               <span class="defaultText chatLeftIcon">搜索人员</span>
           </div>
       </div>

        <div class="organizationList">
            <ul>
                <li>
                    <div>
                        <img class="groupImg" src="css/img/group_chart.png" alt=""/>
                        <span class="groupName">产品部<em>(15/20)</em></span>
                        <span class="groupCollspanO chatLeftIcon"></span>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <!--后台管理-->
    <div class="chatContent back chatHide" >
        <ul class="chatMenu">
            <li class="chatLeftIcon active"></li>
            <li class="chatLeftIcon"></li>
            <li class="chatLeftIcon"></li>
        </ul>
        <div class="newsTabContent">
            <div class="orgnizedgroupChatList"></div>
            <div class="orgnizedusualChatList"></div>
            <div class="orgnizednewsChatList"></div>
        </div>
        <div class="footerPlus"></div>

    </div>


    <!--聊天部分-->
    <div class="chatBoxOuter">
        <div class="chatBox"></div>
    </div>

</body>
</html>