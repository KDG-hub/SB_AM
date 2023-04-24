<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>
	<h1>게시물 리스트</h1>
	<div>
		<a href="/">로고</a>
		
		<ul>
			<li><a href="/">HOME</a></li>
			<li><a href="/usr/article/list">LIST</a></li>
		</ul>
	</div>
	
	<table border = "1">
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>작성자</th>
				<th>제목</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="article" items="${articles }">
				<tr>
					<td>${article.id }</td>
					<td>${article.regDate }</td>
					<td><a href="detail?id=${article.id }">${article.memberId }</a></td>
					<td>${article.title }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>