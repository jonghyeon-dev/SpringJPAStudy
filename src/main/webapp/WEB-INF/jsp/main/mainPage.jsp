<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <%@include file="../layouts/header.jsp"%> 
</head>
<body class="bg-light">
    <div class="container">
        <div class="nav nav-black"> 
              <div class="headTitle"><h2>테스트</h2></div>
              <ul class="navBtn">
                <li><a href="<c:url value='/main.do'/>">홈</a></li>
                <!-- <li><a href="#">테스트</a></li> -->
              </ul>
              <a class="btn btn-success float-right" href='<c:url value="/userLogout.do"/>'>로그아웃</a>
        </div>
        <div>
            <form class="searchForm" id="searchUserForm">
                <table class="table table-bordered searchTable text-center">
                    <colgroup>
                        <col width="15%">
                        <col width="25%">
                        <col width="15%">
                        <col width="25%">
                        <col width="20%">
                    </colgroup>
                    <tr>
                        <th class="table-success"><span class="form-label">번호 :</span></th>
                        <td><input class="form-control" onKeypress="return enterBtnClick(event,'getUserInfo')"  type="number" name="seq" value=""></td>
                        <th class="table-success"><span class="form-label">ID :</span></th>
                        <td><input class="form-control" onKeypress="return enterBtnClick(event,'getUserInfo')" type="number" name="eno" value=""></td>
                        <td>
                            <button class="btn btn-primary searchBtn" type="button" id="getUserInfo">검색</button>
                            <button class="btn btn-primary searchReset" type="button" id="searchReset">검색초기화</button>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="paging" value="0">
                <div style="float:right;">
                    <a class="btn btn-success" href='<c:url value="/userJoin.do"/>'>등록하기</a>
                </div>
            </form>
        </div>
        <div>
            <table class="table table-bordered table-hover">
                <thead>
                    <tr class="text-center">
                        <th class="table-info">번호</th>
                        <th class="table-info">ID</th>
                        <th class="table-info">휴대폰번호</th>
                        <th class="table-info">E-MAIL</th>
                        <th class="table-info">생성일자</th>
                        <th class="table-info">생성시간</th>
                        <th class="table-info">변경일자</th>
                        <th class="table-info">변경시간</th>
                    </tr>
                </thead>
                <tbody id="tbodyUserInfoList">
                    <c:forEach items="${userInfoList.content}" var="items">
                    <tr>
                        <td><c:out value="${items.seq}"/></td>
                        <td><c:out value="${items.eno}"/></td>
                        <td><c:out value="${items.celph}"/></td>
                        <td><c:out value="${items.email}"/></td>
                        <td><c:out value="${items.cretDt}"/></td>
                        <td><c:out value="${items.cretTm}"/></td>
                        <td><c:out value="${items.chgDt}"/></td>
                        <td><c:out value="${items.chgTm}"/></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <nav aria-label="Page navigation example">
                <ul class="pagination" style="justify-content: center;" id="userInfoPagination">
                    <c:set var="totalPage" value="${userInfoList.totalPages}"/>
                    <c:choose>
                        <c:when test="${totalPage > 10}">
                            <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>
                            <li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="0">1</a></li>
                            <c:forEach var="cnt" begin="2" end="10" step="1">
                                <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                            </c:forEach>
                            <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>
                            <c:forEach var="cnt" begin="1" end="${totalPage}" step="1">
                                <c:choose>
                                    <c:when test="${cnt eq 1}">
                                        <li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="${cnt-1}">${cnt}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${totalPage eq 1}">
                                    <li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </div>
    </div>
</body>
<script>
    var mainPage = (function(){
        var init = function(){
            console.log("Main Page Document Is Ready");
        };

        var searchUserInfo = function(seq,eno,paging){
            $.ajax({
                url: '<c:url value="getUserInfo.do"/>',
                type: "GET",
                dataType: "json",
                data: {"seq":seq,
                        "eno":eno,
                        "paging":paging
                    },
                success: response=>{
                    if(response.succeed){
                        let data = response.data.content;
                        let dataText = "";
                        let totalPages = response.data.totalPages;
                        let pageText = "";
                        for(i=0;i<data.length;i++){
                                dataText = dataText + "<tr>";
                                dataText = dataText + "<td>"+data[i].seq+"</td>";
                                dataText = dataText + "<td>"+data[i].eno+"</td>";
                                dataText = dataText + "<td>"+data[i].celph+"</td>";
                                dataText = dataText + "<td>"+data[i].email+"</td>";
                                dataText = dataText + "<td>"+data[i].cretDt+"</td>";
                                dataText = dataText + "<td>"+data[i].cretTm+"</td>";
                                dataText = dataText + "<td>"+data[i].chgDt+"</td>";
                                dataText = dataText + "<td>"+data[i].chgTm+"</td>";
                                dataText = dataText + "<tr>";
                        }
                        if(data.length<10){
                            for(i=0;i<(10 - data.length);i++){
                                dataText = dataText + "<tr>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<td>&nbsp;</td>";
                                dataText = dataText + "<tr>";
                            }
                        }
                        $("#tbodyUserInfoList").empty();
                        if(dataText != ""){
                            $("#tbodyUserInfoList").append(dataText);
                        }

                        // paging 처리
                        if(Number(paging)>0){
                            pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>'
                        }else{
                            pageText = pageText + '<li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="prev">&laquo;</a></li>'
                        }
                        if(totalPages > 10){
                            if(Number(paging)<5){
                                for(page=0;page<10;page++){
                                    if(Number(paging)===page){
                                        pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }else{
                                        pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }
                                }
                            }else if(Number(paging) >= (Number(totalPages) - 5)){
                                for(page=(Number(totalPages) - 10);page<Number(totalPages);page++){
                                    if(Number(paging)===page){
                                        pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }else{
                                        pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }
                                }
                            }else{
                                for(page=Number(paging)-5;page<(5+Number(paging));page++){
                                    if(Number(paging)===page){
                                        pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }else{
                                        pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }
                                }
                            }
                        }else{
                            for(page=0;page<Number(totalPages);page++){
                                    if(Number(paging)===page){
                                        pageText = pageText + '<li class="page-item active"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }else{
                                        pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="'+Number(page)+'">'+(Number(page)+1)+'</a></li>';
                                    }
                                }
                        }
                        if(Number(paging) < (Number(totalPages)-1)){
                            pageText = pageText + '<li class="page-item"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>'
                        }else{
                            pageText = pageText + '<li class="page-item disabled"><a class="page-link" href="#"><input type="hidden" name="page" value="next">&raquo;</a></li>'
                        }
                        $("#userInfoPagination").empty();
                        if(pageText != null){
                            $("#userInfoPagination").append(pageText);
                            $("#userInfoPagination li:not(.disabled.active)").on("click",function(){
                                let selectedPage=$(this).find("input[name='page']").val();
                                let oldData;
                                if(selectedPage === "prev"){
                                    $("#userInfoPagination li").each(function(){
                                        let pageData = $(this).find("input[name='page']").val();
                                        if(oldData === "prev"){
                                            if(Number(pageData) === 0){
                                                selectedPage = pageData;
                                            }else{
                                                selectedPage = Number(pageData)-1;
                                            }
                                            return false;
                                        }else{
                                            oldData = pageData;
                                        }
                                    });
                                }else if(selectedPage === "next"){
                                    $("#userInfoPagination li").each(function(){
                                        let pageData = $(this).find("input[name='page']").val();
                                        if(pageData === "next"){
                                            if(Number(oldData) === (Number(totalPages)-1)){
                                                selectedPage = Number(oldData);
                                            }else{
                                                selectedPage = Number(oldData)+1;
                                            }
                                            return false;
                                        }else{
                                            oldData = pageData;
                                        }
                                    });
                                }
                                searchUserInfo(seq,eno,selectedPage);
                            })
                        }
                        
                    }
                },
                error: e=>{
                    console.log("Ajax Get Data Error :: ", e);
                }
            })
        }

        var registerEvent = function(){
            $("#searchReset").click(e=>{
                $("#searchUserForm")[0].reset();
            });

            $("#getUserInfo").click(e=>{
                let seq=$("#searchUserForm input[name='seq']").val();
                let eno=$("#searchUserForm input[name='eno']").val();
                searchUserInfo(seq,eno,0);
            });

            $("#userInfoPagination li:not(.disabled.active)").on("click",function(){
                let seq=$("#searchUserForm input[name='seq']").val();
                let eno=$("#searchUserForm input[name='eno']").val();
                let selectedPage=$(this).find("input[name='page']").val();
                let totalPages = '<c:out value="${userInfoList.totalPages}"/>';
                if(selectedPage === "next"){
                    if(Number(totalPages) > 10){
                        selectedPage = 6;
                    }else{
                        selectedPage = Number(totalPages)-1;
                    }
                }
                searchUserInfo(seq,eno,selectedPage);
            })

        };
        return {
            init: function(){
                init();
                registerEvent();
            }
        }
    }());

    $(document).ready(e=>{
        mainPage.init();
    });

    function enterBtnClick(event, id){
        if (event.which === 13) {
            $('#' + id).trigger('click');
            return false;
        }
    }
</script>
</html>