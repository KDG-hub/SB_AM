package com.koreaIT.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreaIT.demo.repository.MemberRepository;
import com.koreaIT.demo.util.Utility;
import com.koreaIT.demo.vo.Member;
import com.koreaIT.demo.vo.ResultData;

@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	
	@Autowired
	public MemberService (MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public ResultData doJoin(String loginId, String loginPw, String name, String nickName, String cellphoneNum, String email) {

		Member existsMember = memberRepository.getMemberByLoginId(loginId);
		
		if (existsMember != null) {
			return ResultData.from("F-7",Utility.f("%s는 존재하는 아이디입니다.", loginId));
		}
		
		existsMember = memberRepository.getMemberByNickName(nickName);
		if (existsMember != null) {
			return ResultData.from("F-8",Utility.f("%s는 존재하는 닉네임입니다.", nickName));
		}
		
		existsMember = memberRepository.getMemberByNameAndEmail(name,email);
		if (existsMember != null) {
			return ResultData.from("F-9",Utility.f("이미 사용중인 이름(%s)와 이메일(%s)입니다.", name, email));
		}
		
		memberRepository.doJoin(loginId, loginPw, name, nickName, cellphoneNum, email);
		
		int id = getLastInsertId();
		
		return ResultData.from("S-1",Utility.f("%s님 환영합니다.", nickName),id);
	}

	public int getLastInsertId() {
		return memberRepository.getLastInsertId();
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}
}
