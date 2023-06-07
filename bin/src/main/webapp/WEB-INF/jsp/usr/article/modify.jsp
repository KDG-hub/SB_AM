<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="detail" />
<%@ include file="../common/head.jsp" %>
	<section class="mt-8 text-xl">
		<div class="container mx-auto px-3">
		<form action="doModify" method="POST">
			<input type="hidden" name="id" value="${article.id }"/>
			<div class="table-box-type-1">
				<table class="table table-zebra w-full">
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
						<td><input class="input input-bordered w-full max-w-xs" type="text" name ="title" value="${article.title }"/></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea class="textarea textarea-bordered w-full " name="body" >${article.body }</textarea></td>
					</tr>
				</table>
				<div class="flex justify-end">
						<button class="h-full px-3 flex items-center btn btn-primary">작성</button>
				</div>
			</div>
		</form>
				<button lass="h-full px-3 flex items-center btn btn-primary" type="button" onclick="history.back();">뒤로가기</button>&nbsp;
			</div>
		</div>
	</section>
<%@ include file="../common/foot.jsp" %>