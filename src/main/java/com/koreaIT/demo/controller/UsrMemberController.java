package com.koreaIT.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreaIT.demo.service.MemberService;

@Controller
public class UsrMemberController {
	
	private MemberService memberService; 
	
	@Autowired
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String loginPwChk , String name, String nickName, String cellphoneNum, String email) {
		 if(memberService.idDupChk(loginId) == 1) {
			 return loginId + "는 중복된 아이디입니다.";
		 }
		 if(loginPw != loginPwChk) {
			 return "비밀번호를 다시 확인해주세요.";
		 }
		
		memberService.doJoin(loginId, loginPw, name, nickName, cellphoneNum, email);
		return nickName + "회원님 환영합니다.";
	}
}
