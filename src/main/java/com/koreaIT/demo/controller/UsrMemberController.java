package com.koreaIT.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreaIT.demo.service.MemberService;
import com.koreaIT.demo.vo.Member;

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
		if(loginId == null || loginId.trim().length() == 0) {
			return "아이디를 입력해주세요";
		}
		if(loginPw == null || loginPw.trim().length() == 0) {
			return "비밀번호를 입력해주세요";
		}
		if(name == null || name.trim().length() == 0) {
			return "이름을 입력해주세요";
		}
		if(cellphoneNum == null || cellphoneNum.trim().length() == 0) {
			return "전화번호를 입력해주세요";
		}
		if(nickName == null || nickName.trim().length() == 0) {
			return "닉네임을 입력해주세요";
		}
		if(email == null || email.trim().length() == 0) {
			return "이메일을 입력해주세요";
		}
		if(loginPw == loginPwChk) {
			return "비밀번호를 확인해주세요";
		}
		
		memberService.doJoin(loginId, loginPw, name, nickName, cellphoneNum, email);
			
		int id = memberService.getLastInsertId();
		
		Member member = memberService.getMemberById(id);
			
		return member.getNickName() + "님 환영합니다.";
	}
}
