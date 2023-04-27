package com.koreaIT.demo.vo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.Getter;

public class Rq {

	@Getter
	private int loginedMemberId;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession httpSession;

	public Rq(HttpServletRequest req, HttpServletResponse resp) {
		
		this.req = req;
		this.resp = resp;
		this.httpSession = req.getSession();
		
		int loginedMemberId = 0;
		
		if (httpSession.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}
		
		this.loginedMemberId = loginedMemberId;
	}

	public void login(Member member) {
		httpSession.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		httpSession.removeAttribute("loginedMemberId");
	}

	public String jsReturnOnView(String msg, boolean isHistorBack) {
		req.setAttribute("msh", msg);
		req.setAttribute("isHistorBack", isHistorBack);
		return "usr/common/js";
	}

	/*
	 * public void jsPrintHistoryBack(String msg) {
	 * resp.setContentType("text/html; charset=UTF-8;");
	 * 
	 * print(Utility.jsHistoryBack(msg)); }
	 * 
	 * private void print(String str) { try { resp.getWriter().append(str); } catch
	 * (IOException e) { e.printStackTrace(); } }
	 */

}