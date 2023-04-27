<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	let msg =${msg}.trim();
	let isHistorBack = '${isHistorBack}';
	
	// falsy
	if(msg){
		alert(msg);
	}
	if(isHistorBack){
		history.back();
	}
</script>