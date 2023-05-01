<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 게시판"/>
<%@ include file="../common/head.jsp" %>
	<section class="mt-8 text-xl">
		<div class="container mx-auto px-3">
			<div class="mb-2 flex justify-between items-center">
				<div class="mb-2 flex justify-between items-end">
					<div><span>총 : ${articlesCnt } 개</span></div>
					<form>
						<input type="hidden" name="boardId" value="${board.id }"/>
						<select data-value="${searchKeywordType }" class="select select-bordered" name="searchKeywordType">
							<option value="title">제목</option>
							<option value="body">내용</option>
							<option value="title,body">제목 + 내용</option>
						</select>
						<input class="ml-2 w-80 input input-bordered" name="searchKeyword" placeholder="검색어를 입력해주세요" maxlength="20" value="${searchKeyword }"/>
						<button class="ml-2 btn-text-link btn btn-active btn-ghost">검색</button>
					</form>
				</div>
					<div class="btn-group">
						<c:set var="pageMenuLen" value="5" />
						<c:set var="startPage" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" />
						<c:set var="endPage" value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount }" />
	
						<c:if test="${page == 1 }">
							<a class="btn btn-sm btn-disabled">«</a>
							<a class="btn btn-sm btn-disabled">&lt;</a>
						</c:if>
						<c:if test="${page > 1 }">
							<a class="btn btn-sm" href="?boardId=${board.id }&page=1">«</a>
							<a class="btn btn-sm" href="?boardId=${board.id }&page=${page - 1 }">&lt;</a>
						</c:if>
						<c:forEach begin="${startPage }" end="${endPage }" var="i">
							<a class="btn btn-sm ${page == i ? 'btn-active' : '' }" href="?boardId=${board.id }&page=${i }">${i }</a>
						</c:forEach>
						<c:if test="${page < pagesCount }">
							<a class="btn btn-sm" href="?boardId=${board.id }&page=${page + 1 }">&gt;</a>
							<a class="btn btn-sm" href="?boardId=${board.id }&page=${pagesCount }">»</a>
						</c:if>
						<c:if test="${page == pagesCount }">
							<a class="btn btn-sm btn-disabled">&gt;</a>
							<a class="btn btn-sm btn-disabled">»</a>
						</c:if>
					</div>
				<c:if test="${rq.getLoginedMemberId() !=0 }">
						<a href="write" class="h-full px-3 flex items-center btn btn-primary">글쓰기</a>
				</c:if>
			</div>
			<div class="table-box-type-1">
				<table class="table w-full" >
					<thead>
						<tr>
							<th>번호</th>
							<th>날짜</th>
							<th>제목</th>
							<th>작성자</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="article" items="${articles }">
							<tr class="hover">
								<td>${article.id }</td>
								<td>${article.regDate.substring(2,16) }</td>
								<td><a class = "hover:underline" href="detail?id=${article.id }">${article.title }</a></td>
								<td>${article.writerName }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</section>
<%@ include file="../common/foot.jsp" %>