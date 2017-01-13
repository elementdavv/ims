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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/perfect-scrollbar.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/page/web/css/backstageMg.css"/>
    <script src="<%=request.getContextPath() %>/page/web/js/jquery-2.1.1.min.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/main.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/window.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/seeOrgnizeTree.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/backstageMg.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/Class.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/seeOrgnizeTree.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/dialogOper.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/creatGroup.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/message.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/jquery.mousewheel.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/perfect-scrollbar.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/conversation.js"></script>
    <script src="http://cdn.ronghub.com/RongIMLib-2.2.4.min.js"></script>
    <script src="<%=request.getContextPath() %>/page/web/js/rongyun.js"></script>
    <%--<script src="<%=request.getContextPath() %>/page/web/js/clip.js"></script>--%>
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
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                </ul>
                <div class="selfCreatGroup listCtrl">
                    <span class="triOpen chatLeftIcon"></span>
                    <span class="discrib">我创建的组群</span>
                </div>
                <ul class="groupChatListUl">
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
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
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
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
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
                    </li>
                    <li>
                        <div><img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/><span class="groupName">产品部<em>(15/20)</em></span></div>
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
            <input class="searchInput chatLeftIcon">
                <span class="defaultText">搜索人员</span>
            </input>
        </div>

        <div class="organizationList">
            <ul>
                <li>
                    <div level="1">
                        <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                        <span class="groupName">产品部</span>
                        <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                    </div>
                </li>
                <ul>
                    <li>
                        <div level="2">
                            <span style="height: 20px;width: 32px;display:inline-block;float: left;"></span>
                            <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                            <span class="groupName">adsfadsfadsf</span>
                            <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                        </div>
                    </li>
                    <ul>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                    </ul>
                </ul>
                <li>
                    <div level="1">
                        <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                        <span class="groupName">产品部</span>
                        <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                    </div>
                </li>
                <ul>
                    <li>
                        <div level="2">
                            <span style="height: 20px;width: 32px;display:inline-block;float: left;"></span>
                            <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                            <span class="groupName">adsfadsfadsf</span>
                            <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                        </div>
                    </li>
                    <ul>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                    </ul>
                </ul>
                <li>
                    <div level="1">
                        <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                        <span class="groupName">产品部</span>
                        <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                    </div>
                </li>
                <ul>
                    <li>
                        <div level="2">
                            <span style="height: 20px;width: 32px;display:inline-block;float: left;"></span>
                            <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                            <span class="groupName">adsfadsfadsf</span>
                            <span class="groupCollspanO chatLeftIcon groupCollspan"></span>
                        </div>
                    </li>
                    <ul>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
                                <span class="groupName">人名</span>
                            </div>
                        </li>
                        <li>
                            <div level="3">
                                <span style="height: 20px;width: 64px;display:inline-block;float: left;"></span>
                                <img class="groupImg" src="page/web/css/img/group_chart.png" alt=""/>
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
        <ul class="backstageMg" id="backstageMgId">
            <li class="clearfix">
                <em class="bMg-personalSet"></em>
                <span>个人设置</span>
            </li>
            <li class="clearfix">
                <em class="bMg-systemSet"></em>
                <span>系统设置</span>
            </li>
            <li class="clearfix">
                <em class="bMg-changePw"></em>
                <span>修改密码</span>
            </li>
        </ul>
        <%--<div class="newsTabContent">--%>
            <%--<div class="orgnizedgroupChatList"></div>--%>
            <%--<div class="orgnizedusualChatList"></div>--%>
            <%--<div class="orgnizednewsChatList"></div>--%>
        <%--</div>--%>
        <%--<div class="footerPlus"></div>--%>

    </div>


    <!--聊天部分-->
    <div class="chatBoxOuter">
        <div class="chatBox" style="position:relative" id="chatBox">

    <!--个人设置-->
                <%--<div class="perSetBox orgNavClick chatHide" >--%>
                    <%--<h3 class="perSetBox-title">个人设置</h3>--%>
                    <%--<div class="perSetBox-content clearfix">--%>
                        <%--<div class="perSetBox-leftCont">--%>
                            <%--<ul class="perSetBox-contDetails">--%>
                                <%--<li >--%>
                                    <%--<span>成员账号:</span>--%>
                                    <%--<p>王二虎</p>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span>姓名：</span>--%>
                                    <%--<p>王二虎</p>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span>性别：</span>--%>
                                    <%--<p>--%>
                                        <%--<select class="perSetBox-selSex">--%>
                                            <%--<option>男</option>--%>
                                            <%--<option>女</option>--%>
                                        <%--</select>--%>
                                    <%--</p>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span>职位：</span>--%>
                                    <%--<p>王二虎</p>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span>部门：</span>--%>
                                    <%--<p>王二虎</p>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span>邮箱：</span>--%>
                                    <%--<p>--%>
                                        <%--<input value="123456789" class="perSetBox-editText"/>--%>
                                    <%--</p>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span>电话：</span>--%>
                                    <%--<p>--%>
                                        <%--<input value="123456789" class="perSetBox-editText"/>--%>
                                    <%--</p>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<span>工作签名：</span>--%>
                                    <%--<p>--%>
                                        <%--<textarea class="perSetBox-textarea">--%>

                                        <%--</textarea>--%>
                                    <%--</p>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                            <%--<button class="perSetBox-keep">保存</button>--%>
                        <%--</div>--%>
                        <%--<div class="perSetBox-rightCont">--%>
                            <%--<img src="page/web/css/img/1.jpg" class="perSetBox-head"/>--%>
                            <%--<p class="perSetBox-modifyHead">修改头像</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
    <!--系统设置-->
                <div class="perSetBox orgNavClick chatHide"  >
                        <h3 class="perSetBox-title">系统设置</h3>
                        <div class="systemSetVoice clearfix">
                            <span>通知提示音：</span>
                            <p>
                                <i></i>
                            </p>
                        </div>
                        <b class="systemSet-keep">保存</b>
                </div>
                <div class="perSetBox orgNavClick chatHide" >
                    <h3 class="perSetBox-title">修改密码</h3>
                    <div class="changePassword">
                        <div class="clearfix cp-oldPassword">
                            <span>旧密码：</span>
                            <input type="password">
                        </div>
                        <div class="clearfix">
                            <span>新密码：</span>
                            <input type="password" class="cp-newPassword">
                        </div>
                        <ul class="cp-passwordSecurity clearfix">
                            <li></li>
                            <li></li>
                            <li></li>
                        </ul>
                        <div class="clearfix cp-reNewPassword">
                            <span>新密码：</span>
                            <input type="password">
                        </div>
                    </div>
                    <b class="systemSet-keep">保存</b>
                </div>
    <!--geren消息记录-->
            <div class="mesContainerSelf mesContainer orgNavClick chatHide" id="perContainer">
                <h3 class="perSetBox-title clearfix">
                    <span>张三</span>
                    <div class="messageRecord clearfix">
                        <i class="mr-Location"></i>
                        <i class="mr-record active" id="mr-record"></i>
                    </div>
                </h3>
                <div class="mr-chatview">
                    <p class="mr-Date">-11月11日 星期五-</p>
                    <p class="mr-time">9:28</p>
                    <ul class="mr-chatContent">
                        <li class="mr-chatContentL clearfix">
                            <img src="page/web/css/img/1.jpg" >
                            <div class="mr-chatBox">
                                <span>大家好，请多多指教大家好大家好，请多多指教大家好大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好</span>
                                <i></i>
                            </div>
                        </li>
                        <li class="mr-chatContentR clearfix">
                            <div class="mr-ownChat">
                                <span>xxxxxxxxxxxxxxxxxxx大家好，请多多指教大家好大家好，请多多指教大家好大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好</span>
                                <i></i>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="mr-chateditBox">
                        <div class="mr-expresFile clearfix">
                            <span></span>
                            <i></i>
                        </div>
                        <textarea placeholder="说点什么..."></textarea>
                        <strong class="sendMsg">发送</strong>
                </div>
            </div>
    <!--群组消息记录-->
            <div class="mesContainerGroup mesContainer orgNavClick chatHide" style="margin-right:341px;" >
            <h3 class="perSetBox-title clearfix">
            <span>张三</span>
            <div class="messageRecord clearfix">
            <i class="mr-Location"></i>
            <i class="mr-record"></i>
            </div>
            </h3>
            <div class="mr-chatview">
            <p class="mr-Date">-11月11日 星期五-</p>
            <p class="mr-time">9:28</p>
            <p class="group-addPerson clearfix">
                <span>谁将谁拉进群组<i></i></span>
            </p>
            <ul class="mr-chatContent">
            <li class="mr-chatContentL clearfix">
            <img src="page/web/css/img/1.jpg" >
            <div class="mr-chatBox">
            <span>大家好，请多多指教大家好大家好，请多多指教大家好大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好</span>
            <i></i>
            </div>
            </li>
            <li class="mr-chatContentR clearfix">
            <div class="mr-ownChat">
            <span>大家好，请多多指教大家好大家好，请多多指教大家好大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好，请多多指教大家好</span>
            <i></i>
            </div>
            </li>
            </ul>
            </div>
            <div class="mr-chateditBox">
            <div class="mr-expresFile clearfix">
            <span></span>
            <i></i>
            </div>
            <textarea placeholder="说点什么..."></textarea>
            <strong class="sendMsg">发送</strong>
            </div>
            </div>
    <!--个人资料-->
            <div  class="orgNavClick personalData  chatHide" id="personalData">
                <ul class="infoDetails clearfix" id="perInfo">
                    <li class="active">资料</li>
                    <li>聊天记录</li>
                    <li>聊天文件</li>
                </ul>
                <div class="infoDetailsBox" id="infoDetailsBox">
                    <div class="infoDetails-data active">
                        <div class="infoDet-personal clearfix">
                            <img src="page/web/css/img/1.jpg">
                            <div class="infoDet-text">
                                <p>张三</p>
                                <div class="clearfix">
                                    <span class="infoDet-postInfo"></span>
                                    <span class="infoDet-position"></span>
                                    <span class="infoDet-addPer"></span>
                                </div>
                            </div>
                        </div>
                        <ul class="infoDetList clearfix">
                            <li >
                                <span>手机：</span>
                                <b>123123123123</b>
                            </li>
                                <li >
                                <span>手机：</span>
                                <b>123123123123</b>
                                </li>
                                <li >
                                <span>手机：</span>
                                <b>123123123123</b>
                                </li>
                                <li >
                                <span>手机：</span>
                                <b>123123123123</b>
                                </li>
                                <li >
                                <span>手机：</span>
                                <b>123123123123</b>
                                </li>
                                <li >
                                <span>手机：</span>
                                <b>123123123123</b>
                                </li>
                        </ul>
                    </div>
                    <div class="infoDet-chatRecord">
                        <div class="infoDet-search">
                            <input type="text" placeholder="查找信息..."/>
                            <i></i>
                        </div>
                        <div class="infoDet-recordAll">
                            <p class="infoDet-timeRecord">2016-09-28</p>
                            <ul class="infoDet-contentDet">
                                <li class="infoDet-OthersSay">
                                    <span>张三&nbsp&nbsp&nbsp17:10:10</span>
                                    <p>11111111111</p>
                                </li>
                                <li class="infoDet-selfSay">
                                    <span>张三&nbsp&nbsp&nbsp17:10:10</span>
                                    <p>11111111111</p>
                                </li>
                            </ul>
                        </div>
                        <div class="infoDet-page">
                            <span></span>
                            <div class="infoDet-pageQuery">
                                <i class="infoDet-firstPage"></i>
                                <i class="infoDet-prePage"></i>
                                <i class="infoDet-nextPage"></i>
                                <i class="infoDet-lastPage"></i>
                            </div>
                        </div>
                    </div>
                    <div class="infoDet-flieRecord">
                            <div class="infoDet-search">
                            <input type="text" placeholder="查找信息..."/>
                            <i></i>
                        </div>
                        <div class="infoDet-recordAll">
                            <ul class="chatFile">
                                <li class="chatFile-folder">
                                    <i></i>
                                    <p>
                                        <b>色彩搭配(188.mb)</b>
                                        <span>2016-11-18&nbsp&nbsp15:14&nbsp&nbsp张三</span>
                                    </p>
                                    <strong>打开</strong>
                                    <strong>打开文件夹</strong>
                                </li>
                                <li class="chatFile-zipFile">
                                <i></i>
                                <p>
                                <b>色彩搭配(188.mb)</b>
                                <span>2016-11-18&nbsp&nbsp15:14&nbsp&nbsp张三</span>
                                </p>
                                <strong>打开</strong>
                                <strong>打开文件夹</strong>
                                </li>
                                <li class="chatFile-img">
                                <i></i>
                                <p>
                                <b>色彩搭配(188.mb)</b>
                                <span>2016-11-18&nbsp&nbsp15:14&nbsp&nbsp张三</span>
                                </p>
                                <strong>打开</strong>
                                <strong>打开文件夹</strong>
                                </li>
                                <li class="chatFile-unknown">
                                <i></i>
                                <p>
                                <b>色彩搭配(188.mb)</b>
                                <span>2016-11-18&nbsp&nbsp15:14&nbsp&nbsp张三</span>
                                </p>
                                <strong>打开</strong>
                                <strong>打开文件夹</strong>
                                </li>
                            </ul>
                        </div>
                        <div class="infoDet-page">
                            <span></span>
                            <div class="infoDet-pageQuery">
                                <i class="infoDet-firstPage"></i>
                                <i class="infoDet-prePage"></i>
                                <i class="infoDet-nextPage"></i>
                                <i class="infoDet-lastPage"></i>
                            </div>
                        </div>
                     </div>
                    <div class="group-data" style="display:none">
                        <ul class="groupInfo">
                            <li class="groupInfo-name">
                                <span>群组名称：</span>
                                <b>天方产品部</b>
                            </li>
                            <li class="groupInfo-setTime">
                            <span>群组名称：</span>
                            <b>天方产品部</b>
                            </li>
                            <li class="groupInfo-Controller">
                            <span>群主/管理员：</span>
                           <img src="page/web/css/img/1.jpg">
                            </li>
                            <li class="groupInfo-disturb">
                            <span>消息免打扰：</span>
                            <p>
                                <i></i>
                                <i></i>
                            </p>
                            </li>
                        </ul>
                        <div class="groupInfo-memberList">
                            <div class="groupInfo-number clearfix">
                                <span>成员(6)</span>
                                <p class="clearfix">
                                    <i class="groupInfo-noChat"></i>
                                    <i></i>
                                </p>
                            </div>
                            <ul class="groupInfo-memberAll">
                                <li>
                                    <img src="page/web/css/img/1.jpg">
                                    <p>张三(产品经理)</p>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
    <!--群组资料-->
            <div class="BreadcrumbsOuter">
            <ul class="Breadcrumbs">
            <li><a href=""> 1111 </a> ></li>
            <li><a href=""> 2222 </a> ></li>
            <li><a href=""> 3333 </a> ></li>
            </ul>
            <a class="chatLeftIcon seeOrgnizeTree"></a>
            </div>
    <!--组织的层级导航-->

    <div class="orgNavClick orgNavClick1" id="orgnizedLevel">
    <div class="orgNavTitle">标题</div>
    <ul>
    <li>
    <div class="showImgInfo">
    <img src="page/web/css/img/PersonImg.png" alt=""/>
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
    <img src="page/web/css/img/PersonImg.png" alt=""/>
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
    <img src="page/web/css/img/PersonImg.png" alt=""/>
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
    <img src="page/web/css/img/PersonImg.png" alt=""/>
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
    <img src="page/web/css/img/PersonImg.png" alt=""/>
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
    <div class="conversWindow groupConvers">
    <span class="dialogClose">×</span>
    <div class="dialogHeader">群组管理</div>
    <div class="dialogBody">
    <div class="contactListOuter">
    <p class="outerTitle">选择联系人：</p>
    <div class="contactBox">
    <input class="contactsSearch chatLeftIcon" placeholder="查找联系人..."/>
    <div class="contactsList"></div>
    </div>
    </div>
    <div class="selectedContactOuter">
    <p class="outerTitle">已选择联系人 <em>(2/99)</em>：</p>
    <div class="contactBox">
    <input class="selectedSearch chatLeftIcon" placeholder="查找联系人..."/>
    <div class="selectedList"></div>
    </div>
    </div>

    </div>
    <div class="dialogFooter">
    <input type="button" value="确定" class="manageSure">
    <input type="button" value="取消" class="manageCancle">
    </div>
    </div>
    </div>
    <iframe src="" frameborder="0" scrolling="no" class="chatHide" id="iqs_iframe" border="none"></iframe>
    <%--修改头像部分--%>
    <%--<div class="bMgMask"></div>--%>
    <%--<div class="bMg-cutPicture">--%>
        <%--<h5 class="clearfix">--%>
            <%--<span class="bMg-changPic">修改头像</span>--%>
            <%--<i class="bMg-closeBtn"></i>--%>
        <%--</h5>--%>
        <%--<p class="bMg-selectImg">使用下列所选照片</p>--%>
        <%--<div class="clearfix">--%>
            <%--<div class="bMg-changImgSize">--%>
                <%--<div class="bMg-ImgContainer">--%>
                    <%--<canvas id="canvas_bg" width="200" height="200"></canvas>--%>
                    <%--<canvas id="canvas" width="200" height="200"></canvas>--%>
                    <%--<div class="bMg-rotateDrag clearfix">--%>
                        <%--<span></span>--%>
                        <%--<i></i>--%>
                        <%--<strong></strong>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="bMg-previewImg">--%>
            <%--<canvas id="big" width="80" height="80" radius="40"></canvas>--%>
                <%--&lt;%&ndash;<img src="css/img/1.jpg" class="bMg-selImg">&ndash;%&gt;--%>
                <%--<div class="bMg-gravityImg">--%>
                    <%--<span >添加</span>--%>
                    <%--<span>删除</span>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="bMg-button clearfix">--%>
            <%--<b>取消</b>--%>
            <%--<b>保存</b>--%>
         <%--</div>--%>
    <%--</div>--%>
</body>
</html>