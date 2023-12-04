<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <div>
            <form id="loginUserForm" method="post" action="<c:url value='/getUserRegist.do'/>">
                <table class="table table-bordered">
                    <colgroup>
                        <col width="20%">
                        <col width="30%">
                        <col width="20%">
                        <col width="30%">
                    </colgroup>
                    <tr>
                        <th><span>ID *:</span></th>
                        <td><input type="text" name="userId" value="" required="true"></td>
                        <th><span>PW *:</span></th>
                        <td><input type="password" name="userPw" value="" required="true"></td>
                    </tr>
                    <tr>
                        <th><span>휴대폰번호 :</span></th>
                        <td><input type="text" name="celph" value=""></td>
                        <th><span>이메일 :</span></th>
                        <td><input type="email" name="email" value=""></td>
                    </tr>
                </table>
                <input type="hidden" name="paging" value="0">
                <button class="btn btn-success" type="submit" id="login">회원가입</button>
                <a href='<c:url value="/userLogin.do"/>' class="btn btn-warning">돌아가기</button></a>
            </form>
        <div>
    </div>
</body>
<script>
    var userLoginPage = (function(){
        var init = function(){
            console.log("User Login Page Document Is Ready");
        };

        var registerEvent = function(){
            $("#reset").click(function(){
                $("#joinUserForm")[0].reset();
            });
        };

        return {
            init: function(){
                init();
                registerEvent();
            }
        }
    }());

    $(document).ready(e=>{
        joinPage.init();
    });
</script>
</html>
