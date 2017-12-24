<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
    <meta name="author" content="GeeksLabs">
    <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">

    <title>TMS-综合管理系统</title>
    <%--css--%>
    <jsp:include page="include/css.jsp"/>
  </head>

  <body>

      <%--头部--%>
      <jsp:include page="include/head.jsp"/>

      <!--左侧部分-->
      <jsp:include page="include/left.jsp"/>
      <%--内容部分--%>
      <section id="main-content">

          <section class="wrapper">
              <div id="container">
                  <div class="row">
                      <div class="col-md-3"></div>
                      <div class="col-md-6">
                          <div style="margin-top: 120px">
                              <div class="site-title">
                                  <fieldset>
                                      <legend>
                                          <a href="">景区用户注册</a>
                                      </legend>
                                  </fieldset>
                              </div>
                              <form class="layui-form" method="post" action="/system/scenic/account/add">
                                  <c:if test="${not empty message}">
                                      <div class="layui-bg-red" style="width: auto;height: 30px;font-size: 20px;margin-bottom: 10px"><label style="margin-left: 220px">${message}</label></div>
                                  </c:if>
                                  <c:if test="${not empty scenicAccount}">
                                      <div id="chooseMessage">
                                          <div class="layui-bg-red" style="width: auto;height: 30px;font-size: 20px;margin-bottom: 10px"><label style="margin-left: 180px">您有未完成的注册,是否继续?　　
                                              <a href="/system/scenic/add" style="margin-right: 20px" id="yesHref"><i class="fa fa-check" aria-hidden="true"></i></a>
                                              <a href="javascript:;" id="noHref"><i class="fa fa-times" aria-hidden="true"></i></a>
                                          </label></div>
                                      </div>

                                  </c:if>
                                  <div class="layui-form-item">
                                      <label class="layui-form-label">账户 :</label>
                                      <div class="layui-input-block">
                                          <input type="text" name="account" required  lay-verify="required" placeholder="请输入注册账户名" autocomplete="off" class="layui-input">
                                      </div>
                                  </div>
                                  <div class="layui-form-item">
                                      <label class="layui-form-label">密码 :</label>
                                      <div class="layui-input-block">
                                          <input type="password" id="password" name="password" required  lay-verify="required" placeholder="请输入注册密码" autocomplete="off" class="layui-input">
                                      </div>
                                  </div>
                                  <div class="layui-form-item">
                                      <label class="layui-form-label">确认 :</label>
                                      <div class="layui-input-block">
                                          <input type="password" id="makeSure" name="makeSure" required  lay-verify="required" placeholder="请确认注册密码" autocomplete="off" class="layui-input">
                                      </div>
                                  </div>
                                  <div class="layui-form-item">
                                      <div class="layui-input-block">
                                          <button class="layui-btn" lay-submit lay-filter="formDemo" id="submitBtn">下一步</button>
                                          <button type="button" class="layui-btn layui-btn-primary" id="returnBtn">取消注册</button>
                                      </div>
                                  </div>
                              </form>
                        </div>
                    </div>
                </div>
              </div>
          </section>
      </section>
      <!-- js -->
      <jsp:include page="include/js.jsp"/>
  <script>
      (function(){
          //取消注册
          $("#returnBtn").click(function(){
              window.location.href = "/system/scenic";
          });
          //验证密码 确认密码是否一致
          layui.use('form', function(){
              var form = layui.form;

              //监听提交
              form.on('submit(formDemo)', function(data){
                  //用于从对象中解析出json  parse用于从字符串中解析出json对象
                  var json = JSON.stringify(data.field);
                  //将字符串转化成json对象
                  var accountJson = (new Function("return " + json))();
                  var password = accountJson.password;
                  var makeSure = accountJson.makeSure;
                  if(password!=makeSure){
                      layer.msg("两次输入的密码不一致");
                      return false;
                  }
              });
          });
//          当用户点击不继续操作时 删除session中的数据
          $("#noHref").click(function(){
              $.ajax({
                  type:"post",
                  url:"/system/kill/session",
                  success:function(data){
                    $("#chooseMessage").html("");
                  }
              })
          })

      })()
  </script>

  </body>
</html>
