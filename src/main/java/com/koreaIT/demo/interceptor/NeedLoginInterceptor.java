package com.koreaIT.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.vo.Rq;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setContentType("text/html; charset=UTF-8;");
		Rq rq = (Rq) request.getAttribute("Rq");
		
		if(rq.getLoginedMemberId() == 0) {
			response.getWriter().append(Utility.jsHistoryBack("로그인 후 이용해주세요."));
			return false;
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

}
