package com.koreaIT.demo.controller;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreaIT.demo.service.MemberService;
import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.vo.Member;
import com.koreaIT.demo.vo.ResultData;
import com.koreaIT.demo.vo.Rq;

@Controller
public class UsrMemberController {
	
	private MemberService memberService; 
	
	@Autowired
	public UsrMemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/usr/member/join")
	public String join() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(HttpServletRequest req, String loginId, String loginPw, String loginPwChk , String name, String nickName, String cellphoneNum, String email) {
		Rq rq = (Rq) req.getAttribute("rq");
		
		if(rq.getLoginedMemberId() != 0) {
			return ResultData.from("F-1", Utility.f("로그아웃 후 이용해주세요."));
		}
		
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
		
		ResultData<Integer> doJoinRd = memberService.doJoin(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		if (doJoinRd.isFail()) {
			return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg());
		}
		
		return ResultData.from(doJoinRd.getResultCode(), doJoinRd.getMsg(), "Member", memberService.getMemberById((int) doJoinRd.getData1()));
	}
	
	@RequestMapping("/usr/member/login")
	public String login() {
		return "usr/member/login";
	}
	
	@RequestMapping("usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw) {
		Rq rq =(Rq) req.getAttribute("rq");
		
		if(rq.getLoginedMemberId() != 0) {
			return Utility.jsReplace("이미 로그인중입니다.","/");
		}
		if(Utility.empty(loginId)) {
			return Utility.jsHistoryBack("아이디를 입력해주세요.");
		}
		if(Utility.empty(loginPw)) {
			return Utility.jsHistoryBack("비밀번호를 입력해주세요.");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return Utility.jsHistoryBack(Utility.f("존재하지않는 아이디입니다.", loginId));
		}
		if(member.getLoginPw().equals(loginPw) == false) {
			return Utility.jsHistoryBack("비밀번호가 틀렸습니다.");
		}
		
		rq.login(member);
		
		return Utility.jsReplace(Utility.f("%s님 환영합니다.", member.getNickName()),"/");
	}
	
	@RequestMapping("usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {
		Rq rq =(Rq) req.getAttribute("rq");
		
		rq.logout();
		
		return Utility.jsReplace("로그아웃되었습니다.", "../home/main");
	}
}
