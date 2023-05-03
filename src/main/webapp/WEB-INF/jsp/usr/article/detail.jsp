<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="detail" />
<%@ include file="../common/head.jsp" %>
<script>
	const params = {};
	params.id = parseInt('${param.id}');
	
	function ArticleDetail_increaseViewCount() {
		$.get('doIncreaseViewCount', {
			id : params.id
		}, function(data){
			$('#articleDetail_increaseViewCount').empty().html(data.data1);
		}, 'json')
	}
	
	$(function(){
		//실전코드
		ArticleDetail_increaseViewCount();
		

	})
	
</script>

	<section class="mt-8 text-xl">
		<div class="container mx-auto px-3">
			<div class="table-box-type-1">
				<table class="table w-full table-zebra " >
					<colgroup>
						<col width="200"/>
					</colgroup>
					<tr>
						<th>번호</th>
						<td>${article.id }</td>
					</tr>
					<tr>
						<th>작성날짜</th>
						<td>${article.regDate }</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${article.writerName }</td>
					</tr>
					<tr>
						<th>제목</th>
						<td>${article.title }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${article.body }</td>
					</tr>
					<tr>
						<th>조회수</th>
						<td><span class="badge" id="articleDetail_increaseViewCount">${article.viewCnt }</span></td>
					</tr>
				</table>
			</div>
			<div class="mt-8" >
				<button type="button" onclick="history.back();">뒤로가기</button>&nbsp;
				<c:if test="${article.actorCanChangeData }">
					<a class="btn-text-link" href="modify?id=${article.id }">수정</a>&nbsp;
					<a class="btn-text-link" href="doDelete?id${article.id }" onclick ="if(confirm("정말 삭제하시겠습니까?") == false) return false;" >삭제</a>
				</c:if>
			</div>
		</div>
	</section>
<%@ include file="../common/foot.jsp" %>