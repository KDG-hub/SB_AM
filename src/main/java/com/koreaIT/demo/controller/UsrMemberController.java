package com.koreaIT.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreaIT.demo.service.MemberService;
import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.vo.Member;
import com.koreaIT.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	
	private MemberService memberService; 
	
	@Autowired
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData doJoin(String loginId, String loginPw, String loginPwChk , String name, String nickName, String cellphoneNum, String email) {
		if(Utility.empty(loginId)) {
			return ResultData.from("F-1", Utility.f("아이디를 입력해주세요."));
		}
		if(Utility.empty(loginPw)) {
			return ResultData.from("F-2", Utility.f("비밀번호를 입력해주세요."));
		}
		if(Utility.empty(name)) {
			return ResultData.from("F-3", Utility.f("이름을 입력해주세요."));
		}
		if(Utility.empty(cellphoneNum)) {
			return ResultData.from("F-4", Utility.f("전화번호를 입력해주세요."));
		}
		if(Utility.empty(nickName)) {
			return ResultData.from("F-5", Utility.f("닉네임을 입력해주세요."));
		}
		if(Utility.empty(email)) {
			return ResultData.from("F-5", Utility.f("이메일을 입력해주세요."));
		}
		if(loginPw == loginPwChk) {
			return ResultData.from("F-6", Utility.f("패스워드를 확인해주세요."));
		}
		
		ResultData doJoinRd = memberService.doJoin(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		if (doJoinRd.isFail()) {
			return doJoinRd;
		}
		int id = (int) doJoinRd.getData1();
		
		return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg(),id);
	}
}
