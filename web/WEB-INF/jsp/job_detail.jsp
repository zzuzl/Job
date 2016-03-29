<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="root" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html ng-app="jobDetail">
<head>
    <title>${job.name}</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <script type="text/javascript"
            src="${root}/js/jquery-1.11.2.js"></script>
    <script type="text/javascript"
            src="${root}/js/jquery.fullPage.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>
    <link rel="stylesheet" href="${root}/bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${root}/bootstrapvalidator/css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="${root}/font-awesome-4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${root}/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="${root}/css/style_job_detail.css"/>
    <script src="${root}/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
    <script src="${root}/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
    <script src="${root}/js/dateformat.js"></script>
    <script src="${root}/js/moment-with-locales.js"></script>
    <script src="${root}/js/angular-1.4.8/angular.min.js"></script>
    <%--<script src="${root}/js/controllers/jobDetail.js"></script>--%>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div id="leftDiv">
    <div class="baseInfo">
        <h2>${job.name}
            <c:choose>
                <c:when test="${requestScope.collection == null}">
                    <i class="fa fa-star" id="collect"></i>
                </c:when>
                <c:otherwise>
                    <i class="fa fa-star" id="collect" style="color: red"></i>
                </c:otherwise>
            </c:choose>
        </h2>

        <div class="require">
            <span class="font4" style="margin-right: 30px">${job.low_salary}-${job.high_salary}</span>
            <span class="date" style="margin-right: 30px">
                <fmt:formatDate pattern="yyyy-MM-dd hh:MM" value="${requestScope.job.post_time}"></fmt:formatDate>
            </span>
            <span class="font5">${job.type.name}</span>
            <c:choose>
                <c:when test="${requestScope.apply == null}">
                    <button class="btn btn-danger" id="apply">申请职位</button>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-danger" id="apply" disabled>申请中</button>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="tag">${job.tag}</div>
    </div>
    <div class="jobIntroduce">
        <h4>职位介绍</h4>

        <p>
            技能要求：${job.skill}
        </p>

        <p>
            招聘人数：${job.person_count}
        </p>

        <p>
            职位描述：${job.description}
        </p>

        <p>
            工作时间：
        <table id="table" class="weekTable">
            <tr>
                <td>星期一</td>
                <td>星期二</td>
                <td>星期三</td>
                <td>星期四</td>
                <td>星期五</td>
                <td>星期六</td>
                <td>星期日</td>
            </tr>
            <tr id="week">
                <td><input type="checkbox"/></td>
                <td><input type="checkbox"/></td>
                <td><input type="checkbox"/></td>
                <td><input type="checkbox"/></td>
                <td><input type="checkbox"/></td>
                <td><input type="checkbox"/></td>
                <td><input type="checkbox"/></td>
            </tr>
        </table>
        </p>
    </div>
    <div class="companyIntroduce">
        <h4>公司介绍</h4>

        <p>${job.post_company.company_name}</p>

        <p>公司地址：${job.post_company.address}</p>

        <p>公司简介：${job.post_company.introduce}</p>

        <p>公司规模：${job.post_company.scope}</p>

        <p>联系人：${job.post_company.name}</p>

        <p>手机号：${job.post_company.phone}</p>

        <p>邮箱：${job.post_company.email}</p>

        <div id="container"></div>
    </div>
    <div class="allJobs" ng-controller="CompanyJobController">
        <h4>该公司所有职位</h4>

        <div class="job_item" style="display: block" ng-repeat="item in jobs">
            <table>
                <tr>
                    <td width="30%"><a href="#" class="link">{{item.name}}</a></td>
                    <td width="30%" class="font3">{{item.post_time.time | date:'yyyy-MM-dd hh:mm'}}</td>
                    <td width="30%" class="font5">{{item.post_company.company_name}}</td>
                </tr>
                <tr>
                    <td class="font5">{{item.type.name}}</td>
                    <td class="font4">{{item.low_salary}}-{{item.high_salary}}</td>
                    <td class="font3">{{item.post_company.scope}}</td>
                </tr>
            </table>
        </div>

        <nav style="margin: 0 auto;text-align: center" ng-if="jobs.length > 0">
            <ul class="pagination">
                <li>
                    <a href="javascript:void(0)" aria-label="Previous" ng-click="load(-1)">&laquo;</a>
                </li>
                <li ng-repeat="p in pageArray" ng-class="{active:isCurrentPage(p)}">
                    <a href="javascript:void(0)" ng-click="loadData(p)">{{p}}</a>
                </li>
                <li>
                    <a href="javascript:void(0)" aria-label="Next" ng-click="load(1)">&raquo;</a>
                </li>
            </ul>
        </nav>
    </div>
    <div class="otherJobs">
        <h4>职位推荐</h4>
    </div>
</div>
<div id="rightDiv">
    <div class="companyLogo">
        <img src="${root}/images/${job.post_company.logo}">
    </div>
    <div class="comment" ng-controller="CommentController">
        <h4>评论列表</h4>

        <div id="content">
            <div ng-repeat="item in comments">
                <div>
                    <img ng-src="${root}/images/{{item.user.photo_src}}" class='headPhoto'>
                    <span>{{item.c_time.time | date:'yyyy-MM-dd hh:mm:ss'}}</span>
                </div>
                <div class='contentDiv'>
                    <p>{{item.content}}</p>
                </div>
            </div>
        </div>

        <nav style="margin: 0 auto;text-align: center" id="page" ng-if="comments.length > 0">
            <ul class="pagination pagination-sm">
                <li>
                    <a href="javascript:void(0)" aria-label="Previous" ng-click="load(-1)">&laquo;</a>
                </li>
                <li ng-repeat="p in pageArray" ng-class="{active:isCurrentPage(p)}">
                    <a href="javascript:void(0)" ng-click="loadData(p)">{{p}}</a>
                </li>
                <li>
                    <a href="javascript:void(0)" aria-label="Next" ng-click="load(1)">&raquo;</a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>

<script type="application/javascript">

    //记录收藏按钮的状态
    var flag = true;

    function initMap() {
        var map = new BMap.Map("container");          // 创建地图实例
        var point = new BMap.Point(${job.post_company.x}, ${job.post_company.y});  // 创建点坐标
        map.centerAndZoom(point, 15);               // 初始化地图，设置中心点坐标和地图级别
        var opts = {type: BMAP_NAVIGATION_CONTROL_LARGE};
        map.addControl(new BMap.NavigationControl(opts));
        var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        map.addControl(top_left_control);
        map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
        map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
        var marker = new BMap.Marker(point);        // 创建标注
        map.addOverlay(marker);                     // 将标注添加到地图中
    }

    //格式化时间
    function formatDate() {
        moment.locale("zh_cn");

        var dates = $(".date");
        for (var i = 0; i < dates.length; i++) {
            var dText = dates.eq(i).text();
            dates.eq(i).text(moment(dText, "YYYY-MM-DD hh:mm").fromNow());
        }
    }

    $(function () {
        formatDate();

        flag = ${requestScope.collection != null};
        initMap();
        var spareStr = parseInt(${requestScope.job.work_time}).toString(2);
        var timeLength = spareStr.length;
        if (spareStr.length < 7) {
            for (var i = 0; i < 7 - timeLength; i++) {
                spareStr = "0" + spareStr;
            }
        } else {
            spareStr = spareStr.substr(spareStr.length - 7);
        }

        var week = $("#week td :checkbox");

        for (var i = 0; i < spareStr.length; i++) {
            var c = spareStr.charAt(i);
            week.eq(i).attr("checked", c == '1');
        }
        $(":checkbox").attr("disabled", "disabled");
    });

    $("#collect").click(function () {
        if (${sessionScope.user == null}) {
            window.location = "${root}/user/toLogin.do?from=" + window.location.href;
        } else {
            flag = !flag;
            if (flag) {
                $("#collect").css("color", "red");
            } else {
                $("#collect").css("color", "#888");
            }
            $.post("${root}/job/updateCollection.do", {
                collection: flag,
                j_id:${job.id}
            }, function (data) {
                if (data.msg) {
                    window.location = "${root}/user/toLogin.do?from=" + window.location.href;
                }
            }, "JSON");
        }
    });

    $("#apply").click(function () {
        if (${sessionScope.user == null}) {
            window.location = "${root}/user/toLogin.do?from=" + window.location.href;
        } else {
            $.post("${root}/job/applyJob.do", {
                j_id:${job.id}
            }, function (data) {
                if (data.msg) {
                    window.location = "${root}/user/toLogin.do?from=" + window.location.href;
                } else {
                    window.location = "${root}/job/applySuccess.do";
                }
            }, "JSON");
        }
    });

    /////////////////////////////////////////// angularjs //////////////////////////////////////////////////

    var app = angular.module("jobDetail", []);

    app.controller("CommentController", function ($scope, $http) {
        $scope.currentPage = 1;
        $scope.minPage = 1;
        $scope.maxPage = 1;

        $scope.params = {
            page: 1,
            id:${requestScope.job.id}
        };

        $scope.isCurrentPage = function (p) {
            return p == $scope.currentPage;
        };

        $scope.loadData = function (page) {
            $scope.params.page = page;

            $http.get('${root}/job/getComments.do', {
                params: $scope.params
            }).success(function (data) {
                $scope.comments = data.rows;
                $scope.pageArray = [];

                $scope.maxPage = Math.ceil(data.total / 5);

                if ($scope.maxPage <= 5) {
                    for (var i = 0; i < $scope.maxPage; i++) {
                        $scope.pageArray.push(i + 1);
                    }
                } else {
                    if ($scope.currentPage > 3) {
                        var end = $scope.maxPage > $scope.currentPage + 2 ? $scope.currentPage + 2 : $scope.maxPage;
                        for (var i = $scope.currentPage - 2; i <= end; i++) {
                            $scope.pageArray.push(i);
                        }
                    } else {
                        for (var i = 0; i < 5; i++) {
                            $scope.pageArray.push(i + 1);
                        }
                    }
                }

                $scope.currentPage = page;
            });
        };

        $scope.load = function (temp) {
            if ((temp < 0 && $scope.currentPage <= $scope.minPage) ||
                    ($scope.currentPage >= $scope.maxPage && temp > 0)) {
                return;
            } else {
                $scope.loadData($scope.currentPage + temp);
            }
        };

        //初始化加载第一页评论
        $scope.loadData(1);
    });

    app.controller("CompanyJobController", function ($scope, $http) {
        $scope.currentPage = 1;
        $scope.minPage = 1;
        $scope.maxPage = 1;

        $scope.params = {
            page: 1,
            id:${requestScope.job.post_company.id}
        };

        $scope.isCurrentPage = function (p) {
            return p == $scope.currentPage;
        };

        $scope.loadData = function (page) {
            $scope.params.page = page;

            $http.get('${root}/job/getJobsByCompany.do', {
                params: $scope.params
            }).success(function (data) {
                $scope.jobs = data.rows;
                $scope.pageArray = [];

                $scope.maxPage = Math.ceil(data.total / 5);

                if ($scope.maxPage <= 5) {
                    for (var i = 0; i < $scope.maxPage; i++) {
                        $scope.pageArray.push(i + 1);
                    }
                } else {
                    if ($scope.currentPage > 3) {
                        var end = $scope.maxPage > $scope.currentPage + 2 ? $scope.currentPage + 2 : $scope.maxPage;
                        for (var i = $scope.currentPage - 2; i <= end; i++) {
                            $scope.pageArray.push(i);
                        }
                    } else {
                        for (var i = 0; i < 5; i++) {
                            $scope.pageArray.push(i + 1);
                        }
                    }
                }

                $scope.currentPage = page;
            });
        };

        $scope.load = function (temp) {
            if ((temp < 0 && $scope.currentPage <= $scope.minPage) ||
                    ($scope.currentPage >= $scope.maxPage && temp > 0)) {
                return;
            } else {
                $scope.loadData($scope.currentPage + temp);
            }
        };

        $scope.loadData(1);
    });
</script>
</body>
</html>

