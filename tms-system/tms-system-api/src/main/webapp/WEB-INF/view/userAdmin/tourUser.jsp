<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">

    <title>TMS-综合管理系统</title>
    <%--css--%>
    <jsp:include page="../include/css.jsp"/>
  </head>

  <body>

      <%--头部--%>
      <jsp:include page="../include/head.jsp"/>

      <!--左侧部分-->
      <jsp:include page="../include/left.jsp"/>
      <%--内容部分--%>
      <section id="main-content">

          <section class="wrapper">
              <div id="container">
                  <div class="row">
                      <div class="col-md-1"></div>
                      <div class="col-md-10">
                          <div class="layui-btn-group" style="float: right">
                              <button class="layui-btn" style="background-color: #00b7ee" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="layui-icon">&#xe654;</i></button>
                              <button class="layui-btn" style="background-color: orange" data-toggle="modal" data-target=".bs-example-modal-lg"><i class="layui-icon">&#xe642;</i></button>
                              <button class="layui-btn" style="background-color: red"><i class="layui-icon">&#xe640;</i></button>
                          </div>
                          <c:choose>
                              <c:when test="${not empty accountList}">
                                  <table class="table">
                                      <thead>
                                      <tr style="font-size: 20px;color: #3b883e">
                                          <td class="info"><label>用户名</label></td>
                                          <td class="info"><label>手机号</label></td>
                                          <td class="info"><label>创建时间</label></td>
                                          <td class="info"><label>修改时间</label></td>
                                          <td class="info"><label>当前状态</label></td>
                                          <td class="info"><label>权限</label></td>
                                      </tr>
                                      </thead>
                                      <tbody>
                                      <c:forEach items="${accountList}" var="account">
                                          <tr style="font-size: 20px;color:#1a1a1a" class="scenicMessage" val="${account.id}">
                                              <td class="info"><label>${account.accountName}</label></td>
                                              <td class="info"><label>${account.accountMobile}</label></td>
                                              <td class="info"><label><fmt:formatDate value="${account.createTime}" pattern="YYYY年MM月dd日"/></label></td>
                                              <td class="info"><label><fmt:formatDate value="${account.updateTime}" pattern="YYYY年MM月dd日"/></label></td>
                                              <td class="info"><label>${account.accountState}</label></td>
                                              <td class="info">
                                                  <c:forEach items="${account.roleList}" var="role">
                                                    <label>${role.roleName}</label>
                                                  </c:forEach>
                                              </td>
                                          </tr>
                                      </c:forEach>
                                      </tbody>
                                  </table>
                              </c:when>
                              <c:otherwise>
                                  <h2 style="margin-top: 40px;margin-left: 450px">暂无数据</h2>
                              </c:otherwise>
                          </c:choose>
                      </div>
                  </div>
                  <c:if test="${not empty accountList}">
                      <ul class="pager">
                          <li class="previous disabled"><a href="#"  style="color: #3d8b40"><span aria-hidden="true">&larr;</span> 上一页</a></li>
                          <li class="next"><a href="#" style="color: #3d8b40">下一页 <span aria-hidden="true">&rarr;</span></a></li>
                      </ul>
                  </c:if>
              </div>
          </section>
      </section>
      <%--新增 model--%>
      <div id="myModelAdd" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
          <div class="modal-dialog modal-lg" role="document">
              <div class="modal-content">
                  <p class="lead" style="color: #000000;padding-left: 220px;background-color: #3b883e" ><label style="color: white">景区用户添加</label></p>
                  <div class="row">
                      <div class="col-md-1">
                      </div>
                      <div class="col-md-10">
                          <fieldset>
                              <legend>
                                  <label><a href="javascript:;">基本信息</a></label>
                              </legend>
                          </fieldset>
                          <form class="layui-form layui-form-pane" method="post" action="/system/tour/user">
                              <div class="layui-form-item" pane>
                                  <label class="layui-form-label">用户名</label>
                                  <div class="layui-input-block">
                                      <input type="text" name="accountName" required lay-verify="required" autocomplete="off" class="layui-input">
                                  </div>
                              </div>
                              <div class="layui-form-item" pane>
                                  <label class="layui-form-label">手机号</label>
                                  <div class="layui-input-block">
                                      <input type="text" name="accountMobile" required lay-verify="required" autocomplete="off" class="layui-input">
                                  </div>
                              </div>
                              <div class="layui-form-item" pane>
                                  <label class="layui-form-label">密码</label>
                                  <div class="layui-input-block">
                                      <input type="password" name="accountPassword" required lay-verify="required" autocomplete="off" class="layui-input">
                                  </div>
                              </div>
                              <div>
                                  <fieldset>
                                      <legend>
                                          <label><a href="javascript:;">权限</a></label>
                                      </legend>
                                  </fieldset>
                                  <c:forEach items="${roleList}" var="role">
                                      <input type="checkbox" name="roleName" title="${role.roleName}" value="${role.roleName}">
                                  </c:forEach>
                              </div>
                              <div class="layui-btn-group"style="float: right ;padding-bottom: 20px">
                                  <button class="layui-btn">新增</button>
                                  <button id="giveButton"  type="button" data-dismiss="modal" class="layui-btn">取消</button>
                              </div>
                          </form>
                      </div>
                  </div>
              </div>
          </div>
      </div>
      <%--编辑model--%>
      <div id="myModelChange" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
          <div class="modal-dialog modal-lg" role="document">
              <div class="modal-content">
                  <p class="lead" style="color: #000000;padding-left: 220px;background-color: #3b883e" ><label style="color: white">编辑</label></p>

              </div>
          </div>
      </div>
      <!-- js -->
      <jsp:include page="../include/js.jsp"/>

  <script>
      (function(){
          if("${message}"){
              layer.msg("${message}");
          }
      })()
  </script>

  </body>
</html>
