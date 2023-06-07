<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Login" />
<%@ include file="../common/head.jsp" %>
	<section class="mt-8 text-xl">
		<div class="container mx-auto px-3">
			<form action="doJoin" method="POST">
				<div class="table-box-type-1">
					<table class="table table-zebra w-full">
						<colgroup>
							<col width="200" />
						</colgroup>
						<tbody>
							<tr>
								<th>로그인 아이디</th>
								<td><input class="w-96" type="text" name="loginId" placeholder="아이디를 입력해주세요"/></td>
							</tr>
							<tr>
								<th>로그인 비밀번호</th>
								<td><input class="w-96" type="text" name="loginPw" placeholder="비밀번호를 입력해주세요"/></td>
							</tr>
							<tr>
								<th>비밀번호 확인</th>
								<td><input class="w-96" type="text" name="loginPw" placeholder="비밀번호를 입력해주세요"/></td>
							</tr>
							<tr>
								<th>이름</th>
								<td><input class="w-96" type="text" name="name" placeholder="이름을 입력해주세요"/></td>
							</tr>
							<tr>
								<th>닉네임</th>
								<td><input class="w-96" type="text" name="nickName" placeholder="닉네임을 입력해주세요"/></td>
							</tr>
							<tr>
								<th>이메일</th>
								<td><input class="w-96" type="text" name="email" placeholder="이메일을 입력해주세요"/></td>
							</tr>
							
							<tr>
								<td colspan="2"><button>회원가입</button></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			<div class="btns">
				<button class="btn-text-link" type="button" onclick="history.back();">뒤로가기</button>
			</div>
		</div>
	</section>
<%@ include file="../common/foot.jsp" %>