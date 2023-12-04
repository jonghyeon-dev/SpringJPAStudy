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
            <p>${errorCode}</p>
            <p>${errorMsg}</p>
        <div>
            <a href="<c:url value='/main.do'/>" class="btn btn-primary">홈으로</button>
    </div>
</body>
<script>
    var joinPage = (function(){
        var init = function(){
            console.log("Error Page Document Is Ready");
        };

        var registerEvent = function(){

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
