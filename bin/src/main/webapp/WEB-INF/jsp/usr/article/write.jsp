<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="write" />
<%@ include file="../common/head.jsp" %>
	<section class="mt-8 text-xl">
		<div class="container mx-auto px-3">
			<form action="doAdd" method="POST">
				<div class="table-box-type-1 ">
					<table class="table table-zebra w-full ">
						<colgroup>
							<col width="200"/>
						</colgroup>
						<tr>
							<th>게시판</th>
							<td><select name="boardId">
									<option value="1">자유게시판</option>
									<option value="2">공지사항</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td><input class="input input-bordered w-full"type="text" name ="title""/></td>
						</tr>
						<tr>
							<th>내용</th>
							<td><textarea class="textarea textarea-bordered w-full" name="body" ></textarea></td>
						</tr>
					</table>
					<div class="flex justify-end">
						<button class="h-full px-3 flex items-center btn btn-primary">작성</button>
					</div>
				</div>
			</form>
				<button class="h-full px-3 flex items-center btn btn-primary" type="button" onclick="history.back();">뒤로가기</button>&nbsp;
		</div>
	</section>
<%@ include file="../common/foot.jsp" %>