<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="Main" />
<%@ include file="../common/head.jsp" %>
	<section class="mt-3 ">
		<div class="container mx-auto h-screen px-3 bg-red-600">	
		<div id="map" style="width:100%; height:100%;"></div>
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=8cae663d1a02379b242bd02eb6ef6be3"></script>
		<script>
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(33.450701, 126.570667),
			level: 3
		};

		var map = new kakao.maps.Map(container, options);
		</script>
		</div>
	</section>
<%@ include file="../common/foot.jsp" %>